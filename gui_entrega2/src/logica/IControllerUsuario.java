package logica;

import java.time.LocalDate;
import java.util.Set;

public interface IControllerUsuario {
	 
	//ALTAS
	public void ingresarAsistente(String nickname, 
			String nombre, String email, 
			String apellido, LocalDate fechaNac) throws Exception;
	
	public void ingresarOrganizador(String nickname, String nombre, 
			String email, String descripcion, String web) throws Exception;
	
	public void ingresarInstitucion(String nombre, 
			String descripcion, String web) throws Exception;
	
	
	//LISTAS
	public Set<String> listarInstituciones();
	
	public Set<String> listarUsuarios();

	public Set<String> listarAsistentes();
	
	public DataUsuario infoUsuario(String nickname);

	public Set<String> listarRegistrosAEventos(String nickname);

	public Set<String> listarEdicionesOrganizadas(String nickname);
	
	
	//EDITAR
	public void agregarAsistente(String nicknameAsistente, String nombreInstitucion);
	
	public void editarDatos(String nickname, String nombre, 
			String descripcion, String URL, String apellido, LocalDate fechaNac);

	public Usuario obtenerUsuario(String usuario);
	

}