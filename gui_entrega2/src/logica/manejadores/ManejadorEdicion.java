package logica;

import java.util.HashMap;

public class ManejadorEdicion {
	private static ManejadorEdicion instance;
	private HashMap<String, Edicion> colEdicionesPendientes;
	private HashMap<String, Edicion> colEdicionesConfirmadas;
	private HashMap<String, Edicion> colEdicionesRechazadas;

	private ManejadorEdicion() {
		colEdicionesPendientes = new HashMap<String, Edicion>();
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
		return colEdicionesPendientes.containsKey(nombreEdicion)
			|| colEdicionesConfirmadas.containsKey(nombreEdicion)
			|| colEdicionesRechazadas.containsKey(nombreEdicion);
	}

	// Obtener todas las ediciones Pendientes
	public HashMap<String,Edicion> obtenerEdicionesPendientes() {
		return colEdicionesPendientes;
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
		if (colEdicionesPendientes.containsKey(nombreEdi)) {
			return colEdicionesPendientes.get(nombreEdi);
		} else if (colEdicionesConfirmadas.containsKey(nombreEdi)) {
			return colEdicionesConfirmadas.get(nombreEdi);
		} else if (colEdicionesRechazadas.containsKey(nombreEdi)) {
			return colEdicionesRechazadas.get(nombreEdi);
		}
		return null;
	}

	// Agregar edición a Pendientes
	public void agregarEdicionIngresada(Edicion nueva) {
		colEdicionesPendientes.put(nueva.getNombre(), nueva);
	}

	public void CambioEstado(Edicion edi,EstadoEdicion  nuevoestado) {
		colEdicionesPendientes.remove(edi.getNombre());
		colEdicionesConfirmadas.put(edi.getNombre(), edi);
		
	}
}