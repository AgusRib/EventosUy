package logica;

import java.util.HashMap;

public class ManejadorCategoria{		
    private static ManejadorCategoria instance = null;
    private final HashMap<String,Categoria> categorias;
    
    private ManejadorCategoria() {
		categorias = new HashMap<>();
	}
    
	public static ManejadorCategoria getInstance() {
		if (instance == null) {
			instance = new ManejadorCategoria();
		}
		return instance;
	}
	
	public void agregarCategoria(Categoria cat) {
		categorias.put(cat.getNombre(), cat);
	}
	
	public Categoria obtenerCategoria(String nombreCat) {
		return categorias.get(nombreCat);
	}
	
	public HashMap<String,Categoria> obtenerCategorias(){
		return categorias;
	}
}