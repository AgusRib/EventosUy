package logica;

import java.util.HashMap;

public class ManejadorEdicion {
	private static ManejadorEdicion instance;
	private HashMap<String, Edicion> colEdiciones;

	private ManejadorEdicion() {
		colEdiciones = new HashMap<String, Edicion>();
	}
	
	public static ManejadorEdicion getInstance() {
		if (instance == null) {
			instance = new ManejadorEdicion();
		}
		return instance;
	}
	
<<<<<<< HEAD
	public boolean existeEdicion(String nickname) {
		return colEdiciones.containsKey(nickname);
	}
	
	public HashMap<String,Edicion> obtenerEdiciones() {
		return colEdiciones;
	}
 	
	public Edicion encontrarEdicion(String nickname) {
		return colEdiciones.get(nickname);
	}
=======
	public Edicion encontrarEdicion(String nombreEdi) {
		return (Edicion) colEdiciones.get(nombreEdi);
	}
	
>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
	
}
