package logica;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.time.LocalDate;

public class DTDetalleEvento {
	private String nombre;
	private String sigla;
	private LocalDate fechaAlta;
	private String descripcion;
	private HashSet<String>  categorias;
	private HashSet<String> ediciones;
	public DTDetalleEvento(String nombre,String sigla, LocalDate fecha, String descripcion, HashSet<String> categorias,
			HashSet<String> hashSet) {
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
	public String getDescripcion() {
		return descripcion;
	}
	public HashSet<String> getCategorias() {
		return categorias;
	}
	public HashSet<String> getEdiciones() {
		return ediciones;
	}
	
	
	
	
}
