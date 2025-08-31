package logica;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Registro {

	private Edicion edicion;
	private Asistente asistente;
	private int costo;
	private TipoRegistro tipoReg;

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

	public Registro(Asistente as, TipoRegistro tipoReg, Edicion edi) {
		super();
		this.asistente=as;
		this.edicion=edi;
		this.tipoReg=tipoReg;
		return;
	}

	public Object getEdicion() {
		return edicion;
	}
	
	public void setEdicion(Edicion edicion) {
		this.edicion = edicion;
	}
	
	public Asistente getAsistente() {
		return asistente;
	}
	
	public void setAsistente(Asistente asistente) {
		this.asistente = asistente;
	}
	
	public int getCosto() {
		return costo;
	}
	
	public void setCosto(int costo) {
		this.costo = costo;
	}

}
