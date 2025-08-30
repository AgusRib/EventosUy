package logica;

import java.util.HashMap;
import java.util.HashSet;

public class ManejadorUsuario {
	
	private static ManejadorUsuario instance = null;
	private HashMap<String, Usuario> usuarios;
	private HashSet<String> emails;
	
	public static ManejadorUsuario getInstance() {
		if (instance == null) {
			instance = new ManejadorUsuario();
		}
		return instance;
	}
	
	private ManejadorUsuario() {
		usuarios = new HashMap<String, Usuario>();
		emails = new HashSet<String>();
	}
	
	public void agregarUsuario(Usuario user) {
		usuarios.put(user.getNickname(), user);
		emails.add(user.getEmail());
	}
	
	/**Retorna true si existe un usuario con el atributo nickname
	 **/
	public boolean existeNickname(String nickname) {
		return usuarios.containsKey(nickname);
	}
	
	/**Retorna true si existe un usuario con el atributo email
	 **/
	public boolean existeEmail(String email) {
		return emails.contains(email);
	}
	
	public Asistente obtenerAsistente(String nickname) {
		return (Asistente) usuarios.get(nickname);
	}

	public Organizador obtenerOrganizador(String nickname) {
		return (Organizador) usuarios.get(nickname);
	}
	
	public Usuario obtenerUsuario(String nickname) {
		return usuarios.get(nickname);
	}
	
	public HashSet<String> obtenerUsuarios() {
		return new HashSet<String>(usuarios.keySet());
	}

	public Organizador buscarOrganizadorDeEdicion(String nombreEdi) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
