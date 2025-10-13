import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import excepciones.UsuarioNoEncontrado;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import logica.controllers.IControllerUsuario;
import logica.dataTypes.DTAsistente;
import logica.dataTypes.DTOrganizador;
import logica.dataTypes.DataUsuario;
import logica.models.Factory;
import main.java.ManejadorArchivos;

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

        String usuario = request.getParameter("usuario");
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
                    request.setAttribute("usuarios", org);
                } else {
                    DTAsistente asis = controllerUsuario.infoAsistente(usuario);
                    request.setAttribute("usuarios", asis);
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

        // Route by servlet path (endpoints) instead of 'action' param
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

        String usuario = request.getParameter("usuarios"); // OJO: aquí usa 'usuarios'
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
            request.setAttribute("usuarios", org);
        } else {
            DTAsistente asis = this.controllerUsuario.infoAsistente(usuario);
            request.setAttribute("usuarios", asis);
        }

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

        request.getRequestDispatcher("/WEB-INF/pages/perfil.jsp").forward(request, response);
    }

    
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
                        e.printStackTrace();
                    }
                }
                request.setAttribute("usuarios", usuarios);
                request.getRequestDispatcher("/WEB-INF/pages/listarUsuarios.jsp").forward(request, response);
            }
        } catch (UsuarioNoEncontrado e1) {
            e1.printStackTrace();
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

            // Guardar avatar si vino adjunto
            try {
                Part avatar = request.getPart("avatar");
                if (avatar != null && avatar.getSize() > 0) {
                    ManejadorArchivos.guardarArchivo(avatar, nickParam, "usuarios", getServletContext());
                }
            } catch (Exception ignore) { /* no cortar el flujo por imagen */ }

            // Redirect back to modificarDatos con mensaje ok
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
