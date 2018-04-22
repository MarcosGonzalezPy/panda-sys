package com.panda.panda_sys.model.compras;

public class NotaDebitoDetalle {
	
	private Long id;
	private Long numeroRegistroCompra;
	private Long codigoArticulo;
	private Long cantidad;
	private Long precio;
	private Long iva;
	private Long total;
	private Long impuesto;
	private String tipo;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
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
	public Long getCodigoArticulo() {
		return codigoArticulo;
	}
	public void setCodigoArticulo(Long codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}
	public Long getCantidad() {
		return cantidad;
	}
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	public Long getPrecio() {
		return precio;
	}
	public void setPrecio(Long precio) {
		this.precio = precio;
	}
	public Long getIva() {
		return iva;
	}
	public void setIva(Long iva) {
		this.iva = iva;
	}
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

	
	

}
