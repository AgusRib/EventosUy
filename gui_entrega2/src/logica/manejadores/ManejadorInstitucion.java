package logica.manejadores;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import logica.models.Institucion;
import logica.models.Usuario;

public class ManejadorInstitucion {
	
	private static ManejadorInstitucion instance = null;
	private Map<String, Institucion> instituciones;
	
	public static ManejadorInstitucion getInstance() {
		if (instance == null) {
			instance = new ManejadorInstitucion();
		}
		return instance;
	}

	private ManejadorInstitucion() {
		instituciones = new HashMap<String, Institucion>();
		inicializarInstituciones();
	}
	
	public void agregarInstitucion(Institucion institucion) {
		instituciones.put(institucion.getNombre(), institucion);
	}
	
	public Institucion obtenerInstitucion(String nombreInstitucion) {
		return instituciones.get(nombreInstitucion);
	}
	
	
	/**Retorna un HashSet con los nombres de las instituciones registradas.
	 * */
	public Set<String> obtenerInstituciones() {
		return new HashSet<String>(instituciones.keySet());
	}
	
	public void inicializarInstituciones() {
		// Inicializar instituciones desde la db
	   	EntityManagerFactory emf = Persistence.createEntityManagerFactory("EventosDB");
	    EntityManager em = emf.createEntityManager();
	    List<Institucion> lista = em.createQuery("SELECT i FROM Institucion i", Institucion.class).getResultList();
	    instituciones = new HashMap<String, Institucion>();
	    for (Institucion inst : lista) {
			instituciones.put(inst.getNombre(), inst);
		}
	    em.close();
	}
	
}
