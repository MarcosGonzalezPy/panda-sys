package com.panda.panda_sys.model.compras;

public class RegistroCompraDetalle {
	
	private Integer id;
	private Integer codigoArticulo;
	private Integer cantidad;
	private Long precio;
	private Integer iva;
	private Long total;
	private Long impuesto;
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Long getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(Long impuesto) {
		this.impuesto = impuesto;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCodigoArticulo() {
		return codigoArticulo;
	}
	public void setCodigoArticulo(Integer codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Long getPrecio() {
		return precio;
	}
	public void setPrecio(Long precio) {
		this.precio = precio;
	}
	public Integer getIva() {
		return iva;
	}
	public void setIva(Integer iva) {
		this.iva = iva;
	}
	
	
}
