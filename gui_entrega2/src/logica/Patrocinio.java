package logica;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Patrocinio  {

    private LocalDate fecha;
    private int monto;
    private String codigo;
    private int cantRegsGratis;
    private NivelPatrocinio nivelPatrocinio;
    private String tipoRegistroGratis;
    
	public Patrocinio(LocalDate fecha, int monto, String codigo, int cantRegsGratis, NivelPatrocinio nivelPatrocinio, String tipoRegistroGratis) {
		super();
		this.fecha = fecha;
		this.monto = monto;
		this.codigo = codigo;
		this.cantRegsGratis = cantRegsGratis;
		this.nivelPatrocinio = nivelPatrocinio;
        this.tipoRegistroGratis = tipoRegistroGratis;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getCantRegsGratis() {
		return cantRegsGratis;
	}

	public void setCantRegsGratis(int cantRegsGratis) {
		this.cantRegsGratis = cantRegsGratis;
	}

	public NivelPatrocinio getNivelPatrocinio() {
		return nivelPatrocinio;
	}

	public void setNivelPatrocinio(NivelPatrocinio nivelPatrocinio) {
		this.nivelPatrocinio = nivelPatrocinio;
	}

	public String getTipoRegistroGratis() {
		return tipoRegistroGratis;
	}

	public void setTipoRegistroGratis(String tipoRegistroGratis) {
		this.tipoRegistroGratis = tipoRegistroGratis;
	}
	
	
	
    
	
}
