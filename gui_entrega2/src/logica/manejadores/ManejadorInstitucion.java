package logica.manejadores;

import java.util.HashMap;
import java.util.HashSet;

import logica.models.Institucion;

public class ManejadorInstitucion {
	
	private static ManejadorInstitucion instance = null;
	private HashMap<String, Institucion> instituciones;
	
	public static ManejadorInstitucion getInstance() {
		if (instance == null) {
			instance = new ManejadorInstitucion();
		}
		return instance;
	}

	private ManejadorInstitucion() {
		instituciones = new HashMap<String, Institucion>();
	}
	
	public void agregarInstitucion(Institucion institucion) {
		instituciones.put(institucion.getNombre(), institucion);
	}
	
	public Institucion obtenerInstitucion(String nombreInstitucion) {
		return instituciones.get(nombreInstitucion);
	}
	
	
	/**Retorna un HashSet con los nombres de las instituciones registradas.
	 * */
	public HashSet<String> obtenerInstituciones() {
		return new HashSet<String>(instituciones.keySet());
	}
	
}
