package com.panda.panda_sys.model.inventario;

import java.util.Date;

public class RegistroAjuste {

	private Integer codigo;
	private Long cantidad;
	private String sucursal;
	private Date fecha;
	private String usuario;
	private String autorizante;
	private String documento;
	
	public String getAutorizante() {
		return autorizante;
	}
	public void setAutorizante(String autorizante) {
		this.autorizante = autorizante;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Long getCantidad() {
		return cantidad;
	}
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
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
}
