package logica;
import java.util.Set;

public class ControllerEvento implements IControllerEvento{
	
	
	@Override
	public Set<String> listarEventos() {
		ManejadorEvento mE = ManejadorEvento.getInstance();
		eventos = mE.obtenerEventos();
		for (evento e : eventos) {
			
		}
		
	}

	@Override
	public Set<String> listarEdiciones(String nombreEvento) {
		// falta implementar ManejadorEvento
		ManejadorEvento h_evento = ManejadorEvento.getInstance();
		Evento ev = h_evento.getEvento(nombreEvento);
		
		Set<String> ediciones;
		for (Edicion ed : ev.getEdiciones()) {
			ediciones.add(ed.getNombre());
		}
		return ediciones;
	}

	@Override
	public Set<String> listarPatrocinios(String nombreEdi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DTPatrocinio obtenerPatrocinio(String nombreEdi, String nombreInstitucion) {
		// TODO Auto-generated method stub
		return null;
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
	public void altaTipoDeRegistro(String nombreEdi, String nombre, String desc, Float costo, int cupo) throws Exception{
		
		ManejadorEdicion h_edicion = ManejadorEdicion.getInstance();
		Edicion ed = h_edicion.encontrarEdicion(nombreEdi);
		
		if(!ed.existeTipoRegistro(nombre)) {
			ed.crearTRegistro(nombre,desc,costo,cupo);
		} else {
			throw new Exception("Ya existe un tipo registro con este nombre");
		}
	
	}

	@Override
	public DTDetalleEvento verDetalleEvento(String nombreEvento) {
		// TODO Auto-generated method stub
		
		
	}
	
	
	
}