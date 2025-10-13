import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.controllers.IControllerEvento;
import logica.controllers.IControllerUsuario;
import logica.dataTypes.DTDetalleEdicion;
import logica.dataTypes.DTRegistro;
import logica.dataTypes.DataUsuario;
import logica.dataTypes.DataUsuario.TipoUsuario;
import logica.models.Factory;
import main.java.ManejadorArchivos;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.net.*;

import casosPrueba.CargaDatos;
import excepciones.TipoRegistroExistenteExcepcion;


/**
 * Servlet implementation class ServletEdicion
 */
@WebServlet({ "/ver-registro", "/listar-registros", "/alta-registro", "/alta-tipo-registro" })
public class ServletRegistro extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRegistro() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        IControllerEvento ICE = (IControllerEvento) Factory.getInstance().getControllerEvento();
        
        switch (path) {
	        case "/ver-registro": {
	            String edicion = request.getParameter("edicion");
	            String usuario = request.getParameter("usuario");
	            if (usuario == null || usuario.isBlank()) {
	                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro 'usuario'");
	                return;
	            }
	
	            DTRegistro registro = ICE.infoRegistro(edicion, usuario);
	            if (registro == null) {
	                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Registro no encontrado");
	                return;
	            }
	
	            request.setAttribute("registro", registro);
	            request.setAttribute("usuario", usuario);
	
	            String baseUsuarios = getServletContext().getRealPath("/uploads/usuarios/");
	            String imgUsuario = ManejadorArchivos.buscarArchivo(usuario.toLowerCase(), baseUsuarios);
	            if (imgUsuario != null) {
	                request.setAttribute("imagenUsuario", "uploads/usuarios/" + imgUsuario);
	            } else {
	                request.setAttribute("imagenUsuario", "uploads/usuarios/default.jpg");
	            }
	
	            String nombreEdicion = (edicion != null && !edicion.isBlank()) ? edicion : registro.getNombreEdicion();
	            String baseEdiciones = getServletContext().getRealPath("/uploads/ediciones/");
	            String imgEdicion = ManejadorArchivos.buscarArchivo(nombreEdicion.toLowerCase(), baseEdiciones);
	            if (imgEdicion != null) {
	                request.setAttribute("imagenEdicion", "uploads/ediciones/" + imgEdicion);
	            } else {
	                request.setAttribute("imagenEdicion", "uploads/ediciones/default.jpg");
	            }
	
	            request.getRequestDispatcher("/WEB-INF/pages/detalleRegistro.jsp").forward(request, response);
	            return;
	        }
	
	        case "/listar-registros": {
	            String edicion = request.getParameter("edicion");
	            if (edicion == null || edicion.isBlank()) {
	                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Falta parámetro 'edicion");
	                return;
	            }
	
	            String q = request.getParameter("q");
	            String qNorm = q == null ? "" : q.trim().toLowerCase();
	
	            var asistentes = ICE.listarAsistentesAEdicionDeEvento(edicion);
	
	            List<Map.Entry<String, DTRegistro>> regs = new ArrayList<>();
	            for (var a : asistentes) {
	                DTRegistro r = ICE.infoRegistro(edicion, a.getnickname());
	                if (r != null) {
	                    String nick = a.getnickname();
	                    if (qNorm.isEmpty() || (nick != null && nick.toLowerCase().contains(qNorm))) {
	                        regs.add(new AbstractMap.SimpleEntry<>(nick, r));
	                    }
	                }
	            }
	
	            if (regs.isEmpty()) {
	                request.setAttribute("mensaje", (qNorm.isEmpty() ?
	                    "No hay registros para la edición" :
	                    "No hubo coincidencias para la búsqueda"));
	            }
	
	            Map<String, String> imgsUsuarios = new HashMap<>();
	            String baseUsuarios = getServletContext().getRealPath("/uploads/usuarios/");
	            for (var e : regs) {
	                String nick = e.getKey();
	                String img = ManejadorArchivos.buscarArchivo(nick.toLowerCase(), baseUsuarios);
	                imgsUsuarios.put(nick, (img != null) ? ("uploads/usuarios/" + img) : "uploads/usuarios/default.jpg");
	            }
	
	            request.setAttribute("edicion", edicion);
	            request.setAttribute("registros", regs);
	            request.setAttribute("q", q == null ? "" : q);
	            request.setAttribute("imgsUsuarios", imgsUsuarios);
	
	            request.getRequestDispatcher("/WEB-INF/pages/listarRegistros.jsp").forward(request, response);
	            return;
	        }
            case "/alta-registro": {
            	request.getRequestDispatcher("/WEB-INF/pages/altaRegistro.jsp").forward(request, response);
                return;
            }
            case "/alta-tipo-registro": {    			
    			var eventos = ICE.listarEventos();
    			request.setAttribute("eventos", eventos);
    			
    			String eventoSel = request.getParameter("evento");
    		    if (eventoSel != null && !eventoSel.isBlank()) {
    		        var ediciones = ICE.listarEdiciones(eventoSel);
    		        request.setAttribute("ediciones", ediciones);
    		        request.setAttribute("eventoSel", eventoSel);
    		    }
    			
    		    request.getRequestDispatcher("/WEB-INF/pages/altaTipoRegistro.jsp").forward(request, response);
    		    return;
            }
            default:
                break;
        }

        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path = request.getServletPath();
	    	
	    if ("/alta-registro".equals(path)) {
	    	// Chequear si el usuario es asistente antes de permitir el registro
	    	// TODO Manejar excepciones y mostrar mensajes de error en la JSP
	    	
	    	IControllerEvento ICE = (IControllerEvento) Factory.getInstance().getControllerEvento();
	    	HttpSession session = request.getSession();
	    	
	    	IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();	// PARA TESTING
	    	
	    	
	    	DataUsuario user = (DataUsuario) session.getAttribute("usuario"); 
	    	if (user.getTipo() == TipoUsuario.ASISTENTE) {
	    		try {
	    			// Realizar el alta de registro
					ICE.elegirAsistenteYTipoRegistro(user.getNickname(), request.getParameter("tipoReg"), request.getParameter("edicion"));
					
					// Despachar a JSP con mensaje de exito
					request.setAttribute("mensaje", "El registro se ha realizado con exito.");
					request.setAttribute("error", null);
					
					request.getRequestDispatcher("/WEB-INF/pages/altaRegistro.jsp").forward(request, response);
				} catch (Exception e) {
					// Despachar a JSP con mensaje de error
					request.setAttribute("error", e.getMessage());
					request.setAttribute("mensaje", null);
					
					request.getRequestDispatcher("/WEB-INF/pages/altaRegistro.jsp").forward(request, response);
				}
	    	} else {
	    		request.setAttribute("error", "El usuario no es asistente");
	    	}
	        return;
		} else if ("/alta-tipo-registro".equals(path)) {
			IControllerEvento ICE = (IControllerEvento) Factory.getInstance().getControllerEvento();
			
			String edicion = request.getParameter("edicion");
			String nombre  = request.getParameter("nombre");
			String desc    = request.getParameter("descripcion");
		    String costoS  = request.getParameter("costo");
		    String cupoS   = request.getParameter("cupo");
		    
		    if (edicion == null || edicion.isBlank() || nombre == null || nombre.isBlank() || desc == null || desc.isBlank() || costoS == null || costoS.isBlank() || cupoS == null || cupoS.isBlank()) {
		        request.setAttribute("error", "Completa todos los campos.");
		        request.setAttribute("edicion", edicion);
		        request.setAttribute("nombre", nombre);
		        request.setAttribute("descripcion", desc);
		        request.setAttribute("costo", costoS);
		        request.setAttribute("cupo", cupoS);
		        request.getRequestDispatcher("/WEB-INF/pages/altaTipoRegistro.jsp").forward(request, response);
		        return;
		    }
		    try {
		    	Float costo = Float.parseFloat(costoS.replace(",", "."));
		    	int cupo = Integer.parseInt(cupoS);
		    	if (costo < 0 || cupo <= 0) {
		    		throw new IllegalArgumentException("Costo y cupo deben ser positivos");
		    	}
		    	ICE.altaTipoDeRegistro(edicion, nombre, desc, costo, cupo);
		    	
		    	String url = request.getContextPath() + "/detalleEdicion?nombre=" + URLEncoder.encode(edicion, java.nio.charset.StandardCharsets.UTF_8);
		    	response.sendRedirect(url);
		    	return;
		    } catch (NumberFormatException nfe) {
		    	request.setAttribute("error", "Formato numérico inválido en costo o cupo.");
		        request.setAttribute("edicion", edicion);
		        request.setAttribute("nombre", nombre);
		        request.setAttribute("descripcion", desc);
		        request.setAttribute("costo", costoS);
		        request.setAttribute("cupo", cupoS);
		        request.getRequestDispatcher("/WEB-INF/pages/altaTipoRegistro.jsp").forward(request, response);
		        return;
		    } catch (TipoRegistroExistenteExcepcion e) {
		    	request.setAttribute("error", e.getMessage());
		        request.setAttribute("edicion", edicion);
		        request.setAttribute("nombre", nombre);
		        request.setAttribute("descripcion", desc);
		        request.setAttribute("costo", costoS);
		        request.setAttribute("cupo", cupoS);
		        request.getRequestDispatcher("/WEB-INF/pages/altaTipoRegistro.jsp").forward(request, response);
		        return;
			} catch (Exception e) {
				request.setAttribute("error", "Error al dar de alta el tipo de registro: " + e.getMessage());
		        request.setAttribute("edicion", edicion);
		        request.setAttribute("nombre", nombre);
		        request.setAttribute("descripcion", desc);
		        request.setAttribute("costo", costoS);
		        request.setAttribute("cupo", cupoS);
		        request.getRequestDispatcher("/WEB-INF/pages/altaTipoRegistro.jsp").forward(request, response);
		        return;
			}
				
			
		}
        
   }
    
}