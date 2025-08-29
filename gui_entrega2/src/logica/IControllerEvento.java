package logica;
import java.time.LocalDate;
import java.util.Set;

public interface IControllerEvento{
	public Set<String> listarEventos();
	public Set<String> listarEdiciones(String nombreEvento);
	public Set<String> listarPatrocinios(String nombreEdi);
	public DTPatrocinio obtenerPatrocinio(String nombreEdi, String nombreInstitucion);
	public DTDetalleEdicion mostrarDetallesEdicion(String nombreEdi);
	public DTTipoRegistro verDetalleTRegistro(String nombreEdi,String nomTRegistro);
	public void altaTipoDeRegistro(String nombreEdi, String nombre, Float costo, int cupo);
	public DTDetalleEvento verDetalleEvento(String nombreEvento);
	public Set<DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion);
	public void altaEdicionDeEvento(String nombreEvento, String nicknameOrganizador, String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, String ciudad, String pais);
	public void ingresarCategoria(String string);
	public void ingresarEvento(String nombre, String sigla, String descripcion, String fechaAlta, String[] categorias) throws Exception;

}
