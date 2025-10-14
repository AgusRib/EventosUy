package logica.models;

import java.time.LocalDate;

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
	public Registro(Asistente asis, TipoRegistro tipoReg, Edicion edi) {
		super();
		this.asistente=asis;
		this.edicion=edi;
		this.setTipoReg(tipoReg);
		this.costo = tipoReg.getCosto();
		IControllerEvento cEve = new ControllerEvento();
		this.fechaRegistro = cEve.getFechaSistema();
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
