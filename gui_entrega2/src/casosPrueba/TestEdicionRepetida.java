package casosPrueba;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.Test;

import excepciones.NombreEventoExcepcion;
import logica.Categoria;
import logica.Edicion;
import logica.Evento;
import logica.Factory;
import logica.IControllerEvento;
import logica.ManejadorCategoria;
import logica.ManejadorEdicion;
import logica.ManejadorEvento;
import logica.ManejadorUsuario;
import logica.Organizador;


public class TestEdicionRepetida {
	@Test
	public void TestEdicionRepetida() throws NombreEventoExcepcion, Exception {
		IControllerEvento ICE = Factory.getInstance().getControllerEvento();
		HashSet<String> categorias = new HashSet<String>();
		categorias.add("cat1");
		Categoria cat = new Categoria("cat1");
		ManejadorCategoria.getInstance().agregarCategoria(cat);
		//String nickname, String nombre, String email, String descripcion, String web
		Organizador org = new Organizador("Vegetta","Samuel","samu@gmail.com","desc1","www.vegetta.com");
		ManejadorUsuario.getInstance().agregarUsuario(org);
		ICE.altaEvento("ev1", "e1", ICE.getFechaSistema(), "desc1",categorias);
		Evento eventoSeleccionado = ManejadorEvento.getInstance().obtenerEvento("ev1");
		
		Edicion ed1 = new Edicion("edicion1", "sigla", LocalDate.of(2004, 1, 1), LocalDate.of(2004, 1, 9), ICE.getFechaSistema(),
				"Montevideo", "Uruguay");
		
		ManejadorEdicion.getInstance().agregarEdicion(ed1);
		
		assertThrows(Exception.class, () -> {
			ICE.altaEdicionDeEvento("ev1","Vegetta", "edicion1", "sigla2", LocalDate.of(2004, 1, 1), LocalDate.of(2004, 1, 9), ICE.getFechaSistema(), "Montevideo", "Uruguay");
		});
	}
}
