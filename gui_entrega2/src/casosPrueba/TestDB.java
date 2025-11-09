package casosPrueba;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.Test;

import excepciones.NombreEventoExcepcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import logica.controllers.IControllerEvento;
import logica.manejadores.ManejadorCategoria;
import logica.manejadores.ManejadorEdicion;
import logica.manejadores.ManejadorEvento;
import logica.manejadores.ManejadorUsuario;
import logica.models.Categoria;
import logica.models.Edicion;
import logica.models.Evento;
import logica.models.Factory;
import logica.models.Organizador;

public class TestDB {
	
	@Test
	public void TestJPA() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("EventosDB");
		EntityManager em = emf.createEntityManager();
		
		//creamos una edicion 
		IControllerEvento ICE = Factory.getInstance().getControllerEvento();
		HashSet<String> categorias = new HashSet<String>();
		categorias.add("cat1");
		Categoria cat = new Categoria("cat1");
		ManejadorCategoria.getInstance().agregarCategoria(cat);
		//String nickname, String nombre, String email, String descripcion, String web
		Organizador org = new Organizador("Vegetta","Samuel","samu@gmail.com","a","desc1","www.vegetta.com");
		ManejadorUsuario.getInstance().agregarUsuario(org);
		try {
			ICE.altaEvento("ev1", "e1", ICE.getFechaSistema(), "desc1",categorias);
		} catch (NombreEventoExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Evento eventoSeleccionado = ManejadorEvento.getInstance().obtenerEvento("ev1");
		
		Edicion ed1 = new Edicion("edicion1", "sigla", LocalDate.of(2004, 1, 1), LocalDate.of(2004, 1, 9), ICE.getFechaSistema(),
				"Montevideo", "Uruguay", eventoSeleccionado,org);
		
		ManejadorEdicion.getInstance().agregarEdicionIngresada(ed1);
		
		assertThrows(Exception.class, () -> {
			ICE.altaEdicionDeEvento("ev1","Vegetta", "edicion1", "sigla2", LocalDate.of(2004, 1, 1), LocalDate.of(2004, 1, 9), ICE.getFechaSistema(), "Montevideo", "Uruguay");
		});
		
		//la persistimos
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(ed1);
		tx.commit();
		
		Edicion edObtenida = em.find(Edicion.class, ed1.getId());
		System.out.println("Edicion obtenida: " + edObtenida.getNombre());
		
		em.close();
		emf.close();
		
	}
		
}


