package com.panda.panda_sys.util;

public class PandaException extends Exception{
	
	private String mensaje;

	public PandaException(String mensaje){
		super();
		this.mensaje = mensaje;		
	}
	
	@Override
	public String getMessage(){
		return "PANDA ERROR: "+mensaje;
	}
}
