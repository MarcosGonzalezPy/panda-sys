package com.panda.panda_sys.model.servicios;

public class CircuitoServicioIngreso extends CircuitoServicio{

	private String paso;
	private String cliente;
	private String correo;
	private String encargado;
	private String telefono;
	private String detalleEquipo;
	private String detalleTrabajo;

	public String getPaso() {
		return paso;
	}

	public void setPaso(String paso) {
		this.paso = paso;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getEncargado() {
		return encargado;
	}

	public void setEncargado(String encargado) {
		this.encargado = encargado;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDetalleEquipo() {
		return detalleEquipo;
	}

	public void setDetalleEquipo(String detalleEquipo) {
		this.detalleEquipo = detalleEquipo;
	}

	public String getDetalleTrabajo() {
		return detalleTrabajo;
	}

	public void setDetalleTrabajo(String detalleTrabajo) {
		this.detalleTrabajo = detalleTrabajo;
	}

}
