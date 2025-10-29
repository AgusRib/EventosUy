package logica.data_types;

import java.time.LocalDate;
import java.util.Set;

public class DTDetalleEvento {
	private String nombre;
	private String sigla;
	private LocalDate fechaAlta;
	private String descripcion;
	private Set<String>  categorias;
	private Set<String> ediciones;
	public DTDetalleEvento(String nombre, String sigla, LocalDate fecha, String descripcion, Set<String> categorias,
			Set<String> hashSet) {
		super();
		this.nombre = nombre;
		this.sigla = sigla;
		this.fechaAlta= fecha;
		this.descripcion = descripcion;
		this.categorias = categorias;
		this.ediciones = hashSet;
	}
	
	public String getNombre() {
		return nombre;
	}
	public String getSigla() {
		return sigla;
	}
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public Set<String> getCategorias() {
		return categorias;
	}
	public Set<String> getEdiciones() {
		return ediciones;
	}
	
	public DTDetalleEvento() {

	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setCategorias(Set<String> categorias) {
		this.categorias = categorias;
	}

	public void setEdiciones(Set<String> ediciones) {
		this.ediciones = ediciones;
	}
	
	
	
}
