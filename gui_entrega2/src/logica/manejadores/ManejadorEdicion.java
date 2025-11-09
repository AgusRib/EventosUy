package logica.manejadores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import logica.enumerators.EstadoEdicion;
import logica.models.Edicion;

public class ManejadorEdicion {
	private static ManejadorEdicion instance;
	private Map<String, Edicion> colEdicionesIngresadas;
	private Map<String, Edicion> colEdicionesConfirmadas;
	private Map<String, Edicion> colEdicionesRechazadas;
	private Map<String, Edicion> colEdicionesArchivadas;
	private ManejadorEdicion() {
		colEdicionesIngresadas = new HashMap<String, Edicion>();
		colEdicionesConfirmadas = new HashMap<String, Edicion>();
		colEdicionesRechazadas = new HashMap<String, Edicion>();
		colEdicionesArchivadas = new HashMap<String, Edicion>();
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
			|| colEdicionesRechazadas.containsKey(nombreEdicion)
			|| colEdicionesArchivadas.containsKey(nombreEdicion);
	}

	// Obtener todas las ediciones Pendientes
	public Map<String, Edicion> obtenerEdicionesPendientes() {
		return colEdicionesIngresadas;
	}

	// Obtener todas las ediciones confirmadas
	public Map<String, Edicion> obtenerEdicionesConfirmadas() {
		return colEdicionesConfirmadas;
	}

	// Obtener todas las ediciones rechazadas
	public Map<String, Edicion> obtenerEdicionesRechazadas() {
		return colEdicionesRechazadas;
	}
	
	// Obtener todas las ediciones archivadas
	public Map<String, Edicion> obtenerEdicionesArchivadas() {
		return colEdicionesArchivadas;
	}

	// Buscar una edición por nombre en todas las colecciones
	public Edicion encontrarEdicion(String nombreEdi) {
		if (colEdicionesIngresadas.containsKey(nombreEdi)) {
			return colEdicionesIngresadas.get(nombreEdi);
		} else if (colEdicionesConfirmadas.containsKey(nombreEdi)) {
			return colEdicionesConfirmadas.get(nombreEdi);
		} else if (colEdicionesRechazadas.containsKey(nombreEdi)) {
			return colEdicionesRechazadas.get(nombreEdi);
		} else if (colEdicionesArchivadas.containsKey(nombreEdi)) {
			return colEdicionesArchivadas.get(nombreEdi);
		}
		return null;
	}

	// Agregar edición a Pendientes
	public void agregarEdicionIngresada(Edicion nueva) {
		colEdicionesIngresadas.put(nueva.getNombre(), nueva);
	}

	public void cambioEstado(Edicion edi, EstadoEdicion nuevoestado) {
		colEdicionesIngresadas.remove(edi.getNombre());
		if (nuevoestado == EstadoEdicion.Confirmada) {
			colEdicionesConfirmadas.put(edi.getNombre(), edi);
			edi.setEstado(EstadoEdicion.Confirmada);
		} else if (nuevoestado == EstadoEdicion.Rechazada) {
			colEdicionesRechazadas.put(edi.getNombre(), edi);
			edi.setEstado(EstadoEdicion.Rechazada);
		}
		
	}
	
	public void archivarEdicion(Edicion edi) {
		colEdicionesConfirmadas.remove(edi.getNombre());
		edi.setEstado(EstadoEdicion.Archivada);
		colEdicionesArchivadas.put(edi.getNombre(), edi);
	}
	
    public void inicializarEdicionesArchivadas() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("EventosDB");
        EntityManager em = emf.createEntityManager();
        List<Edicion> lista = em.createQuery("SELECT e FROM Edicion e", Edicion.class).getResultList();
        colEdicionesArchivadas.clear();
        for (Edicion edicion : lista) {
			colEdicionesArchivadas.put(edicion.getNombre(), edicion);
		}
        em.close();
    }

    public Edicion getEdicionArchivada(String nombre) {
        return colEdicionesArchivadas.get(nombre);
    }
}