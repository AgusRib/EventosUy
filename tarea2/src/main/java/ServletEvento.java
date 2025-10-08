package java;


import java.time.LocalDate;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logica.controllers.IControllerEvento;
import logica.controllers.IControllerUsuario;
import logica.models.Factory;
import logica.dt.DTDetalleEvento;
import logica.dt.DTDetalleEdicion;
import excepciones.nombreEventoExcepcion;

/**
 * Servlet implementation class EventosServlet
 */
@WebServlet("/eventos")
public class eventos extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private IControllerEvento controllerEvento;
    private IControllerUsuario controllerUsuario;
    
   public eventos() {
        super();
        Factory factory = Factory.getInstance();
        this.controllerEvento = factory.getIControllerEvento();
        this.controllerUsuario = factory.getIControllerUsuario();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null || action.equals("listar")) {
            listarEventos(request, response);
        } else if (action.equals("detalle")) {
            mostrarDetalleEvento(request, response);
        } else if (action.equals("categorias")) {
            listarCategorias(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action != null && action.equals("crear")) {
            crearEvento(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
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
                eventoInfo.put("imagenEvento", "/images/eventos/" + nombreEvento.toLowerCase() + ".jpg");
                eventosInfo.add(eventoInfo);
            }
        }
        
        request.setAttribute("eventos", eventosFiltrados);                    
        request.setAttribute("eventosInfo", eventosInfo);      
        request.setAttribute("categoriaSeleccionada", categoria);
        request.setAttribute("nombreBusqueda", nombreBusqueda); 
        request.setAttribute("totalEventos", todosLosEventos.size());
        request.setAttribute("eventosFiltrados", eventosFiltrados.size());
        
        request.getRequestDispatcher("/listarEventos.jsp").forward(request, response);
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
        
        request.getRequestDispatcher("/consultaEventoDinamico.jsp").forward(request, response);
    }
   
    private void crearEvento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String sigla = request.getParameter("sigla");
        String descripcion = request.getParameter("descripcion");
        
        try {
            // Obtener las categorías seleccionadas desde la request (puede venir como múltiples valores)
            String[] categoriasArray = request.getParameterValues("categorias");
            Set<String> categorias = new java.util.LinkedHashSet<>();
            if (categoriasArray != null) {
                for (String c : categoriasArray) {
                    if (c != null && !c.trim().isEmpty()) {
                        categorias.add(c);
                    }
                }
            }
            
            LocalDate fechaEvento = (LocalDate) request.getSession().getAttribute("fecha");
            if (fechaEvento == null) {
                fechaEvento = LocalDate.now();
            }
            
            controllerEvento.altaEvento(nombre, sigla, fechaEvento, descripcion, categorias);
            
           
            request.setAttribute("mensaje", "El evento '" + nombre + "' ha sido creado exitosamente.");
            request.setAttribute("tipoMensaje", "success");
            request.setAttribute("nombre", nombre);
            mostrarDetalleEvento(request, response);
            
        } catch(nombreEventoExcepcion e) {
            
            request.setAttribute("error", "Ya existe un evento con el nombre ingresado.");
            request.setAttribute("nombre", nombre);
            request.setAttribute("sigla", sigla);
            request.setAttribute("descripcion", descripcion);
            request.getRequestDispatcher("/altaEvento.jsp").forward(request, response);
            
        }
        catch (Exception e) {
            request.setAttribute("error", "Error al crear evento: " + e.getMessage());
            request.getRequestDispatcher("/altaEvento.jsp").forward(request, response);
        }
    }
    
    private void listarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Set<String> todasLasCategorias = controllerEvento.listarCategorias();
        request.setAttribute("categorias", todasLasCategorias);
        
        // Verificar si viene un parámetro que indique dónde mostrar las categorías
        String destino = request.getParameter("destino");
        
        if ("altaEvento".equals(destino)) {
            // Si es para el formulario de alta de evento
            request.getRequestDispatcher("/altaEvento.jsp").forward(request, response);
        } else {
            // Por defecto, mostrar la página de categorías
            request.getRequestDispatcher("/categorias.jsp").forward(request, response);
        }
    }
}
