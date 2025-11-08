package logica.models;

import java.util.HashSet;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name="ORGANIZADOR")
public class Organizador extends Usuario {

	@Column(name="DESC", nullable = false) private String descripcion;
    @Column(name="WEB") private String web;
    @ElementCollection @CollectionTable(name = "EDICIONES_ORGANIZADAS") private Set<String> ediciones = new HashSet<String>();
	
    //GETTERS Y SETTERS
    public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	
    public Set<String> getEdiciones() {
		return ediciones;
	}
    
	/*public void setEdiciones(HashSet<String> ediciones) {
		this.ediciones = ediciones;
	}*/
	
	//METODOS DE LA COLECCION DE EDICIONES
	public void agregarEdicion(String nombreEdicion) {
		ediciones.add(nombreEdicion);
	}
	
	public boolean organizaEdicion(String nombreEdicion) {
		return ediciones.contains(nombreEdicion);
	}
	
	//CONSTRUCTOR
	public Organizador(String nickname, String nombre, String email, String password, String descripcion, String web) {
		super(nickname, nombre, email, password);
		this.descripcion = descripcion;
		this.web = web;
	}
	
	public Organizador() { super(); } //necesario para que funcione el jpa
	
}
