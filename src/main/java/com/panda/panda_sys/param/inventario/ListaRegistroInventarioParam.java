package com.panda.panda_sys.param.inventario;

import java.util.List;

import com.panda.panda_sys.model.inventario.RegistroInventario;

public class ListaRegistroInventarioParam {
	
	public List<RegistroInventario> getLista() {
		return lista;
	}

	public void setLista(List<RegistroInventario> lista) {
		this.lista = lista;
	}

	private List<RegistroInventario> lista;

}
