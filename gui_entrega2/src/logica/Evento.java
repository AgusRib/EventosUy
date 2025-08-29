package logica;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;




public class Evento{
	private String nombre;
	private String sigla;
	private Date fechaAlta;
	private String descripcion;
	//private SortedSet(Edicion*) ediciones; ??
	private final Set<Edicion> ediciones;
	private final Set<Categoria> categorias;

	public Evento(String nombre, Date fecha, String descripcion) {
		this.nombre = nombre;
		this.fechaAlta = fecha;
		this.descripcion = descripcion;
		//this.ediciones = NULL; ??
		this.categorias = new HashSet<>();
		this.ediciones = new HashSet<>();
	}

	public String getNombre() {
		return nombre;
	}

	public Date getfechaAlta() {
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
		if (getEdicion(nueva.getNombre()) != null) throw new IllegalArgumentException("Ya existe una edición con ese nombre");
		ediciones.add(nueva);
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
}