

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.controllers.IControllerEvento;
import logica.models.Factory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

/**
 * Servlet implementation class ServletCargaDatos
 */
@WebServlet("/cargarDatos")
public class ServletCargaDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCargaDatos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			CargarDatos.cargarDatos(getServletContext().getRealPath("/WEB-INF"));
	        IControllerEvento iEvento = Factory.getInstance().getControllerEvento();
	        Set<String> categorias = iEvento.listarCategorias();

	        HttpSession session = request.getSession();
	        session.setAttribute("categorias", categorias);
	        
	        
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/HomeServlet"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.getWriter().append("Error al cargar los datos");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
