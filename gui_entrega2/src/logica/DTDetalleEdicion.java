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

<<<<<<< HEAD
=======

>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
	public String getSigla() {
		return sigla;
	}

<<<<<<< HEAD
=======

>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

<<<<<<< HEAD
=======

>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
	public LocalDate getFechaFin() {
		return fechaFin;
	}

<<<<<<< HEAD
=======

>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

<<<<<<< HEAD
=======

>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
	public String getCiudad() {
		return ciudad;
	}
<<<<<<< HEAD
=======



>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
	public String getPais() {
		return pais;
	}

<<<<<<< HEAD
=======


>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
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