package logica;

import java.time.LocalDate;
import java.util.Set;

public interface IControllerUsuario {
	 
	public void ingresarAsistente(String nickname, 
			String nombre, String email, 
			String apellido, LocalDate fechaNac) throws Exception;
	
	public Set<String> listarInstituciones();
	
	public void agregarAsistente(String nicknameAsistente, String nombreInstitucion);
	
	public void ingresarOrganizador(String nickname, String nombre, 
			String email, String descripcion, String web) throws Exception;
	
	public Set<String> listarUsuarios();
	
	public void editarDatos(String nickname, String nombre, 
			String descripcion, String URL, String apellido, LocalDate fechaNac);

	public DataUsuario infoUsuario(String nickname);

	public Set<String> listarRegistrosAEventos(String nickname);

	public Set<String> listarEdicionesOrganizadas(String nickname);


	

}