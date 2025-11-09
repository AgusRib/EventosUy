package logica.models;

import jakarta.persistence.*;

@Entity
@Table(name = "INSTITUCION")
public class Institucion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, unique = true)
	private String nombre;
	
	@Column
	private String descripcion;
	
	@Column
	private String web;
	
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public String getWeb() {
		return web;
	}
	
	
	public Institucion(String nombre, String descripcion, String web) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.web = web;
	}
	
	public Institucion() {} 
	
	
	
}
