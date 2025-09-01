package logica;

import java.time.LocalDate;

public class DTPatrocinio {
	private LocalDate fecha;
	private float monto;
	private String codigo;
	private NivelPatrocinio nivelPatrocinio;
    private final String tipoRegistroGratis;   // NUEVO
    private final int cantRegsGratis;
	public DTPatrocinio(LocalDate fecha, float monto, String codigo, NivelPatrocinio nivelPatrocinio, String tipoRegistroGratis, int cantRegsGratis) {
		super();
		this.fecha = fecha;
		this.monto = monto;
		this.codigo = codigo;
		this.nivelPatrocinio = nivelPatrocinio;
		this.tipoRegistroGratis = tipoRegistroGratis;
		this.cantRegsGratis = cantRegsGratis;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public float getMonto() {
		return monto;
	}
	public String getCodigo() {
		return codigo;
	}
	public NivelPatrocinio getNivelPatrocinio() {
		return nivelPatrocinio;
	}
	public String getTipoRegistroGratis() {
		return tipoRegistroGratis;
	}
	public int getCantRegsGratis() {
		return cantRegsGratis;
	}
	/*public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public void setMonto(float monto) {
		this.monto = monto;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void setNivelPatrocinio(NivelPatrocinio nivelPatrocinio) {
		this.nivelPatrocinio = nivelPatrocinio;
	}*/
	
	
}
