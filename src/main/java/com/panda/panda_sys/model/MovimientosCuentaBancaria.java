package com.panda.panda_sys.model;

import java.sql.Date;

public class MovimientosCuentaBancaria {

	private Integer codigo;
	private String estado;
	private Integer codigo_cuenta_bancaria;
	private Date fecha;
	private String usuario;
	private Integer monto;
	private String documento;
	private Integer numero_documento;
	private String tipo;
	
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
	public Integer getCodigo_cuenta_bancaria() {
		return codigo_cuenta_bancaria;
	}
	public void setCodigo_cuenta_bancaria(Integer codigo_cuenta_bancaria) {
		this.codigo_cuenta_bancaria = codigo_cuenta_bancaria;
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
	public Integer getMonto() {
		return monto;
	}
	public void setMonto(Integer monto) {
		this.monto = monto;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public Integer getNumero_documento() {
		return numero_documento;
	}
	public void setNumero_documento(Integer numero_documento) {
		this.numero_documento = numero_documento;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	

}
