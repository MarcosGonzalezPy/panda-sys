package com.panda.panda_sys.model.ventas;

import java.util.List;

public class Factura {

	private FacturaCabecera cabecera;
	private List<FacturaDetalle> detalle;
	private List<FacturaDetalle> detalleEliminar;
	
	public FacturaCabecera getCabecera() {
		return cabecera;
	}
	public void setCabecera(FacturaCabecera cabecera) {
		this.cabecera = cabecera;
	}
	public List<FacturaDetalle> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<FacturaDetalle> detalle) {
		this.detalle = detalle;
	}
	public List<FacturaDetalle> getDetalleEliminar() {
		return detalleEliminar;
	}
	public void setDetalleEliminar(List<FacturaDetalle> detalleEliminar) {
		this.detalleEliminar = detalleEliminar;
	}

}
