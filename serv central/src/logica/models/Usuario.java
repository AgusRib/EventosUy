package logica.models;

import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)	
public abstract class Usuario {
	
	
	@Id @GeneratedValue(strategy = GenerationType.TABLE) @Column(name = "USER_ID") private int id;
	@Column(name="NICKNAME", nullable = false, unique = true) private String nickname;
	@Column(name="EMAIL", nullable = false, unique = true) private String email;
	@Column(name="NOMBRE") private String nombre;
	@Column(name="PASSWORD") private String password;
	@Transient
	private List<Usuario> seguidores;
	@Transient
	private List<Usuario> seguidos;

	public int getId() {
		return id;
	}

	public String getNickname() {
		return nickname;
	}
	
	public List<Usuario> getSeguidores() {
		return seguidores;
	}
	public List<Usuario> getSeguidos() {
		return seguidos;
	}
	
	
	
	public  String getNombre() {
		return nombre;
	}
	
	public String getEmail() {
		return email;
	}
	public  String getPassword() {
		return password;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Usuario(String nickname, String nombre, String email, String password) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		
		
	}

	public void agregarSeguido(Usuario userSeguido) {
		if (seguidos == null) {
			seguidos = new ArrayList<Usuario>();
		}
		// Evitar duplicados
		if (!seguidos.contains(userSeguido)) {
			seguidos.add(userSeguido);
			
			// Actualizar la relación bidireccional: añadir este usuario a los seguidores del usuario seguido
			if (userSeguido.seguidores == null) {
				userSeguido.seguidores = new ArrayList<Usuario>();
			}
			if (!userSeguido.seguidores.contains(this)) {
				userSeguido.seguidores.add(this);
			}
		}
	}

	public void eliminarSeguido(Usuario userSeguido) {
		if (seguidos != null) {
			seguidos.remove(userSeguido);
			
			// Actualizar la relación bidireccional: remover este usuario de los seguidores del usuario que se deja de seguir
			if (userSeguido.seguidores != null) {
				userSeguido.seguidores.remove(this);
			}
		}
	}

		//agrego un constructor vacio pq JPA lo necesita para hacer la tabla
	public Usuario() {}

	

}