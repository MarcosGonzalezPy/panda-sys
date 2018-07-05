package com.panda.panda_sys.model.pagos;

import java.util.List;


import com.panda.panda_sys.model.FondoDebito;

public class Pagos {
	
	private PagosCabecera pagosCabecera;
	private List<DetallePago> listaDetallePago;
	private List<FondoDebito> listaFondoDebito;
	
	public PagosCabecera getPagosCabecera() {
		return pagosCabecera;
	}
	public void setPagosCabecera(PagosCabecera pagosCabecera) {
		this.pagosCabecera = pagosCabecera;
	}
	public List<DetallePago> getListaDetallePago() {
		return listaDetallePago;
	}
	public void setListaDetallePago(List<DetallePago> listaDetallePago) {
		this.listaDetallePago = listaDetallePago;
	}
	public List<FondoDebito> getListaFondoDebito() {
		return listaFondoDebito;
	}
	public void setListaFondoDebito(List<FondoDebito> listaFondoDebito) {
		this.listaFondoDebito = listaFondoDebito;
	}
}
