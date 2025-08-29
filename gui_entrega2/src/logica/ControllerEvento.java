package logica;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ControllerEvento implements IControllerEvento{
	
	
	@Override
	public HashSet<String> listarEventos() {
		ManejadorEvento mE = ManejadorEvento.getInstance();
		HashSet<String> evs = mE.obtenerNombresEventos();
		return evs;
	}

	@Override
	public HashSet<String> listarEdiciones(String nombreEvento) {
		ManejadorEvento mE = ManejadorEvento.getInstance();
		Evento ev = mE.obtenerEvento(nombreEvento);
		return ev.getEdiciones();
	}

	@Override
	public Set<String> listarPatrocinios(String nombreEdi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTPatrocinio obtenerPatrocinio(String nombreEdi, String nombreInstitucion) {
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(nombreEdi);
		return edi.getPatrocinio(nombreInstitucion);
	}

	@Override
	public DTDetalleEdicion mostrarDetallesEdicion(String nombreEdi) {
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion ed = mEdi.encontrarEdicion(nombreEdi);
		return ed.devolverDT();
	}

	@Override
	public DTTRegistro verDetalleTRegistro(String nombreEdi, String nomTRegistro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void altaTipoDeRegistro(String nombreEdi, String nombre, Float costo, int cupo) throws Exception{
		
		
	}

	@Override
	public DTDetalleEvento verDetalleEvento(String nombreEvento) {
		// TODO Auto-generated method stub
		
		
	}
	
	@Override
	public Set<DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion) {
		//TODO zangano
		ManejadorEvento mE = ManejadorEvento.getInstance();
		Evento ev = mE.obtenerEvento(nombreEvento);
		Set<DTTipoRegistro> setTipoReg = ev.infoTipoRegDeEdi(nombreEdicion);
		return setTipoReg;
		
	}
	
	@Override
	public void altaEdicionDeEvento(String nombreEvento, String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, String ciudad, String pais) {
		ManejadorEvento mEve = ManejadorEvento.getInstance(); 
		Evento ev = mEve.obtenerEvento(nombreEvento);
		if(ev == null) throw new IllegalArgumentException("No existe el evento: " + nombreEvento);
		Edicion nueva = new Edicion(nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais);
		ev.agregarEdicion(nueva);
		
	}
	
	
	
	
}