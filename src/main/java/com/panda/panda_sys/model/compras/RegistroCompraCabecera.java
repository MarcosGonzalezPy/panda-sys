package com.panda.panda_sys.model.compras;

import java.sql.Date;

public class RegistroCompraCabecera {
	
	private Integer id;
	private String condicionCompra;
	private String plazo;
	private String proveedor;
	private String sucursal;
	private Date fechaEntrega;
	private Date fecha;
	private String usuario;
	private String glosa;
	private String estado;
	private Long proveedorCodigo;
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}	
	public Long getProveedorCodigo() {
		return proveedorCodigo;
	}
	public void setProveedorCodigo(Long proveedorCodigo) {
		this.proveedorCodigo = proveedorCodigo;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCondicionCompra() {
		return condicionCompra;
	}
	public void setCondicionCompra(String condicion) {
		this.condicionCompra = condicion;
	}
	public String getPlazo() {
		return plazo;
	}
	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}
