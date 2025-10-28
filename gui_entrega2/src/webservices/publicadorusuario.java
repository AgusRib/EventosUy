package webservices;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import excepciones.EmailRepetido;
import excepciones.NombreInstiExistente;
import excepciones.NombreUsuarioExistente;
import excepciones.UsuarioNoEncontrado;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;


import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.Endpoint;
import logica.controllers.IControllerUsuario;
import logica.data_types.DTAsistente;
import logica.data_types.DTOrganizador;
import logica.data_types.DataUsuario;
import logica.models.Factory;
import logica.models.Usuario;



@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class publicadorusuario {
	 private Endpoint endpoint = null;
	 private IControllerUsuario ICU;
	    //Constructor
	    public publicadorusuario(){
	     IControllerUsuario ICU=Factory.getInstance().getControllerUsuario();
	    }

	    //Operaciones las cuales quiero publicar

	    @WebMethod(exclude = true)
	    public void publicar(){
	         endpoint = Endpoint.publish("http://localhost:8080/publicadorusuario", this);
	    }
	    
	    @WebMethod(exclude = true)
	    public Endpoint getEndpoint() {
	            return endpoint;
	    }
	    
	    @WebMethod
		public void ingresarAsistente(String nickname, 
				String nombre, String email, String password,
				String apellido, LocalDate fechaNac) throws NombreUsuarioExistente, EmailRepetido, Exception{
			
			ICU.ingresarAsistente( nickname, 
					 nombre, email, password,
					 apellido, fechaNac);
		}
		
		@WebMethod
		public void ingresarOrganizador(String nickname, String nombre, 
				String email, String password, String descripcion, String web) throws NombreUsuarioExistente, EmailRepetido, Exception{
			ICU.ingresarOrganizador( nickname, nombre, 
					 email, password, descripcion, web);
		}
		

		
		
		
		@WebMethod
		public Set<String> listarInstituciones(){
			return ICU.listarInstituciones();
		}
		
		
		
		
		
		 @WebMethod
		public Set<String> listarUsuarios(){
			 return ICU.listarUsuarios();
		 }
		 @WebMethod
		public Set<String> listarAsistentes(){
			 return ICU.listarAsistentes();
		 }
		 @WebMethod
		public Set<String> listarOrganizadores(){
			 return ICU.listarOrganizadores();
		 }
		 @WebMethod
		public DataUsuario infoUsuario(String nickname) throws UsuarioNoEncontrado{
			 return ICU.infoUsuario(nickname);
		 }
		 @WebMethod
		public Set<String> listarRegistrosAEventos(String nickname){
			 return ICU.listarRegistrosAEventos(nickname);
		 }
		 @WebMethod
		public Set<String> listarEdicionesOrganizadas(String nickname){
			 return ICU.listarEdicionesOrganizadas(nickname);
		 }

		
		
		 @WebMethod
		public void agregarAsistente(String nicknameAsistente, String nombreInstitucion) {
			 ICU.agregarAsistente(nicknameAsistente, nombreInstitucion);
		 }
		
		
		 @WebMethod
		public Usuario obtenerUsuario(String usuario) {
			 return ICU.obtenerUsuario(usuario);
		 }
		 @WebMethod
		public void editarAsistente(String nick, String nombre, String apellido, LocalDate fechaNac) {
			 ICU.editarAsistente(nick, nombre, apellido, fechaNac);
		 }
		 @WebMethod
		public void editarOrganizador(String nick, String nombre,  String descripcion, String web) {
			 ICU.editarOrganizador(nick, nombre, descripcion, web);
		 }
		
		
		 @WebMethod
		public DTAsistente infoAsistente(String nickname) {
			 return ICU.infoAsistente(nickname);
		 }
		 @WebMethod
		public DTOrganizador infoOrganizador(String nickname) {
			 return ICU.infoOrganizador(nickname);
		 }
		 @WebMethod
		public void altaInstitucion(String nombre, String descripcion, String web) throws NombreInstiExistente, Exception{
			 ICU.altaInstitucion(nombre, descripcion, web);
		 }
		 @WebMethod
		DataUsuario iniciarSesionNickname(String nickname, String password) {
			 return ICU.iniciarSesionNickname(nickname, password);
		 }
		 
		 @WebMethod
		DataUsuario iniciarSesionEmail(String email, String password) {
			 return ICU.iniciarSesionEmail(email, password);
		 }
		 @WebMethod
		String obtenerInstitucionAsistente(String nickname) {
			 return ICU.obtenerInstitucionAsistente(nickname);}
	    
	    
	    
	    


	    
	    @WebMethod
	    public byte[] getFile(@WebParam(name = "fileName") String name)
	                    throws  IOException {
	        byte[] byteArray = null;
	        try {
	                File f = new File("files/" + name);
	                FileInputStream streamer = new FileInputStream(f);
	                byteArray = new byte[streamer.available()];
	                streamer.read(byteArray);
	        } catch (IOException e) {
	                throw e;
	        }
	        return byteArray;
	    }
	
	

	
	
	
	
	
	
	
	
	
}
