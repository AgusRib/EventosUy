import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.controllers.IControllerEvento;
import logica.controllers.IControllerUsuario;
import logica.dataTypes.DTDetalleEdicion;
import logica.dataTypes.DTPatrocinio;
import logica.dataTypes.DTTipoRegistro;
import logica.dataTypes.DataUsuario;
import logica.dataTypes.DataUsuario.TipoUsuario;
import logica.models.Factory;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import excepciones.FechaInicioPOSTFINAL;
import excepciones.FechaInicioPREALTA;
import excepciones.NombreEdicionExistenteExcepcion;

@MultipartConfig
@WebServlet({ "/detalleEdicion", "/altaEdicion", "/altaRegistro", "/listarEdiciones", "/detalleEdicion/altaEdicion" })
public class ServletEdicion extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEdicion() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Use servletPath to determine which mapping was called
        String path = request.getServletPath();
        
        switch (path) {
            case "/detalleEdicion": {  // EJEMPLO: /detalleEdicion?nombre=edicion1
            	// TODO Manejar excepciones y mostrar mensajes de error en la JSP
            	
            	IControllerEvento ICE = (IControllerEvento) Factory.getInstance().getControllerEvento();
                String nombre = request.getParameter("nombre");
                
                try {
                	// Fetch detalle de edicion
					DTDetalleEdicion ed = ICE.mostrarDetallesEdicion(nombre);
	                request.setAttribute("edicion", ed);
	                
	                // Fetch tipos de Registro
	                Set<DTTipoRegistro> tiposReg = new HashSet<>();
	                for (String tr : ICE.listarTiposDeRegistro(nombre)) {
	                	tiposReg.add(ICE.verDetalleTRegistro(nombre, tr));
	                }
	                request.setAttribute("tiposRegistro", tiposReg);

	                // Fetch Patrocinios
	                Set<DTPatrocinio> setPatrocinios = new HashSet<>();
	                for (String patrocinio : ICE.listarPatrocinios(nombre)) {
	                	setPatrocinios.add(ICE.obtenerPatrocinio(nombre, patrocinio));
	                }
	                request.setAttribute("patrocinios", setPatrocinios);
	                
	                // Fetch imagen de edicion
	                String edicionImg = ManejadorArchivos.buscarArchivo(nombre.toLowerCase(), getServletContext().getRealPath("/uploads/ediciones/"));
	                if (edicionImg != null) {
	                	request.setAttribute("imagenEdicion", "uploads/ediciones/" + edicionImg);
	                } else {
	                	request.setAttribute("imagenEdicion", "uploads/ediciones/default.jpg");
	                }
	                
	                // Fetch imagen organizador
	                String organizadorImg = ManejadorArchivos.buscarArchivo(ed.getOrganizador().toLowerCase(), getServletContext().getRealPath("/uploads/usuarios/"));
	                if (organizadorImg != null) {
	                	request.setAttribute("imagenOrganizador", "uploads/usuarios/" + organizadorImg);
	                } else {
	                	request.setAttribute("imagenOrganizador", "uploads/usuarios/default.jpg");
	                }
	                
	                // Despachar a JSP
	                request.getRequestDispatcher("/WEB-INF/pages/detalleEdicion.jsp").forward(request, response);
				} catch (Exception e) {
					response.getWriter().append(e.getMessage());
				}
                return;
            }
            case "/listarEdiciones": {
            	DataUsuario user = (DataUsuario) request.getSession().getAttribute("usuario");  //TODO
            	
                request.setAttribute("ediciones", Collections.emptyList());
                request.getRequestDispatcher("/WEB-INF/pages/listarEdiciones.jsp").forward(request, response);
                return;
            }
    		case "/detalleEdicion/altaEdicion" : {       // EJEMPLO: /detalleEdicion/altaEdicion?nombreEvento=evento1
                
                /*
                IControllerUsuario ICU = (IControllerUsuario) Factory.getInstance().getControllerUsuario();      //TESTING
                request.getSession().setAttribute("usuario", ICU.infoUsuario("miseventos"));	//TESTING
                */
                
                HttpSession session = request.getSession();
                DataUsuario user = (DataUsuario) session.getAttribute("usuario");

                
				// Verifica que el usuario haya iniciado sesion como organizador
                if (user == null || user.getTipo() != TipoUsuario.ORGANIZADOR) {
					// mostrar mensaje de error, el usuario no es organizador
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Solo los organizadores pueden dar de alta ediciones.");
				} else {
                	request.setAttribute("nombreEvento", request.getParameter("nombreEvento"));
					request.getRequestDispatcher("/WEB-INF/pages/altaEdicion.jsp").forward(request, response);
				}
    			return;
        	}
            default:
                break;
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String path = request.getServletPath();
    	
    	switch (path) {
        case "/altaEdicion": {
        	
            IControllerEvento ICE = (IControllerEvento) Factory.getInstance().getControllerEvento();
            HttpSession session = request.getSession();
            DataUsuario user = (DataUsuario) session.getAttribute("usuario");
            
			String nombre = request.getParameter("nombre");
			String sigla = request.getParameter("sigla");
			String ciudad = request.getParameter("ciudad");
			String pais = request.getParameter("pais");
			String fechaInicio = request.getParameter("fechaInicio");
			String fechaFin = request.getParameter("fechaFin");
			Part imagen = request.getPart("imagen");
			String nombreEvento = request.getParameter("nombreEvento");
			String organizador = user.getNickname();
			
			/*
			request.getSession().setAttribute("fecha", LocalDate.now());	//TESTING
			IControllerUsuario ICU = (IControllerUsuario) Factory.getInstance().getControllerUsuario();      //TESTING
			session.setAttribute("usuario", ICU.infoUsuario("miseventos"));	//TESTING
			*/
			
			
			try {
				if (user.getTipo() != TipoUsuario.ORGANIZADOR) {
					// mostrar mensaje de error, el usuario no es organizador
					throw new Exception("Necesita estar autenticado como organizador para dar de alta una edición.");
				}
				ICE.altaEdicionDeEvento(nombreEvento, organizador, nombre, sigla, LocalDate.parse(fechaInicio), LocalDate.parse(fechaFin),(LocalDate) session.getAttribute("fecha"), ciudad, pais);
				ManejadorArchivos.guardarArchivo(imagen, nombre, "ediciones", getServletContext());
				
				
				request.setAttribute("mensaje", "Edicion dada de alta exitosamente");
				request.setAttribute("error", null);
				request.setAttribute("nombreEvento", nombreEvento);
				request.getRequestDispatcher("/WEB-INF/pages/altaEdicion.jsp").forward(request, response);
			}	
			catch (Exception e) {
				request.setAttribute("error", e.getMessage());
				request.setAttribute("mensaje", null);
				request.getRequestDispatcher("/WEB-INF/pages/altaEdicion.jsp").forward(request, response);
				e.printStackTrace();
			}
			
            return;
        }
    }

}}