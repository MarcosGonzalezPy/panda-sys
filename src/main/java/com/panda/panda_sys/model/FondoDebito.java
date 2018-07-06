package com.panda.panda_sys.model;

import java.util.Date;

public class FondoDebito {
	
	private Integer codigo;
	private String estado;
	private Date fecha;
	private Date fechaVencimiento;
	private Integer cliente;
	private String numero;
	private Integer monto;
	private String sucursal;
	private Date fechaPago;
	private Integer dias;
	private String documento;
	private Long documentoNumero;
	
	public Long getPagoDetalle() {
		return pagoDetalle;
	}
	public void setPagoDetalle(Long pagoDetalle) {
		this.pagoDetalle = pagoDetalle;
	}
	private String glosa;
	private Long pagoDetalle;
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public Long getDocumentoNumero() {
		return documentoNumero;
	}
	public void setDocumentoNumero(Long documentoNumero) {
		this.documentoNumero = documentoNumero;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public Integer getCliente() {
		return cliente;
	}
	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Integer getMonto() {
		return monto;
	}
	public void setMonto(Integer monto) {
		this.monto = monto;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	public Integer getDias() {
		return dias;
	}
	public void setDias(Integer dias) {
		this.dias = dias;
	}
}