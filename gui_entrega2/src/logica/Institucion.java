package logica;

public class Institucion {
	
	private String nombre;
	private String descripcion;
	private String web;
	
	public String getNombre() {
		return nombre;
	}
	/*public void setNombre(String nombre) {
		this.nombre = nombre;
	}
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
	*/
	public Institucion(String nombre, String descripcion, String web) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.web = web;
	}
	
	
	
}
