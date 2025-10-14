package logica.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import logica.dataTypes.DTAsistente;
import logica.dataTypes.DTDetalleEdicion;
import logica.dataTypes.DTPatrocinio;
import logica.dataTypes.DTTipoRegistro;
import logica.enumerators.EstadoEdicion;

public class Edicion {
	private String nombre;
	private String sigla;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private LocalDate fechaAlta;
	private String ciudad;
	private String pais;
	private final Set<Registro> registros;
    private final Set<TipoRegistro> tiposRegistro;
    private Evento evento;
	private final Map<String, Patrocinio> patrociniosPorInstitucion = new LinkedHashMap<>();
	private Organizador organizador;
	private EstadoEdicion estado;
    
	public Edicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta,
			String ciudad, String pais, Evento evento, Organizador organizador) {
		super();
		this.nombre = nombre;
		this.sigla = sigla;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.fechaAlta = fechaAlta;
		this.ciudad = ciudad;
		this.pais = pais;
		this.registros = new LinkedHashSet<>();
		this.tiposRegistro = new LinkedHashSet<>();
		this.evento = evento;
		this.organizador =organizador;
		this.setEstado(EstadoEdicion.Ingresada); //siempre que agregamos una edicion, estado = ingresada.

	}
	
	
	// Getters y setters
	public String getNombre() {
		return nombre;
	}
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public String getSigla() {
		return sigla;
	}
	public String getCiudad() {
		return ciudad;
	}
	public String getPais() {
		return pais;
	}
	public Set<TipoRegistro> getTiposRegistro() {
		return tiposRegistro;
	}
	public Organizador getOrganizador() {
		return organizador;
	}
	
	public EstadoEdicion getEstado() {
		return estado;
	}

	public void setEstado(EstadoEdicion estado) {
		this.estado = estado;
	}
	
	
	//Otros
	public Set<DTTipoRegistro> obtenerTipoReg(){
		Set<DTTipoRegistro> setTipoReg = new HashSet<>();
		for (TipoRegistro tipoReg : this.tiposRegistro) {
			setTipoReg.add(tipoReg.infoTipoRegistro());
		}
		return setTipoReg;
	}
	
	public Set<DTAsistente> obtenerAsistentes(){
		Set<DTAsistente> setAsist= new HashSet<>();
		for (Registro reg : this.registros) {
			Asistente asist = reg.getAsistente();
			setAsist.add(asist.infoAsist() );
		}
		return setAsist;
	}
	
	public DTPatrocinio getPatrocinio(String nombreInstitucion) {
		Patrocinio pat = patrociniosPorInstitucion.get(nombreInstitucion);
		
		if (pat==null) return null;
		else {
		return new DTPatrocinio(
				nombreInstitucion,
		        pat.getFecha(),
		        pat.getMonto(),
		        pat.getCodigo(),
		        pat.getNivelPatrocinio(),
		        pat.getTipoRegistroGratis(),
		        pat.getCantRegsGratis()
						);
		}
	}
	

	public DTDetalleEdicion devolverDT() {
		Set<String> nombresTiposRegistros = new LinkedHashSet<>();
		for (TipoRegistro tr : this.tiposRegistro) {
			if (tr != null && tr.getNombre() != null) {
	            nombresTiposRegistros.add(tr.getNombre());
	        }
		}
		Set<String> nombresInstituciones = patrociniosPorInstitucion.keySet();
		
		return new DTDetalleEdicion(
				this.nombre,
				this.sigla,
				this.fechaInicio,
				this.fechaFin,
				this.fechaAlta,
				this.ciudad,
				this.pais,
				organizador.getNickname(),
				nombresTiposRegistros,
				nombresInstituciones,
				this.estado
		);
	}
	
	public TipoRegistro getTipoRegistro(String nomTRegistro) {
		for (TipoRegistro tipoReg : this.tiposRegistro) {
			if (nomTRegistro.equals(tipoReg.getNombre())) {
				return tipoReg;
			}
		}
		return null;
	}
	
	public boolean verificarCupoTipoReg(String tipoReg) {
		
		TipoRegistro tReg = this.getTipoRegistro( tipoReg );
		return tReg.verificarCupo();
	}
	
	public boolean verificarRegistros(String nickAsist) {
		
		for (Registro reg : this.registros) {
			Asistente asist = reg.getAsistente();
			String nick = asist.getNickname();
			if (nick == nickAsist) {
				return false;
			}
		}
		return true;
	}

	public void crearRegistro(Asistente asis, String tipoReg) {
		
		TipoRegistro treg = this.getTipoRegistro(tipoReg);
		treg.restarCupo();
		Registro nReg = new Registro(asis, treg, this);
		this.registros.add(nReg);
		asis.addRegistro(nReg);
		return;
	}

	public boolean existeTipoRegistro(String nombreTRegis) {
		
		if (this.tiposRegistro != null) {
			for (TipoRegistro tipoRegistro : tiposRegistro) {
				if (tipoRegistro.getNombre() == nombreTRegis) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public void crearTRegistro(String nom, String desc, Float costo, int cupo) {
		TipoRegistro newTRegistro = new TipoRegistro(nom, desc, costo, cupo);
		tiposRegistro.add(newTRegistro);
	}
	
	public void agregarPatrocinio(String nombreInstitucion, Patrocinio pat) {
		patrociniosPorInstitucion.put(nombreInstitucion, pat);
	}


	public Set<String> getPatrocinios() {
		return patrociniosPorInstitucion.keySet();
	}


	public Evento getEvento() {
		return evento;
	}


	public void setOrganizador(Organizador org) {
	  		this.organizador = org;
		
	}
}
