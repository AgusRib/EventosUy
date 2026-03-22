package logica.manejadores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import logica.enumerators.EstadoEdicion;
import logica.models.Edicion;
import logica.models.EdicionArchivada;

public class ManejadorEdicion {
	private static ManejadorEdicion instance;
	private Map<String, Edicion> colEdicionesIngresadas;
	private Map<String, Edicion> colEdicionesConfirmadas;
	private Map<String, Edicion> colEdicionesRechazadas;
	private Map<String, Edicion> colEdicionesArchivadas;
	private Map<String, EdicionArchivada> DAOSArchivadas;
	private ManejadorEdicion() {
		colEdicionesIngresadas = new HashMap<String, Edicion>();
		colEdicionesConfirmadas = new HashMap<String, Edicion>();
		colEdicionesRechazadas = new HashMap<String, Edicion>();
		colEdicionesArchivadas = new HashMap<String, Edicion>();
		DAOSArchivadas = new HashMap<String, EdicionArchivada>();
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
		// Leemos la entidad EdicionArchivada y reconstruimos objetos Edicion en memoria
		List<logica.models.EdicionArchivada> lista = em.createQuery("SELECT e FROM EdicionArchivada e", logica.models.EdicionArchivada.class).getResultList();
		colEdicionesArchivadas.clear();
		for (logica.models.EdicionArchivada eArch : lista) {
			// Guardamos el DAO para posibles usos futuros
			DAOSArchivadas.put(eArch.getNombre(), eArch);
			
			// Reconstruir Edicion a partir de EdicionArchivada
			String nombreEvento = eArch.getNombreEvento();
			logica.manejadores.ManejadorEvento mEv = logica.manejadores.ManejadorEvento.getInstance();
			logica.models.Evento evento = mEv.obtenerEvento(nombreEvento);
			logica.manejadores.ManejadorUsuario mUs = logica.manejadores.ManejadorUsuario.getInstance();
			logica.models.Organizador org = null;
			if (eArch.getOrganizadorNick() != null) {
				org = mUs.obtenerOrganizador(eArch.getOrganizadorNick());
			}
			logica.models.Edicion ed = new logica.models.Edicion(eArch.getNombre(), eArch.getSigla(), eArch.getFechaInicio(), eArch.getFechaFin(), eArch.getFechaAlta(), eArch.getCiudad(), eArch.getPais(), evento, org);
			// Asegurar la asociación bidireccional en memoria: si encontramos el organizador,
			// agregar esta edición a su colección transient de ediciones.
			if (org != null) {
				org.agregarEdicion(ed.getNombre());
			}
			// Si no encontramos el Evento en memoria, preservamos el nombre del evento desde la fila archivada
			if (evento == null && eArch.getNombreEvento() != null) {
				ed.setNombreEvento(eArch.getNombreEvento());
			}
			ed.setEstado(logica.enumerators.EstadoEdicion.Archivada);
			// Reconstruir registros archivados asociados (buscar asistentes por nickname si existen)
			if (eArch.getRegistros() != null) {
							for (logica.models.RegistroArchivado rArch : eArch.getRegistros()) {
								logica.models.Asistente asis = null;
								if (rArch.getAsistente() != null) {
									String nick = rArch.getAsistente().getNickname();
									try {
										asis = mUs.obtenerAsistente(nick);
									} catch (IllegalArgumentException ex) {
										// Si el asistente no está cargado en memoria, registrarlo desde la entidad recuperada de la BD
										mUs.agregarUsuario(rArch.getAsistente());
										asis = mUs.obtenerAsistente(nick);
									}
								}
								// Crear registro en memoria y asociarlo
								logica.models.Registro reg = new logica.models.Registro(asis, rArch.getNombreTipoRegistro(), rArch.getCosto(), rArch.getFechaRegistro(), ed, false);
								ed.agregarRegistro(reg);
								if (asis != null) asis.addRegistro(reg);
							}
			}
			colEdicionesArchivadas.put(ed.getNombre(), ed);
		}
		em.close();
    }

    public Edicion getEdicionArchivada(String nombre) {
        return colEdicionesArchivadas.get(nombre);
    }

	public Map<String, EdicionArchivada> getDAOSArchivadas() {
		return DAOSArchivadas;
	}

}