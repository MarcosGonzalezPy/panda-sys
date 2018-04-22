package com.panda.panda_sys.model.compras;

import java.sql.Date;

public class NotaDebitoCabecera {
	
	private Long id;
	private Long numeroRegistroCompra;
	private String sucursal;
	private String usuario;
	private String estado;
	private Date fecha;
	private String glosa;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getNumeroRegistroCompra() {
		return numeroRegistroCompra;
	}
	public void setNumeroRegistroCompra(Long numeroRegistroCompra) {
		this.numeroRegistroCompra = numeroRegistroCompra;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
}
