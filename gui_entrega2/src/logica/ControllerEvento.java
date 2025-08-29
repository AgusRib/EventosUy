package logica;
import java.time.LocalDate;
import java.util.Set;

public class ControllerEvento implements IControllerEvento{
	
	
	@Override
	public Set<String> listarEventos() {
		manejadorEvento mE = manejadorEvento.getInstance();
		eventos = mE.obtenerEventos();
		for (evento e : eventos) {
			
		}
		
	}

	@Override
	public Set<String> listarEdiciones(String nombreEvento) {
		// TODO Auto-generated method stub
		
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
	public DTEdicion mostrarDetallesEdicion(String nombreEdi) {
		// TODO Auto-generated method stub
		return null;
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