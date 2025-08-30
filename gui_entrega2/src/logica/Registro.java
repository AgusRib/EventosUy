package logica;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Registro {

	private Asistente asistente;
	private Edicion edicion;
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

	public void Registro(Asistente as, TipoRegistro tipoReg, Edicion edi) {
		super();
		this.asistente=as;
		this.edicion=edi;
		this.tipoReg=tipoReg;
		return;
	}

}
