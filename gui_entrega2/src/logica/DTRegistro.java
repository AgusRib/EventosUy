package logica;

import java.time.LocalDate;

public class DTRegistro {
	private LocalDate fechaRegistro;
	private String nombreEdicion;
	private String nombreAsistente;
	private float costo;
	
	public DTRegistro(LocalDate fechaRegistro, String nombreEdicion, String nombreAsistente, float costo) {
		this.fechaRegistro = fechaRegistro;
		this.nombreEdicion = nombreEdicion;
		this.nombreAsistente = nombreAsistente;
		this.costo = costo;
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
	
	
	
	
	
}
