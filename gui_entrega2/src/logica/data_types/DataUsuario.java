package logica.data_types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
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
	
	public DataUsuario() {

	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}
	
	
}