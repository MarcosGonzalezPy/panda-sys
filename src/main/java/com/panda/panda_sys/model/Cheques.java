package com.panda.panda_sys.model;

import java.sql.Date;

public class Cheques {

	private Integer codigo;
	private Integer monto;
	private Integer numeroCheque;
	private String estado;
	private String banco;
	private Date fecha;
	private String glosa;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getMonto() {
		return monto;
	}

	public void setMonto(Integer monto) {
		this.monto = monto;
	}

	public Integer getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(Integer numeroCheque) {
		this.numeroCheque = numeroCheque;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

}
