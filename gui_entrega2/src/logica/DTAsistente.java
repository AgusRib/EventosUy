package logica;

import java.time.LocalDate;


public class DTAsistente {
	
	private String nickname;
	private String nombre;
	private String email;
	private String apellido;
	private LocalDate fechaNacimiento;

	public DTAsistente(String nickname, String nombre, String email, String apellido, LocalDate fechaNacimiento) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.email = email;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getnickname() {
		return nickname;
	}

	/*public void setnickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getapellido() {
		return apellido;
	}

	public void setapellido(String apellido) {
		this.apellido = apellido;
	}
	
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}*/
}

