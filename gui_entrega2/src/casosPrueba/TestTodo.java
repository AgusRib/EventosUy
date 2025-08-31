package casosPrueba;

import org.junit.Test;

public class TestTodo {

	@Test
	public void test() {
		TestControllerEvento tCE = new TestControllerEvento();
		tCE.testAltaEventoEdicionCategoria();
		
		TestControllerUsuario tCU = new TestControllerUsuario();
		tCU.testAltaUsuarioInstitucion();
	}
}
