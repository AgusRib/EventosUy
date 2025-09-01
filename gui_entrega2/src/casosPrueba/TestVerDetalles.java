package casosPrueba;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import logica.DTDetalleEdicion;
import logica.DTDetalleEvento;
import logica.DTEdicion;
import logica.DataUsuario;
import logica.DataUsuario.TipoUsuario;
import logica.Factory;
import logica.IControllerEvento;
import logica.IControllerUsuario;
import logica.Organizador;

public class TestVerDetalles {
	
	@Test
	public void testVerDetalles() throws Exception {
		// Checkeo si funciona el alta de usuario y ver detalles
		IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();
		IControllerEvento ICE = Factory.getInstance().getControllerEvento();
		
		ICU.ingresarAsistente("Willyrex", "Guillermo", "willy@gmail.com", "Diaz", LocalDate.of(2004, 1,1));
		ICU.ingresarOrganizador("Vegetta", "Samuel", "vegetta@gmail.com", "Muy buenas a todos guapisimos", "www.v777.com");
		DataUsuario dtaAsistente = (DataUsuario) ICU.infoUsuario("Willyrex");
		DataUsuario dtaOrganizador = (DataUsuario) ICU.infoUsuario("Vegetta");
		
		DataUsuario dt1 = new DataUsuario("Willyrex", "Guillermo", "willy@gmail.com",TipoUsuario.ASISTENTE);
		DataUsuario dt2 = new DataUsuario("Vegetta", "Samuel", "vegetta@gmail.com",TipoUsuario.ORGANIZADOR);
		
		// Checkeo si coinciden datos del asistente
		assertEquals(dtaAsistente.getNickname(), dt1.getNickname());
		assertEquals(dtaAsistente.getNombre(), dt1.getNombre());
		assertEquals(dtaAsistente.getEmail(), dt1.getEmail());
		assertEquals(dtaAsistente.getTipo(), dt1.getTipo());
		
		//Checkeo si coinnciden datos del organizdor
		assertEquals(dtaOrganizador.getNickname(), dt2.getNickname());
		assertEquals(dtaOrganizador.getNombre(), dt2.getNombre());
		assertEquals(dtaOrganizador.getEmail(), dt2.getEmail());
		assertEquals(dtaOrganizador.getTipo(), dt2.getTipo());
		
		// Checkeo si funciona mostrarDetallesEdicion
		Set<String> categoriasSet = new java.util.HashSet<String>();
		categoriasSet.add("Categoria1");
		ICE.ingresarCategoria("Categoria1");
		ICE.altaEvento("Evento1", "ev1", ICE.getFechaSistema(), "descripcion 1", categoriasSet); 
		ICE.altaEdicionDeEvento("Evento1", "Vegetta", "Edicion1", "ed1", LocalDate.of(2004, 1,1), LocalDate.of(2004, 1,8), LocalDate.of(2003, 12,31), "Montevideo", "Uruguay");
		DTEdicion dtEdicion = new DTEdicion("Edicion1", "ed1",LocalDate.of(2004, 1,1), LocalDate.of(2004, 1,8), "Montevideo", "Uruguay");
		DTDetalleEdicion detalleEdicion = ICE.mostrarDetallesEdicion("Edicion1");
		Organizador org = (Organizador) ICU.obtenerUsuario("Vegetta");
		
		// Checkeo si coinciden los datos de la edicion
		assertEquals(detalleEdicion.getNombre(), dtEdicion.getNombre());
		assertEquals(detalleEdicion.getSigla(), dtEdicion.getSigla());
		assertEquals(detalleEdicion.getFechaInicio(), dtEdicion.getFechaInicio());
		assertEquals(detalleEdicion.getFechaFin(), dtEdicion.getFechaFin());
		assertEquals(detalleEdicion.getCiudad(), dtEdicion.getCiudad());
		assertEquals(detalleEdicion.getPais(), dtEdicion.getPais());
		assertEquals(true, org.organizaEdicion(detalleEdicion.getNombre()));
		
		// Checkeo que funcione DTDetalleEdicion creandola a mano
		HashSet<String> categorias = new HashSet<String>();
		categorias.add("Categoria1");
		HashSet<String> edis = new HashSet<String>();
		edis.add("Edicion1");
		edis.add("Edicion2");
		
		DTDetalleEvento dtde = new DTDetalleEvento("Edicion1","ev1",ICE.getFechaSistema(),"descripcion 1",categorias,edis);
		assertEquals("Edicion1",dtde.getNombre());
		assertEquals("ev1",dtde.getSigla());
		assertEquals("descripcion 1",dtde.getDescripcion());
		assertEquals(categorias,dtde.getCategorias());
		assertEquals(edis,dtde.getEdiciones());
		
		
	}
	
}
