package logica;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DTDetalleEdicion {
	private String nombre;
	private String sigla;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private LocalDate fechaAlta;
	private String ciudad;
	private String pais;
	private String organizador;
	
    private final Set<String> nombresTiposRegistros;
	private final Set<String> nombresInstituciones;
	
	public DTDetalleEdicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta,
			String ciudad, String pais, String organizador, Set<String> tiposRegistro, Set<String> instituciones) {
		super();
		this.nombre = nombre;
		this.sigla = sigla;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.fechaAlta = fechaAlta;
		this.ciudad = ciudad;
		this.pais = pais;
		this.organizador = organizador;
		this.nombresTiposRegistros = tiposRegistro;
		this.nombresInstituciones = instituciones;
	}

	public String getNombre() {
		return nombre;
	}


	public String getSigla() {
		return sigla;
	}


	public LocalDate getFechaInicio() {
		return fechaInicio;
	}


	public LocalDate getFechaFin() {
		return fechaFin;
	}


	public LocalDate getFechaAlta() {
		return fechaAlta;
	}


	public String getCiudad() {
		return ciudad;
	}



	public String getPais() {
		return pais;
	}



	public String getOrganizador() {
		return organizador;
	}



	public Set<String> getNombresTiposRegistros() {
		return nombresTiposRegistros;
	}

	public Set<String> getNombresInstituciones() {
		return nombresInstituciones;
	}

	public void setOrganizador(Object object) {
		
		this.organizador = (String) object;
		
	}
	
	

	
	
	
	
	
}