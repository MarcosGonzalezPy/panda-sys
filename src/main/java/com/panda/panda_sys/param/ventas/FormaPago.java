package com.panda.panda_sys.param.ventas;

public class FormaPago {
	
	private String medioPago;
	private String marcaTarjeta;
	private Long importe;
	
	public String getMedioPago() {
		return medioPago;
	}
	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}
	public String getMarcaTarjeta() {
		return marcaTarjeta;
	}
	public void setMarcaTarjeta(String marcaTarjeta) {
		this.marcaTarjeta = marcaTarjeta;
	}
	public Long getImporte() {
		return importe;
	}
	public void setImporte(Long importe) {
		this.importe = importe;
	}
}
