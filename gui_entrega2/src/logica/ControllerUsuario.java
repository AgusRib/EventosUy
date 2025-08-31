package logica;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import logica.DataUsuario.TipoUsuario;

//TODO: implementar excepciones para manejar campos vacíos en las altas salvo la web de organizador ya que es opcional
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
		Asistente asistente = mU.obtenerAsistente(nicknameAsistente);
		asistente.setInstitucion(mI.obtenerInstitucion(nombreInstitucion));

	}

	@Override
	public void ingresarOrganizador(String nickname, String nombre, String email, String descripcion, String web) throws Exception {
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
		return ManejadorUsuario.getInstance().obtenerUsuarios();
	}

	@Override
	public Set<String> listarAsistentes() {
		return ManejadorUsuario.getInstance().obtenerAsistentes();
	}

	@Override
	public void editarDatos(String nickname, String nombre, String descripcion, String URL, String apellido,
			LocalDate fechaNac) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public DataUsuario infoUsuario(String nickname) {
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		Usuario user = mU.obtenerUsuario(nickname);
		TipoUsuario tipo;
		if (user instanceof Asistente) {
			tipo = TipoUsuario.ASISTENTE;
		} else {
			tipo = TipoUsuario.ORGANIZADOR;
		}
		return new DataUsuario(user.getNickname(), user.getNombre(), user.getEmail(), tipo);
	}

	@Override
	public Usuario obtenerUsuario(String usuario) {
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		return mU.obtenerUsuario(usuario);
	}
	
	@Override
	public Set<String> listarRegistrosAEventos(String nickname) {
		ManejadorUsuario mI = ManejadorUsuario.getInstance();
		Asistente asistente = mI.obtenerAsistente(nickname);
		Set<Registro> regs = asistente.getRegistros();
		Set<String> nombresRegistros= new HashSet<>();
		for (Registro reg: regs) {
			nombresRegistros.add((reg.getEdicion()).getNombre());
		}
		return nombresRegistros;
	}
	
	@Override
	public Set<String> listarEdicionesOrganizadas(String nickname) {
		ManejadorUsuario mI = ManejadorUsuario.getInstance();
		Organizador org = mI.obtenerOrganizador(nickname);
		return org.getEdiciones();
	}
	
	@Override
	public void ingresarInstitucion(String nombre, String descripcion, String web) throws Exception {
		ManejadorInstitucion mI = ManejadorInstitucion.getInstance();
		if (mI.obtenerInstitucion(nombre) != null) {
			throw new Exception("Ya existe una institucion con este nombre");
		} else {
			Institucion institucion = new Institucion(nombre, descripcion, web);
			mI.agregarInstitucion(institucion);
		}
	}
	
	
	
}
