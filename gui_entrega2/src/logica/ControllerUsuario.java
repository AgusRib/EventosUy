package logica;

import java.time.LocalDate;
import java.util.Set;

public class ControllerUsuario implements IControllerUsuario {

	@Override
	public void ingresarAsistente(String nickname, String nombre, String email, String apellido,
			LocalDate fechaNac) throws Exception {
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		if (mU.existeNickname(nickname)) {
			throw new Exception("Ya existe un usuario con este nickname");
		} else if (mU.existeEmail(email)) {
			throw new Exception("Ya existe un usuario con este email");
		} else {
			Asistente user = new Asistente(nickname, nombre, email, apellido, fechaNac);
			mU.agregarUsuario(user);
		}
	}

	@Override
	public Set<String> listarInstituciones() {
		ManejadorInstitucion mI = ManejadorInstitucion.getInstance();
		return mI.obtenerInstituciones();
	}

	@Override
	public void agregarAsistente(String nicknameAsistente, String nombreInstitucion) {
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		ManejadorInstitucion mI = ManejadorInstitucion.getInstance();
		Asistente user = mU.obtenerAsistente(nicknameAsistente);
		user.setInstitucion(mI.ObtenerInstitucion(nombreInstitucion));

	}

	@Override
	public void ingresarOrganizador(String nickname, String nombre, String email, String descripcion, String web) throws Exception {
		// TODO Auto-generated method stub
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		if (mU.existeNickname(nickname)) {
			throw new Exception("Ya existe un usuario con este nickname");
		} else if (mU.existeEmail(email)) {
			throw new Exception("Ya existe un usuario con este email");
		} else {
			Organizador user = new Organizador(nickname, nombre, email, descripcion, web);
			mU.agregarUsuario(user);
		}
	}

	@Override
	public Set<String> listarUsuarios() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editarDatos(String nickname, String nombre, String descripcion, String URL, String apellido,
			LocalDate fechaNac) {
		// TODO Auto-generated method stub

	}

}
