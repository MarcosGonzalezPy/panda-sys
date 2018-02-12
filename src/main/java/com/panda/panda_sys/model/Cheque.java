package com.panda.panda_sys.model;

import java.util.Date;

public class Cheque {

	private Integer codigo;
	private Integer codigoCuentaBancaria;
	private Integer cliente;
	private Integer monto;
	private String glosa;
	private Date fecha;
	private Date fechaCobro;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigoCuentaBancaria() {
		return codigoCuentaBancaria;
	}

	public void setCodigoCuentaBancaria(Integer codigoCuentaBancaria) {
		this.codigoCuentaBancaria = codigoCuentaBancaria;
	}

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public Integer getMonto() {
		return monto;
	}

	public void setMonto(Integer monto) {
		this.monto = monto;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaCobro() {
		return fechaCobro;
	}

	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}

}
