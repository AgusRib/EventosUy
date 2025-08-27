package logica;

import java.util.Date;
import java.util.List;




public class Evento{
	private String nombre;
	private Date fecha;
	private String descripcion;
	private String url;
	private List<Edicion> ediciones;
	private List<Categoria> categorias;

	public Evento(String nombre, Date fecha, String descripcion, String url) {
		this.nombre = nombre;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.url = url;
	}

	public String getNombre() {
		return nombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getUrl() {
		return url;
	}

	public List<Edicion> getEdiciones() {
		return ediciones;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}
	
	
}