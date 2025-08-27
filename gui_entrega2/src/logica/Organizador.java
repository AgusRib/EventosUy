package logica;

public class Organizador extends Usuario {

    private String descripcion;
    private String web;
	
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
    
    public Organizador(String nickname, String nombre, String email, String descripcion, String web) {
		super(nickname, nombre, email);
		this.descripcion = descripcion;
		this.web = web;
	}

}
