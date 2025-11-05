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

// Imports de webservices
import webservices.DataUsuario;
import webservices.PublicadorUsuario;
import webservices.PublicadorUsuarioService;
import webservices.WrapperHashSet;

/**
 * Servlet implementation class ServletAutenticator
 */
@WebServlet({"/iniciosesion", "/cerrarsesion"})
@MultipartConfig
public class ServletAutenticator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ServletAutenticator() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		
		switch (path) {
		
			case "/iniciosesion": {
				request.getRequestDispatcher("/WEB-INF/pages/iniciosesion.jsp").forward(request, response);
				break;
			}
			case "/cerrarsesion": {
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
			case "/iniciosesion": {
				procesarInicioSesion(request, response);
				break;
			}
			default:
				doGet(request, response);
				break;
		}
	}
	
	private void procesarInicioSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nicknameomail = request.getParameter("nickname");
		String password = request.getParameter("password");
		
		
		PublicadorUsuarioService serviceUsuario = new PublicadorUsuarioService();
		PublicadorUsuario portUsuario = serviceUsuario.getPublicadorUsuarioPort();
		
		DataUsuario usuario = new DataUsuario();
		boolean loginExitoso = false;
		
		
		try {
			usuario = portUsuario.iniciarSesionNickname(nicknameomail.trim(), password.trim());
			if (usuario != null) {
				loginExitoso = true;
			}
		} catch (Exception e) {
			
		}
		
		
		if (!loginExitoso) {
			try {
				usuario = portUsuario.iniciarSesionEmail(nicknameomail.trim(), password.trim());
				if (usuario != null) {
					loginExitoso = true;
				}
			} catch (Exception e) {
				// Login falló completamente
			}
		}
		
		if (loginExitoso && usuario != null) {
			
			HttpSession session = request.getSession();
			session.setAttribute("usuario", usuario);
			
			
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
	
	private void cargarInstituciones(HttpServletRequest request, PublicadorUsuario portUsuario) {
		try {
			WrapperHashSet institucionesWrapper = portUsuario.listarInstituciones();
			List<Object> institucionesObj = institucionesWrapper.getItem();
			Set<String> instituciones = new java.util.HashSet<>();
			for (Object obj : institucionesObj) {
				instituciones.add((String) obj);
			}
			request.setAttribute("instituciones", instituciones);
		} catch (Exception ex) {
			System.err.println("Error obteniendo instituciones: " + ex.getMessage());
			request.setAttribute("instituciones", new java.util.HashSet<String>());
		}
	}
}