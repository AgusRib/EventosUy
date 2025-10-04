package logica.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import logica.controllers.ControllerEvento;
import logica.controllers.IControllerEvento;

public class Registro {

	private Edicion edicion;
	private Asistente asistente;
	private float costo;
	private TipoRegistro tipoReg;
	private LocalDate fechaRegistro;

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}


	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	//TODO: implementar caso en el que el costo sea 0 (ej: asistente de una institucion que patrocina)
	public Registro(Asistente as, TipoRegistro tipoReg, Edicion edi) {
		super();
		this.asistente=as;
		this.edicion=edi;
		this.setTipoReg(tipoReg);
		this.costo = tipoReg.getCosto();
		IControllerEvento ce = new ControllerEvento();
		this.fechaRegistro = ce.getFechaSistema();
		return;
	}

	public Edicion getEdicion() {
		return edicion;
	}
	
	/*public void setEdicion(Edicion edicion) {
		this.edicion = edicion;
	}*/
	
	public Asistente getAsistente() {
		return asistente;
	}
	
	/*public void setAsistente(Asistente asistente) {
		this.asistente = asistente;
	}*/
	
	public float getCosto() {
		return costo;
	}
	
	/*public void setCosto(int costo) {
		this.costo = costo;
	}


	public TipoRegistro getTipoReg() {
		return tipoReg;
	}*/


	public void setTipoReg(TipoRegistro tipoReg) {
		this.tipoReg = tipoReg;
	}

}
