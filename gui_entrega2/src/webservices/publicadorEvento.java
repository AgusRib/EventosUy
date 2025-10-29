package webservices;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import excepciones.AsistenteYaRegistrado;
import excepciones.CupoLLeno;
import excepciones.FechaInicioPOSTFINAL;
import excepciones.FechaInicioPREALTA;
import excepciones.NombreEdicionExistenteExcepcion;
import excepciones.NombreEventoExcepcion;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.ws.Endpoint;
import logica.controllers.IControllerEvento;
import logica.data_types.DTAsistente;
import logica.data_types.DTDetalleEdicion;
import logica.data_types.DTDetalleEvento;
import logica.data_types.DTPatrocinio;
import logica.data_types.DTRegistro;
import logica.data_types.DTTipoRegistro;
import logica.enumerators.NivelPatrocinio;
import logica.models.Factory;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class publicadorEvento {
	private Endpoint endpoint = null;
	IControllerEvento ICE = Factory.getInstance().getControllerEvento();
	
    //Constructor
    public publicadorEvento(){}

    @WebMethod(exclude = true)
    public void publicar(){
         endpoint = Endpoint.publish("http://localhost:8080/publicadorEvento", this);
    }
    
    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
            return endpoint;
    }
    
    @WebMethod
	public HashSet<String> listarEventos() {
		return (HashSet<String>) ICE.listarEventos();
	}
	
	@WebMethod
	public HashSet<String> listarEdicionesTodas(){
		return (HashSet<String>) ICE.listarEdicionesTodas();
	}
	
	public void altaEvento(String nombre, String sigla, LocalDate fechaAlta, String descripcion, HashSet<String> categorias)throws NombreEventoExcepcion, Exception {
		ICE.altaEvento(nombre, sigla, fechaAlta, descripcion, categorias);
	}
		
	
	@WebMethod
	public HashSet<String> listarCategorias() {
		return (HashSet<String>) ICE.listarCategorias();
	}
	

	@WebMethod
	public HashSet<String> listarEdiciones(String nombreEvento) {
		return (HashSet<String>) ICE.listarEdiciones(nombreEvento);
	}

	@WebMethod
	public HashSet<String> listarPatrocinios(String nombreEdi) {		
		return (HashSet<String>) ICE.listarPatrocinios(nombreEdi);
	}

	@WebMethod
	public DTPatrocinio obtenerPatrocinio(String nombreEdi, String nombreInstitucion) {
		return ICE.obtenerPatrocinio(nombreEdi, nombreInstitucion);
	}

	@WebMethod
	public DTDetalleEdicion mostrarDetallesEdicion(String nombreEdi) {
		return ICE.mostrarDetallesEdicion(nombreEdi);
	}

	@WebMethod
	public DTTipoRegistro verDetalleTRegistro(String nombreEdi, String nomTRegistro) {
		return ICE.verDetalleTRegistro(nombreEdi, nomTRegistro);
	}

	@WebMethod
	public void altaTipoDeRegistro(String nombreEdi, String nombre, String desc, Float costo, int cupo) throws excepciones.TipoRegistroExistenteExcepcion, Exception {
		ICE.altaTipoDeRegistro(nombreEdi, nombre, desc, costo, cupo);
	}

	@WebMethod
	public DTDetalleEvento verDetalleEvento(String nombreEvento) {
		return ICE.verDetalleEvento(nombreEvento);
	}
	
	@WebMethod
	public HashSet<String> listarTiposDeRegistro(String nombreEdi) {
		return (HashSet<String>) ICE.listarTiposDeRegistro(nombreEdi);
	}
	
	@WebMethod
	public DTRegistro infoRegistro(String edicion, String usuario) {
		return ICE.infoRegistro(edicion, usuario);
	}

	@WebMethod
	public void altaEdicionDeEvento(String nombreEvento, String nicknameOrganizador, String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, String ciudad, String pais)throws NombreEdicionExistenteExcepcion, FechaInicioPOSTFINAL, FechaInicioPREALTA, Exception {
		ICE.altaEdicionDeEvento(nombreEvento, nicknameOrganizador, nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais);
		
	}
	
	@WebMethod
	public void ingresarCategoria(String string) {
		ICE.ingresarCategoria(string);
	}
	
	@WebMethod
	public HashSet<DTAsistente> listarAsistentesAEdicionDeEvento(String nomEdi) {
		return (HashSet<DTAsistente>) ICE.listarAsistentesAEdicionDeEvento(nomEdi);
	}
	
	@WebMethod
	public void elegirAsistenteYTipoRegistro(String nickAsistente, String tipoReg, String nomEdi) throws FechaInicioPREALTA, CupoLLeno, AsistenteYaRegistrado, Exception { 
		ICE.elegirAsistenteYTipoRegistro(nickAsistente, tipoReg, nomEdi);
	}
	
	@WebMethod
	public void altaRegistro(String nickAsistente, String tipoReg, String nombreEdi) {
		ICE.altaRegistro(nickAsistente, tipoReg, nombreEdi);
	}
	
	@WebMethod
	public void altaPatrocinio(String nombreEdi, String institucion, NivelPatrocinio nivel, double aporteEconomico, String tipoRegistroGratis, int cantidadGratis, String codigo) {
	    ICE.altaPatrocinio(nombreEdi, institucion, nivel, aporteEconomico, tipoRegistroGratis, cantidadGratis, codigo);
	}

	@WebMethod
	public LocalDate getFechaSistema() {
		return ICE.getFechaSistema();
	}

	@WebMethod
	public LocalDate setFechaSistema(LocalDate fechaNueva) {
		return ICE.setFechaSistema(fechaNueva);
	}
	
	public String nomEvPorEd(String nomEdi) {
		return ICE.nomEvPorEd(nomEdi);
	}
	
	@WebMethod
	public void aceptarEdicion(String nomedi, String nomev) {
		ICE.aceptarEdicion(nomedi, nomev);	
	}
	
	@WebMethod
	public void rechazarEdicion(String nomedi, String nomev) {
		ICE.rechazarEdicion(nomedi, nomev);
	}
	
	@WebMethod
	public HashSet<String> listarEdicionesConfirmadas(String nombreEvento) {
		return (HashSet<String>) ICE.listarEdicionesConfirmadas(nombreEvento);
	}
	
	@WebMethod
	public HashSet<String> listarEdicionesPendientes(String nombreEvento) {
		return (HashSet<String>) ICE.listarEdicionesPendientes(nombreEvento);
	}
	
	@WebMethod
	public ArrayList<DTDetalleEvento> obtenerEventosRecientes() {
		return (ArrayList<DTDetalleEvento>) ICE.obtenerEventosRecientes();
	}
   
    @WebMethod
    public byte[] getFile(@WebParam(name = "fileName") String name)
                    throws  IOException {
        byte[] byteArray = null;
        try {
                File f = new File("files/" + name);
                FileInputStream streamer = new FileInputStream(f);
                byteArray = new byte[streamer.available()];
                streamer.read(byteArray);
        } catch (IOException e) {
                throw e;
        }
        return byteArray;
    }
}
