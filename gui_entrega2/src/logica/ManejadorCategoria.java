package logica;

import java.util.HashSet;

public class ManejadorCategoria {
 	private static ManejadorCategoria instance = null;
 	private HashSet<Categoria> categorias;
 	
 	private ManejadorCategoria() {
 		categorias = new HashSet<Categoria>();
 	}
	
	public static ManejadorCategoria getInstance() {
		if (instance == null) {
			instance = new ManejadorCategoria();
		}
		return instance;
	}
	
	public HashSet<Categoria> getCategorias() {
		return categorias;
	}
	
	public void agregarCategoria(Categoria cat) {
		categorias.add(cat);
	}
}
