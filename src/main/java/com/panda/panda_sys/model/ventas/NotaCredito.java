package com.panda.panda_sys.model.ventas;

import java.util.List;

public class NotaCredito {
	
	private NotaCreditoCabecera notaCreditoCabecera;
	private List<NotaCreditoDetalle> notaCreditoDetalles;
	private List<NotaCreditoDetalle> eliminarNotaCreditoDetalle;
	
	public List<NotaCreditoDetalle> getEliminarNotaCreditoDetalle() {
		return eliminarNotaCreditoDetalle;
	}
	public void setEliminarNotaCreditoDetalle(List<NotaCreditoDetalle> eliminarNotaCreditoDetalle) {
		this.eliminarNotaCreditoDetalle = eliminarNotaCreditoDetalle;
	}
	public NotaCreditoCabecera getNotaCreditoCabecera() {
		return notaCreditoCabecera;
	}
	public void setNotaCreditoCabecera(NotaCreditoCabecera notaCreditoCabecera) {
		this.notaCreditoCabecera = notaCreditoCabecera;
	}
	public List<NotaCreditoDetalle> getNotaCreditoDetalles() {
		return notaCreditoDetalles;
	}
	public void setNotaCreditoDetalles(List<NotaCreditoDetalle> notaCreditoDetalles) {
		this.notaCreditoDetalles = notaCreditoDetalles;
	}

}
