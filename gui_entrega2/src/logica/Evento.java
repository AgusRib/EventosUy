package logica;

<<<<<<< HEAD
import java.util.Date;
import java.util.List;
=======

import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;
>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git




public class Evento{
	private String nombre;
	private String sigla;
	private LocalDate fechaAlta;
	private String descripcion;
<<<<<<< HEAD
	private String url;
	private List<Edicion> ediciones;
	private List<Categoria> categorias;
=======
	//private SortedSet(Edicion*) ediciones; ??
	private final HashSet<Edicion> ediciones;
	private final HashSet<Categoria> categorias;
>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git

	public Evento(String nombre,String sigla, LocalDate fecha, String descripcion) {
		this.nombre = nombre;
		this.fechaAlta = fecha;
		this.sigla = sigla;
		this.descripcion = descripcion;
		//this.ediciones = NULL; ??
		this.categorias = new HashSet<>();
		this.ediciones = new HashSet<>();
	}

	public String getNombre() {
		return nombre;
	}

	public LocalDate getfechaAlta() {
		return fechaAlta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	
	
	public Edicion getEdicion(String nombreEdicion) {	
		/*for (Edicion* edi : this.ediciones) {
			if (edi.nombre == nombreEdicion) { ???
				return edi;
			}
		}*/
		for (Edicion edi : this.ediciones) {
			if (nombreEdicion.equals(edi.getNombre())) {
				return edi;
			}
		}
		return null;
		
	}
	
	public Set<DTTipoRegistro> infoTipoRegDeEdi(String nombreEdicion){
		Set<DTTipoRegistro> setTipoReg = new HashSet<>();
		Edicion edi = getEdicion(nombreEdicion);
		setTipoReg = edi.obtenerTipoReg();
		return setTipoReg;
		
	}
	
	public void agregarEdicion(Edicion nueva) {
		if (nueva == null) throw new IllegalArgumentException("Edición vacía");
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		if (getEdicion(nueva.getNombre()) != null) throw new IllegalArgumentException("Ya existe una edición con ese nombre");
		ediciones.add(nueva);
	}
	
	public void agregarCategoria(Categoria cat) {
		if (cat == null) throw new IllegalArgumentException("Categoría vacía");
		
		if (this.categorias.contains(cat)) throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
		categorias.add(cat);
	}
	
	public HashSet<String> getEdiciones() {
		HashSet<String> eds = new HashSet<String>();
		for (Edicion edi : this.ediciones) {
			eds.add(edi.getNombre());
		}
		return eds;
	}
	public HashSet<String> getCategorias() {
		HashSet<String> cats = new HashSet<String>();
		for (Categoria cat : this.categorias) {
			cats.add(cat.getNombre());
		}
		return cats;
	}
	
	public DTDetalleEvento devolverDT() {
		DTDetalleEvento dtE = new DTDetalleEvento(this.nombre, this.sigla, this.fechaAlta, this.descripcion, this.getCategorias(), this.getEdiciones());
		return dtE;
	}

	public List<Edicion> getEdiciones() {
		return ediciones;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}
	
	
}