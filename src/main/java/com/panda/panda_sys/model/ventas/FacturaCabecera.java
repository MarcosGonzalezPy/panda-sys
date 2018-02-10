package com.panda.panda_sys.model.ventas;

import java.util.Date;

public class FacturaCabecera {
	
	private String numeroFactura;
	private String timbrado;
	private Integer codigoPersona;
	private String cliente;
	private String ruc;
	private String telefono;
	private String sucursal;
	private Integer caja;
	private String condicionCompra;
	private Integer medioPago;
	private String condicionPago;
	private Integer cuotas;
	private Date fecha;
	private String usuario;
	private String estado;
	private String cajero;
	
	public String getCondicionPago() {
		return condicionPago;
	}
	public void setCondicionPago(String condicionPago) {
		this.condicionPago = condicionPago;
	}
	
	public String getCajero() {
		return cajero;
	}
	public void setCajero(String cajero) {
		this.cajero = cajero;
	}
	public String getNumeroFactura() {
		return numeroFactura;
	}
	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	public String getTimbrado() {
		return timbrado;
	}
	public void setTimbrado(String timbrado) {
		this.timbrado = timbrado;
	}
	public Integer getCodigoPersona() {
		return codigoPersona;
	}
	public void setCodigoPersona(Integer codigoPersona) {
		this.codigoPersona = codigoPersona;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public Integer getCaja() {
		return caja;
	}
	public void setCaja(Integer caja) {
		this.caja = caja;
	}
	public String getCondicionCompra() {
		return condicionCompra;
	}
	public void setCondicionCompra(String condicionCompra) {
		this.condicionCompra = condicionCompra;
	}
	public Integer getMedioPago() {
		return medioPago;
	}
	public void setMedioPago(Integer medioPago) {
		this.medioPago = medioPago;
	}
	public Integer getCuotas() {
		return cuotas;
	}
	public void setCuotas(Integer cuotas) {
		this.cuotas = cuotas;
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}	

}
