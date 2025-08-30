package logica;

import java.util.List;

public class ManejadorEvento {
    private static ManejadorEvento instance;
    private List<Evento> eventos;
    
    private ManejadorEvento() {
        eventos = null;
    }
    
    public static ManejadorEvento getInstance() {
        if (instance == null) {
            instance = new ManejadorEvento();
        }
        return instance;
    }
    
    public List<Evento> obtenerEventos() {
    	return this.eventos;
    }
    
    public Evento getEvento(String nomEvento) {
    	for (Evento evento : eventos) {
			if (evento.getNombre() == nomEvento) {
				return evento;
			}
		}
    	return null; // devuelve null si no encontro el evento, no se si puede pasar 
    }
}