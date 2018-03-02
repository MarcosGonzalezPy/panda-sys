package com.panda.panda_sys.model.servicios;

import java.util.List;

import com.panda.panda_sys.model.ventas.FacturaDetalle;

public class CircuitoServicioCotizacion {
	
	private CircuitoServicio circuitoServicio;
	private List<FacturaDetalle> listaDetalle;
	
	public CircuitoServicio getCircuitoServicio() {
		return circuitoServicio;
	}
	public void setCircuitoServicio(CircuitoServicio circuitoServicio) {
		this.circuitoServicio = circuitoServicio;
	}
	public List<FacturaDetalle> getListaDetalle() {
		return listaDetalle;
	}
	public void setListaDetalle(List<FacturaDetalle> listaDetalle) {
		this.listaDetalle = listaDetalle;
	}
}
