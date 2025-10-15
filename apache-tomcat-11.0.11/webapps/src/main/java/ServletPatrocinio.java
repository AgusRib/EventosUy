import java.io.IOException;
import java.util.Set;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import excepciones.NombreInstiExistente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import logica.controllers.IControllerEvento;
import logica.controllers.IControllerUsuario;
import logica.dataTypes.DTPatrocinio;
import logica.dataTypes.DataUsuario;
import logica.dataTypes.DTTipoRegistro;
import logica.enumerators.NivelPatrocinio;
import logica.models.Factory;

/**
 * Servlet implementation class ServletPatrocinio
 */
@WebServlet({"/altaInstitucion","/altaPatrocinio"})
@MultipartConfig
public class ServletPatrocinio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPatrocinio() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path = request.getServletPath();
    	String nombreEdicion = request.getParameter("nombreEdicion");
    	request.getSession().setAttribute("nombreEdicion", nombreEdicion);
    	
    	IControllerEvento ICE = Factory.getInstance().getControllerEvento();
    	switch(path) {
        case "/detallePatrocinio":
            try {
                String nombreInstitucion = request.getParameter("nombreInstitucion");
                DTPatrocinio patrocinio = ICE.obtenerPatrocinio(nombreEdicion, nombreInstitucion);
                request.setAttribute("patrocinio", patrocinio);
                request.getRequestDispatcher("/WEB-INF/pages//detallePatrocinio.jsp").forward(request, response);
            } catch (Exception e) {
                
                request.setAttribute("error", "Error al obtener detalles del patrocinio: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/pages//error.jsp").forward(request, response);
            }
            break;
            
        case "/altaInstitucion":
        	
        	request.setAttribute("mensaje", null);
        	request.setAttribute("error", null);
        	request.getRequestDispatcher("/WEB-INF/pages/altaInstitucion.jsp").forward(request, response);
             break;
            
        case "/altaPatrocinio":
        	IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();
            request.getSession().setAttribute("nombreEdicion", nombreEdicion);
            try {
                Set<String> instituciones = ICU.listarInstituciones();
                request.setAttribute("instituciones", instituciones);
               
                try {
                    if (nombreEdicion != null && !nombreEdicion.isEmpty()) {
                        Set<String> tipos = ICE.listarTiposDeRegistro(nombreEdicion);
                        request.setAttribute("tiposRegistro", tipos);
                    }
                } catch (Exception e) {
                   
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
          
            Object sessFecha = request.getSession().getAttribute("fecha");
            if (sessFecha != null) {
                request.setAttribute("fecha", sessFecha.toString());
            } else {
                request.setAttribute("fecha", null);
            }
            request.getRequestDispatcher("/WEB-INF/pages//altaPatrocinio.jsp").forward(request, response);
            break;
    }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        IControllerEvento ICE = Factory.getInstance().getControllerEvento();
        IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();
    

        switch(path) {

            case "/altaPatrocinio": {
                String nombreEdi = request.getParameter("edicion");
           
                if (nombreEdi == null || nombreEdi.isEmpty()) {
                    Object s = request.getSession().getAttribute("nombreEdicion");
                    if (s != null) nombreEdi = s.toString();
                }
              
                System.out.println("DEBUG ICE impl: " + (ICE != null ? ICE.getClass().getName() : "<null-controller>") + ", nombreEdi='" + nombreEdi + "'");
                String institucion = request.getParameter("institucion");
                NivelPatrocinio nivel = null;
                try { nivel = NivelPatrocinio.valueOf(request.getParameter("nivelPatrocinio")); } catch (Exception ex) { /* keep null */ }
                double aporteEconomico = 0.0;
                try { aporteEconomico = Double.parseDouble(request.getParameter("aporte")); } catch (Exception ex) { aporteEconomico = 0.0; }
                String tipoRegistroGratis = request.getParameter("tipoRegGratis");
                int cantidadGratis = 0;
                try { cantidadGratis = Integer.parseInt(request.getParameter("cantGratis")); } catch (Exception ex) { cantidadGratis = 0; }
                String codigo = request.getParameter("codigo");
             
                
                ICE.setFechaSistema((LocalDate)request.getSession().getAttribute("fecha"));

                
                request.setAttribute("edicion", nombreEdi);
                request.setAttribute("institucion", institucion);
                request.setAttribute("nivelPatrocinio", request.getParameter("nivelPatrocinio"));
                request.setAttribute("aporte", request.getParameter("aporte"));
                request.setAttribute("tipoRegGratis", tipoRegistroGratis);
                request.setAttribute("cantGratis", request.getParameter("cantGratis"));
                request.setAttribute("codigo", codigo);

                // Basic context and auth check
                HttpSession session = request.getSession();
                DataUsuario user = (DataUsuario) session.getAttribute("usuario");
                if (user == null || user.getTipo() != DataUsuario.TipoUsuario.ORGANIZADOR) {
                    request.setAttribute("error", "Debe iniciar sesión como organizador para registrar un patrocinio.");
                    request.getRequestDispatcher("/WEB-INF/pages/altaPatrocinio.jsp").forward(request, response);
                    break;
                }

                try {
                    // Server-side validations that cannot be bypassed from the JSP
                    // 1) There must be tipos de registro for the edition
                    Set<String> tiposDisponibles = null;
                    try {
                        if (nombreEdi != null && !nombreEdi.isEmpty()) tiposDisponibles = ICE.listarTiposDeRegistro(nombreEdi);
                    } catch (Exception ignore) { }
                    if (tiposDisponibles == null || tiposDisponibles.isEmpty()) {
                        request.setAttribute("error", "No existen tipos de registro para la edición seleccionada. No es posible registrar un patrocinio hasta que exista al menos un tipo de registro para la edición.");
                        try { request.setAttribute("instituciones", ICU.listarInstituciones()); } catch (Exception e) { }
                        request.setAttribute("tiposRegistro", tiposDisponibles);
                        request.getRequestDispatcher("/WEB-INF/pages/altaPatrocinio.jsp").forward(request, response);
                        break;
                    }

                    // 2) All form fields are required (server-side): institucion, nivelPatrocinio, aporte, tipoRegGratis, cantGratis, codigo
                    String aporteParam = request.getParameter("aporte");
                    String tipoRegGratisParam = request.getParameter("tipoRegGratis");
                    String cantGratisParam = request.getParameter("cantGratis");
                    String codigoParam = request.getParameter("codigo");
                    if (institucion == null || institucion.trim().isEmpty() || nivel == null || aporteParam == null || aporteParam.trim().isEmpty() || tipoRegGratisParam == null || tipoRegGratisParam.trim().isEmpty() || cantGratisParam == null || cantGratisParam.trim().isEmpty() || codigoParam == null || codigoParam.trim().isEmpty()) {
                        request.setAttribute("error", "Todos los campos del formulario son obligatorios.");
                        try { request.setAttribute("instituciones", ICU.listarInstituciones()); } catch (Exception e) { }
                        try { request.setAttribute("tiposRegistro", ICE.listarTiposDeRegistro(nombreEdi)); } catch (Exception e) { }
                        request.getRequestDispatcher("/WEB-INF/pages/altaPatrocinio.jsp").forward(request, response);
                        break;
                    }

                    // 3) The cantidad de registros gratuitos cannot exceed the cupo of the selected tipo (checked in servlet)
                    try {
                        if (tipoRegGratisParam != null && !tipoRegGratisParam.isEmpty()) {
                            DTTipoRegistro detalleTipo = ICE.verDetalleTRegistro(nombreEdi, tipoRegGratisParam);
                            if (detalleTipo != null) {
                                int cupo = detalleTipo.getCupo();
                                if (cantidadGratis > cupo) {
                                    request.setAttribute("error", "La cantidad de registros gratuitos (" + cantidadGratis + ") no puede ser mayor al cupo del tipo de registro escogido (" + cupo + ").");
                                    try { request.setAttribute("instituciones", ICU.listarInstituciones()); } catch (Exception e) { }
                                    try { request.setAttribute("tiposRegistro", ICE.listarTiposDeRegistro(nombreEdi)); } catch (Exception e) { }
                                    request.getRequestDispatcher("/WEB-INF/pages/altaPatrocinio.jsp").forward(request, response);
                                    break;
                                }
                            }
                        }
                    } catch (Exception ignore) { }

                    boolean existe = false;
                    try {
                       
                        System.out.println("DEBUG: comprobando existencia de patrocinio por obtenerPatrocinio(edicion, institucion)");
                        System.out.println("DEBUG institucion recibida='" + institucion + "', nombreEdi='" + nombreEdi + "'");
                        if (institucion != null && !institucion.trim().isEmpty()) {
                            DTPatrocinio dtp = null;
                            try {
                                dtp = ICE.obtenerPatrocinio(nombreEdi, institucion);
                            } catch (Exception e) {
                                System.out.println("DEBUG obtenerPatrocinio lanzó excepción: " + e.getMessage());
                            }
                            if (dtp != null) {
                                System.out.println("DEBUG: obtenerPatrocinio devolvió un patrocinio (no null)");
                                existe = true;
                            } else {
                                System.out.println("DEBUG: obtenerPatrocinio devolvió null => no existe patrocinio");
                            }
                        } else {
                            System.out.println("DEBUG: institucion vacia, no chequeamos existencia");
                        }
                    } catch (Exception ex) {
                        System.out.println("DEBUG listarPatrocinios: excepción al listar => " + ex.getMessage());
                    }

                    
                    boolean excedePorcentaje = false;
                    float costoTipo = 0.0f;
                    try {
                        if (tipoRegistroGratis != null && !tipoRegistroGratis.isEmpty()) {
                            DTTipoRegistro dttr = ICE.verDetalleTRegistro(nombreEdi, tipoRegistroGratis);
                            if (dttr != null) costoTipo = dttr.getCosto();
                        }
                    } catch (Exception ignore) { }

                    double costoTotalGratis = costoTipo * (double) cantidadGratis;
                    if (aporteEconomico <= 0.0 && cantidadGratis > 0) {
                        excedePorcentaje = true;
                    } else if (aporteEconomico > 0.0) {
                        if (costoTotalGratis > 0.2 * aporteEconomico) excedePorcentaje = true;
                    }

                    if (existe) {
                        request.setAttribute("error", "Ya existe un patrocinio de la institución '" + institucion + "' para la edición '" + nombreEdi + "'. Puede editarlo o cancelar.");
                        request.setAttribute("permitirEditar", true);

                        try { request.setAttribute("instituciones", ICU.listarInstituciones()); } catch (Exception e) { }
                        try { request.setAttribute("tiposRegistro", ICE.listarTiposDeRegistro(nombreEdi)); } catch (Exception e) { }
                        request.getRequestDispatcher("/WEB-INF/pages/altaPatrocinio.jsp").forward(request, response);
                        break;
                    } else if (excedePorcentaje) {
                        request.setAttribute("error", "El costo de los registros gratuitos ("+costoTotalGratis+") supera el 20% del aporte económico.");
                        request.setAttribute("permitirEditar", true);
                        try { request.setAttribute("instituciones", ICU.listarInstituciones()); } catch (Exception e) { }
                        try { request.setAttribute("tiposRegistro", ICE.listarTiposDeRegistro(nombreEdi)); } catch (Exception e) { }
                        request.getRequestDispatcher("/WEB-INF/pages/altaPatrocinio.jsp").forward(request, response);
                        break;
                    } else {
                     
                        try {
                            ICE.altaPatrocinio(nombreEdi, institucion, nivel, aporteEconomico, tipoRegistroGratis, cantidadGratis, codigo);
                           
                            try {
                                String target = request.getContextPath() + "/altaPatrocinio";
                                boolean hasParam = false;
                                if (nombreEdi != null && !nombreEdi.isEmpty()) {
                                    target += "?nombreEdicion=" + URLEncoder.encode(nombreEdi, "UTF-8");
                                    hasParam = true;
                                }
                                String mensajeExito = "Patrocinio registrado con éxito.";
                                if (hasParam) {
                                    target += "&mensaje=" + URLEncoder.encode(mensajeExito, "UTF-8");
                                } else {
                                    target += "?mensaje=" + URLEncoder.encode(mensajeExito, "UTF-8");
                                }
                                response.sendRedirect(target);
                                return;
                            } catch (UnsupportedEncodingException uee) {
                                // fallback: redirect without encoded params
                                String target = request.getContextPath() + "/altaPatrocinio";
                                if (nombreEdi != null && !nombreEdi.isEmpty()) {
                                    target += "?nombreEdicion=" + nombreEdi;
                                }
                                // append plain message param
                                if (target.contains("?")) target += "&mensaje=Patrocinio registrado con éxito.";
                                else target += "?mensaje=Patrocinio registrado con éxito.";
                                response.sendRedirect(target);
                                return;
                            }
                        } catch (Exception e) {
                            request.setAttribute("error", "Error al registrar el patrocinio: " + e.getMessage());
                            try { request.setAttribute("instituciones", ICU.listarInstituciones()); } catch (Exception ex) { }
                            request.getRequestDispatcher("/WEB-INF/pages/altaPatrocinio.jsp").forward(request, response);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Error procesando el alta de patrocinio: " + e.getMessage());
                    request.getRequestDispatcher("/WEB-INF/pages/altaPatrocinio.jsp").forward(request, response);
                }
                break;
            }

            case "/altaInstitucion": {
               
                String nombreInsti = request.getParameter("nombre");
                String desc = request.getParameter("descripcion");
                String web = request.getParameter("url");
                Part imagenInsti = null;
                try { imagenInsti = request.getPart("imagen"); } catch (Exception e) {  }

                try {
                    ICU.altaInstitucion(nombreInsti, desc, web);
                 
                   
                      
                            ManejadorArchivos.guardarArchivo(imagenInsti, nombreInsti, "instituciones", getServletContext());
                        
                    
                    request.setAttribute("mensaje", "Institución creada exitosamente.");
                    request.setAttribute("error", null);
                    request.setAttribute("nombre", nombreInsti);
                    request.setAttribute("descripcion", desc);
                    request.setAttribute("url", web);
                    request.getRequestDispatcher("/WEB-INF/pages/altaInstitucion.jsp").forward(request, response);
                } catch (NombreInstiExistente e) {
                    request.setAttribute("error", "El nombre de la institucion ya existe");
                 
                    request.setAttribute("nombre", nombreInsti);
                    request.setAttribute("descripcion", desc);
                    request.setAttribute("url", web);
                    request.getRequestDispatcher("/WEB-INF/pages/altaInstitucion.jsp").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Error al crear la institución: " + e.getMessage());
                    request.setAttribute("nombre", nombreInsti);
                    request.setAttribute("descripcion", desc);
                    request.setAttribute("url", web);
                    request.getRequestDispatcher("/WEB-INF/pages/altaInstitucion.jsp").forward(request, response);
                }
                break;
            }

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
}
