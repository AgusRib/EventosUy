import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import logica.controllers.IControllerUsuario;
import logica.data_types.DataUsuario;
import logica.models.Factory;
import excepciones.NombreUsuarioExistente;
import excepciones.EmailRepetido;
import java.io.IOException;
import jakarta.servlet.annotation.MultipartConfig;

/**
 * Servlet implementation class ServletAutenticator
 */
@WebServlet({"/registro", "/iniciosesion", "/cerrarsesion"})
@MultipartConfig
public class ServletAutenticator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private IControllerUsuario controllerUsuario;
    
    public ServletAutenticator() {
        super();
        Factory factory = Factory.getInstance();
        this.controllerUsuario = factory.getControllerUsuario();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		
		switch (path) {
			case "/registro": {
				// Obtener instituciones y pasarlas a la página de registro
			
					java.util.Set<String> instituciones = controllerUsuario.listarInstituciones();
					request.setAttribute("instituciones", instituciones);
				
				// Mostrar página de registro
				request.getRequestDispatcher("/WEB-INF/pages/registro.jsp").forward(request, response);
				break;
			}
			case "/iniciosesion": {
				// Mostrar página de inicio de sesión
				request.getRequestDispatcher("/WEB-INF/pages/iniciosesion.jsp").forward(request, response);
				break;
			}
			case "/cerrarsesion": {
				// Cerrar sesión y redirigir a home
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.invalidate();
				}
				response.sendRedirect(request.getContextPath() + "/HomeServlet");
				break;
			}
			default:
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		
		switch (path) {
			case "/registro": {
				procesarRegistro(request, response);
				break;
			}
			case "/iniciosesion": {
				procesarInicioSesion(request, response);
				break;
			}
			default:
				doGet(request, response);
				break;
		}
	}
	
	private void procesarRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickname = request.getParameter("nickname");
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String fechaNacimiento = request.getParameter("fechaNacimiento");
		
		// Determinar tipo de usuario basado en los checkboxes
		String[] tiposUsuario = request.getParameterValues("tipoUsuario");
		String tipoUsuario = "asistente"; 
		
		if (tiposUsuario != null) {
		
			for (String tipo : tiposUsuario) {
				if ("organizador".equals(tipo)) {
					tipoUsuario = "organizador";
					break;
				} else if ("asistente".equals(tipo)) {
					tipoUsuario = "asistente";
				}
			}
		}
		
		try {
		
			
			// Procesar fecha de nacimiento
			java.time.LocalDate fechaNac = null;
			if (fechaNacimiento != null && !fechaNacimiento.trim().isEmpty()) {
				try {
					fechaNac = java.time.LocalDate.parse(fechaNacimiento);
				} catch (Exception e) {
					request.setAttribute("error", "Formato de fecha inválido.");
					preservarDatosFormulario(request, nickname, nombre, apellido, email, fechaNacimiento, tipoUsuario);
					// Recargar instituciones para mostrar la página
				
						java.util.Set<String> instituciones = controllerUsuario.listarInstituciones();
						request.setAttribute("instituciones", instituciones);
					
					request.getRequestDispatcher("/WEB-INF/pages/registro.jsp").forward(request, response);
					return;
				}
			}
			
			// Registrar usuario
			if ("organizador".equals(tipoUsuario)) {
				// Para organizador: nickname, nombre, email, password, descripcion, web
				String descripcion = request.getParameter("descripcion");
				String web = request.getParameter("sitioWeb");
				if (descripcion == null) descripcion = "";
				if (web == null) web = "";
				
				controllerUsuario.ingresarOrganizador(nickname.trim(), nombre.trim(), 
													  email.trim(), password.trim(), descripcion, web);
				
				// Guardar imagen de perfil usando ManejadorArchivos
				Part imagen = request.getPart("imagen");
				ManejadorArchivos.guardarArchivo(imagen, nickname.toLowerCase(), "usuarios", getServletContext());
			} else {
				// Para asistente: nickname, nombre, email, password, apellido, fechaNac
				controllerUsuario.ingresarAsistente(nickname.trim(), nombre.trim(), 
												   email.trim(), password.trim(),
												   apellido != null ? apellido.trim() : "", fechaNac);
				
				// Guardar imagen de perfil usando ManejadorArchivos
				Part imagen = request.getPart("imagen");
				ManejadorArchivos.guardarArchivo(imagen, nickname.toLowerCase(), "usuarios", getServletContext());
			}
			
			String pfp = ManejadorArchivos.buscarArchivo(nickname.toLowerCase(), getServletContext().getRealPath("/uploads/usuarios/"));
			System.out.println("PFP seteada en sesión: " + pfp);
			if (pfp != null) {
				request.getSession().setAttribute("pfp", pfp);
			} else {
				request.getSession().setAttribute("pfp", "default.png");
			}
			
			// Registro exitoso - redirigir a inicio de sesión
			// Include a flag so the login page can show a success message
            response.sendRedirect(request.getContextPath() + "/iniciosesion?registered=true");
			
		} catch (NombreUsuarioExistente e) {
			request.setAttribute("error", "Ya existe un usuario con ese nickname.");
			preservarDatosFormulario(request, nickname, nombre, apellido, email, fechaNacimiento, tipoUsuario);
			// Recargar instituciones para mostrar la página
			
				java.util.Set<String> instituciones = controllerUsuario.listarInstituciones();
				request.setAttribute("instituciones", instituciones);
			
			request.getRequestDispatcher("/WEB-INF/pages/registro.jsp").forward(request, response);
		} catch (EmailRepetido e) {
			request.setAttribute("error", "Ya existe un usuario con ese email.");
			preservarDatosFormulario(request, nickname, nombre, apellido, email, fechaNacimiento, tipoUsuario);
			// Recargar instituciones para mostrar la página
			try {
				java.util.Set<String> instituciones = controllerUsuario.listarInstituciones();
				request.setAttribute("instituciones", instituciones);
			} catch (Exception ex) {
				System.err.println("Error obteniendo instituciones: " + ex.getMessage());
			}
			request.getRequestDispatcher("/WEB-INF/pages/registro.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("error", "Error al registrar usuario: " + e.getMessage());
			preservarDatosFormulario(request, nickname, nombre, apellido, email, fechaNacimiento, tipoUsuario);
			// Recargar instituciones para mostrar la página
			
				java.util.Set<String> instituciones = controllerUsuario.listarInstituciones();
				request.setAttribute("instituciones", instituciones);
			
			request.getRequestDispatcher("/WEB-INF/pages/registro.jsp").forward(request, response);
		}
	}
	
	private void procesarInicioSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nicknameomail = request.getParameter("nickname");
		String password = request.getParameter("password");
	
		
		try {
			// Verificar credenciales y obtener datos del usuario directamente
			DataUsuario usuario = controllerUsuario.iniciarSesionNickname(nicknameomail.trim(), password.trim());
			if (usuario == null) {
				usuario = controllerUsuario.iniciarSesionEmail(nicknameomail.trim(), password.trim());
				
			}
			
			if (usuario != null) {
				// Crear sesión y setear el atributo usuario
				HttpSession session = request.getSession();
				session.setAttribute("usuario", usuario);
				
				String pfp = ManejadorArchivos.buscarArchivo(usuario.getNickname().toLowerCase(), getServletContext().getRealPath("/uploads/usuarios/"));
				System.out.println("PFP seteada en sesión: " + pfp);
				if (pfp != null) {
					request.getSession().setAttribute("pfp", pfp);
				} else {
					request.getSession().setAttribute("pfp", "default.png");
				}
				
				// Redirigir a la página principal
				response.sendRedirect(request.getContextPath() + "/HomeServlet");
			} else {
				
				request.setAttribute("error", "Credenciales incorrectas.");
				request.setAttribute("nickname", nicknameomail);
				request.getRequestDispatcher("/WEB-INF/pages/iniciosesion.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("error", "Error al iniciar sesión: " + e.getMessage());
			request.setAttribute("nickname", nicknameomail);
			request.getRequestDispatcher("/WEB-INF/pages/iniciosesion.jsp").forward(request, response);
		}
	}
	
	private void preservarDatosFormulario(HttpServletRequest request, String nickname, String nombre, 
										 String apellido, String email, String fechaNacimiento, String tipoUsuario) {
		request.setAttribute("nickname", nickname);
		request.setAttribute("nombre", nombre);
		request.setAttribute("apellido", apellido);
		request.setAttribute("email", email);
		request.setAttribute("fechaNacimiento", fechaNacimiento);
		request.setAttribute("tipoUsuario", tipoUsuario);
	}
}