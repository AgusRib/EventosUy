package logica.data_types;

import java.time.LocalDate;




public class DTAsistente extends DataUsuario {
	
	private String nickname;
	private String nombre;
	private String email;
	private String apellido;
	private LocalDate fechaNacimiento;
	private String institucion;

	public DTAsistente(String nickname, String nombre, String email, String apellido, LocalDate fechaNacimiento,String institucion) {
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


   public String getInstitucion() {
		return institucion;
	}}

