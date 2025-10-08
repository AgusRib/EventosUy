

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.controllers.IControllerEvento;
import logica.models.Evento;
import logica.models.Factory;


@WebServlet("/HomeServlet")
public class ServletHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletHome() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IControllerEvento iEvento = Factory.getInstance().getControllerEvento();
		List<Evento> eventosRecientes = iEvento.obtenerEventosRecientes();
		
		
		request.setAttribute("eventos_recientes", eventosRecientes);
		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
