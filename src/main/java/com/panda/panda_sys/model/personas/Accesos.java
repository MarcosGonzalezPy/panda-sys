package com.panda.panda_sys.model.personas;

import java.util.List;

public class Accesos {
	private String usuario;
	private String contrasenha;
	private String rol;
	private List<String> modulos;
	
	public List<String> getModulos() {
		return modulos;
	}
	public void setModulos(List<String> modulos) {
		this.modulos = modulos;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContrasenha() {
		return contrasenha;
	}
	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	private String codigo;

}
