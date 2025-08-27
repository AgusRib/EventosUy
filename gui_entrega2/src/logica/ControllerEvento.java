package logica;
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
	public void altaTipoDeRegistro(String nombreEdi, String nombre, Float costo, int cupo) throws Exception{
		
		
	}

	@Override
	public DTDetalleEvento verDetalleEvento(String nombreEvento) {
		// TODO Auto-generated method stub
		
		
	}
	
	@Override
	public Set<DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion) {
		//TODO zangano
		manejadorEvento mE = manejadorEvento.getInstance();
		Evento ev = mE.getEvento(nombreEvento);
		Edicion edi = ev.find(nombreEdicion);
		
		return edi.obtenerTipoReg()
		
	}
	
	
	
	
}