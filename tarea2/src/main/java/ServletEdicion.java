import jakarta.servlet.ServletException;
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


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import excepciones.FechaInicioPOSTFINAL;
import excepciones.FechaInicioPREALTA;
import excepciones.NombreEdicionExistenteExcepcion;


@WebServlet({ "/detalleEdicion", "/altaEdicion", "/altaRegistro", "/listarEdiciones" })
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
            case "/detalleEdicion": {
            	// TODO Manejar excepciones y mostrar mensajes de error en la JSP
            	
            	IControllerEvento ICE = (IControllerEvento) Factory.getInstance().getControllerEvento();
                String nombre = request.getParameter("nombre");
                response.getWriter().append("Detalle de Edicion: ").append(nombre).append("\n");
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
	                System.out.println(ed.getNombresInstituciones().size());
	                for (String patrocinio : ICE.listarPatrocinios(nombre)) {
	                	setPatrocinios.add(ICE.obtenerPatrocinio(nombre, patrocinio));
	                	System.out.println(patrocinio);
	                }
	                request.setAttribute("patrocinios", setPatrocinios);
	                
	                // Despachar a JSP
	                request.getRequestDispatcher("/WEB-INF/pages/detalleEdicion.jsp").forward(request, response);
				} catch (Exception e) {
					response.getWriter().append(e.getMessage());
				}
                return;
            }
            case "/listarEdiciones": {
                request.setAttribute("ediciones", Collections.emptyList());
                request.getRequestDispatcher("/WEB-INF/pages/listarEdiciones.jsp").forward(request, response);
                return;
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
            // TODO Chequear si el usuario es organizador antes de permitir el alta
        	// TODO Agregar imagen de parametro 
        	// TODO Manejar excepciones y mostrar mensajes de error en la JSP
        	
            IControllerEvento ICE = (IControllerEvento) Factory.getInstance().getControllerEvento();
			String nombre = request.getParameter("nombre");
			String sigla = request.getParameter("sigla");
			String ciudad = request.getParameter("ciudad");
			String pais = request.getParameter("pais");
			String fechaInicio = request.getParameter("fechaInicio");
			String fechaFin = request.getParameter("fechaFin");
			String imagen = request.getParameter("imagen");
			String nombreEvento = request.getParameter("nombreEvento");
			String organizador = request.getParameter("organizador");
			
			HttpSession session = request.getSession();
			
			try {
				ICE.altaEdicionDeEvento(nombreEvento, organizador, nombre, sigla, LocalDate.parse(fechaInicio), LocalDate.parse(fechaFin),(LocalDate) session.getAttribute("fecha"),ciudad, pais);
			} catch (NombreEdicionExistenteExcepcion e) {
				// Auto-generated catch block
				e.printStackTrace();
			} catch (FechaInicioPOSTFINAL e) {
				// Auto-generated catch block
				e.printStackTrace();
			} catch (FechaInicioPREALTA e) {
				// Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
			
            return;
        }
        case "/altaRegistro": {
        	// Chequear si el usuario es asistente antes de permitir el registro
        	// TODO Manejar excepciones y mostrar mensajes de error en la JSP
        	
        	IControllerEvento ICE = (IControllerEvento) Factory.getInstance().getControllerEvento();
        	HttpSession session = request.getSession();
        	
        	IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();	// PARA TESTING
        	session.setAttribute("usuario", ICU.infoUsuario("msilva"));	// PARA TESTING
        	
        	DataUsuario user = (DataUsuario) session.getAttribute("usuario"); 
        	if (user.getTipo() == TipoUsuario.ASISTENTE) {
        		try {
        			// Realizar el alta de registro
					ICE.elegirAsistenteYTipoRegistro(user.getNickname(), request.getParameter("tipoReg"), request.getParameter("edicion"));
					ICE.altaRegistro(user.getNickname(), request.getParameter("tipoReg"), request.getParameter("edicion"));
					
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
        		// Mostrar mensaje de error, el usuario no es asistente
        	}
            return;
    	}
    }

}}