package logica;

import java.time.LocalDate;
import java.util.List;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Edicion {
	private String nombre;
	private String sigla;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private LocalDate fechaAlta;
	private String ciudad;
	private String pais;
	private List<TipoRegistro> TRegistros;
	private final Set<Registro> registros;
    private final Set<TipoRegistro> tiposRegistro;
	private final Map<String, DTPatrocinio> patrociniosPorInstitucion = new LinkedHashMap<>();
    
	public Edicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta,
			String ciudad, String pais) {
		super();
		this.nombre = nombre;
		this.sigla = sigla;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.fechaAlta = fechaAlta;
		this.ciudad = ciudad;
		this.pais = pais;
		this.registros = null;
		this.tiposRegistro = new LinkedHashSet<>();

	}
	
	
	// Getters y setters
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public Set<TipoRegistro> getTiposRegistro() {
		return tiposRegistro;
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
		DTPatrocinio p = patrociniosPorInstitucion.get(nombreInstitucion);
		return p;
		
	}


	public DTDetalleEdicion devolverDT() {
		Set<String> nombresTiposRegistros = new LinkedHashSet<>();
		for (TipoRegistro tr : this.tiposRegistro) {
			if (tr != null && tr.getNombre() != null) {
	            nombresTiposRegistros.add(tr.getNombre());
	        }
		}
		Set<String> nombresInstituciones = new LinkedHashSet<>(patrociniosPorInstitucion.keySet());
		
		return new DTDetalleEdicion(
				this.nombre,
				this.sigla,
				this.fechaInicio,
				this.fechaFin,
				this.fechaAlta,
				this.ciudad,
				this.pais,
				null,
				nombresTiposRegistros,
				nombresInstituciones
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

	public void crearRegistro(Asistente as, String tipoReg) {
		
		TipoRegistro treg = this.getTipoRegistro(tipoReg);
		treg.restarCupo();
		Registro nReg = new Registro(as, treg, this);
		this.registros.add(nReg);
		return;
	}

	public boolean existeTipoRegistro(String nombreTRegis) {
		
		if(this.TRegistros != null) {
			for (TipoRegistro tipoRegistro : TRegistros) {
				if(tipoRegistro.getNombre() == nombreTRegis) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public void crearTRegistro(String nom,String desc,Float costo, int cupo) {
		TipoRegistro newTRegistro = new TipoRegistro(nom,desc,costo,cupo);
		TRegistros.addFirst(newTRegistro);
	}
	

	
}
