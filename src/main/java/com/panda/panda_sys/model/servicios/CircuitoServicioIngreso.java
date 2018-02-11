package com.panda.panda_sys.model.servicios;

public class CircuitoServicioIngreso extends CircuitoServicio{

	private Long cliente;
	private Long encargado;
	private String correo;
	private String telefono;
	private String detalleEquipo;
	private String detalleTrabajo;
	private String clienteNombreApellido;
	private String encargadoNombreApellido;

	public Long getCliente() {
		return cliente;
	}
	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}
	public Long getEncargado() {
		return encargado;
	}
	public void setEncargado(Long encargado) {
		this.encargado = encargado;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
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
	public String getClienteNombreApellido() {
		return clienteNombreApellido;
	}
	public void setClienteNombreApellido(String clienteNombreApellido) {
		this.clienteNombreApellido = clienteNombreApellido;
	}
	public String getEncargadoNombreApellido() {
		return encargadoNombreApellido;
	}
	public void setEncargadoNombreApellido(String encargadoNombreApellido) {
		this.encargadoNombreApellido = encargadoNombreApellido;
	}
	
}
