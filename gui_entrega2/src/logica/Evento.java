package logica;

import java.util.Date;




public class Evento{
	private String nombre;
	private Date fecha;
	private String descripcion;
	private String url;
	private SortedSet(Edicion*) ediciones;

	public Evento(String nombre, Date fecha, String descripcion, String url) {
		this.nombre = nombre;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.url = url;
		this.ediciones = NULL;
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
	
	public Edicion getEdicion(String nombreEdicion) {	
		for (Edicion* edi : this.ediciones) {
			if (edi.nombre == nombreEdicion) {
				return edi;
			}
		}
	}
}