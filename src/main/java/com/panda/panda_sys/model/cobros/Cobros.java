package com.panda.panda_sys.model.cobros;

import java.util.List;

import com.panda.panda_sys.model.FondoCredito;

public class Cobros {
	
	private ReciboCabecera reciboCabecera;	
	private List<DetalleCobro> listaDetalleCobro;
	private List<FondoCredito> listaFondoCredito;
	
	public ReciboCabecera getReciboCabecera() {
		return reciboCabecera;
	}
	public void setReciboCabecera(ReciboCabecera reciboCabecera) {
		this.reciboCabecera = reciboCabecera;
	}
	
	public List<DetalleCobro> getListaDetalleCobro() {
		return listaDetalleCobro;
	}
	public void setListaDetalleCobro(List<DetalleCobro> listaDetalleCobro) {
		this.listaDetalleCobro = listaDetalleCobro;
	}
	public List<FondoCredito> getListaFondoCredito() {
		return listaFondoCredito;
	}
	public void setListaFondoCredito(List<FondoCredito> listaFondoCredito) {
		this.listaFondoCredito = listaFondoCredito;
	}

}
