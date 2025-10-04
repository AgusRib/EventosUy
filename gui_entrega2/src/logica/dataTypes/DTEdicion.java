package logica.dataTypes;

import java.time.LocalDate;

public class DTEdicion {
	private String nombre;
	private String sigla;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String ciudad;
	private String pais;
	public DTEdicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin, String ciudad,
			String pais) {
		super();
		this.nombre = nombre;
		this.sigla = sigla;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.ciudad = ciudad;
		this.pais = pais;
	}
	public String getNombre() {
		return nombre;
	}
	public String getSigla() {
		return sigla;
	}
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public String getCiudad() {
		return ciudad;
	}
	public String getPais() {
		return pais;
	}
	
	
}
