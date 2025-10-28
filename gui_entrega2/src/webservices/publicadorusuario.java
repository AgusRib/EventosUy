package webservices;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;


import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.Endpoint;



@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class publicadorusuario {
	 private Endpoint endpoint = null;
	    //Constructor
	    public publicadorusuario(){}

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
