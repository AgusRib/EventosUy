package logica;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Registro {
	
	private Asistente asistente;
	private Edicion edicion;
	
	private LocalDate fechaRegistro;
	
	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public Asistente getAsistente() {
		return this.asistente;
	}
	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	

}
