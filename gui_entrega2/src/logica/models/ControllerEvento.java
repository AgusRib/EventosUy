package logica.models;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import excepciones.AsistenteYaRegistrado;
import excepciones.CupoLLeno;
import excepciones.FechaInicioPOSTFINAL;
import excepciones.FechaInicioPREALTA;
import excepciones.FechaRegPREALTA;
import excepciones.NombreEdicionExistenteExcepcion;
import excepciones.NombreEventoExcepcion;
import logica.controllers.IControllerEvento;
import logica.dataTypes.DTAsistente;
import logica.dataTypes.DTDetalleEdicion;
import logica.dataTypes.DTDetalleEvento;
import logica.dataTypes.DTPatrocinio;
import logica.dataTypes.DTRegistro;
import logica.dataTypes.DTTipoRegistro;
import logica.enumerators.EstadoEdicion;
import logica.enumerators.NivelPatrocinio;
import logica.manejadores.ManejadorCategoria;
import logica.manejadores.ManejadorEdicion;
import logica.manejadores.ManejadorEvento;
import logica.manejadores.ManejadorUsuario;


public class ControllerEvento implements IControllerEvento{
	
	public static LocalDate fechaSistema = LocalDate.now();
	
	@Override
	public Set<String> listarEventos() {
		ManejadorEvento mE = ManejadorEvento.getInstance();
		HashMap<String,Evento> eventos = mE.obtenerEventos(); 
		Set<String> nomEventos = new LinkedHashSet<>();
		for (Evento e : eventos.values()) {
			nomEventos.add(e.getNombre());
		}
		return nomEventos;
	}
	
	@Override
	public Set<String>listarEdicionesTodas(){
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		HashMap<String,Edicion> ediciones = mEdi.obtenerEdicionesPendientes();
		ediciones.putAll(mEdi.obtenerEdicionesConfirmadas());
		ediciones.putAll(mEdi.obtenerEdicionesRechazadas());
		Set<String> nomEdiciones = new LinkedHashSet<>();
		for (Edicion e : ediciones.values()) {
			nomEdiciones.add(e.getNombre());
		}
		return nomEdiciones;
	}
	
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
	public Set<String> listarEdiciones(String nombreEvento) {
		
		ManejadorEvento h_evento = ManejadorEvento.getInstance();
		Evento ev = h_evento.obtenerEvento(nombreEvento);
		Set<String> ediciones = new LinkedHashSet<>();
		
		if(ev != null) {
		  ediciones = ev.getEdiciones();
		}
		return ediciones;
	}

	@Override
	public Set<String> listarPatrocinios(String nombreEdi) {
		Set<String> listaPat = new LinkedHashSet<>();
		
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(nombreEdi);
		listaPat = edi.getPatrocinios();
		
		return listaPat;
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
		System.out.println(ed);
		DTDetalleEdicion dtEdi = ed.devolverDT();
		Organizador org = ed.getOrganizador();
		dtEdi.setOrganizador(org != null ? org.getNombre() : null);
		return dtEdi;
	}

	@Override
	public DTTipoRegistro verDetalleTRegistro(String nombreEdi, String nomTRegistro) {
		ManejadorEdicion h_edicion = ManejadorEdicion.getInstance();
		Edicion edi = h_edicion.encontrarEdicion(nombreEdi);
		TipoRegistro tRegis = edi.getTipoRegistro(nomTRegistro);
		return tRegis.infoTipoRegistro();
	}

	@Override
	public void altaTipoDeRegistro(String nombreEdi, String nombre, String desc, Float costo, int cupo) throws excepciones.TipoRegistroExistenteExcepcion, Exception {
		
		ManejadorEdicion h_edicion = ManejadorEdicion.getInstance();
		Edicion ed = h_edicion.encontrarEdicion(nombreEdi);
		if(!ed.existeTipoRegistro(nombre)) {
			ed.crearTRegistro(nombre,desc,costo,cupo);
		} else {
			throw new excepciones.TipoRegistroExistenteExcepcion("Ya existe un tipo registro con este nombre");
		}
	
	}

	@Override
	public DTDetalleEvento verDetalleEvento(String nombreEvento) {
	  ManejadorEvento mE = ManejadorEvento.getInstance();
	  Evento ev = mE.obtenerEvento(nombreEvento);
	  DTDetalleEvento dtE = ev.devolverDT();
	  return dtE;		
	}
	
	@Override
	public Set<String> listarTiposDeRegistro(String nombreEdi) {
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion ed = mEdi.encontrarEdicion(nombreEdi);
	    Set<String> tiposReg = new HashSet<>();
	    if (ed == null) {
	        return tiposReg; // o lanzar una excepción si prefieres
	    };
	    for (TipoRegistro tr : ed.getTiposRegistro()) {
	    	tiposReg.add(tr.getNombre());
	    }
		return tiposReg;
	}
	
@Override
	public DTRegistro infoRegistro(String edicion, String usuario) {
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(edicion);
		
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		Asistente usu = mU.obtenerAsistente(usuario);
		
		Registro reg = usu.getRegistro(edi);
		DTRegistro dtR = new DTRegistro(reg.getFechaRegistro(), edi.getNombre(), usu.getNickname(), reg.getCosto());
		return dtR;
	}

	@Override
	public void altaEdicionDeEvento(String nombreEvento, String nicknameOrganizador, String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, String ciudad, String pais)throws NombreEdicionExistenteExcepcion,FechaInicioPOSTFINAL,FechaInicioPREALTA, Exception {
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		if(mEdi.existeEdicion(nombre)) throw new NombreEdicionExistenteExcepcion("Ya existe una edicion con el nombre: " + nombre);
		if(fechaInicio.isAfter(fechaFin)) throw new FechaInicioPOSTFINAL("La fecha de inicio no puede ser posterior a la fecha de finalizacion");
		if(fechaInicio.isBefore(fechaAlta)) throw new FechaInicioPREALTA(" La fecha de inicio no puede ser anterior a la fecha de alta de edicion");
		
		ManejadorEvento mEve = ManejadorEvento.getInstance();
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		Evento ev = mEve.obtenerEvento(nombreEvento);
		
		if(ev == null) throw new IllegalArgumentException("No existe el evento: " + nombreEvento);
		if(fechaInicio.isBefore(ev.getFechaAlta())) throw new FechaInicioPREALTA(" La fecha de inicio no puede ser anterior a la fecha de alta del evento");
		if(fechaAlta.isBefore(ev.getFechaAlta())) throw new FechaInicioPREALTA(" La fecha de alta de edicion no puede ser anterior a la fecha de alta del evento");
		
		Organizador org = mU.obtenerOrganizador(nicknameOrganizador);
		org.agregarEdicion(nombre);

		Edicion nueva = new Edicion(nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais,ev,org);
		ev.agregarEdicion(nueva); 
		mEdi.agregarEdicionIngresada(nueva);
		
	}
	
	public void ConfirmarRechazarEdicion(String nombreEdi, boolean aceptar) {
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(nombreEdi);
		if(aceptar && edi.getEstado() == EstadoEdicion.Ingresada) {
			edi.setEstado(EstadoEdicion.Confirmada);
			mEdi.agregarEdicionIngresada(edi);
		} else {
			edi.setEstado(EstadoEdicion.Rechazada);
			//evaluar si hay que eliminar de alguna coleccion
		}
	}
	
	@Override
	public void ingresarCategoria(String string) {
		ManejadorCategoria mC = ManejadorCategoria.getInstance();
		mC.agregarCategoria(new Categoria(string));
	}
	
	@Override
	public Set<DTAsistente> listarAsistentesAEdicionDeEvento(String nomEdi) {
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(nomEdi);
		return edi.obtenerAsistentes()	;
	}
	
	@Override
	public void elegirAsistenteYTipoRegistro(String nickAsistente, String tipoReg, String nomEdi) throws FechaInicioPREALTA, CupoLLeno,AsistenteYaRegistrado, Exception { 
		//asumo que nomEdi viene de la interfaz en memoria
			
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(nomEdi);
		Evento ev = edi.getEvento();
		if(ev.getFechaAlta().isAfter(fechaSistema)) throw new FechaRegPREALTA("La edicion no se encuentra habilitada para registros.");
		
		
		if(!edi.verificarCupoTipoReg(tipoReg)) throw new CupoLLeno("No hay cupo disponible para el tipo de registro seleccionado.");
			
	    if(!edi.verificarRegistros(nickAsistente)) throw new AsistenteYaRegistrado("El asistente ya se encuentra registrado en la edicion seleccionada.");
		
		
		
		altaRegistro(nickAsistente, tipoReg, nomEdi);
		
		
	}
	
	@Override
	public void altaRegistro(String nickAsistente, String tipoReg, String nombreEdi) {
		
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(nombreEdi);
		
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		Asistente as = mU.obtenerAsistente(nickAsistente);
		
		edi.crearRegistro(as,tipoReg);
		return;
	}
	
	@Override
	public void altaPatrocinio(String nombreEdi, String institucion, NivelPatrocinio nivel, double aporteEconomico, String tipoRegistroGratis, int cantidadGratis, String codigo) {
	    ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
	    Edicion ed = mEdi.encontrarEdicion(nombreEdi);
	    TipoRegistro tr = ed.getTipoRegistro(tipoRegistroGratis);
	    double costoUnit = tr.getCosto();
	    double costoGratis = costoUnit * cantidadGratis;
	    double limite = aporteEconomico * 0.20;
	    Patrocinio p = new Patrocinio(
	            fechaSistema,
	            (int) Math.round(aporteEconomico),
	            codigo,
	            cantidadGratis,
	            nivel,
	            tipoRegistroGratis
	    );
	    ed.agregarPatrocinio(institucion, p);
	}

	@Override
	public LocalDate getFechaSistema() {
		return fechaSistema;
	}

	@Override
	public LocalDate setFechaSistema(LocalDate fechaNueva) {
		return fechaSistema = fechaNueva;
	}
	
	public String NomEvPorEd(String nomEdi) {
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		Edicion edi = mEdi.encontrarEdicion(nomEdi);
		Evento ev = edi.getEvento();
		return ev.getNombre();
	}
	@Override
	public void AceptarEdicion(String nomedi, String nomev) {
		ManejadorEvento mE = ManejadorEvento.getInstance();
		Evento ev = mE.obtenerEvento(nomev);
		Edicion edi = ev.getEdicion(nomedi);
		edi.setEstado(EstadoEdicion.Confirmada);
		ev.CambioEstado(edi,EstadoEdicion.Confirmada);
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		mEdi.CambioEstado(edi,EstadoEdicion.Confirmada);
		
		
	}
	@Override
	public void RechazarEdicion(String nomedi, String nomev) {
		ManejadorEvento mE = ManejadorEvento.getInstance();
		Evento ev = mE.obtenerEvento(nomev);
		Edicion edi = ev.getEdicion(nomedi);
		edi.setEstado(EstadoEdicion.Rechazada);
		ev.CambioEstado(edi,EstadoEdicion.Rechazada);
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		mEdi.CambioEstado(edi,EstadoEdicion.Rechazada);
		
		
	}
	
	@Override
	public Set<String>listarEdicionesConfirmadas(String nombreEvento) {
		
		ManejadorEvento h_evento = ManejadorEvento.getInstance();
		Evento ev = h_evento.obtenerEvento(nombreEvento);
		Set<String> ediciones = new LinkedHashSet<>();
		
		if(ev != null) {
		  for (Edicion e : ev.getColEdicionesConfirmadas()) {
			  ediciones.add(e.getNombre());
		  }
		}
		return ediciones;
	}
	
	@Override
	public Set<String> listarEdicionesPendientes(String nombreEvento) {
		
		ManejadorEvento h_evento = ManejadorEvento.getInstance();
		Evento ev = h_evento.obtenerEvento(nombreEvento);
		Set<String> ediciones = new LinkedHashSet<>();
		
		if(ev != null) {
		  for (Edicion e : ev.getColEdicionesPendientes()) {
			  ediciones.add(e.getNombre());
		  }
		}
		return ediciones;
	}
	
	
}