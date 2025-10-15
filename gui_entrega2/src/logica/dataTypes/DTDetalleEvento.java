package logica.data_types;

import java.time.LocalDate;
import java.util.HashSet;

public class DTDetalleEvento {
	private String nombre;
	private String sigla;
	private LocalDate fechaAlta;
	private String descripcion;
	private HashSet<String>  categorias;
	private HashSet<String> ediciones;
	public DTDetalleEvento(String nombre, String sigla, LocalDate fecha, String descripcion, HashSet<String> categorias,
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
	public LocalDate getFechaAlta() {
		return fechaAlta;
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
