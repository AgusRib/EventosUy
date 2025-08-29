package logica;

import java.time.LocalDate;
import java.util.List;

public class Edicion {
	private String nombre;
	private String sigla;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private LocalDate fechaAlta;
	private String ciudad;
	private String pais;
	private List<TipoRegistro> TRegistros;
	
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
	public boolean existeTipoRegistro(String nombreTRegis) {
		
		if(TRegistros != null) {
			// Busca en la lista TRegistros y devuelve True si encuentra el nickname 
			for (TipoRegistro tipoRegistro : TRegistros) {
				if(tipoRegistro.getNombre() == nombreTRegis) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public TipoRegistro obtenerTipoRegistro(String nombreTRegis) {
			
			if(TRegistros != null) {
				
				for (TipoRegistro tipoRegistro : TRegistros) {
					if(tipoRegistro.getNombre() == nombreTRegis) {
						return tipoRegistro;
					}
				}
			}
			
			return null; //null si no lo encuentra.
	}
	
	public void crearTRegistro(String nom, String desc, Float costo, int cupo) {
		TipoRegistro newTRegistro = new TipoRegistro(nom,desc,costo,cupo);
		TRegistros.addFirst(newTRegistro);
		
	}
	
	
}
