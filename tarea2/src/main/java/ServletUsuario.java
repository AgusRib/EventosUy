import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.controllers.IControllerEvento;
import logica.controllers.IControllerUsuario;
import logica.models.Factory;
import logica.dataTypes.DataUsuario;
import excepciones.UsuarioNoEncontrado;
// import logica.dataTypes.DataUsuario$TipoUsuario;


/**
 * Servlet implementation class Usuarios
 */
@WebServlet ("/usuarios")
public class ServletUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private IControllerUsuario controllerUsuario;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletUsuario() {
        super();
        Factory factory = Factory.getInstance();
        this.controllerUsuario = factory.getControllerUsuario();
    }

	/** 
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 * @return 
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		String usuario = request.getParameter("usuario");
        
        if (action == null || (action.equals("listar") && (usuario == null) )) {
        		listarUsuarios(request, response);
		} else if ( action.equals("MiPerfil" )) {
				//MiPerfil, corresponde a este servlet??
		} else if ( action.equals("detalleUsuario") ) {
			detalleUsuario(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		String usuario = request.getParameter("usuario");
		
			if (action == null || action.equals("modificarDatos") ) {
				modificarDatos(request, response);
		} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
	    }
    
	}
	
	private void detalleUsuario(HttpServletRequest request, HttpServletResponse response) {
		
		// ve el perfil de un solo usuario
		String usuario = request.getParameter("usuario");

					DataUsuario usr;
					try {
						usr = this.controllerUsuario.infoUsuario(usuario);
					} catch(UsuarioNoEncontrado ex){
						response.sendError(404); // el usuario no existe
						request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").
								include(request, response);
						return;
					}
					
					// setea el usuario
					request.setAttribute("usuario", usr);
					
					request.getRequestDispatcher("/WEB-INF/usuarios/detalleUsuario.jsp").
							forward(request, response);
		
	}

	private void modificarDatos(HttpServletRequest request, HttpServletResponse response) {
		
		// modifica los datos de un solo usuario
				String usuario = request.getParameter("usuario");
				String[] datos = request.getParameterValues("datos");

				DataUsuario usr;
				try {
					usr = this.controllerUsuario.infoUsuario(usuario);
				} catch(UsuarioNoEncontrado ex){
					response.sendError(404); // el usuario no existe
					request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").
							include(request, response);
					return;
				}
				
				// setea el usuario
				request.setAttribute("usuario", usr);
				request.setAttribute("datos", datos);
				
				request.getRequestDispatcher("/WEB-INF/usuarios/modificarDatos.jsp").
						forward(request, response);
	}

	private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String usuario = request.getParameter("usuario");
		
			// no se seteó el usuario (lista todos los usuarios)
			
			Collection<String> usrs = this.controllerUsuario.listarUsuarios();
			
			request.setAttribute("usuarios", usrs);
			
			request.getRequestDispatcher("/WEB-INF/usuarios/listarUsuarios.jsp").
					forward(request, response);
	
	}
	

}
