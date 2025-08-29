package logica;

import java.util.List;

public class ManejadorCategoria {
	private static ManejadorCategoria instance;
	private List<Categoria> categorias; 
	
	private ManejadorCategoria() {
		categorias = null;
	}
	
	
	public ManejadorCategoria getInstance() {
		
		if(instance == null) {
			instance = new ManejadorCategoria();
		}
		return instance;
	}
	
}
