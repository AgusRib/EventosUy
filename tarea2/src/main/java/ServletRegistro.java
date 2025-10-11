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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import casosPrueba.CargaDatos;


/**
 * Servlet implementation class ServletEdicion
 */
@WebServlet({ "/ver-registro", "/listar-registros", "/alta-registro" })
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

                request.setAttribute("edicion", edicion);
                request.setAttribute("registros", regs);
                request.setAttribute("q", q == null ? "" : q);
                request.getRequestDispatcher("/WEB-INF/pages/listarRegistros.jsp").forward(request, response);
                return;
              }
            case "/alta-registro": {
            	request.getRequestDispatcher("/WEB-INF/pages/altaRegistro.jsp").forward(request, response);
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
	    	session.setAttribute("usuario", ICU.infoUsuario("msilva"));	// PARA TESTING
	    	
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
		}
        
   }
    
}