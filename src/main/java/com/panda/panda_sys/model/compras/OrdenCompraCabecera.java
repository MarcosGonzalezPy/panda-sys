package com.panda.panda_sys.model.compras;

import java.util.Date;

public class OrdenCompraCabecera {
	
	private int codigo;
	private String sucursal;
	private String proveedor;
	private String condicionCompra;
	private String plazos;
	private Date fechaEntrega;
	private Date fechaCreacion;
	private String usuario;
	private String estado;
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public String getCondicionCompra() {
		return condicionCompra;
	}
	public void setCondicionCompra(String condicion_compra) {
		this.condicionCompra = condicion_compra;
	}
	public String getPlazos() {
		return plazos;
	}
	public void setPlazos(String plazos) {
		this.plazos = plazos;
	}
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}	
}
