package com.panda.panda_sys.model.reportes;

public class ReporteParametros {

	private Integer reporteId;
	private String parametro;
	private String estado;
	private String tipoDato;
 
	public Integer getReporteId() {
		return reporteId;
	}

	public void setReporteId(Integer reporteId) {
		this.reporteId = reporteId;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

}
