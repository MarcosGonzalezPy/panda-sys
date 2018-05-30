package com.panda.panda_sys.param.ventas;

import java.util.List;

public class RegistrarPago {
	
	private String numeroFactura;
	private String condicionCompra;
	private Integer cuotas;
	private Integer plazo;
	private String timbrado;
	private String caja;
	private Integer medioPago;
	private String cajero;
	private String sucursal;
	private Integer cliente;
	private Long monto;
	private List<FormaPago> listaFormaPago;
	
	public Long getMonto() {
		return monto;
	}
	public void setMonto(Long monto) {
		this.monto = monto;
	}
	
	public Integer getCliente() {
		return cliente;
	}
	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}
	
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getCajero() {
		return cajero;
	}
	public void setCajero(String cajero) {
		this.cajero = cajero;
	}
	public String getCondicionCompra() {
		return condicionCompra;
	}
	public void setCondicionCompra(String condicionCompra) {
		this.condicionCompra = condicionCompra;
	}
	public String getCaja() {
		return caja;
	}
	public void setCaja(String caja) {
		this.caja = caja;
	}
	public Integer getMedioPago() {
		return medioPago;
	}
	public void setMedioPago(Integer medioPago) {
		this.medioPago = medioPago;
	}
	public String getTimbrado() {
		return timbrado;
	}
	public void setTimbrado(String timbrado) {
		this.timbrado = timbrado;
	}
	
	
	public List<FormaPago> getListaFormaPago() {
		return listaFormaPago;
	}
	public void setListaFormaPago(List<FormaPago> listaFormaPago) {
		this.listaFormaPago = listaFormaPago;
	}
	public String getNumeroFactura() {
		return numeroFactura;
	}
	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	
	public Integer getCuotas() {
		return cuotas;
	}
	public void setCuotas(Integer cuotas) {
		this.cuotas = cuotas;
	}
	public Integer getPlazo() {
		return plazo;
	}
	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}
}
