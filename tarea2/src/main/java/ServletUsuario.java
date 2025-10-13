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
import logica.dataTypes.DTAsistente;
import logica.dataTypes.DTOrganizador;
import logica.dataTypes.DataUsuario;
import excepciones.UsuarioNoEncontrado;
// import logica.dataTypes.DataUsuario$TipoUsuario;


/**
 * Servlet implementation class Usuarios
 */
@WebServlet ( {"/pages", "/usuarios", "/listarUsuarios", "/detalleUsuario", "/modificarDatos", "/MiPerfil"} )
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
		
		String path = request.getServletPath();
		
		if ("/modificarDatos".equals(path)) {
		    if (usuario == null || usuario.isBlank()) {
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro 'usuario'");
		        return;
		    }
		    try {
		        DataUsuario du = controllerUsuario.infoUsuario(usuario);
		        request.setAttribute("usuario", du);

		        if (du.getTipo() == DataUsuario.TipoUsuario.ORGANIZADOR) {
		            DTOrganizador org = controllerUsuario.infoOrganizador(usuario);
		            request.setAttribute("usuarios", org);
		        } else {
		            DTAsistente asis = controllerUsuario.infoAsistente(usuario);
		            request.setAttribute("usuarios", asis);
		        }

		        if ("1".equals(request.getParameter("ok"))) {
		            request.setAttribute("mensaje", "Modificaciones realizadas exitosamente.");
		        }

		        request.getRequestDispatcher("/WEB-INF/pages/modificarDatos.jsp")
		               .forward(request, response);
		        return;

		    } catch (excepciones.UsuarioNoEncontrado e) {
		        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
		        return;
		    } catch (Exception e) {
		        request.setAttribute("error", "No se pudieron cargar los datos: " + e.getMessage());
		        request.getRequestDispatcher("/WEB-INF/pages/modificarDatos.jsp")
		               .forward(request, response);
		        return;
		    }
		}
        
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
        String action  = request.getParameter("action");
        String usuario = request.getParameter("usuario");

        if ("modificarDatos".equals(action) && usuario != null && !usuario.isBlank()) {
            modificarDatos(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
    }
	
	private void detalleUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UsuarioNoEncontrado {
		
		// ve el perfil de un solo usuario
		String usuario = request.getParameter("usuarios");

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
					if ( usr.getTipo() == DataUsuario.TipoUsuario.ORGANIZADOR ) {
						DTOrganizador org = this.controllerUsuario.infoOrganizador(usuario);
						request.setAttribute("usuarios", org);
					} else {
						DTAsistente asis = this.controllerUsuario.infoAsistente(usuario);
						request.setAttribute("usuarios", asis);
					}
					
					try {
						request.getRequestDispatcher("/WEB-INF/pages/detalleUsuario.jsp").
								forward(request, response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
	}

	private void modificarDatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String nickParam = request.getParameter("usuario");
	    if (nickParam == null || nickParam.isBlank()) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro 'usuario'");
	        return;
	    }

	    DataUsuario usr;
	    try {
	        usr = this.controllerUsuario.infoUsuario(nickParam);
	    } catch (UsuarioNoEncontrado ex) {
	        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
	        return;
	    }

	    try {
	        if (usr.getTipo() == DataUsuario.TipoUsuario.ASISTENTE) {
	            String nombre   = trimOrNull(request.getParameter("nombre"));
	            String apellido = trimOrNull(request.getParameter("apellido"));
	            String fechaNacStr = trimOrNull(request.getParameter("fechaNac")); // formato: YYYY-MM-DD

	            if (isBlankAny(nombre, apellido, fechaNacStr)) {
	                request.setAttribute("error", "Completá nombre, apellido y fecha de nacimiento.");
	                request.setAttribute("usuario", usr);
	                forwardEditar(request, response);
	                return;
	            }

	            LocalDate fechaNac;
	            try {
	                fechaNac = LocalDate.parse(fechaNacStr);
	            } catch (Exception pe) {
	                request.setAttribute("error", "Fecha de nacimiento inválida (formato esperado: AAAA-MM-DD).");
	                request.setAttribute("usuario", usr);
	                request.setAttribute("nombre", nombre);
	                request.setAttribute("apellido", apellido);
	                request.setAttribute("fechaNac", fechaNacStr);
	                forwardEditar(request, response);
	                return;
	            }

	            controllerUsuario.editarAsistente(nickParam, nombre, apellido, fechaNac);

	        } else if (usr.getTipo() == DataUsuario.TipoUsuario.ORGANIZADOR) {
	            String nombre      = trimOrNull(request.getParameter("nombre"));
	            String descripcion = trimOrNull(request.getParameter("descripcion"));
	            String web         = trimOrNull(request.getParameter("web"));

	            if (isBlankAny(nombre, descripcion)) {
	                request.setAttribute("error", "Completá nombre y descripción.");
	                request.setAttribute("usuario", usr);
	                request.setAttribute("nombre", nombre);
	                request.setAttribute("descripcion", descripcion);
	                request.setAttribute("web", web);
	                forwardEditar(request, response);
	                return;
	            }

	            controllerUsuario.editarOrganizador(nickParam, nombre, descripcion, web);
	        } else {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de usuario no soportado");
	            return;
	        }

	        String url = request.getContextPath() 
		                + "/modificarDatos?usuario="
		                + java.net.URLEncoder.encode(nickParam, java.nio.charset.StandardCharsets.UTF_8)
		                + "&ok=1";
		    response.sendRedirect(url);

	        return;

	    } catch (Exception e) {
	        request.setAttribute("error", "No se pudieron guardar los cambios: " + e.getMessage());
	        request.setAttribute("usuario", usr);
	        forwardEditar(request, response);
	    }
	}

	/* Helpers */
	private static String trimOrNull(String s) {
	    return s == null ? null : s.trim();
	}
	private static boolean isBlankAny(String... arr) {
	    if (arr == null) return true;
	    for (String s : arr) {
	        if (s == null || s.isBlank()) return true;
	    }
	    return false;
	}
	private void forwardEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Reutilizá tu JSP de edición. En tu código original usabas /WEB-INF/pages/modificarDatos.jsp
	    request.getRequestDispatcher("/WEB-INF/pages/modificarDatos.jsp").forward(request, response);
	}


	private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
			// no se seteó el usuario (lista todos los usuarios)
			
			Set<String> usrs = this.controllerUsuario.listarUsuarios();
			
			try {
				if (usrs.isEmpty())
					throw new UsuarioNoEncontrado("No hay usuarios registrados");
				else {
					Set<DataUsuario> usuarios = new java.util.HashSet<DataUsuario>();
					for (String u : usrs) {
						try {
							DataUsuario usr = this.controllerUsuario.infoUsuario(u);
							usuarios.add(usr);
						} catch (UsuarioNoEncontrado e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					request.setAttribute("usuarios", usuarios);
					
					request.getRequestDispatcher("/WEB-INF/pages/listarUsuarios.jsp").
							forward(request, response);
				
				}
				
			} catch (UsuarioNoEncontrado e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
	
	}
	

}
