
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import excepciones.UsuarioNoEncontrado;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import logica.controllers.IControllerUsuario;
import logica.controllers.IControllerEvento;
import logica.dataTypes.DTAsistente;
import logica.dataTypes.DTOrganizador;
import logica.dataTypes.DataUsuario;
import logica.models.Factory;

@MultipartConfig
@WebServlet({ "/usuarios", "/listarUsuarios", "/detalleUsuario", "/modificarDatos", "/perfil" })
public class ServletUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IControllerUsuario controllerUsuario;

    public ServletUsuario() {
        super();
        Factory factory = Factory.getInstance();
        this.controllerUsuario = factory.getControllerUsuario();
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DataUsuario usrSession = (DataUsuario) request.getSession().getAttribute("usuario");
        String usuario = usrSession != null ? usrSession.getNickname() : null;
        String usuarios = request.getParameter("usuarios");
        String path    = request.getServletPath();

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
                    request.setAttribute("detalleUsuario", org);
                } else {
                    DTAsistente asis = controllerUsuario.infoAsistente(usuario);
                    request.setAttribute("detalleUsuario", asis);
                }

                // Imagen de perfil
                String dirUsuarios   = getServletContext().getRealPath("/uploads/usuarios/");
                String nombreArchivo = ManejadorArchivos.buscarArchivo(usuario.toLowerCase(), dirUsuarios);
                if (nombreArchivo != null) {
                    request.setAttribute("imagenUsuario", "uploads/usuarios/" + nombreArchivo);
                } else {
                    request.setAttribute("imagenUsuario", "uploads/usuarios/default.jpg");
                }

                if ("1".equals(request.getParameter("ok"))) {
                    request.setAttribute("mensaje", "Modificaciones realizadas exitosamente.");
                }

                request.getRequestDispatcher("/WEB-INF/pages/modificarDatos.jsp").forward(request, response);
                return;

            } catch (UsuarioNoEncontrado e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
                return;
            } catch (Exception e) {
                request.setAttribute("error", "No se pudieron cargar los datos: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/pages/modificarDatos.jsp").forward(request, response);
                return;
            }
        }

  
        switch (path) {
            case "/usuarios":
            case "/listarUsuarios":
                listarUsuarios(request, response);
                break;

            case "/perfil":
                perfil(request, response);
                break;

            case "/detalleUsuario":
                try {
                    detalleUsuario(request, response);
                } catch (UsuarioNoEncontrado e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String path = request.getServletPath();

        if ("/modificarDatos".equals(path) && usuario != null && !usuario.isBlank()) {
            modificarDatos(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
    }

    
    private void detalleUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UsuarioNoEncontrado {

        String usuario = request.getParameter("usuarios"); 
        if (usuario == null || usuario.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro 'usuarios'");
            return;
        }

        
        DataUsuario sessionUser = (DataUsuario) request.getSession().getAttribute("usuario");
        if (sessionUser != null && sessionUser.getNickname() != null
                && sessionUser.getNickname().equalsIgnoreCase(usuario)) {
            response.sendRedirect(request.getContextPath() + "/perfil");
            return;
        }

        DataUsuario usr;
        try {
            usr = this.controllerUsuario.infoUsuario(usuario);
        } catch (UsuarioNoEncontrado ex) {
            response.sendError(404);
            request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
            return;
        }

        
        request.setAttribute("usuario", usr);

        if (usr.getTipo() == DataUsuario.TipoUsuario.ORGANIZADOR) {
            DTOrganizador org = this.controllerUsuario.infoOrganizador(usuario);
            request.setAttribute("detalleUsuario", org);

            
            try {
                IControllerEvento ICE = Factory.getInstance().getControllerEvento();
                Set<String> ediciones = this.controllerUsuario.listarEdicionesOrganizadas(usuario);
                request.setAttribute("ediciones", ediciones);

                Map<String, String> edicionesMap = new HashMap<>();
                String dirEdiciones = getServletContext().getRealPath("/uploads/ediciones/");
                if (ediciones != null) {
                    for (String ed : ediciones) {
                        String nombreArchivo = ManejadorArchivos.buscarArchivo(ed.toLowerCase(), dirEdiciones);
                        if (nombreArchivo != null) {
                            edicionesMap.put(ed, "uploads/ediciones/" + nombreArchivo);
                        } else {
                            edicionesMap.put(ed, "assets/images/SinFoto.jpg");
                        }
                    }
                }
                request.setAttribute("edicionesMap", edicionesMap);

            } catch (Exception ignore) {
                request.setAttribute("ediciones", java.util.Collections.emptySet());
                request.setAttribute("edicionesMap", java.util.Collections.emptyMap());
            }
        } else {
            DTAsistente asis = this.controllerUsuario.infoAsistente(usuario);
            request.setAttribute("detalleUsuario", asis);
        }

        // set imagenUsuario attribute
        try {
            String dirUsuarios = getServletContext().getRealPath("/uploads/usuarios/");
            String nombreArchivo = ManejadorArchivos.buscarArchivo(usuario.toLowerCase(), dirUsuarios);
            if (nombreArchivo != null) {
                request.setAttribute("imagenUsuario", "uploads/usuarios/" + nombreArchivo);
            } else {
                request.setAttribute("imagenUsuario", "uploads/usuarios/default.jpg");
            }
        } catch (Exception ignore) {}

        request.getRequestDispatcher("/WEB-INF/pages/detalleUsuario.jsp").forward(request, response);
    }

    
    private void perfil(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataUsuario usrSession = (DataUsuario) request.getSession().getAttribute("usuario");
        String usuario = usrSession.getNickname();

        DataUsuario usr;
        try {
            usr = this.controllerUsuario.infoUsuario(usuario);
        } catch (UsuarioNoEncontrado ex) {
            response.sendError(404);
            request.getRequestDispatcher("/WEB-INF/errorPages/404.jsp").include(request, response);
            return;
        }

        if (usr.getTipo() == DataUsuario.TipoUsuario.ORGANIZADOR) {
            DTOrganizador org = this.controllerUsuario.infoOrganizador(usuario);
            request.setAttribute("usuario", org);
        } else {
            DTAsistente asis = this.controllerUsuario.infoAsistente(usuario);
            request.setAttribute("usuario", asis);
        }

       
        try {
            String dirUsuarios = getServletContext().getRealPath("/uploads/usuarios/");
            String nombreArchivo = ManejadorArchivos.buscarArchivo(usuario.toLowerCase(), dirUsuarios);
            if (nombreArchivo != null) {
                request.setAttribute("imagenUsuario", "uploads/usuarios/" + nombreArchivo);
            } else {
                request.setAttribute("imagenUsuario", "uploads/usuarios/default.jpg");
            }
        } catch (Exception ignore) {}

        request.getRequestDispatcher("/WEB-INF/pages/perfil.jsp").forward(request, response);
    }

    
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String q = trimOrNull(request.getParameter("q"));

        Set<String> usrs = this.controllerUsuario.listarUsuarios();
        try {
            if (usrs == null || usrs.isEmpty())
                throw new UsuarioNoEncontrado("No hay usuarios registrados");
            else {
                Set<DataUsuario> usuarios = new java.util.HashSet<DataUsuario>();
                Map<String, String> imgsUsuarios = new java.util.HashMap<>();
                String dirUsuarios = getServletContext().getRealPath("/uploads/usuarios/");

                for (String u : usrs) {
                    
                    if (q != null && !q.isBlank()) {
                        if (!u.toLowerCase().contains(q.toLowerCase())) {
                            continue;
                        }
                    }

                    try {
                        DataUsuario usr = this.controllerUsuario.infoUsuario(u);
                        usuarios.add(usr);

                        
                        try {
                            String nombreArchivo = ManejadorArchivos.buscarArchivo(u.toLowerCase(), dirUsuarios);
                            if (nombreArchivo != null) {
                                imgsUsuarios.put(u, "uploads/usuarios/" + nombreArchivo);
                            } else {
                                imgsUsuarios.put(u, "uploads/usuarios/default.jpg");
                            }
                        } catch (Exception ignore) {
                            imgsUsuarios.put(u, "uploads/usuarios/default.jpg");
                        }

                    } catch (UsuarioNoEncontrado e) {
                        e.printStackTrace();
                    }
                }

                request.setAttribute("usuarios", usuarios);
                request.setAttribute("imgsUsuarios", imgsUsuarios);
                request.setAttribute("q", q == null ? "" : q);
                request.getRequestDispatcher("/WEB-INF/pages/listarUsuarios.jsp").forward(request, response);
            }
        } catch (UsuarioNoEncontrado e1) {
            e1.printStackTrace();
            request.setAttribute("usuarios", java.util.Collections.emptySet());
            request.setAttribute("imgsUsuarios", java.util.Collections.emptyMap());
            request.setAttribute("q", q == null ? "" : q);
            request.getRequestDispatcher("/WEB-INF/pages/listarUsuarios.jsp").forward(request, response);
        }
    }

    
    private void modificarDatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nickParam = request.getParameter("usuario");
        if (nickParam == null || nickParam.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro 'usuario'");
            return;
        }

        // Debug logging: print incoming parameters to server console
        // System.out.println("[modificarDatos] start for usuario=" + nickParam);
        // System.out.println("[modificarDatos] received nombre=" + request.getParameter("nombre")
        //         + ", apellido=" + request.getParameter("apellido")
        //         + ", fechaNac=" + request.getParameter("fechaNac")
        //         + ", descripcion=" + request.getParameter("descripcion")
        //         + ", web=" + request.getParameter("web"));

        DataUsuario usr;
        try {
            usr = this.controllerUsuario.infoUsuario(nickParam);
        } catch (UsuarioNoEncontrado ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
            return;
        }

        try {
            if (usr.getTipo() == DataUsuario.TipoUsuario.ASISTENTE) {
                // editing ASISTENTE
                String nombre     = trimOrNull(request.getParameter("nombre"));
                String apellido   = trimOrNull(request.getParameter("apellido"));
                String fechaNacStr= trimOrNull(request.getParameter("fechaNac")); // YYYY-MM-DD

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
                // System.out.println("[modificarDatos] editarAsistente called");

            } else if (usr.getTipo() == DataUsuario.TipoUsuario.ORGANIZADOR) {
                // editing ORGANIZADOR
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
                // System.out.println("[modificarDatos] editarOrganizador called");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de usuario no soportado");
                return;
            }

            // Guardar avatar si vino adjunto
            try {
                Part avatar = request.getPart("avatar");
                if (avatar != null && avatar.getSize() > 0) {
                    ManejadorArchivos.guardarArchivo(avatar, nickParam, "usuarios", getServletContext());
    				String pfp = ManejadorArchivos.buscarArchivo(nickParam.toLowerCase(), getServletContext().getRealPath("/uploads/usuarios/"));
    				System.out.println("PFP seteada en sesión: " + pfp);
    				if (pfp != null) {
    					request.getSession().setAttribute("pfp", pfp);
    				} else {
    					request.getSession().setAttribute("pfp", "default.png");
    				}
                }
            } catch (Exception ignore) { /* no cortar el flujo por imagen */ }

            // Redirect back to modificarDatos with success message
            String url = request.getContextPath()
                    + "/modificarDatos?usuario="
                    + java.net.URLEncoder.encode(nickParam, java.nio.charset.StandardCharsets.UTF_8)
                    + "&ok=1";
            // System.out.println("[modificarDatos] redirecting to " + url);
            response.sendRedirect(url);
            return;

        } catch (Exception e) {
            request.setAttribute("error", "No se pudieron guardar los cambios: " + e.getMessage());
            request.setAttribute("usuario", usr);
            forwardEditar(request, response);
         }
     }

    
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

        Object uo = request.getAttribute("usuario");
        if (uo instanceof DataUsuario du) {
            try {
                String dirUsuarios   = getServletContext().getRealPath("/uploads/usuarios/");
                String nombreArchivo = ManejadorArchivos.buscarArchivo(du.getNickname().toLowerCase(), dirUsuarios);
                request.setAttribute("imagenUsuario",
                        nombreArchivo != null ? "uploads/usuarios/" + nombreArchivo : "uploads/usuarios/default.jpg");
            } catch (Exception ignore) {}
        }
        request.getRequestDispatcher("/WEB-INF/pages/modificarDatos.jsp").forward(request, response);
    }
}
