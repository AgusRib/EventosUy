import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.controllers.IControllerUsuario;
import logica.dataTypes.DataUsuario;
import logica.models.Factory;
import excepciones.NombreUsuarioExistente;
import excepciones.EmailRepetido;
import java.io.IOException;

/**
 * Servlet implementation class ServletAutenticator
 */
@WebServlet({"/registro", "/iniciosesion", "/cerrarsesion"})
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
				response.sendRedirect(request.getContextPath() + "/eventos");
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
		String confirmPassword = request.getParameter("confirmPassword");
		String fechaNacimiento = request.getParameter("fechaNacimiento");
		String tipoUsuario = request.getParameter("tipoUsuario");
		
		try {
			// Validar que las contraseñas coincidan
			if (!password.equals(confirmPassword)) {
				request.setAttribute("error", "Las contraseñas no coinciden.");
				preservarDatosFormulario(request, nickname, nombre, apellido, email, fechaNacimiento, tipoUsuario);
				request.getRequestDispatcher("/WEB-INF/pages/registro.jsp").forward(request, response);
				return;
			}
			
			// Procesar fecha de nacimiento
			java.time.LocalDate fechaNac = null;
			if (fechaNacimiento != null && !fechaNacimiento.trim().isEmpty()) {
				try {
					fechaNac = java.time.LocalDate.parse(fechaNacimiento);
				} catch (Exception e) {
					request.setAttribute("error", "Formato de fecha inválido.");
					preservarDatosFormulario(request, nickname, nombre, apellido, email, fechaNacimiento, tipoUsuario);
					request.getRequestDispatcher("/WEB-INF/pages/registro.jsp").forward(request, response);
					return;
				}
			}
			
			// Registrar usuario
			if ("organizador".equals(tipoUsuario)) {
				// Para organizador: nickname, nombre, email, password, descripcion, web
				String descripcion = ""; // Default empty description
				String web = ""; // Default empty web
				controllerUsuario.ingresarOrganizador(nickname.trim(), nombre.trim(), 
													  email.trim(), password.trim(), descripcion, web);
			} else {
				// Para asistente: nickname, nombre, email, password, apellido, fechaNac
				controllerUsuario.ingresarAsistente(nickname.trim(), nombre.trim(), 
												   email.trim(), password.trim(),
												   apellido.trim(), fechaNac);
			}
			
			// Registro exitoso - redirigir a inicio de sesión
			response.sendRedirect(request.getContextPath() + "/iniciosesion");
			
		} catch (NombreUsuarioExistente e) {
			request.setAttribute("error", "Ya existe un usuario con ese nickname.");
			preservarDatosFormulario(request, nickname, nombre, apellido, email, fechaNacimiento, tipoUsuario);
			request.getRequestDispatcher("/WEB-INF/pages/registro.jsp").forward(request, response);
		} catch (EmailRepetido e) {
			request.setAttribute("error", "Ya existe un usuario con ese email.");
			preservarDatosFormulario(request, nickname, nombre, apellido, email, fechaNacimiento, tipoUsuario);
			request.getRequestDispatcher("/WEB-INF/pages/registro.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("error", "Error al registrar usuario: " + e.getMessage());
			preservarDatosFormulario(request, nickname, nombre, apellido, email, fechaNacimiento, tipoUsuario);
			request.getRequestDispatcher("/WEB-INF/pages/registro.jsp").forward(request, response);
		}
	}
	
	private void procesarInicioSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		
		try {
			// Verificar credenciales y obtener datos del usuario directamente
			DataUsuario usuario = controllerUsuario.iniciarSesionNickname(nickname.trim(), password.trim());
			
			if (usuario != null) {
				// Crear sesión y setear el atributo usuario
				HttpSession session = request.getSession();
				session.setAttribute("usuario", usuario);
				
				// Redirigir a la página principal
				response.sendRedirect(request.getContextPath() + "/eventos");
			} else {
				request.setAttribute("error", "Credenciales incorrectas.");
				request.setAttribute("nickname", nickname);
				request.getRequestDispatcher("/WEB-INF/pages/iniciosesion.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("error", "Error al iniciar sesión: " + e.getMessage());
			request.setAttribute("nickname", nickname);
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