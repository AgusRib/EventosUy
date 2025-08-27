package logica;

import java.util.HashMap;
import java.util.List;


public class ManejadorEvento{
	private static ManejadorEvento instance = null;
	private HashMap<String,Evento> eventos;
	
	public static ManejadorEvento getInstance() {
		if (instance == null) {
			instance = new ManejadorEvento();
		}
		return instance;
	}
	
	private ManejadorEvento() {
		eventos = new HashMap<String, Evento>();
	}
	
	
	public HashMap<String,Evento> obtenerEventos() {
		return eventos;
	}
	
	public Evento obtenerEvento(String nombreEvento) {
		return eventos.get(nombreEvento);
	}
	
	
	
	
}
