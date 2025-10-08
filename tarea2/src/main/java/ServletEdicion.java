import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.controllers.IControllerEvento;
import logica.dataTypes.DTDetalleEdicion;
import logica.models.Factory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import casosPrueba.CargaDatos;


/**
 * Servlet implementation class ServletEdicion
 */
@WebServlet({ "/detalleEdicion", "/AltaEdicion", "/RegistroEdicion", "/listarEdiciones" })
public class ServletEdicion extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEdicion() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Use servletPath to determine which mapping was called
        String path = request.getServletPath();
        
        switch (path) {
            case "/detalleEdicion": {
            	IControllerEvento ICE = (IControllerEvento) Factory.getInstance().getControllerEvento();
                // Example: get id param and set an attribute for the JSP
                String nombre = request.getParameter("nombre");
                response.getWriter().append("Detalle de Edicion: ").append(nombre).append("\n");
                // In a real app: fetch the object from DB/service by id
                try {
					DTDetalleEdicion ed = ICE.mostrarDetallesEdicion(nombre);
	                request.setAttribute("edicion", ed);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					response.getWriter().append(e.getMessage());
				}
                // Forward to the JSP that will render the detail 
                request.getRequestDispatcher("/WEB-INF/pages/detalleEdicion.jsp").forward(request, response);
                return;
            }
            case "/AltaEdicion": {
                // Show a form to create a new "edicion"
                request.getRequestDispatcher("/WEB-INF/pages/altaEdicion.jsp").forward(request, response);
                return;
            }
            case "/RegistroEdicion": {
                // Normally handle form submission here (POST). For GET, forward to a confirmation page
                request.getRequestDispatcher("/WEB-INF/pages/registroEdicion.jsp").forward(request, response);
                return;
            }
            case "/listarEdiciones": {
                // In a real app: List<Edicion> lista = edicionService.findAll();
                // request.setAttribute("ediciones", lista);
                request.setAttribute("ediciones", Collections.emptyList());
                request.getRequestDispatcher("/WEB-INF/pages/listarEdiciones.jsp").forward(request, response);
                return;
            }
            default:
                break;
        }

        // Fallback response
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // For form submissions you typically read parameters, save data and redirect
        // Example minimal behavior: delegate to doGet
        doGet(request, response);
    }

}