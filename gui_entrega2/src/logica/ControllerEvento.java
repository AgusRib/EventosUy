package logica;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import excepciones.NombreEventoExcepcion;

public class ControllerEvento implements IControllerEvento{
	
	@Override
	public void altaEvento(String nombre, String sigla, LocalDate fechaAlta, String descripcion,Set<String> categorias)throws NombreEventoExcepcion, Exception {
		
		ManejadorEvento mE = ManejadorEvento.getInstance();
		if (mE.existeEvento(nombre)) {throw new Exception("El evento ya existe");}
		else {
			Evento nuevoEvento= new Evento(nombre, sigla, fechaAlta, descripcion);
			ManejadorCategoria mC = ManejadorCategoria.getInstance();
			for (String cat : categorias) {
				nuevoEvento.agregarCategoria(mC.obtenerCategoria(cat));
			}
			mE.agregarEvento(nuevoEvento);
		}
		
	   
	}
	
	@Override
	public Set<String> listarCategorias() {
		ManejadorCategoria mC = ManejadorCategoria.getInstance();
		return mC.obtenernombresCategorias();
	}
	
	
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
		if (ev == null) {
			return new HashSet<>(); // Return empty set if event not found
		}
		return ev.getEdiciones();
	}

	@Override
	public Set<String> listarPatrocinios(String nombreEdi) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Set(DTAsistente) listarAsistentesAEdicionDeEvento(String nomEdi) {
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(nombreEdi);
		return edi.obtenerAsistene()
		
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
		DTDetalleEdicion dtEdi = ed.devolverDT();
		Organizador org = ManejadorUsuario.getInstance().buscarOrganizadorDeEdicion(nombreEdi);
		dtEdi.setOrganizador(org != null ? org.getNombre() : null);
		return dtEdi;
	}

	@Override
	public DTTipoRegistro verDetalleTRegistro(String nombreEdi, String nomTRegistro) {
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(nombreEdi);
		TipoRegistro reg = edi.getTipoRegistro(nomTRegistro);
		return reg.infoTipoRegistro();
	}

	@Override
	public void altaTipoDeRegistro(String nombreEdi, String nombre, Float costo, int cupo) throws Exception{
		    
		
	}

	@Override
	public DTDetalleEvento verDetalleEvento(String nombreEvento) {
	  ManejadorEvento mE = ManejadorEvento.getInstance();
	  Evento ev = mE.obtenerEvento(nombreEvento);
	  DTDetalleEvento dtE = ev.devolverDT();
	  return dtE;
		
		
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
	public void altaEdicionDeEvento(String nombreEvento, String nicknameOrganizador, String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, String ciudad, String pais) {
		ManejadorEvento mEve = ManejadorEvento.getInstance();
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		Evento ev = mEve.obtenerEvento(nombreEvento);
		if(ev == null) throw new IllegalArgumentException("No existe el evento: " + nombreEvento);
		Edicion nueva = new Edicion(nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais);
		ev.agregarEdicion(nueva);
		Organizador org = mU.obtenerOrganizador(nicknameOrganizador);
		org.agregarEdicion(nombre);
		
	}
	
	@Override
	public void ingresarCategoria(String string) {
		ManejadorCategoria mC = ManejadorCategoria.getInstance();
		mC.agregarCategoria(new Categoria(string));
		return;
	}
	
	
	
	
}