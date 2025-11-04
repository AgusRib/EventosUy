import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
// Agregar imports necesarios para conversión de fechas
import javax.xml.datatype.XMLGregorianCalendar;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import webservices.DataUsuario;
import webservices.DtDetalleEdicion;
import webservices.DtDetalleEvento;
import webservices.HashSet;
import webservices.PublicadorEvento;
import webservices.PublicadorEventoService;
import webservices.PublicadorUsuario;
import webservices.PublicadorUsuarioService;
import webservices.TipoUsuario;
import webservices.WrapperHashSet;

/**
 * Servlet implementation class EventosServlet
 */
@WebServlet({ "/eventos", "/listarEventos", "/detalleEvento", "/altaEvento", "/categorias" })
@MultipartConfig
public class ServletEvento extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
   public ServletEvento() {
        super();
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
        
        PublicadorEventoService serviceEvento = new PublicadorEventoService();
        PublicadorEvento portEvento = serviceEvento.getPublicadorEventoPort();
    	
        PublicadorUsuarioService serviceUsuario = new PublicadorUsuarioService();
        PublicadorUsuario portUsuario = serviceUsuario.getPublicadorUsuarioPort();
        
        switch (path) {
            case "/eventos":
            case "/listarEventos": {
                listarEventos(request, response, portEvento);
                return;
            }
            case "/detalleEvento": {
                mostrarDetalleEvento(request, response, portEvento);
                return;
            }
            case "/categorias": {
                listarCategorias(request, response, portEvento);
                return;
            }
            case "/altaEvento": {
                // Simplemente cargar el formulario - sin redirects
                request.setAttribute("destino", "altaEvento");
                request.setAttribute("error", null);
                // Clear any previous success message when opening the form
                request.setAttribute("mensaje", null);
                listarCategorias(request, response, portEvento);
                return;
            }
            default:
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
    	String path = request.getServletPath();
        
        if (path.equals("/altaEvento")) {
            PublicadorEventoService serviceEvento = new PublicadorEventoService();
            PublicadorEvento portEvento = serviceEvento.getPublicadorEventoPort();
            
            crearEvento(request, response, portEvento);
        } 
    }
    
    private void listarEventos(HttpServletRequest request, HttpServletResponse response, PublicadorEvento portEvento) throws ServletException, IOException {
        String categoria = request.getParameter("categoria");
        String nombreBusqueda = request.getParameter("nombre"); 
        
        try {
            WrapperHashSet todosLosEventosWrapper = portEvento.listarEventos();
            List<Object> todosLosEventosObj = todosLosEventosWrapper.getItem();
            Set<String> todosLosEventos = new java.util.HashSet<>();
            for (Object obj : todosLosEventosObj) {
                todosLosEventos.add((String) obj);
            }
            
            Set<java.util.Map<String, Object>> eventosInfo = new java.util.LinkedHashSet<>();
            Set<String> eventosFiltrados = new java.util.LinkedHashSet<>();
            
            for (String nombreEvento : todosLosEventos) {
                DtDetalleEvento detalleEvento = portEvento.verDetalleEvento(nombreEvento);
                boolean incluirEvento = true;
                
                // Filtro por categoría 
                if (categoria != null && !categoria.trim().isEmpty() && !categoria.equals("todas")) {
            
                    List<String> categoriasObj = detalleEvento.getCategorias();
                    Set<String> categorias = new java.util.HashSet<>();
                    for (String cat : categoriasObj) {
                        categorias.add((String) cat);
                    }
                    incluirEvento = categorias.contains(categoria);
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
                    
                    // Fetch imagen de evento (copiado de ServletEdicion)
                    String eventoImg = ManejadorArchivos.buscarArchivo(nombreEvento.toLowerCase(), getServletContext().getRealPath("/uploads/eventos/"));
                    if (eventoImg != null) {
                        eventoInfo.put("imagenEvento", "uploads/eventos/" + eventoImg);
                    } else {
                        eventoInfo.put("imagenEvento", "uploads/eventos/default.jpg");
                    }
                    
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
        } catch (Exception e) {
            response.getWriter().append(e.getMessage());
        }
    }
    
    private void mostrarDetalleEvento(HttpServletRequest request, HttpServletResponse response, PublicadorEvento portEvento) throws ServletException, IOException {
        String nombreEvento = request.getParameter("nombre");

        System.out.println("Nombre del evento recibido: " + nombreEvento);
        
        try {
            DtDetalleEvento detalleEvento = portEvento.verDetalleEvento(nombreEvento);
            
            // Fetch imagen de evento (copiado de ServletEdicion)
            String eventoImg = ManejadorArchivos.buscarArchivo(nombreEvento.toLowerCase(), getServletContext().getRealPath("/uploads/eventos/"));
            if (eventoImg != null) {
                request.setAttribute("imagenEvento", "uploads/eventos/" + eventoImg);
            } else {
                request.setAttribute("imagenEvento", "uploads/eventos/default.jpg");
            }
            
            // Verificar el tipo de usuario para listar ediciones
            DataUsuario usuario = (DataUsuario) request.getSession().getAttribute("usuario");
            
            // Obtener todas las ediciones (confirmadas y no confirmadas)
            List<Object> todasLasEdicionesObj = portEvento.listarEdiciones(nombreEvento).getItem();
            Set<String> todasLasEdiciones = new java.util.HashSet<>();
            for (Object obj : todasLasEdicionesObj) {
                todasLasEdiciones.add((String) obj);
            }
            
            List<Object> edicionesConfirmadasObj = portEvento.listarEdicionesConfirmadas(nombreEvento).getItem();
            Set<String> edicionesConfirmadas = new java.util.HashSet<>();
            for (Object obj : edicionesConfirmadasObj) {
                edicionesConfirmadas.add((String) obj);
            }
            
            Set<java.util.Map<String, Object>> edicionesMinimas = new java.util.LinkedHashSet<>();
            
            // Para cada nombre de edición, verificar si debe mostrarse
            for (String nombreEdicion : todasLasEdiciones) {
                DtDetalleEdicion detalleEdicion = portEvento.mostrarDetallesEdicion(nombreEdicion);
                
                // Verificar si el usuario es el organizador específico de esta edición
                boolean esOrganizadorDeEstaEdicion = usuario != null && 
                                                   usuario.getTipo() == TipoUsuario.ORGANIZADOR && 
                                                   usuario.getNickname().equals(detalleEdicion.getOrganizador());
                
                // Determinar si mostrar esta edición:
                // - Si es organizador de esta edición: mostrar siempre
                // - Si no es organizador de esta edición: mostrar solo si está confirmada
                boolean mostrarEdicion = esOrganizadorDeEstaEdicion || edicionesConfirmadas.contains(nombreEdicion);
                
                if (mostrarEdicion) {
                    java.util.Map<String, Object> edicionMinima = new java.util.HashMap<>();
                    edicionMinima.put("nombre", detalleEdicion.getNombre());
                    edicionMinima.put("ciudad", detalleEdicion.getCiudad());
                    edicionMinima.put("pais", detalleEdicion.getPais());
                    
                    // Convertir XMLGregorianCalendar a LocalDate
                    XMLGregorianCalendar xmlFechaInicio = detalleEdicion.getFechaInicio();
                    XMLGregorianCalendar xmlFechaFin = detalleEdicion.getFechaFin();
                    
                    LocalDate fechaInicio = null;
                    LocalDate fechaFin = null;
                    
                    if (xmlFechaInicio != null) {
                        fechaInicio = xmlFechaInicio.toGregorianCalendar().toZonedDateTime().toLocalDate();
                    }
                    if (xmlFechaFin != null) {
                        fechaFin = xmlFechaFin.toGregorianCalendar().toZonedDateTime().toLocalDate();
                    }
                    
                    edicionMinima.put("fechaInicio", fechaInicio);
                    edicionMinima.put("fechaFin", fechaFin);
                    
                    // Fetch imagen de edicion (copiado de ServletEdicion)
                    String edicionImg = ManejadorArchivos.buscarArchivo(detalleEdicion.getNombre().toLowerCase(), getServletContext().getRealPath("/uploads/ediciones/"));
                    if (edicionImg != null) {
                        edicionMinima.put("imagenEdicion", "uploads/ediciones/" + edicionImg);
                    } else {
                        edicionMinima.put("imagenEdicion", "uploads/ediciones/default.jpg");
                    }
                    
                    // Incluir estado solo si el usuario es el organizador específico de esta edición
                    if (esOrganizadorDeEstaEdicion) {
                        edicionMinima.put("estado", detalleEdicion.getEstado());
                    }
                    edicionMinima.put("esOrganizadorDeEstaEdicion", esOrganizadorDeEstaEdicion);
                    
                    edicionesMinimas.add(edicionMinima);
                }
            }
            
            request.setAttribute("evento", detalleEvento);
            request.setAttribute("ediciones", edicionesMinimas);
         
            
            request.getRequestDispatcher("/WEB-INF/pages/detalleEvento.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().append(e.getMessage());
        }
    }
   
    private void crearEvento(HttpServletRequest request, HttpServletResponse response, PublicadorEvento portEvento) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String sigla = request.getParameter("sigla");
        String descripcion = request.getParameter("descripcion");
        
        try {
            // Obtener las categorías seleccionadas desde la request
            String[] categoriasArray = request.getParameterValues("categorias");
            WrapperHashSet categorias = new WrapperHashSet();
            if (categoriasArray != null) {
                for (String c : categoriasArray) {
                    if (c != null && !c.trim().isEmpty()) {
                        categorias.getItem().add(c.trim());;
                    }
                }
            }
          
            // Manejar la imagen usando ManejadorArchivos
            Part imagenPart = request.getPart("imagen");
            ManejadorArchivos.guardarArchivo(imagenPart, nombre.toLowerCase(), "eventos", getServletContext());
            
            LocalDate fechaEvento = (LocalDate) request.getSession().getAttribute("fecha");
            if (fechaEvento == null) {
                // Usar fecha hardcodeada en lugar de LocalDate.now()
                fechaEvento = LocalDate.of(2025, 1, 15);
            }
            
            portEvento.altaEvento(nombre != null ? nombre.trim() : "", 
                                sigla != null ? sigla.trim() : "", 
                                fechaEvento.toString(), 
                                descripcion != null ? descripcion.trim() : "", 
                                categorias);
            
            // Mostrar mensaje de registro exitoso en la misma página de alta (como en altaEdicion)
            WrapperHashSet todasLasCategoriasWrapper = portEvento.listarCategorias();
            List<Object> todasLasCategoriasObj = todasLasCategoriasWrapper.getItem();
            Set<String> todasLasCategorias = new java.util.HashSet<>();
            for (Object obj : todasLasCategoriasObj) {
                todasLasCategorias.add((String) obj);
            }
            request.setAttribute("categorias", todasLasCategorias);
            // Clear any previous error and set success message
            request.setAttribute("error", null);
            request.setAttribute("mensaje", "Evento creado exitosamente.");
            // Preservar valores del formulario por si el usuario quiere crear otra cosa
            request.setAttribute("nombre", nombre);
            request.setAttribute("sigla", sigla);
            request.setAttribute("descripcion", descripcion);
            // Ensure no stale category selections remain
            request.setAttribute("categoriasSeleccionadas", null);
            request.getRequestDispatcher("/WEB-INF/pages/AltaEvento.jsp").forward(request, response);
             
        } catch(Exception e) {
            // Cargar las categorías para que el JSP pueda mostrar el dropdown
            try {
                WrapperHashSet todasLasCategoriasWrapper = portEvento.listarCategorias();
                List<Object> todasLasCategoriasObj = todasLasCategoriasWrapper.getItem();
                Set<String> todasLasCategorias = new java.util.HashSet<>();
                for (Object obj : todasLasCategoriasObj) {
                    todasLasCategorias.add((String) obj);
                }
                request.setAttribute("categorias", todasLasCategorias);
            } catch (Exception ex) {
                // Si no se pueden cargar las categorías, usar conjunto vacío
                request.setAttribute("categorias", new java.util.HashSet<String>());
            }
            
            String errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.contains("Ya existe un evento")) {
                request.setAttribute("error", "Ya existe un evento con el nombre ingresado.");
            } else {
                request.setAttribute("error", "Error al crear evento: " + errorMessage);
            }
            
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
    
    private void listarCategorias(HttpServletRequest request, HttpServletResponse response, PublicadorEvento portEvento) throws ServletException, IOException {
        try {
            WrapperHashSet todasLasCategoriasWrapper = portEvento.listarCategorias();
            List<Object> todasLasCategoriasObj = todasLasCategoriasWrapper.getItem();
            Set<String> todasLasCategorias = new java.util.HashSet<>();
            for (Object obj : todasLasCategoriasObj) {
                todasLasCategorias.add((String) obj);
            }
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
                request.getRequestDispatcher("/WEB-INF/pages/componente/categorias-sidebar.jsp").forward(request, response);
            }
        } catch (Exception e) {
            response.getWriter().append(e.getMessage());
        }
    }
}