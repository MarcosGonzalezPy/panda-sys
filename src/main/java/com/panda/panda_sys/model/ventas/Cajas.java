package com.panda.panda_sys.model.ventas;

public class Cajas {

	private Integer codigo;
	private String estado;
	private String sucursal;
	private String numero;
	private String expedicion;
	
	public String getExpedicion() {
		return expedicion;
	}
	public void setExpedicion(String expedicion) {
		this.expedicion = expedicion;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
}
