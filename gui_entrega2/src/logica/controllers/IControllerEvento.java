package logica.controllers;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import excepciones.AsistenteYaRegistrado;
import excepciones.CupoLLeno;
import excepciones.FechaInicioPOSTFINAL;
import excepciones.FechaInicioPREALTA;
import excepciones.FechaRegPREALTA;
import excepciones.NombreEdicionExistenteExcepcion;
import excepciones.NombreEventoExcepcion;
import logica.dataTypes.DTAsistente;
import logica.dataTypes.DTDetalleEdicion;
import logica.dataTypes.DTDetalleEvento;
import logica.dataTypes.DTPatrocinio;
import logica.dataTypes.DTRegistro;
import logica.dataTypes.DTTipoRegistro;
import logica.enumerators.NivelPatrocinio;
import logica.models.Evento;


public interface IControllerEvento{
	public void altaEvento(String nombre, String sigla, LocalDate fechaAlta, String descripcion, Set<String> categorias)throws NombreEventoExcepcion, Exception;
	public Set<String> listarEventos();
	public Set<String> listarCategorias();
	public Set<String> listarEdiciones(String nombreEvento);
	public Set<String> listarPatrocinios(String nombreEdi);
	public DTPatrocinio obtenerPatrocinio(String nombreEdi, String nombreInstitucion);
	public List<Evento> obtenerEventosRecientes();
	public DTDetalleEdicion mostrarDetallesEdicion(String nombreEdi);
	public DTTipoRegistro verDetalleTRegistro(String nombreEdi,String nomTRegistro);
	public void altaTipoDeRegistro(String nombreEdi, String nombre, String descripcion, Float costo, int cupo) throws excepciones.TipoRegistroExistenteExcepcion,Exception;
	public DTDetalleEvento verDetalleEvento(String nombreEvento);
	public Set<String> listarTiposDeRegistro( String nombreEdicion);
	public void altaEdicionDeEvento(String nombreEvento, String nicknameOrganizador, String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, String ciudad, String pais) throws NombreEdicionExistenteExcepcion, FechaInicioPOSTFINAL,FechaInicioPREALTA, Exception;
	public void ingresarCategoria(String string);
	public DTRegistro infoRegistro(String edicion, String usuario);
	Set<DTAsistente> listarAsistentesAEdicionDeEvento(String nomEdi);
	void elegirAsistenteYTipoRegistro(String nickAsistente, String tipoReg, String nomEdi) throws FechaRegPREALTA, CupoLLeno,AsistenteYaRegistrado, Exception;
	void altaRegistro(String nickAsistente, String tipoReg, String nombreEdi);
	void altaPatrocinio(String nombreEdi, String institucion, NivelPatrocinio nivel, double aporteEconomico, String tipoRegistroGratis, int cantidadGratis, String codigo);
	public LocalDate getFechaSistema();
	public LocalDate setFechaSistema(LocalDate fechaNueva);
	public String NomEvPorEd(String nomEdi);
	public void AceptarEdicion(String nomEdi, String nomEv);
	public void RechazarEdicion(String nomEdi, String nomEv);
	Set<String> listarEdicionesTodas();
	Set<String> listarEdicionesConfirmadas(String nombreEvento);
	Set<String> listarEdicionesPendientes(String nombreEvento);
	
	
}

