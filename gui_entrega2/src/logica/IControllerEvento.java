package logica;
import java.util.Set;

public interface IControllerEvento{
	public Set<String> listarEventos();
	public Set<String> listarEdiciones(String nombreEvento);
	public Set<String> listarPatrocinios(String nombreEdi);
	public DTPatrocinio obtenerPatrocinio(String nombreEdi, String nombreInstitucion);
	public DTEdicion mostrarDetallesEdicion(String nombreEdi);
	public DTTipoRegistro verDetalleTRegistro(String nombreEdi,String nomTRegistro);
	public void altaTipoDeRegistro(String nombreEdi, String nombre, String descripcion, Float costo, int cupo) throws Exception;
	public DTDetalleEvento verDetalleEvento(String nombreEvento);
	}
