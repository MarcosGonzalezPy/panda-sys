package com.panda.panda_sys.model.compras;

import java.util.List;

public class RegistroCompra {

	private RegistroCompraCabecera cabecera;
	private List<RegistroCompraDetalle> detalle;
	
	public RegistroCompraCabecera getCabecera() {
		return cabecera;
	}
	public void setCabecera(RegistroCompraCabecera cabecera) {
		this.cabecera = cabecera;
	}
	public List<RegistroCompraDetalle> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<RegistroCompraDetalle> detalle) {
		this.detalle = detalle;
	}
}
