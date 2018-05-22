package com.panda.panda_sys.param.compras;

public class OrdenCompraDetalleParam {
	
	private Integer id;
	private Integer codigo;
	private Integer codigoArticulo;
	private Integer cantidad;
	private Long precio;
	private Integer iva;
	private Long total;
	private Long impuesto;
	private String descripcion;
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
