import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Set;

import excepciones.NombreEventoExcepcion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import logica.controllers.IControllerEvento;
import logica.controllers.IControllerUsuario;
import logica.dataTypes.DTDetalleEdicion;
import logica.dataTypes.DTDetalleEvento;
import logica.models.Factory;

/**
 * Servlet implementation class EventosServlet
 */
@WebServlet({ "/eventos", "/listarEventos", "/detalleEvento", "/altaEvento", "/categorias" })
@MultipartConfig
public class ServletEvento extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private IControllerEvento controllerEvento;
    private IControllerUsuario controllerUsuario;
    
   public ServletEvento() {
        super();
        Factory factory = Factory.getInstance();
        this.controllerEvento = factory.getControllerEvento();
        this.controllerUsuario = factory.getControllerUsuario();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
        String path = request.getServletPath();
        
        switch (path) {
            case "/eventos":
            case "/listarEventos": {
                listarEventos(request, response);
                return;
            }
            case "/detalleEvento": {
                mostrarDetalleEvento(request, response);
                return;
            }
            case "/categorias": {
                listarCategorias(request, response);
                return;
            }
            case "/altaEvento": {
                // Simplemente cargar el formulario - sin redirects
                request.setAttribute("destino", "altaEvento");
                request.setAttribute("error", null); // ESTA ES LA LÍNEA QUE FALTABA
                listarCategorias(request, response);
                return;
            }
            default:
                break;
        }
        
      
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        
        if (path.equals("/altaEvento")) {
            crearEvento(request, response);
        } 
    }
    
    private void listarEventos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoria = request.getParameter("categoria");
        String nombreBusqueda = request.getParameter("nombre"); 
        Set<String> todosLosEventos = controllerEvento.listarEventos();
        Set<java.util.Map<String, Object>> eventosInfo = new java.util.LinkedHashSet<>();
        Set<String> eventosFiltrados = new java.util.LinkedHashSet<>();
        
        for (String nombreEvento : todosLosEventos) {
            DTDetalleEvento detalleEvento = controllerEvento.verDetalleEvento(nombreEvento);
            boolean incluirEvento = true;
            
            // Filtro por categoría 
            if (categoria != null && !categoria.trim().isEmpty() && !categoria.equals("todas")) {
                incluirEvento = detalleEvento.getCategorias() != null && 
                               detalleEvento.getCategorias().contains(categoria);
            }
            
            // Filtro por nombre - búsqueda parcial case-insensitive
            if (incluirEvento && nombreBusqueda != null && !nombreBusqueda.trim().isEmpty()) {
                String nombreEventoLower = nombreEvento.toLowerCase();
                String busquedaLower = nombreBusqueda.trim().toLowerCase();
                incluirEvento = nombreEventoLower.contains(busquedaLower);
            }
            
            if (incluirEvento) {
                eventosFiltrados.add(nombreEvento);
                java.util.Map<String, Object> eventoInfo = new java.util.HashMap<>();
                eventoInfo.put("nombre", detalleEvento.getNombre());
                eventoInfo.put("descripcion", detalleEvento.getDescripcion());
                eventoInfo.put("imagenEvento", "/assets/images/eventos/" + nombreEvento.toLowerCase() + ".jpg");
                eventosInfo.add(eventoInfo);
            }
        }
        
        request.setAttribute("eventos", eventosFiltrados);                    
        request.setAttribute("eventosInfo", eventosInfo);      
        request.setAttribute("categoriaSeleccionada", categoria);
        request.setAttribute("nombreBusqueda", nombreBusqueda); 
        request.setAttribute("totalEventos", todosLosEventos.size());
        request.setAttribute("eventosFiltrados", eventosFiltrados.size());
        
        request.getRequestDispatcher("/WEB-INF/pages/listarEventos.jsp").forward(request, response);
    }
    
    private void mostrarDetalleEvento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombreEvento = request.getParameter("nombre");
        String imagenEvento = "/images/eventos/" + nombreEvento.toLowerCase() + ".jpg";
        DTDetalleEvento detalleEvento = controllerEvento.verDetalleEvento(nombreEvento);
        Set<String> nombresEdiciones = controllerEvento.listarEdiciones(nombreEvento);
        Set<java.util.Map<String, Object>> edicionesMinimas = new java.util.LinkedHashSet<>();
        
        // Para cada nombre de edición, obtener solo los datos que necesitamos
        for (String nombreEdicion : nombresEdiciones) {
            DTDetalleEdicion detalleEdicion = controllerEvento.mostrarDetallesEdicion(nombreEdicion);
            java.util.Map<String, Object> edicionMinima = new java.util.HashMap<>();
            edicionMinima.put("nombre", detalleEdicion.getNombre());
            edicionMinima.put("ciudad", detalleEdicion.getCiudad());
            edicionMinima.put("pais", detalleEdicion.getPais());
            edicionMinima.put("fechaInicio", detalleEdicion.getFechaInicio());
            edicionMinima.put("fechaFin", detalleEdicion.getFechaFin());
            edicionMinima.put("imagenEdicion", "/images/ediciones/" + nombreEdicion.toLowerCase() + ".jpg");
            edicionesMinimas.add(edicionMinima);
        }
        
        Set<String> todasLasCategorias = controllerEvento.listarCategorias();
        
        request.setAttribute("evento", detalleEvento);
        request.setAttribute("imagenEvento", imagenEvento);
        request.setAttribute("ediciones", edicionesMinimas);
        request.setAttribute("categorias", todasLasCategorias);
        
        request.getRequestDispatcher("/WEB-INF/pages/consultaEventoDinamico.jsp").forward(request, response);
    }
   
    private void crearEvento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String sigla = request.getParameter("sigla");
        String descripcion = request.getParameter("descripcion");
        
        try {
            // Obtener las categorías seleccionadas desde la request
            String[] categoriasArray = request.getParameterValues("categorias");
            Set<String> categorias = new java.util.LinkedHashSet<>();
            if (categoriasArray != null) {
                for (String c : categoriasArray) {
                    if (c != null && !c.trim().isEmpty()) {
                        categorias.add(c);
                    }
                }
            }
          

            // Manejar la imagen directamente
            Part imagenPart = request.getPart("imagen");
            if (imagenPart != null && imagenPart.getSize() > 0) {
                String nombreoriginal = imagenPart.getSubmittedFileName();
                if (nombreoriginal != null && !nombreoriginal.trim().isEmpty()) {
                    // Obtener extensión del archivo
                    String extension = nombreoriginal.contains(".") ? 
                        nombreoriginal.substring(nombreoriginal.lastIndexOf(".")) : ".jpg";
                    
                    // Crear nombre del archivo
                    String nombreImagen = nombre.toLowerCase().replaceAll("[^a-z0-9]", "") + extension;
                    
                    // Obtener la ruta física real del directorio webapp
                    String rutaWebapp = request.getServletContext().getRealPath("/");
                    String rutaImagenes = rutaWebapp + "assets/images/eventos/";
                    
                    // Crear directorio si no existe
                    Path directorioImagenes = Paths.get(rutaImagenes);
                    if (!Files.exists(directorioImagenes)) {
                        Files.createDirectories(directorioImagenes);
                    }
                    
                    // Ruta completa del archivo
                    Path rutaCompleta = Paths.get(rutaImagenes + nombreImagen);
                    
                    // Guardar archivo
                    try (InputStream input = imagenPart.getInputStream()) {
                        Files.copy(input, rutaCompleta, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
            
            LocalDate fechaEvento = (LocalDate) request.getSession().getAttribute("fecha");
            if (fechaEvento == null) {
                fechaEvento = LocalDate.now();
            }
            
            controllerEvento.altaEvento(nombre != null ? nombre.trim() : "", 
                                      sigla != null ? sigla.trim() : "", 
                                      fechaEvento, 
                                      descripcion != null ? descripcion.trim() : "", 
                                      categorias);
            
            // Limpiar cualquier dato de error que pueda haber quedado en la sesión
            request.getSession().removeAttribute("altaEvento_error");
            request.getSession().removeAttribute("altaEvento_nombre");
            request.getSession().removeAttribute("altaEvento_sigla");
            request.getSession().removeAttribute("altaEvento_descripcion");
            request.getSession().removeAttribute("altaEvento_categorias");
            
            // Usar redirect para ir al detalle del evento creado
            response.sendRedirect(request.getContextPath() + "/detalleEvento?nombre=" + 
                java.net.URLEncoder.encode(nombre, "UTF-8"));
            
        } catch(NombreEventoExcepcion e) {
            // Cargar las categorías para que el JSP pueda mostrar el dropdown
            Set<String> todasLasCategorias = controllerEvento.listarCategorias();
            request.setAttribute("categorias", todasLasCategorias);
            
            request.setAttribute("error", "Ya existe un evento con el nombre ingresado.");
            request.setAttribute("nombre", nombre);
            request.setAttribute("sigla", sigla);
            request.setAttribute("descripcion", descripcion);
            
            // Preservar categorías seleccionadas
            String[] categoriasSeleccionadas = request.getParameterValues("categorias");
            if (categoriasSeleccionadas != null) {
                request.setAttribute("categoriasSeleccionadas", categoriasSeleccionadas);
            }
            
            request.getRequestDispatcher("/WEB-INF/pages/AltaEvento.jsp").forward(request, response);
            
        } catch (Exception e) {
            // Cargar las categorías también para otros errores
            Set<String> todasLasCategorias = controllerEvento.listarCategorias();
            request.setAttribute("categorias", todasLasCategorias);
            
            request.setAttribute("error", "Error al crear evento: " + e.getMessage());
            request.setAttribute("nombre", nombre);
            request.setAttribute("sigla", sigla);
            request.setAttribute("descripcion", descripcion);
            
            // Preservar categorías seleccionadas
            String[] categoriasSeleccionadas = request.getParameterValues("categorias");
            if (categoriasSeleccionadas != null) {
                request.setAttribute("categoriasSeleccionadas", categoriasSeleccionadas);
            }
            
            request.getRequestDispatcher("/WEB-INF/pages/AltaEvento.jsp").forward(request, response);
        }
    }
    
    private void listarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Set<String> todasLasCategorias = controllerEvento.listarCategorias();
        request.setAttribute("categorias", todasLasCategorias);
        
        // Verificar si viene un parámetro o atributo que indique dónde mostrar las categorías
        String destino = request.getParameter("destino");
        if (destino == null) {
            destino = (String) request.getAttribute("destino");
        }
        
        if ("altaEvento".equals(destino)) {
            // Si es para el formulario de alta de evento
            request.getRequestDispatcher("/WEB-INF/pages/AltaEvento.jsp").forward(request, response);
        } else {
            // Por defecto, mostrar la página de categorías
            request.getRequestDispatcher("/WEB-INF/pages/categorias.jsp").forward(request, response);
        }
    }
    
}