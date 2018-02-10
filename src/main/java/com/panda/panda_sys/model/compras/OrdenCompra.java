package com.panda.panda_sys.model.compras;

import java.util.List;

public class OrdenCompra {

	private OrdenCompraCabecera cabecera;
	private List<OrdenCompraDetalle> detalle;
	
	public OrdenCompraCabecera getCabecera() {
		return cabecera;
	}
	public void setCabecera(OrdenCompraCabecera cabecera) {
		this.cabecera = cabecera;
	}
	public List<OrdenCompraDetalle> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<OrdenCompraDetalle> detalle) {
		this.detalle = detalle;
	}

}
