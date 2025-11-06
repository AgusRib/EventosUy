import java.io.IOException;
import java.util.List;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import logica.controllers.IControllerUsuario;
import logica.models.Factory;
// Imports de webservices
import webservices.DataUsuario;
import webservices.EmailRepetido_Exception;
import webservices.NombreUsuarioExistente_Exception;
import webservices.PublicadorUsuario;
import webservices.PublicadorUsuarioService;
import webservices.WrapperHashSet;

/**
 * Servlet implementation class ServletAutenticator
 */
@WebServlet({"/registro", "/iniciosesion", "/cerrarsesion"})
@MultipartConfig
public class ServletAutenticator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ServletAutenticator() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		
		switch (path) {
			case "/registro": {
				// Obtener instituciones usando webservices
				//PublicadorUsuarioService serviceUsuario = new PublicadorUsuarioService();
				//PublicadorUsuario portUsuario = serviceUsuario.getPublicadorUsuarioPort();
				
				IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();
				
				try {
					//WrapperHashSet institucionesWrapper = portUsuario.listarInstituciones();
					Set<String> institucionesObj = ICU.listarInstituciones();
					//List<Object> institucionesObj = institucionesWrapper.getItem();
					Set<String> instituciones = new java.util.HashSet<>();
					for (Object obj : institucionesObj) {
						instituciones.add((String) obj);
					}
					request.setAttribute("instituciones", instituciones);
				} catch (Exception e) {
					System.err.println("Error obteniendo instituciones: " + e.getMessage());
					request.setAttribute("instituciones", new java.util.HashSet<String>());
				}
				
				request.getRequestDispatcher("/WEB-INF/pages/registro.jsp").forward(request, response);
				break;
			}
			case "/iniciosesion": {
				request.getRequestDispatcher("/WEB-INF/pages/iniciosesion.jsp").forward(request, response);
				break;
			}
			case "/cerrarsesion": {
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.invalidate();
				}
				response.sendRedirect(request.getContextPath() + "/iniciosesion");
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
		
		//PublicadorUsuarioService serviceUsuario = new PublicadorUsuarioService();
		//PublicadorUsuario portUsuario = serviceUsuario.getPublicadorUsuarioPort();
		
		IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();
		
		// Determinar tipo de usuario
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
					request.getRequestDispatcher("/WEB-INF/pages/registro.jsp").forward(request, response);
					return;
				}
			}
			
			// Registrar usuario usando webservices
			if ("organizador".equals(tipoUsuario)) {
				String descripcion = request.getParameter("descripcion");
				String web = request.getParameter("sitioWeb");
				if (descripcion == null) descripcion = "";
				if (web == null) web = "";
				
				ICU.ingresarOrganizador(nickname.trim(), nombre.trim(), 
											   email.trim(), password.trim(), descripcion, web);
			} else {
				String fechaString = fechaNac != null ? fechaNac.toString() : "";
				ICU.ingresarAsistente(nickname.trim(), nombre.trim(), 
											 email.trim(), password.trim(),
											 apellido != null ? apellido.trim() : "", fechaNac /*aca iba fechaString*/);
				
				String institucion = request.getParameter("institucion");
				if (institucion != null && !institucion.trim().isEmpty()) {
					ICU.agregarAsistente(nickname.trim(), institucion.trim());
				}
			}
			
			// Guardar imagen de perfil
			Part imagen = request.getPart("imagen");
			ManejadorArchivos.guardarArchivo(imagen, nickname.toLowerCase(), "usuarios", getServletContext());
			
			// Registro exitoso
			response.sendRedirect(request.getContextPath() + "/iniciosesion?registered=true");
			
		} catch (NombreUsuarioExistente_Exception e) {
			request.setAttribute("error", "Ya existe un usuario con ese nickname.");
			preservarDatosFormulario(request, nickname, nombre, apellido, email, fechaNacimiento, tipoUsuario);
			request.getRequestDispatcher("/WEB-INF/pages/registro.jsp").forward(request, response);
		} catch (EmailRepetido_Exception e) {
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
		String nicknameomail = request.getParameter("nickname");
		String password = request.getParameter("password");
		
		IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();
		
		//PublicadorUsuarioService serviceUsuario = new PublicadorUsuarioService();
		//PublicadorUsuario portUsuario = serviceUsuario.getPublicadorUsuarioPort();
		
		//DataUsuario usuario = new DataUsuario();
		logica.data_types.DataUsuario usuario = new logica.data_types.DataUsuario();
		boolean loginExitoso = false;
		
		// Intentar login por nickname primero
		try {
			//usuario = portUsuario.iniciarSesionNickname(nicknameomail.trim(), password.trim());
			usuario = ICU.iniciarSesionNickname(nicknameomail.trim(), password.trim());
			if (usuario != null) {
				loginExitoso = true;
			}
		} catch (Exception e) {
			// Otros errores (contraseña incorrecta, etc.)
		}
		
		// Si no funcionó por nickname, intentar por email
		if (!loginExitoso) {
			try {
				usuario = ICU.iniciarSesionEmail(nicknameomail.trim(), password.trim());
				if (usuario != null) {
					loginExitoso = true;
				}
			} catch (Exception e) {
				// Login falló completamente
			}
		}
		
		if (loginExitoso && usuario != null) {
			// Crear sesión y configurar atributos
			HttpSession session = request.getSession();
			session.setAttribute("usuario", usuario);
			
			// Configurar imagen de perfil
			String pfp = ManejadorArchivos.buscarArchivo(usuario.getNickname().toLowerCase(), 
														getServletContext().getRealPath("/uploads/usuarios/"));
			if (pfp != null) {
				session.setAttribute("pfp", "uploads/usuarios/" + pfp);
			} else {
				session.setAttribute("pfp", "uploads/usuarios/default.jpg");
			}
			
			response.sendRedirect(request.getContextPath() + "/HomeServlet");
		} else {
			request.setAttribute("error", "Credenciales incorrectas.");
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