package logica.data_types;

import logica.dataTypes.TipoUsuario;

public class DataUsuario {
	
	public enum TipoUsuario {ASISTENTE, ORGANIZADOR}
	
	private String nickname;
	private String nombre;
	private String email;
	private TipoUsuario tipo;
	
	public String getNickname() {
		return nickname;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getEmail() {
		return email;
	}
	
	public TipoUsuario getTipo() {
		return tipo;
	}
	
	public DataUsuario(String nickname, String nombre, String email, TipoUsuario tipo) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.email = email;
		this.tipo = tipo;
	}
	
	
	
}
