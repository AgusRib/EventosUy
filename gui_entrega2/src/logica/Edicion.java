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
	private final Set<Registro> registros;
    private final Set<TipoRegistro> tiposRegistro;
	private final Map<String, Patrocinio> patrociniosPorInstitucion = new LinkedHashMap<>();
    
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
		this.registros = new LinkedHashSet<>();
		this.tiposRegistro = new LinkedHashSet<>();

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
	
	/*blic void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}*/
	
	
	
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
		Patrocinio p = patrociniosPorInstitucion.get(nombreInstitucion);
		return new DTPatrocinio(
		        p.getFecha(),
		        p.getMonto(),
		        p.getCodigo(),
		        p.getNivelPatrocinio(),
		        p.getTipoRegistroGratis(),
		        p.getCantRegsGratis()
		 );
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
		as.addRegistro(nReg);
		return;
	}

	public boolean existeTipoRegistro(String nombreTRegis) {
		
		if(this.tiposRegistro != null) {
			for (TipoRegistro tipoRegistro : tiposRegistro) {
				if(tipoRegistro.getNombre() == nombreTRegis) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public void crearTRegistro(String nom,String desc,Float costo, int cupo) {
		TipoRegistro newTRegistro = new TipoRegistro(nom,desc,costo,cupo);
		tiposRegistro.add(newTRegistro);
	}
	
	public void agregarPatrocinio(String nombreInstitucion, Patrocinio pat) {
		patrociniosPorInstitucion.put(nombreInstitucion, pat);
	}


	public Set<String> getPatrocinios() {
		return new LinkedHashSet<>(patrociniosPorInstitucion.keySet());
	}
	

	
}
