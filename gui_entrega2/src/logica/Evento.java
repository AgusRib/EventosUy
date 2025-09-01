package logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;

public class Evento{
	private String nombre;
	private String sigla;
	private LocalDate fechaAlta;
	private String descripcion;
	private List<Edicion> colEdiciones;
	private List<Categoria> colCategorias;

	public Evento(String nombre,String sigla, LocalDate fecha, String descripcion) {
		this.nombre = nombre;
		this.fechaAlta = fecha;
		this.sigla = sigla;
		this.descripcion = descripcion;
		//this.colEdiciones = NULL; ??
		this.colEdiciones = new ArrayList<>();
		this.colCategorias = new ArrayList<>();
		
	}

	public String getNombre() {
		return nombre;
	}

	/*public LocalDate getfechaAlta() {
		return fechaAlta;
	}*/

	public String getDescripcion() {
		return descripcion;
	}

	
	
	public Edicion getEdicion(String nombreEdicion) {	
		
		for (Edicion edi : this.colEdiciones) {
			if (nombreEdicion.equals(edi.getNombre())) {
				return edi;
			}
		}
		return null;
		
	}
	
	/*public Set<DTTipoRegistro> infoTipoRegDeEdi(String nombreEdicion){
		Set<DTTipoRegistro> setTipoReg = new HashSet<>();
		Edicion edi = getEdicion(nombreEdicion);
		setTipoReg = edi.obtenerTipoReg();
		return setTipoReg;
		
	}*/
	
	public void agregarEdicion(Edicion nueva) {
		if (nueva == null) throw new IllegalArgumentException("Edición vacía");
		ManejadorEdicion mEdi = ManejadorEdicion.getInstance();
		if (getEdicion(nueva.getNombre()) != null) throw new IllegalArgumentException("Ya existe una edición con ese nombre");
		colEdiciones.add(nueva);
	}
	
	public void agregarCategoria(Categoria cat) {
		if (cat == null) throw new IllegalArgumentException("Categoría vacía");
		
		if (this.colCategorias.contains(cat)) throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
		colCategorias.add(cat);
	}
	
	public HashSet<String> getEdiciones() {
		HashSet<String> eds = new HashSet<String>();
		for (Edicion edi : this.colEdiciones) {
			eds.add(edi.getNombre());
		}
		return eds;
	}
	public HashSet<String> getCategorias() {
		HashSet<String> cats = new HashSet<String>();
		for (Categoria cat : this.colCategorias) {
			cats.add(cat.getNombre());
		}
		return cats;
	}
	
	public DTDetalleEvento devolverDT() {
		DTDetalleEvento dtE = new DTDetalleEvento(this.nombre, this.sigla, this.fechaAlta, this.descripcion, this.getCategorias(), this.getCategorias());
		return dtE;
	}

	public String getSigla() {
		return sigla;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public List<Edicion> getColEdiciones() {
		return colEdiciones;
	}

	public List<Categoria> getColCategorias() {
		return colCategorias;
	}

	
	
	
}