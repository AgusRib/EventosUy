package casosPrueba;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.Test;

import logica.Factory;
import logica.IControllerEvento;
import logica.ManejadorEvento;

public class TestControllerEvento {
	
	@Test
	public void testAltaEventoEdicionCategoria() {
		
		IControllerEvento ICE = Factory.getInstance().getControllerEvento();
		ManejadorEvento mE = ManejadorEvento.getInstance();
		
		ICE.ingresarCategoria("Categoria1");
		ICE.ingresarCategoria("Categoria2");
		ICE.ingresarCategoria("Categoria2");
		
		HashSet<String> categoriasSet = new HashSet<String>();
		categoriasSet.add("Categoria1");
		
		//TEST ALTA DE EVENTO
		try {
			ICE.altaEvento("Evento1", "E1", LocalDate.of(2018, 02, 13), "Descripcion del evento 1", categoriasSet);
			ICE.altaEvento("Evento1", "E1", LocalDate.of(2022, 16, 11), "Descripcion del evento 2", categoriasSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(true, mE.existeEvento("Evento1"));
		assertEquals(false, mE.existeEvento("Evento2"));
		assertEquals(mE.obtenerEvento("Evento1").getDescripcion(), "Descripcion del evento 1");
		assertEquals(mE.obtenerEvento("Evento1").getCategorias().size(), 1);
		
		//TEST LISTAR EVENTOS
		HashSet<String> eventosSet = new HashSet<String>();
		eventosSet.add("Evento1");
		assertEquals(true, ICE.listarEventos().equals(eventosSet));
		
		
		//TEST LISTAR CATEGORIAS
		categoriasSet.add("Categoria2");
		assertEquals(true, ICE.listarCategorias().equals(categoriasSet));
		
		//TEST ALTA DE EDICION
		try {
			ICE.altaEdicionDeEvento("Evento1", "Org1", "Edicion1", "ED1", LocalDate.of(2023, 05, 20), LocalDate.of(2023, 05, 25), LocalDate.of(2022, 11, 15), "Ciudad1", "Pais1");
			ICE.altaEdicionDeEvento("Evento2", "Org1", "Edicion1", "ED1", LocalDate.of(2023, 05, 20), LocalDate.of(2023, 05, 25), LocalDate.of(2022, 11, 15), "Ciudad1", "Pais1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(true, mE.obtenerEvento("Evento1").getEdiciones().contains("Edicion1"));
		assertEquals(false, mE.obtenerEvento("Evento1").getEdiciones().contains("Edicion2"));
		
		
		
	}

}
