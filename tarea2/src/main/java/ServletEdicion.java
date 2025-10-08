import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.models.Edicion;

import java.io.IOException;
import java.util.Collections;

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
                // Example: get id param and set an attribute for the JSP
                String id = request.getParameter("id");
                // In a real app: fetch the object from DB/service by id
                Edicion ed = edicionService.findById(id);
                // request.setAttribute("edicion", ed);
                request.setAttribute("edicionId", id);
                // Forward to the JSP that will render the detail
                //request.getRequestDispatcher("/WEB-INF/pages/detalleEdicion.jsp").forward(request, response);
                response.getWriter().append(id);
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