package com.panda.panda_sys.model.ventas;

import java.util.Date;

public class CajasMovimientos{

	private Integer codigo;
	private String estado;
	private String codigoCaja;
	private String usuario;
	private Integer montoApertura;
	private String usuarioCreacion;
	private Date fechaApertura;
	private Date fechaCierre;
	
	public Date getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	public Date getFechaApertura() {
		return fechaApertura;
	}
	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
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
	public String getCodigoCaja() {
		return codigoCaja;
	}
	public void setCodigoCaja(String codigoCaja) {
		this.codigoCaja = codigoCaja;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Integer getMontoApertura() {
		return montoApertura;
	}
	public void setMontoApertura(Integer montoApertura) {
		this.montoApertura = montoApertura;
	}
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	
	

	
	
}
