package logica;
<<<<<<< HEAD
import java.util.List;
=======
import java.time.LocalDate;
import java.util.HashSet;
>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
import java.util.Set;
import excepciones.NombreEventoExcepcion;

public class ControllerEvento implements IControllerEvento{
	
	@Override
<<<<<<< HEAD
	public Set<String> listarEventos() {
		ManejadorEvento mE = ManejadorEvento.getInstance();
		List<Evento> eventos = mE.obtenerEventos();
		
		Set<String> nomEventos = null;
		for (Evento e : eventos) {
			nomEventos.add(e.getNombre());
=======
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
>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
		}
		
<<<<<<< HEAD
		return nomEventos;
		
=======
	   
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
>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
	}

	@Override
<<<<<<< HEAD
	public Set<String> listarEdiciones(String nombreEvento) {
		
		ManejadorEvento h_evento = ManejadorEvento.getInstance();
		Evento ev = h_evento.getEvento(nombreEvento);
		
		Set<String> ediciones = null;
		for (Edicion ed : ev.getEdiciones()) {
			ediciones.add( ed.getNombre() );
		}
		return ediciones;
=======
	public HashSet<String> listarEdiciones(String nombreEvento) {
		ManejadorEvento mE = ManejadorEvento.getInstance();
		Evento ev = mE.obtenerEvento(nombreEvento);
		if (ev == null) {
			return new HashSet<>(); // Return empty set if event not found
		}
		return ev.getEdiciones();
>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
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
		DTDetalleEdicion dtEdi = ed.devolverDT();
		Organizador org = ManejadorUsuario.getInstance().buscarOrganizadorDeEdicion(nombreEdi);
		dtEdi.setOrganizador(org != null ? org.getNombre() : null);
		return dtEdi;
	}

	@Override
	public DTTipoRegistro verDetalleTRegistro(String nombreEdi, String nomTRegistro) {
<<<<<<< HEAD
		ManejadorEdicion h_edicion = ManejadorEdicion.getInstance();
		Edicion edi = h_edicion.encontrarEdicion(nombreEdi);
		TipoRegistro tRegis = edi.obtenerTipoRegistro(nomTRegistro);
		return tRegis.infoTipoRegistro();
=======
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(nombreEdi);
		TipoRegistro reg = edi.getTipoRegistro(nomTRegistro);
		return reg.infoTipoRegistro();
>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
	}

	@Override
<<<<<<< HEAD
	public void altaTipoDeRegistro(String nombreEdi, String nombre, String desc, Float costo, int cupo) throws Exception{
		
		ManejadorEdicion h_edicion = ManejadorEdicion.getInstance();
		Edicion ed = h_edicion.encontrarEdicion(nombreEdi);
=======
	public void altaTipoDeRegistro(String nombreEdi, String nombre, Float costo, int cupo) throws Exception{
		    
>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
		
		if(!ed.existeTipoRegistro(nombre)) {
			ed.crearTRegistro(nombre,desc,costo,cupo);
		} else {
			throw new Exception("Ya existe un tipo registro con este nombre");
		}
	
	}

	@Override
	public DTDetalleEvento verDetalleEvento(String nombreEvento) {
<<<<<<< HEAD
		return null;
=======
	  ManejadorEvento mE = ManejadorEvento.getInstance();
	  Evento ev = mE.obtenerEvento(nombreEvento);
	  DTDetalleEvento dtE = ev.devolverDT();
	  return dtE;
		
		
>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
	}
	
	@Override
	public Set<String> listarTiposDeRegistro(String nombreEdi) {
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion ed = mEdi.encontrarEdicion(nombreEdi);
	    Set<String> tiposReg = new HashSet<>();
	    for (TipoRegistro tr : ed.getTiposRegistro()) {
	    	tiposReg.add(tr.getNombre());
	    }
		return tiposReg;
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
	
	@Override
	public Set<DTAsistente> listarAsistentesAEdicionDeEvento(String nomEdi) {
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(nombreEdi);
		return edi.obtenerAsistentes()	;
	}
	
	@Override
	public boolean elegirAsistenteYTipoRegistro(String nickAsistente, String tipoReg, String nomEdi) { //asumo que nomEdi viene de la interfaz en memoria
	
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(nombreEdi);
		
		boolean ok = edi.verificarCupoTipoReg(tipoReg);
		
		ok = edi.verificarRegistros(nickAsistente);
		
		if (ok==False) return False;
		
		altaRegistro(nickAsistente, tipoReg, edi);
		
		return ok;
	}
	
	@Override
	public void altaRegistro(String nickAsistente, string tipoReg, string edi) {
		
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(nombreEdi);
		
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		Asistente as = mU.obtenerAsistente(nickAsistente);
		
		edi.crearRegistro(as,tipoReg);
		return;
	}
	
	
	
}