package casosPrueba;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.Test;

import logica.DataUsuario;
import logica.DataUsuario.TipoUsuario;
import logica.Factory;
import logica.IControllerUsuario;
import logica.ManejadorUsuario;

public class testAltaUsuarioInstitucion {
		
	@Test
	public void testAltaUsuarioInstitucion() {
		
		
		IControllerUsuario ICU = Factory.getInstance().getControllerUsuario();
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		
		//TEST ALTA DE ASISTENTE
		try {
			ICU.ingresarInstitucion("Instituto Tecnologico", "Instituto de tecnologia de punta", "www.it.com");
			ICU.ingresarAsistente("ignaciotema", "Ignacio", "ignaciotema@gmail.com", "Tejera", LocalDate.of(2006, 02, 18));
			ICU.agregarAsistente("ignaciotema", "Instituto Tecnologico");
			ICU.ingresarAsistente("ignaciotema", "Nombre", "correo@gmail.com", "Apellido", LocalDate.of(2000, 01, 01));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ICU.ingresarAsistente("otroNick", "Nombre", "ignaciotema@gmail.com", "Apellido", LocalDate.of(2000, 01, 01));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		assertEquals(ICU.infoUsuario("ignaciotema").getNickname(), "ignaciotema");
		assertEquals(ICU.infoUsuario("ignaciotema").getNombre(), "Ignacio");
		assertEquals(ICU.infoUsuario("ignaciotema").getEmail(), "ignaciotema@gmail.com");
		assertEquals(ICU.infoUsuario("ignaciotema").getTipo(), TipoUsuario.ASISTENTE);
		assertEquals(mU.obtenerAsistente("ignaciotema").getInstitucion().getNombre(), "Instituto Tecnologico");
		
		
		//TEST ALTA DE INSTITUCION
		try {
			ICU.ingresarInstitucion("Instituto de Artes", "Descripcion diferente", "www.it.com");
			ICU.ingresarInstitucion("Instituto de Artes", "Instituto de tecnologia de punta", "www.it.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashSet institucionesSet = new HashSet<String>();
		institucionesSet.add("Instituto Tecnologico");
		institucionesSet.add("Instituto de Artes");
		assertEquals(true, ICU.listarInstituciones().equals(institucionesSet));
		
		
		
		//TEST ALTA DE ORGANIZADOR
		try {
			ICU.ingresarOrganizador("nachito", "Ignacio", "nachito@gmail.com", "descripcion generica 123", "www.nachito.com");
			ICU.ingresarOrganizador("nachito", "Nombre", "correo@gmail.com", "descripcion generica 123", "www.nachito.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ICU.ingresarOrganizador("otroNick", "Nombre", "nachito@gmail.com", "Apellido", "www.nachito.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(ICU.infoUsuario("nachito").getNickname(), "nachito");
		assertEquals(ICU.infoUsuario("nachito").getNombre(), "Ignacio");
		assertEquals(ICU.infoUsuario("nachito").getEmail(), "nachito@gmail.com");
		assertEquals(ICU.infoUsuario("nachito").getTipo(), TipoUsuario.ORGANIZADOR);
	
		
		try {
			ICU.ingresarOrganizador("ignaciotema", "Nombre", "correo232@gmail.com","desc", "www.web.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashSet<String> usuariosSet = new HashSet<String>();
		usuariosSet.add("ignaciotema");
		usuariosSet.add("nachito");
		assertEquals(true, ICU.listarUsuarios().equals(usuariosSet));
		
	}
	
}
