package com.panda.panda_sys.model.catalogo;

import java.sql.Date;

public class NumerosCheque {

	private Integer codigo;
	private String estado;
	private String banco;
	private Integer numeroCuentaBancaria;
	private Integer numeroDesde;
	private Integer numeroHasta;
	private Date fechaCreacion;
	private String usuario;
	
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
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public Integer getNumeroCuentaBancaria() {
		return numeroCuentaBancaria;
	}
	public void setNumeroCuentaBancaria(Integer numeroCuentaBancaria) {
		this.numeroCuentaBancaria = numeroCuentaBancaria;
	}
	public Integer getNumeroDesde() {
		return numeroDesde;
	}
	public void setNumeroDesde(Integer numeroDesde) {
		this.numeroDesde = numeroDesde;
	}
	public Integer getNumeroHasta() {
		return numeroHasta;
	}
	public void setNumeroHasta(Integer numeroHasta) {
		this.numeroHasta = numeroHasta;
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
