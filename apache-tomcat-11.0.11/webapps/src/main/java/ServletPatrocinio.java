package main.java;
import java.io.IOException;
import java.util.Set;

import excepciones.NombreInstiExistente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.controllers.IControllerEvento;
import logica.controllers.IControllerUsuario;
import logica.dataTypes.DTPatrocinio;
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
    
    //doget
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
                // Handle exceptions - set error message and forward to error page
                request.setAttribute("error", "Error al obtener detalles del patrocinio: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/pages//error.jsp").forward(request, response);
            }
            break;
            
        case "/altaInstitucion":
        	// Imitate AltaEvento: ensure mensaje and error are initialized so the JSP can show/clear alerts
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("/WEB-INF/pages//altaPatrocinio.jsp").forward(request, response);
            break;
    }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path = request.getServletPath();
    	IControllerEvento ICE = Factory.getInstance().getControllerEvento();
    	IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();
    	//	void altaPatrocinio(String nombreEdi, String institucion, NivelPatrocinio nivel, 
    	//  double aporteEconomico, String tipoRegistroGratis, int cantidadGratis, String codigo);

    
    
    	switch(path) {
    		
			case "/altaPatrocinio":
				String nombreEdi = request.getParameter("edicion");
				String institucion = request.getParameter("institucion");
				NivelPatrocinio nivel = NivelPatrocinio.valueOf(request.getParameter("nivelPatrocinio"));
				double aporteEconomico = Double.parseDouble(request.getParameter("aporte"));
				String tipoRegistroGratis = request.getParameter("tipoRegGratis");
				int cantidadGratis = Integer.parseInt(request.getParameter("cantGratis"));
				String codigo = request.getParameter("codigo");
			
				ICE.altaPatrocinio(nombreEdi, institucion, nivel, aporteEconomico, tipoRegistroGratis, cantidadGratis, codigo);
				
				request.getRequestDispatcher("/altaPatrocinio").forward(request, response);
				break;
	    	
			case "/altaInstitucion":
				// altaInstitucion(String nombre, String descripcion, String web) throws NombreInstiExistente, Exception;
				
				String nombreInsti = request.getParameter("nombre");
				String desc = request.getParameter("descripcion");
				String web = request.getParameter("url");
				
				try {
					ICU.altaInstitucion(nombreInsti, desc, web);
					// success: imitate AltaEvento behaviour — set mensaje and clear error, preserve fields
					request.setAttribute("mensaje", "Institución creada exitosamente.");
					request.setAttribute("error", null);
					request.setAttribute("nombre", nombreInsti);
					request.setAttribute("descripcion", desc);
					request.setAttribute("url", web);
					request.getRequestDispatcher("/WEB-INF/pages/altaInstitucion.jsp").forward(request, response);
				} catch (NombreInstiExistente e) {
					request.setAttribute("error", "El nombre de la institucion ya existe");
					// preserve values
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
    }
    
}