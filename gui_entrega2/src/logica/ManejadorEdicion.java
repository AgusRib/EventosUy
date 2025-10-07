package logica;

import java.util.HashMap;

public class ManejadorEdicion {
	private static ManejadorEdicion instance;
	private HashMap<String, Edicion> colEdicionesIngresadas;
	private HashMap<String, Edicion> colEdicionesConfirmadas;
	private HashMap<String, Edicion> colEdicionesRechazadas;

	private ManejadorEdicion() {
		colEdicionesIngresadas = new HashMap<String, Edicion>();
		colEdicionesConfirmadas = new HashMap<String, Edicion>();
		colEdicionesRechazadas = new HashMap<String, Edicion>();
	}
	
	public static ManejadorEdicion getInstance() {
		if (instance == null) {
			instance = new ManejadorEdicion();
		}
		return instance;
	}

	// Verifica si existe una edición en alguna de las colecciones
	public boolean existeEdicion(String nombreEdicion) {
		return colEdicionesIngresadas.containsKey(nombreEdicion)
			|| colEdicionesConfirmadas.containsKey(nombreEdicion)
			|| colEdicionesRechazadas.containsKey(nombreEdicion);
	}

	// Obtener todas las ediciones ingresadas
	public HashMap<String,Edicion> obtenerEdicionesIngresadas() {
		return colEdicionesIngresadas;
	}

	// Obtener todas las ediciones confirmadas
	public HashMap<String,Edicion> obtenerEdicionesConfirmadas() {
		return colEdicionesConfirmadas;
	}

	// Obtener todas las ediciones rechazadas
	public HashMap<String,Edicion> obtenerEdicionesRechazadas() {
		return colEdicionesRechazadas;
	}

	// Buscar una edición por nombre en todas las colecciones
	public Edicion encontrarEdicion(String nombreEdi) {
		if (colEdicionesIngresadas.containsKey(nombreEdi)) {
			return colEdicionesIngresadas.get(nombreEdi);
		} else if (colEdicionesConfirmadas.containsKey(nombreEdi)) {
			return colEdicionesConfirmadas.get(nombreEdi);
		} else if (colEdicionesRechazadas.containsKey(nombreEdi)) {
			return colEdicionesRechazadas.get(nombreEdi);
		}
		return null;
	}

	// Agregar edición a ingresadas
	public void agregarEdicionIngresada(Edicion nueva) {
		colEdicionesIngresadas.put(nueva.getNombre(), nueva);
	}

	public void CambioEstado(Edicion edi,EstadoEdicion  nuevoestado) {
		colEdicionesIngresadas.remove(edi.getNombre());
		colEdicionesConfirmadas.put(edi.getNombre(), edi);
		
	}
}