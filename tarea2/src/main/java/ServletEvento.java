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
import logica.controllers.IControllerEvento;
import logica.controllers.IControllerUsuario;
import logica.dataTypes.DTDetalleEdicion;
import logica.dataTypes.DTDetalleEvento;
import logica.dataTypes.DataUsuario;
import logica.dataTypes.DataUsuario.TipoUsuario;
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

    /**
     * Normaliza un texto removiendo acentos y convirtiéndolo a minúsculas
     * para hacer búsquedas insensibles a mayúsculas y acentos
     */
    private String normalizeText(String text) {
        if (text == null) return "";
        
        // Convertir a minúsculas
        String normalized = text.toLowerCase();
        
        // Remover acentos y diacríticos
        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        
        return normalized;
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
            
            // Filtro por nombre - búsqueda parcial insensible a mayúsculas y acentos
            if (incluirEvento && nombreBusqueda != null && !nombreBusqueda.trim().isEmpty()) {
                String nombreEventoNormalizado = normalizeText(nombreEvento);
                String busquedaNormalizada = normalizeText(nombreBusqueda.trim());
                incluirEvento = nombreEventoNormalizado.contains(busquedaNormalizada);
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
        
        // TEMPORAL - Para probar roles
        String testRole = request.getParameter("role");
        if (testRole != null) {
            if ("organizador".equals(testRole)) {
                DataUsuario testUser = new DataUsuario("test", "Test User", "test@test.com", TipoUsuario.ORGANIZADOR);
                request.getSession().setAttribute("usuario", testUser);
            } else if ("asistente".equals(testRole)) {
                DataUsuario testUser = new DataUsuario("test", "Test User", "test@test.com", TipoUsuario.ASISTENTE);
                request.getSession().setAttribute("usuario", testUser);
            } else if ("logout".equals(testRole)) {
                request.getSession().removeAttribute("usuario");
            }
        }
    	
        String nombreEvento = request.getParameter("nombre");
    	

       System.out.println("Nombre del evento recibido: " + nombreEvento); // Línea de depuración
        String imagenEvento = "/assets/images/eventos/" + nombreEvento.toLowerCase() + ".jpg";
        DTDetalleEvento detalleEvento = controllerEvento.verDetalleEvento(nombreEvento);
        Set<String> nombresEdiciones;
        
        // Verificar el tipo de usuario para listar ediciones
        DataUsuario usuario = (DataUsuario) request.getSession().getAttribute("usuario");
        if (usuario != null && usuario.getTipo() == TipoUsuario.ORGANIZADOR) {
            // Organizador: listar todas las ediciones (confirmadas y no confirmadas)
            nombresEdiciones = controllerEvento.listarEdiciones(nombreEvento);
        } else {
            // Asistente o no logueado: listar solo ediciones confirmadas
            nombresEdiciones = controllerEvento.listarEdicionesConfirmadas(nombreEvento);
        }
        
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
            edicionMinima.put("imagenEdicion", "/assets/images/ediciones/" + nombreEdicion.toLowerCase() + ".jpg");
            
            // Incluir estado solo si el usuario es organizador
            if (usuario != null && usuario.getTipo() == TipoUsuario.ORGANIZADOR) {
                edicionMinima.put("estado", detalleEdicion.getEstado());
            }
            
            edicionesMinimas.add(edicionMinima);
        }
        
     
        
        request.setAttribute("evento", detalleEvento);
        request.setAttribute("imagenEvento", imagenEvento);
        request.setAttribute("ediciones", edicionesMinimas);
     
        
        request.getRequestDispatcher("/WEB-INF/pages/detalleEvento.jsp").forward(request, response);
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
                    String nombreImagen = nombre.toLowerCase()+ extension;
                    
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
                // Usar fecha hardcodeada en lugar de LocalDate.now()
                fechaEvento = LocalDate.of(2025, 1, 15);
            }
            
            controllerEvento.altaEvento(nombre != null ? nombre.trim() : "", 
                                      sigla != null ? sigla.trim() : "", 
                                      fechaEvento, 
                                      descripcion != null ? descripcion.trim() : "", 
                                      categorias);
            
          
            
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
            // Por defecto, mostrar el componente sidebar con las categorías cargadas
            request.getRequestDispatcher("/WEB-INF/pages/componentes/categorias-sidebar.jsp").forward(request, response);
        }
    }
    
}