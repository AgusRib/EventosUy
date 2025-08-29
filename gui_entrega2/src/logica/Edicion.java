package logica;

import java.time.LocalDate;
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
	
	public DTPatrocinio getPatrocinio(String nombreInstitucion) {
		DTPatrocinio p = patrociniosPorInstitucion.get(nombreInstitucion);
		return p;
		
	}



	
	

	
}
