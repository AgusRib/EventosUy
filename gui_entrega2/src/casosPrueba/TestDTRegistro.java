package casosPrueba;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import logica.Asistente;
import logica.DTRegistro;
import logica.Edicion;
import logica.IControllerEvento;
import logica.ManejadorEdicion;
import logica.ManejadorUsuario;
import logica.Registro;

public class TestDTRegistro {
	@Test
	public void testazo() {
		try {
			CargaDatos.cargarDatos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		IControllerEvento ICE = logica.Factory.getInstance().getControllerEvento();
		
		ManejadorEdicion me = ManejadorEdicion.getInstance();
		Edicion ed = me.encontrarEdicion("Maratón de Montevideo 2025");
		DTRegistro dt = ICE.infoRegistro(ed.getNombre(),"sofirod");
		ManejadorUsuario mU= logica.ManejadorUsuario.getInstance();
		Asistente asis = mU.obtenerAsistente("sofirod");
		Registro reg=asis.getRegistro(ed);
		
		assertEquals(true, dt.getCosto() == reg.getCosto());
		assertEquals(true, dt.getNombreEdicion().equals(ed.getNombre()));
		assertEquals(true, dt.getFechaRegistro().equals(reg.getFechaRegistro()));
		assertEquals(true, dt.getNombreAsistente().equals(asis.getNickname()));
		
		
		
		
		
	  
		
		
		
	}
}