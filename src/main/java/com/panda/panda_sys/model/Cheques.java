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
	private Long codigoPersona;
	private String nombreApellido;
	private String tipo;
	private String cuentaBancaria;
	private String documento;
	private Long documentoNumero;
	
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



	public String getCuentaBancaria() {
		return cuentaBancaria;
	}

	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public Long getCodigoPersona() {
		return codigoPersona;
	}

	public void setCodigoPersona(Long codigoPersona) {
		this.codigoPersona = codigoPersona;
	}

	public String getNombreApellido() {
		return nombreApellido;
	}

	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

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
