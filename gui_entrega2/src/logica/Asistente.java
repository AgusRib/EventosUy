package logica;

import java.time.LocalDate;

public class Asistente extends Usuario {
	
	private String apellido;
	private LocalDate fechaNacimiento;
	private Institucion institucion = null;

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Asistente(String nickname, String nombre, String email, String apellido, LocalDate fechaNacimiento) {
		super(nickname, nombre, email);
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
	}

	
}
