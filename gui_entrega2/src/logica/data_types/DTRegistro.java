package logica.data_types;

import java.time.LocalDate;

import logica.models.TipoRegistro;

public class DTRegistro {
	private LocalDate fechaRegistro;
	private String nombreEdicion;
	private String nombreAsistente;
	private TipoRegistro tipoRegistro;
	private float costo;
	
	public DTRegistro(LocalDate fechaRegistro, String nombreEdicion, String nombreAsistente, float costo, TipoRegistro tipoReg) {
		this.fechaRegistro = fechaRegistro;
		this.nombreEdicion = nombreEdicion;
		this.nombreAsistente = nombreAsistente;
		this.costo = costo;
		this.tipoRegistro = tipoReg;
	}
	
	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}
	
	public String getNombreEdicion() {
		return nombreEdicion;
	}
	
	public String getNombreAsistente() {
		return nombreAsistente;
	}
	
	public float getCosto() {
		return costo;
	}

	public TipoRegistro getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(TipoRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
	
	
	
	
	
}
