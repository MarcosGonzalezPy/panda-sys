package com.panda.panda_sys.model.compras;

import java.sql.Date;

public class OrdenCompraCabecera {
	
	private int codigo;
	private String sucursal;
	private String proveedor;
	private String condicionCompra;
	private Integer plazo;
	private Date fechaEntrega;
	private Date fechaCreacion;
	private String usuario;
	private String estado;
	private Long proveedorCodigo;
	private String numeroFactura;
	private Date fechaRecepcion;
	private Long timbrado;
	private String ruc;
	private String usuarioRecepcion;
	private Long monto;
	private String nc;
	
	public Long getMonto() {
		return monto;
	}
	public void setMonto(Long monto) {
		this.monto = monto;
	}
	public String getNc() {
		return nc;
	}
	public void setNc(String nc) {
		this.nc = nc;
	}
	public String getNumeroFactura() {
		return numeroFactura;
	}
	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	public Long getTimbrado() {
		return timbrado;
	}
	public void setTimbrado(Long timbrado) {
		this.timbrado = timbrado;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getUsuarioRecepcion() {
		return usuarioRecepcion;
	}
	public void setUsuarioRecepcion(String usuarioRecepcion) {
		this.usuarioRecepcion = usuarioRecepcion;
	}
	public Long getProveedorCodigo() {
		return proveedorCodigo;
	}
	public void setProveedorCodigo(Long proveedorCodigo) {
		this.proveedorCodigo = proveedorCodigo;
	}
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
	public Integer getPlazo() {
		return plazo;
	}
	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
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
