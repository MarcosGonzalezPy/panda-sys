package com.panda.panda_sys.model.reportes;

public class ReporteParametros {

	private Integer reporte_id;
	private String parametro;
	private String estado;
	private String tipoDato;

	public Integer getReporte_id() {
		return reporte_id;
	}

	public void setReporte_id(Integer reporte_id) {
		this.reporte_id = reporte_id;
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
