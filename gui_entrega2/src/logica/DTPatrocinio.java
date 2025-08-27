package logica;

import java.time.LocalDate;

public class DTPatrocinio {
	private LocalDate fecha;
	private float monto;
	private String codigo;
	private NivelPatrocinio nivelPatrocinio;
	public DTPatrocinio(LocalDate fecha, float monto, String codigo, NivelPatrocinio nivelPatrocinio) {
		super();
		this.fecha = fecha;
		this.monto = monto;
		this.codigo = codigo;
		this.nivelPatrocinio = nivelPatrocinio;
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
	
	
}
