package logica.data_types;


public class DTOrganizador extends DataUsuario {
	
	private String nickname;
	private String nombre;
	private String email;
	private String descripcion;
	private String web;
	
	public DTOrganizador(String nickname, String nombre, String email, String descripcion, String web) {
		super(nickname, nombre, email, TipoUsuario.ORGANIZADOR);
		this.nickname = nickname;
		this.nombre = nombre;
		this.email = email;
		this.descripcion = descripcion;
		this.web = web;
	}
	
	public String getNickname() {
		return nickname;
	}
	public String getNombre() {
		return nombre;
	}
	public String getEmail() {
		return email;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getWeb() {
		return web;
	}
}
	