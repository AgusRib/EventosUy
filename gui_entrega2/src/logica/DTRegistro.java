package logica;

import java.time.LocalDate;

public class DTRegistro {
	private LocalDate fechaRegistro;
	private String nombreEdicion;
	private String nombreAsistente;
	private int costo;
	
	public DTRegistro(LocalDate fechaRegistro, String nombreEdicion, String nombreAsistente, int costo) {
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
	
	public int getCosto() {
		return costo;
	}
	
	
	
	
	
}
