package logica;
import java.time.LocalDate;
import java.util.Set;
import excepciones.NombreEventoExcepcion;

public interface IControllerEvento{
	public void altaEvento(String nombre, String sigla, LocalDate fechaAlta, String descripcion, Set<String> categorias)throws NombreEventoExcepcion, Exception;
	public Set<String> listarEventos();
	public Set<String> listarCategorias();
	public Set<String> listarEdiciones(String nombreEvento);
	public Set<String> listarPatrocinios(String nombreEdi);
	public DTPatrocinio obtenerPatrocinio(String nombreEdi, String nombreInstitucion);
	public DTDetalleEdicion mostrarDetallesEdicion(String nombreEdi);
	public DTTipoRegistro verDetalleTRegistro(String nombreEdi,String nomTRegistro);
	public void altaTipoDeRegistro(String nombreEdi, String nombre, String descripcion, Float costo, int cupo) throws Exception;
	public DTDetalleEvento verDetalleEvento(String nombreEvento);
	public Set<String> listarTiposDeRegistro( String nombreEdicion);
	public void altaEdicionDeEvento(String nombreEvento, String nicknameOrganizador, String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, String ciudad, String pais);
	public void ingresarCategoria(String string);
	Set<DTAsistente> listarAsistentesAEdicionDeEvento(String nomEdi);
	boolean elegirAsistenteYTipoRegistro(String nickAsistente, String tipoReg, String nomEdi);
	void altaRegistro(String nickAsistente, String tipoReg, String nombreEdi);
}

