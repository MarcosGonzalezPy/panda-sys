package com.panda.panda_sys.param;

import com.panda.panda_sys.model.catalogo.Articulos;

public class ArticulosParam extends Articulos{

	private Integer cantidadMinima;
	private Integer cantidadMaxima;
	
	public Integer getCantidadMinima() {
		return cantidadMinima;
	}
	public void setCantidadMinima(Integer cantidadMinima) {
		this.cantidadMinima = cantidadMinima;
	}
	public Integer getCantidadMaxima() {
		return cantidadMaxima;
	}
	public void setCantidadMaxima(Integer cantidadMaxima) {
		this.cantidadMaxima = cantidadMaxima;
	}
}
