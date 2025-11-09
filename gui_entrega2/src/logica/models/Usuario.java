package logica.models;

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

	public int getId() {
		return id;
	}
	
	public String getNickname() {
		return nickname;
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
	
	//agrego un constructor vacio pq JPA lo necesita para hacer la tabla
	public Usuario() {}
	

}
