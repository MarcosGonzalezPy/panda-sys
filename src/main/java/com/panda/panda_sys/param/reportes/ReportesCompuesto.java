package com.panda.panda_sys.param.reportes;

import java.util.List;

import com.panda.panda_sys.model.reportes.ReporteParametros;
import com.panda.panda_sys.model.reportes.Reportes;

public class ReportesCompuesto {

	private Reportes reportes;
	private List<ReporteParametros> listaParametros;
	private List<ReporteParametros> listaReporteParametrosEliminar;

	public List<ReporteParametros> getListaReporteParametrosEliminar() {
		return listaReporteParametrosEliminar;
	}

	public void setListaReporteParametrosEliminar(List<ReporteParametros> listaReporteParametrosEliminar) {
		this.listaReporteParametrosEliminar = listaReporteParametrosEliminar;
	}

	public Reportes getReportes() {
		return reportes;
	}

	public void setReportes(Reportes reportes) {
		this.reportes = reportes;
	}

	public List<ReporteParametros> getListaParametros() {
		return listaParametros;
	}

	public void setListaParametros(List<ReporteParametros> listaParametros) {
		this.listaParametros = listaParametros;
	}

}
