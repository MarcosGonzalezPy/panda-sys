package com.panda.panda_sys.model.personas;

public class Usuarios extends Personas {

	private String usuario;
	private String contrasenha;
	private String rol;
	private String resetear;

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

	public String getResetear() {
		return resetear;
	}

	public void setResetear(String resetear) {
		this.resetear = resetear;
	}

}
