package main.java;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.controllers.IControllerEvento;
import logica.controllers.IControllerUsuario;
import logica.dataTypes.DTDetalleEdicion;
import logica.dataTypes.DTPatrocinio;
import logica.dataTypes.DTTipoRegistro;
import logica.dataTypes.DataUsuario;
import logica.dataTypes.DataUsuario.TipoUsuario;
import logica.models.Factory;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import excepciones.FechaInicioPOSTFINAL;
import excepciones.FechaInicioPREALTA;
import excepciones.NombreEdicionExistenteExcepcion;
import excepciones.UsuarioNoEncontrado;

@MultipartConfig
@WebServlet({ "/detalleEdicion", "/altaEdicion", "/altaRegistro", "/listarEdiciones", "/detalleEdicion/altaEdicion" })
public class ServletEdicion extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEdicion() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Use servletPath to determine which mapping was called
        String path = request.getServletPath();
        
        switch (path) {
            case "/detalleEdicion": {  // EJEMPLO: /detalleEdicion?nombre=edicion1
            	// TODO Manejar excepciones y mostrar mensajes de error en la JSP
            	
            	IControllerEvento ICE = (IControllerEvento) Factory.getInstance().getControllerEvento();
            	IControllerUsuario ICU = (IControllerUsuario) Factory.getInstance().getControllerUsuario();
                String nombre = request.getParameter("nombre");
                
                try {
                	// Fetch detalle de edicion
					DTDetalleEdicion ed = ICE.mostrarDetallesEdicion(nombre);
	                request.setAttribute("edicion", ed);
	                
	                // Fetch tipos de Registro
	                Set<DTTipoRegistro> tiposReg = new HashSet<>();
	                for (String tr : ICE.listarTiposDeRegistro(nombre)) {
	                	tiposReg.add(ICE.verDetalleTRegistro(nombre, tr));
	                }
	                request.setAttribute("tiposRegistro", tiposReg);

	                // Fetch Patrocinios
	                Set<DTPatrocinio> setPatrocinios = new HashSet<>();
	                for (String patrocinio : ICE.listarPatrocinios(nombre)) {
	                	setPatrocinios.add(ICE.obtenerPatrocinio(nombre, patrocinio));
	                }
	                request.setAttribute("patrocinios", setPatrocinios);
	                
	                // Fetch imagen de edicion
	                String edicionImg = ManejadorArchivos.buscarArchivo(nombre.toLowerCase(), getServletContext().getRealPath("/uploads/ediciones/"));
	                if (edicionImg != null) {
	                	request.setAttribute("imagenEdicion", "uploads/ediciones/" + edicionImg);
	                } else {
	                	request.setAttribute("imagenEdicion", "uploads/ediciones/default.jpg");
	                }
	                
	                // Fetch imagen organizador
	                String organizadorImg = ManejadorArchivos.buscarArchivo(ed.getOrganizador().toLowerCase(), getServletContext().getRealPath("/uploads/usuarios/"));
	                if (organizadorImg != null) {
	                	request.setAttribute("imagenOrganizador", "uploads/usuarios/" + organizadorImg);
	                } else {
	                	request.setAttribute("imagenOrganizador", "uploads/usuarios/default.jpg");
	                }
	                
	                // Fetch nombre evento
	                String nombreEvento = ICE.NomEvPorEd(nombre);
	                request.setAttribute("nombreEvento", nombreEvento);
	                
	                // Fetch imagen evento
	                String imagenEvento = ManejadorArchivos.buscarArchivo(
	                        nombreEvento.toLowerCase(),
	                        getServletContext().getRealPath("/uploads/eventos/"));
	                if (imagenEvento != null) {
	                    request.setAttribute("imagenEvento", "uploads/eventos/" + imagenEvento);
	                } else {
	                    request.setAttribute("imagenEvento", "uploads/eventos/default.jpg");
	                }

	                
	                // Fetch si el usuario registrado en la edicion
	                HttpSession session = request.getSession();
	                
	                DataUsuario user = (DataUsuario) session.getAttribute("usuario");
	                if (user != null && user.getTipo() == TipoUsuario.ASISTENTE) {
	                	Set<String> edicionesRegistradas = ICU.listarRegistrosAEventos(user.getNickname());
	                	if (edicionesRegistradas.contains(nombre)) {
	                		request.setAttribute("usuarioRegistrado", true);
	                	} else {
	                		request.setAttribute("usuarioRegistrado", false);
	                	}
	                }
	                
	                // Fetch si el usuario es organizador de la edicion
	                if (user != null && user.getTipo() == TipoUsuario.ORGANIZADOR && user.getNickname().equals(ed.getOrganizador())) {
	                	request.setAttribute("esOrganizador", true);
	                } else {
	                	request.setAttribute("esOrganizador", false);
	                }

	                
	                // Despachar a JSP
	                request.getRequestDispatcher("/WEB-INF/pages/detalleEdicion.jsp").forward(request, response);
				} catch (Exception e) {
					response.getWriter().append(e.getMessage());
				}
                return;
            }
            case "/listarEdiciones": {
            	IControllerUsuario ICU = (IControllerUsuario) Factory.getInstance().getControllerUsuario();
            	IControllerEvento ICE = (IControllerEvento) Factory.getInstance().getControllerEvento();
            	
            	DataUsuario user = (DataUsuario) request.getSession().getAttribute("usuario"); 
            	
            	// Fetch ediciones
        		Set<DTDetalleEdicion> ediciones = new HashSet<>();
            	if (user != null && user.getTipo() == TipoUsuario.ORGANIZADOR) {
            		for (String edicion : ICU.listarEdicionesOrganizadas(user.getNickname())) {
            			
            			DTDetalleEdicion ed = ICE.mostrarDetallesEdicion(edicion);
            			ediciones.add(ed);
            			
                        // Fetch imagen de ediciones
                        String edicionImg = ManejadorArchivos.buscarArchivo(ed.getNombre().toLowerCase(), getServletContext().getRealPath("/uploads/ediciones/"));
                        if (edicionImg != null) {
                        	request.setAttribute(ed.getNombre(), "uploads/ediciones/" + edicionImg);
                        } else {
                        	request.setAttribute(ed.getNombre(), "uploads/ediciones/default.jpg");
                        }
                        

            		
            		} 
            	
            	
            	
            	} else if (user != null ) {
            		for (String edicion : ICU.listarRegistrosAEventos(user.getNickname())) {
            			
            			DTDetalleEdicion ed = ICE.mostrarDetallesEdicion(edicion);
            			ediciones.add(ed);
            			
                        // Fetch imagen de ediciones
                        String edicionImg = ManejadorArchivos.buscarArchivo(ed.getNombre().toLowerCase(), getServletContext().getRealPath("/uploads/ediciones/"));
                        if (edicionImg != null) {
                        	request.setAttribute(ed.getNombre(), "uploads/ediciones/" + edicionImg);
                        } else {
                        	request.setAttribute(ed.getNombre(), "uploads/ediciones/default.jpg");
                        }
					}
            	}
            
            	
                request.setAttribute("ediciones", ediciones);
                request.getRequestDispatcher("/WEB-INF/pages/listarEdiciones.jsp").forward(request, response);
                return;
            }
    		
            
            case "/detalleEdicion/altaEdicion" : {       // EJEMPLO: /detalleEdicion/altaEdicion?nombreEvento=evento1
                
                
                
                
                HttpSession session = request.getSession();
                DataUsuario user = (DataUsuario) session.getAttribute("usuario");

                
				// Verifica que el usuario haya iniciado sesion como organizador
                if (user == null || user.getTipo() != TipoUsuario.ORGANIZADOR) {
					// mostrar mensaje de error, el usuario no es organizador
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Solo los organizadores pueden dar de alta ediciones.");
				} else {
                	request.setAttribute("nombreEvento", request.getParameter("nombreEvento"));
					request.getRequestDispatcher("/WEB-INF/pages/altaEdicion.jsp").forward(request, response);
				}
    			return;
        	}
            case "/altaRegistro": {
                IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();
                IControllerEvento  ICE = Factory.getInstance().getControllerEvento();

                HttpSession session = request.getSession(false);
                DataUsuario user = (session == null) ? null : (DataUsuario) session.getAttribute("usuario");
                if (user == null || user.getTipo() != DataUsuario.TipoUsuario.ASISTENTE) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Solo los asistentes pueden registrarse a ediciones.");
                    return;
                }

                // 1) Param obligatorio
                String edicionParam = request.getParameter("edicion");
                if (edicionParam == null || edicionParam.isBlank()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro 'edicion'");
                    return;
                }

                try {
                    // 2) Verificar que la edición exista y obtener su DTO
                    DTDetalleEdicion ed = ICE.mostrarDetallesEdicion(edicionParam);
                    if (ed == null) { // por si tu implementación devuelve null en vez de tirar excepción
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "La edición indicada no existe.");
                        return;
                    }
                    request.setAttribute("edicion", ed); // <-- el JSP debe mostrar ed.getNombre(), no el parámetro

                    // 3) Tipos de registro (desde backend)
                    Set<DTTipoRegistro> tiposReg = new java.util.HashSet<>();
                    for (String tr : ICE.listarTiposDeRegistro(ed.getNombre())) { // uso el nombre del DTO, no el parámetro
                        tiposReg.add(ICE.verDetalleTRegistro(ed.getNombre(), tr));
                    }
                    request.setAttribute("tiposRegistro", tiposReg);

                    // 4) Imagen de edición (buscada por nombre canónico del DTO)
                    String edicionImg = ManejadorArchivos.buscarArchivo(
                            ed.getNombre().toLowerCase(),
                            getServletContext().getRealPath("/uploads/ediciones/"));
                    request.setAttribute("imagenEdicion",
                            edicionImg != null ? "uploads/ediciones/" + edicionImg : "uploads/ediciones/default.jpg");

                    // 5) Evento + imagen del evento
                    String nombreEvento = ICE.NomEvPorEd(ed.getNombre());
                    request.setAttribute("nombreEvento", nombreEvento);
                    String imagenEvento = ManejadorArchivos.buscarArchivo(
                            (nombreEvento == null ? "" : nombreEvento.toLowerCase()),
                            getServletContext().getRealPath("/uploads/eventos/"));
                    request.setAttribute("imagenEvento",
                            imagenEvento != null ? "uploads/eventos/" + imagenEvento : "uploads/eventos/default.jpg");

                    // 6) Ya registrado
                    boolean yaRegistrado = ICU.listarRegistrosAEventos(user.getNickname()).contains(ed.getNombre());
                    request.setAttribute("yaRegistrado", yaRegistrado);

                    // 7) Mostrar form
                    request.getRequestDispatcher("/WEB-INF/pages/altaRegistro.jsp").forward(request, response);
                    return;

                } catch (Exception ex) {
                    // Si tu mostrarDetallesEdicion lanza excepción cuando no existe
                    request.setAttribute("error", "No se pudo cargar la edición: " + ex.getMessage());
                    request.getRequestDispatcher("/WEB-INF/pages/altaRegistro.jsp").forward(request, response);
                    return;
                }
            }

            default:
                break;
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String path = request.getServletPath();
    	
    	switch (path) {
        case "/altaEdicion": {
        	
            IControllerEvento ICE = (IControllerEvento) Factory.getInstance().getControllerEvento();
            HttpSession session = request.getSession();
            DataUsuario user = (DataUsuario) session.getAttribute("usuario");
            
			String nombre = request.getParameter("nombre");
			String sigla = request.getParameter("sigla");
			String ciudad = request.getParameter("ciudad");
			String pais = request.getParameter("pais");
			String fechaInicio = request.getParameter("fechaInicio");
			String fechaFin = request.getParameter("fechaFin");
			Part imagen = request.getPart("imagen");
			String nombreEvento = request.getParameter("nombreEvento");
			String organizador = user.getNickname();
			
			/*
			request.getSession().setAttribute("fecha", LocalDate.now());	//TESTING
			IControllerUsuario ICU = (IControllerUsuario) Factory.getInstance().getControllerUsuario();      //TESTING
			session.setAttribute("usuario", ICU.infoUsuario("miseventos"));	//TESTING
			*/
			
			
			try {
				if (user.getTipo() != TipoUsuario.ORGANIZADOR) {
					// mostrar mensaje de error, el usuario no es organizador
					throw new Exception("Necesita estar autenticado como organizador para dar de alta una edición.");
				}
				ICE.altaEdicionDeEvento(nombreEvento, organizador, nombre, sigla, LocalDate.parse(fechaInicio), LocalDate.parse(fechaFin),(LocalDate) session.getAttribute("fecha"), ciudad, pais);
				ManejadorArchivos.guardarArchivo(imagen, nombre, "ediciones", getServletContext());
				
				
				request.setAttribute("mensaje", "Edicion dada de alta exitosamente");
				request.setAttribute("error", null);
				request.setAttribute("nombreEvento", nombreEvento);
				request.getRequestDispatcher("/WEB-INF/pages/altaEdicion.jsp").forward(request, response);
			}	
			catch (Exception e) {
				// Preserve form field values when there's an error
				request.setAttribute("nombre", nombre);
				request.setAttribute("sigla", sigla);
				request.setAttribute("ciudad", ciudad);
				request.setAttribute("pais", pais);
				request.setAttribute("fechaInicio", fechaInicio);
				request.setAttribute("fechaFin", fechaFin);
				request.setAttribute("nombreEvento", nombreEvento);
				
				request.setAttribute("error", e.getMessage());
				request.setAttribute("mensaje", null);
				request.getRequestDispatcher("/WEB-INF/pages/altaEdicion.jsp").forward(request, response);
				e.printStackTrace();
			}
			
            return;
        }
        case "/altaRegistro": {
        	IControllerUsuario ICU = (IControllerUsuario) Factory.getInstance().getControllerUsuario();
        	IControllerEvento ICE = (IControllerEvento) Factory.getInstance().getControllerEvento();
            var session = request.getSession(false);
            DataUsuario user = (session == null) ? null : (DataUsuario) session.getAttribute("usuario");
            if (user == null || user.getTipo() != DataUsuario.TipoUsuario.ASISTENTE) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Solo los asistentes pueden registrarse a ediciones.");
                return;
            }

            String edicion = request.getParameter("edicion");
            String tipoReg = request.getParameter("tipoRegistro");
            String forma   = request.getParameter("formaRegistro"); // "general" | "patrocinio"
            String codigo  = request.getParameter("codigoPatrocinio"); // puede ser null

            // Reinyectar por si hay error
            request.setAttribute("edicionStr", edicion);
            request.setAttribute("tipoRegistroSel", tipoReg);
            request.setAttribute("formaRegistroSel", forma);
            request.setAttribute("codigoPatrocinioVal", codigo == null ? "" : codigo);

            if (edicion == null || edicion.isBlank() || tipoReg == null || tipoReg.isBlank() ||
                forma == null || forma.isBlank()) {
                request.setAttribute("error", "Completá la edición, el tipo de registro y la forma de registro.");
                // Volvemos a GET del form para recargar listas/imagenes
                response.sendRedirect(request.getContextPath() + "/altaRegistro?edicion=" + 
                     java.net.URLEncoder.encode(edicion == null ? "" : edicion, java.nio.charset.StandardCharsets.UTF_8));
                return;
            }

            try {
                // 1) Ya registrado?
                if (ICU.listarRegistrosAEventos(user.getNickname()).contains(edicion)) {
                    request.setAttribute("error", "Ya estás registrado en esta edición.");
                    request.getRequestDispatcher("/WEB-INF/pages/altaRegistro.jsp").forward(request, response);
                    return;
                }

                // 2) Cupo disponible para el tipo?
                // Obtenemos el DTO del tipo para consultar costo/cupo/lo que haya
                DTTipoRegistro dtoTipo = ICE.verDetalleTRegistro(edicion, tipoReg);

                // TODO: Reemplazar por tu método real que valida cupos.
                // Por ejemplo: boolean hayCupo = ICE.hayCupo(edicion, tipoReg);
                boolean hayCupo = true; // fallback si no hay API. De ser posible, usar dtoTipo.getCupoRestante() > 0
                try {
                    // si tu DTO expone cupo disponible:
                    var m = dtoTipo.getClass().getMethod("getCupoRestante");
                    Object v = m.invoke(dtoTipo);
                    if (v instanceof Integer rest) hayCupo = rest > 0;
                } catch (Exception ignore) {}

                if (!hayCupo) {
                    request.setAttribute("error", "No hay cupos disponibles para el tipo seleccionado.");
                    request.getRequestDispatcher("/WEB-INF/pages/altaRegistro.jsp").forward(request, response);
                    return;
                }

                // 3) Validación de patrocinio si corresponde
                boolean usarPatrocinio = "patrocinio".equalsIgnoreCase(forma);
                if (usarPatrocinio) {
                    if (codigo == null || codigo.isBlank()) {
                        request.setAttribute("error", "Ingresá el código de patrocinio.");
                        request.getRequestDispatcher("/WEB-INF/pages/altaRegistro.jsp").forward(request, response);
                        return;
                    }

                    // TODO: reemplazá los siguientes métodos por los de TU API:
                    // - validar que el código sea para esta edición
                    // - validar que aplique al tipo de registro
                    // - validar que sea de la institución del asistente
                    // - validar que no haya agotado el cupo de usos
                    boolean valido = false;
                    try {
                        // Ejemplos de posibles firmas:
                        // valido = ICE.validarCodigoPatrocinio(edicion, tipoReg, user.getNickname(), codigo);
                        // o bien:
                        // DTPatrocinio p = ICE.obtenerPatrocinio(edicion, codigo);
                        // valido = tuValidacion(p, tipoReg, user, ...);
                        var p = ICE.obtenerPatrocinio(edicion, codigo); // si no existe, lanzará excepción
                        // Chequeos “manuales” defensivos usando reflexión para no romper si cambian nombres
                        boolean okTipo = true, okInst = true, okUsos = true;
                        try {
                            var mt = p.getClass().getMethod("getTipoRegistro");
                            okTipo = tipoReg.equals(String.valueOf(mt.invoke(p)));
                        } catch (Exception ignore) {}
                        try {
                            var mi = p.getClass().getMethod("getInstitucion");
                            var inst = String.valueOf(mi.invoke(p));
                            // si tu usuario tiene institución en DTO, comparala;
                            // si no, saltá este chequeo o hacelo en la capa de negocio
                        } catch (Exception ignore) {}
                        try {
                            var mu = p.getClass().getMethod("getUsosDisponibles");
                            Object v = mu.invoke(p);
                            if (v instanceof Integer u) okUsos = u > 0;
                        } catch (Exception ignore) {}
                        valido = okTipo && okInst && okUsos;
                    } catch (Exception ex) {
                        valido = false;
                    }

                    if (!valido) {
                        request.setAttribute("error", "El código de patrocinio es inválido o no aplica.");
                        request.getRequestDispatcher("/WEB-INF/pages/altaRegistro.jsp").forward(request, response);
                        return;
                    }

                    // 4) Registrar con patrocinio (costo 0)
                    // TODO: reemplazá por tu método real de negocio:
                    // ICE.registrarConPatrocinio(user.getNickname(), tipoReg, edicion, codigo);
                    ICE.elegirAsistenteYTipoRegistro(user.getNickname(), tipoReg, edicion);
                    // TODO: si tu lógica requiere “marcar uso”, hacelo aquí:
                    // ICE.consumirPatrocinio(edicion, codigo, user.getNickname());

                    request.setAttribute("mensaje", "Registro realizado exitosamente con patrocinio (costo $0).");
                } else {
                    // 4b) Registro general (paga costo del tipo)
                    ICE.elegirAsistenteYTipoRegistro(user.getNickname(), tipoReg, edicion);
                    request.setAttribute("mensaje", "Registro realizado exitosamente.");
                }

                // Tras éxito, recargamos el form como “confirmación” (o redirigí a detalle)
                // Dejo forward para mostrar mensajes en el mismo JSP:
                // Reponer combos e imágenes
                DTDetalleEdicion ed = ICE.mostrarDetallesEdicion(edicion);
                request.setAttribute("edicion", ed);
                Set<DTTipoRegistro> tiposReg = new java.util.HashSet<>();
                for (String tr : ICE.listarTiposDeRegistro(edicion)) {
                    tiposReg.add(ICE.verDetalleTRegistro(edicion, tr));
                }
                request.setAttribute("tiposRegistro", tiposReg);
                String edImg = ManejadorArchivos.buscarArchivo(edicion.toLowerCase(),
                        getServletContext().getRealPath("/uploads/ediciones/"));
                request.setAttribute("imagenEdicion",
                        edImg != null ? "uploads/ediciones/" + edImg : "uploads/ediciones/default.jpg");
                String nomEv = ICE.NomEvPorEd(edicion);
                request.setAttribute("nombreEvento", nomEv);
                String imgEv = ManejadorArchivos.buscarArchivo(nomEv.toLowerCase(),
                        getServletContext().getRealPath("/uploads/eventos/"));
                request.setAttribute("imagenEvento",
                        imgEv != null ? "uploads/eventos/" + imgEv : "uploads/eventos/default.jpg");

                request.getRequestDispatcher("/WEB-INF/pages/altaRegistro.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/pages/altaRegistro.jsp").forward(request, response);
            }
            return;
        }
        default:
            break;
    }

}}