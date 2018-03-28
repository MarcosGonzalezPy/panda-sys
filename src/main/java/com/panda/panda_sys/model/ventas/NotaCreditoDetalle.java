package com.panda.panda_sys.model.ventas;

public class NotaCreditoDetalle {
		
	private String descripcion;
	private String facturaId;
	private String codigoArticulo;
	private Integer cantidad;
	private Integer precio;
	private Integer iva;
	private Integer total;
	private Integer impuesto;
	private String tipo;
	private String estado;

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getFacturaId() {
		return facturaId;
	}
	public void setFacturaId(String facturaId) {
		this.facturaId = facturaId;
	}
	public String getCodigoArticulo() {
		return codigoArticulo;
	}
	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Integer getPrecio() {
		return precio;
	}
	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
	public Integer getIva() {
		return iva;
	}
	public void setIva(Integer iva) {
		this.iva = iva;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(Integer impuesto) {
		this.impuesto = impuesto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

}
