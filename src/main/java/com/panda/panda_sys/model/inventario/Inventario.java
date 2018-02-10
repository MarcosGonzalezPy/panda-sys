package com.panda.panda_sys.model.inventario;

public class Inventario {
	
	private Integer codigo;
	private Integer cantidad;
	private String sucursal;
	private Integer maximo;
	private Integer minimo;
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public Integer getMaximo() {
		return maximo;
	}
	public void setMaximo(Integer maximo) {
		this.maximo = maximo;
	}
	public Integer getMinimo() {
		return minimo;
	}
	public void setMinimo(Integer minimo) {
		this.minimo = minimo;
	} 
}
