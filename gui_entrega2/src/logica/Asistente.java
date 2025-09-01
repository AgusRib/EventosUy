package logica;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Asistente extends Usuario {
	
	private String apellido;
	private LocalDate fechaNacimiento;
	private Institucion institucion = null;
	private Set<Registro> registros = new HashSet<Registro>();
	
	/*public String getApellido() {
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
	}*/

	public Institucion getInstitucion() {
		return institucion;
	}
	
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}
	

	public Set<Registro> getRegistros() {
		return registros;
	}
	
	public void addRegistro(Registro reg) {
		this.registros.add(reg);
	}
	
	public Registro getRegistro(Edicion edicion) {
		for (Registro reg : registros) {
			if (reg.getEdicion().equals(edicion)) {
				return reg;
			}
		}
		return null;
	}

	public Asistente(String nickname, String nombre, String email, String apellido, LocalDate fechaNacimiento) {
		super(nickname, nombre, email);
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public DTAsistente infoAsist() {
		//para evitar los getters podriamos hacer que Usuario sea protected 
		DTAsistente dt = new DTAsistente(this.getNickname(), this.getNombre(), this.getEmail(), this.apellido, this.fechaNacimiento);
		return dt;
	}
	

	
}
