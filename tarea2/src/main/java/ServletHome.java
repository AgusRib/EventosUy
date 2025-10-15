
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.controllers.IControllerEvento;
import logica.dataTypes.DTDetalleEvento;
import logica.models.Factory;


@WebServlet("/HomeServlet")
public class ServletHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletHome() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		IControllerEvento iEvento = Factory.getInstance().getControllerEvento();

		
		List<DTDetalleEvento> eventosRecientes = iEvento.obtenerEventosRecientes();
		request.setAttribute("eventos_recientes", eventosRecientes);
		
        // Fetch imagen de edicion
		for (DTDetalleEvento e : eventosRecientes) {
	        String eventoImg = ManejadorArchivos.buscarArchivo(e.getNombre().toLowerCase(), getServletContext().getRealPath("/uploads/eventos/"));
	        if (eventoImg != null) {
	        	request.setAttribute(e.getNombre(), "uploads/eventos/" + eventoImg);
	        } else {
	        	request.setAttribute(e.getNombre(), "uploads/eventos/default.jpg");
	        }
        }
		
		request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}