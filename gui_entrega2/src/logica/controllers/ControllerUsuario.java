package logica.controllers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import excepciones.EmailRepetido;
import excepciones.NombreInstiExistente;
import excepciones.NombreUsuarioExistente;
import logica.dataTypes.DTAsistente;
import logica.dataTypes.DTOrganizador;
import logica.dataTypes.DataUsuario;
import logica.dataTypes.DataUsuario.TipoUsuario;
import logica.manejadores.ManejadorInstitucion;
import logica.manejadores.ManejadorUsuario;
import logica.models.Asistente;
import logica.models.Institucion;
import logica.models.Organizador;
import logica.models.Registro;
import logica.models.Usuario;

//TODO: implementar excepciones para manejar campos vacíos en las altas salvo la web de organizador ya que es opcional
public class ControllerUsuario implements IControllerUsuario {

	@Override
	public void ingresarAsistente(String nickname, String nombre, String email, String apellido,
			LocalDate fechaNac) throws NombreUsuarioExistente,EmailRepetido, Exception {
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		if (mU.existeNickname(nickname)) {
			throw new NombreUsuarioExistente("Ya existe un usuario con este nickname");
		} else if (mU.existeEmail(email)) {
			throw new EmailRepetido("Ya existe un usuario con este email");
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
	public void ingresarOrganizador(String nickname, String nombre, String email, String descripcion, String web) throws NombreUsuarioExistente,EmailRepetido, Exception {
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		if (mU.existeNickname(nickname)) {
			throw new NombreUsuarioExistente("Ya existe un usuario con este nickname");
		} else if (mU.existeEmail(email)) {
			throw new EmailRepetido("Ya existe un usuario con este email");
		} else {
			Organizador user = new Organizador(nickname, nombre, email, descripcion, web);
			mU.agregarUsuario(user);
		}
	}

	@Override
	public Set<String> listarUsuarios() {
		return ManejadorUsuario.getInstance().obtenerUsuarios();
	}
	
	
	public Set<String> listarOrganizadores() {
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		Set<String> organizadores = new HashSet<>();
		for (String nick: mU.obtenerUsuarios()) {
			if (mU.obtenerUsuario(nick) instanceof Organizador) {
				organizadores.add(nick);
			}
		}
		return organizadores;
	}
	

	@Override
	public Set<String> listarAsistentes() {
		return ManejadorUsuario.getInstance().obtenerAsistentes();
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
	public DTAsistente infoAsistente(String nickname) {
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		Asistente user = mU.obtenerAsistente(nickname);
		DTAsistente dtA;
		if (user.getInstitucion() != null) {
			dtA = new DTAsistente(user.getNickname(), user.getNombre(), user.getEmail(), user.getApellido(), user.getFechaNacimiento());
		} else {
			dtA = new DTAsistente(user.getNickname(), user.getNombre(), user.getEmail(), user.getApellido(), user.getFechaNacimiento());
		}
		return dtA;
	}
	
	
	@Override
	public DTOrganizador infoOrganizador(String nickname) {
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		Organizador user = mU.obtenerOrganizador(nickname);
		DTOrganizador dtO = new DTOrganizador(user.getNickname(), user.getNombre(), user.getEmail(), user.getDescripcion(), user.getWeb());
		return dtO;
	}
	@Override
	public void editarAsistente(String nick,String nom, String apellido, LocalDate fdef) {
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		Asistente user = mU.obtenerAsistente(nick);
		user.setApellido(apellido);
		user.setFechaNacimiento(fdef);
		user.setNombre(nom);}
	
	@Override
	public void editarOrganizador(String nick,String nom, String descripcion, String web) {
		ManejadorUsuario mU = ManejadorUsuario.getInstance();
		Organizador user = mU.obtenerOrganizador(nick);
		user.setDescripcion(descripcion);
		user.setWeb(web);
		user.setNombre(nom);}
	
		
	
	


   @Override
   public void altaInstitucion(String nombre, String descripcion, String web) throws NombreInstiExistente, Exception {
	   ManejadorInstitucion mI = ManejadorInstitucion.getInstance();
	   if (mI.obtenerInstitucion(nombre) != null) {
		   throw new NombreInstiExistente("Ya existe una institucion con este nombre");
	   } else {
		   Institucion institucion = new Institucion(nombre, descripcion, web);
		   mI.agregarInstitucion(institucion);
	   }
   
	
   }}
	


    

