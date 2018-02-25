package com.panda.panda_sys.model.servicios;

public class CircuitoServicio {
	
	private Long secuencia;
	private String estado;
	private Long paso;
	private String lugar;
	private String responsable;
	private String fecha;
	private String observacion;
	
	public Long getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(Long secuencia) {
		this.secuencia = secuencia;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Long getPaso() {
		return paso;
	}
	public void setPaso(Long paso) {
		this.paso = paso;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
}
