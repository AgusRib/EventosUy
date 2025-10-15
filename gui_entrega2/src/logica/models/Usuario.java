package logica.models;

public abstract class Usuario {
	
	private String nickname;
	protected String nombre;
	private String email;
	private String password;

	public String getNickname() {
		return nickname;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	/*
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	 
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
		
	public void setEmail(String email) {
		this.email = email;
	}*/

	public Usuario(String nickname, String nombre, String email, String password) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		
	}

	

}
