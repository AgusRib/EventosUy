package logica;
import java.util.Set;

public interface IControllerEvento{
	public Set<String> listarEventos();
	public Set<String> listarEdiciones(String nombreEvento);
	public Set<String> listarPatrocinios(String nombreEdi);
	public DTPatrocinio obtenerPatrocinio(String nombreEdi, String nombreInstitucion);
	public DTEdicion mostrarDetallesEdicion(String nombreEdi);
	public DTTRegistro verDetalleTRegistro(String nombreEdi,String nomTRegistro);
	public void altaTipoDeRegistro(String nombreEdi, String nombre, Float costo, int cupo);
	public DTDetalleEvento verDetalleEvento(String nombreEvento);)
	}
