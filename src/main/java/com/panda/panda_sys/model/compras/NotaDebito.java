package com.panda.panda_sys.model.compras;

import java.util.List;

public class NotaDebito {
	
	private NotaDebitoCabecera cabecera;
	private List<NotaDebitoDetalle> detalle;
	
	public NotaDebitoCabecera getCabecera() {
		return cabecera;
	}
	public void setCabecera(NotaDebitoCabecera cabecera) {
		this.cabecera = cabecera;
	}
	public List<NotaDebitoDetalle> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<NotaDebitoDetalle> detalle) {
		this.detalle = detalle;
	}
	
}
