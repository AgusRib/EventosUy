package logica;

import java.util.List;

public class DTDetalleEvento {
	private String nombre;
	private String sigla;
	private String descripcion;
	private List<String> categorias;
	private List<String> ediciones;
	public DTDetalleEvento(String nombre, String sigla, String descripcion, List<String> categorias,
			List<String> ediciones) {
		super();
		this.nombre = nombre;
		this.sigla = sigla;
		this.descripcion = descripcion;
		this.categorias = categorias;
		this.ediciones = ediciones;
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
	public List<String> getCategorias() {
		return categorias;
	}
	public List<String> getEdiciones() {
		return ediciones;
	}
	
	
	
	
}
