package logica;

<<<<<<< HEAD
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
=======
import java.util.HashMap;
import java.util.HashSet;
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
	
	public HashSet<String> obtenerNombresEventos() {
		return new HashSet<String>(eventos.keySet());
	}
	
	public Evento obtenerEvento(String nombreEvento) {
		return eventos.get(nombreEvento);
	}
	
	public void agregarEvento(Evento e) {
		eventos.put(e.getNombre(), e);
	}
	public boolean existeEvento(String nombreEvento) {
		return eventos.containsKey(nombreEvento);
	}
	
	
	
	
}
>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
