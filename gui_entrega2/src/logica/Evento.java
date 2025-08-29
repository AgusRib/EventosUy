package logica;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;




public class Evento{
	private String nombre;
	private Date fecha;
	private String descripcion;
	private String url;
	//private SortedSet(Edicion*) ediciones; ??
	private final Set<Edicion> ediciones;

	public Evento(String nombre, Date fecha, String descripcion, String url) {
		this.nombre = nombre;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.url = url;
		//this.ediciones = NULL; ??
		this.ediciones = new HashSet<>();
	}

	public String getNombre() {
		return nombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getUrl() {
		return url;
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
}