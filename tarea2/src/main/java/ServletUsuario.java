import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Set;
import java.text.Normalizer;

import excepciones.NombreEventoExcepcion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.controllers.IControllerUsuario;
import logica.models.Factory;
import logica.dataTypes.DataUsuario;
import excepciones.UsuarioNoEncontrado;
// import logica.dataTypes.DataUsuario$TipoUsuario;


/**
 * Servlet implementation class Usuarios
 */
@WebServlet ( {"/usuarios", "/listarUsuarios", "/detalleUsuario", "/modificarDatos", "/MiPerfil"} )
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
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UsuarioNoEncontrado {
		
		String action = request.getParameter("action");
		String usuario = request.getParameter("usuario");
        
        if (action == null || (action.equals("listar") && (usuario == null) )) {
        		listarUsuarios(request, response);
		} else if ( action.equals("MiPerfil" )) {
				//MiPerfil, corresponde a este servlet??
		} else if ( action.equals("detalleUsuario") ) {
			try {
				detalleUsuario(request, response);
			} catch (UsuarioNoEncontrado e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		
			if ((action == null || action.equals("modificarDatos")&& usuario != null)) {
				modificarDatos(request, response);
		} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
	    }
    
	}
	
	private void detalleUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UsuarioNoEncontrado {
		
		// ve el perfil de un solo usuario
		String usuario = request.getParameter("usuario");

					DataUsuario usr;
					try {
						usr = this.controllerUsuario.infoUsuario(usuario);
					} catch(UsuarioNoEncontrado ex){
						try {
							response.sendError(404);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // el usuario no existe
						try {
							request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").
									include(request, response);
						} catch (ServletException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return;
					}
					
					// setea el usuario
					request.setAttribute("usuario", usr);
					
					try {
						request.getRequestDispatcher("/WEB-INF/usuarios/detalleUsuario.jsp").
								forward(request, response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
	}

	private void modificarDatos(HttpServletRequest request, HttpServletResponse response) {
		
		// modifica los datos de un solo usuario
				String usuario = request.getParameter("usuario");
				String[] datos = request.getParameterValues("datos");

				DataUsuario usr;
				try {
					usr = this.controllerUsuario.infoUsuario(usuario);
				} catch(UsuarioNoEncontrado ex){
					try {
						response.sendError(404);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // el usuario no existe
					try {
						request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").
								include(request, response);
					} catch (ServletException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
				
				// setea el usuario
				request.setAttribute("usuario", usr);
				request.setAttribute("datos", datos);
				
				try {
					request.getRequestDispatcher("/WEB-INF/usuarios/modificarDatos.jsp").
							forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
			// no se seteó el usuario (lista todos los usuarios)
			
			Set<String> usrs = this.controllerUsuario.listarUsuarios();
			
			request.setAttribute("usuarios", usrs);
			
			request.getRequestDispatcher("/WEB-INF/usuarios/listarUsuarios.jsp").
					forward(request, response);
	
	}
	

}
