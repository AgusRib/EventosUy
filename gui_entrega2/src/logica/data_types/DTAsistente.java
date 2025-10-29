package logica.data_types;

import java.time.LocalDate;




public class DTAsistente extends DataUsuario {
	
	private String nickname;
	private String nombre;
	private String email;
	private String apellido;
	private LocalDate fechaNacimiento;
	private String institucion;

	public DTAsistente(String nickname, String nombre, String email, String apellido, LocalDate fechaNacimiento, String institucion) {
		super(nickname, nombre, email, TipoUsuario.ASISTENTE);
		this.nickname = nickname;
		this.nombre = nombre;
		this.email = email;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		
	}

	public String getnickname() {
		return nickname;
	}

	public String getNombre() {
		return nombre;
	}

	

	public String getEmail() {
		return email;
	}

	

	public String getapellido() {
		return apellido;
	}


	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}


   public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

   public String getInstitucion() {
		return institucion;
	}

	public DTAsistente() {
	
	}
}

