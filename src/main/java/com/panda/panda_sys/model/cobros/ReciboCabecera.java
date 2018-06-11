package com.panda.panda_sys.model.cobros;

import java.security.Timestamp;

public class ReciboCabecera {
	
	private Long id;
	private Long codigoPersona;
	private String nombrePersona;
	private String telefono;
	private String sucursal;
	private Long caja;
	private String cajero;
	private Timestamp fecha;
	private Long codigoPago;
	private Long monto;
	private String glosa;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCodigoPersona() {
		return codigoPersona;
	}
	public void setCodigoPersona(Long codigoPersona) {
		this.codigoPersona = codigoPersona;
	}
	public String getNombrePersona() {
		return nombrePersona;
	}
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
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
	public Long getCaja() {
		return caja;
	}
	public void setCaja(Long caja) {
		this.caja = caja;
	}
	public String getCajero() {
		return cajero;
	}
	public void setCajero(String cajero) {
		this.cajero = cajero;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	public Long getCodigoPago() {
		return codigoPago;
	}
	public void setCodigoPago(Long codigoPago) {
		this.codigoPago = codigoPago;
	}
	public Long getMonto() {
		return monto;
	}
	public void setMonto(Long monto) {
		this.monto = monto;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}	

}
