package com.panda.panda_sys.resources.recursosUtiles;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.panda.panda_sys.util.DescargarFichero;
import com.panda.panda_sys.util.Secuencia;

@Path("/util")
public class UtilResource {
	
	@GET
	@Path("/secuencia/{seq}")
	public Response secuencia(@PathParam("seq") String seq) throws SQLException{
		Secuencia secuenciaService = new Secuencia();
		String secuencia = secuenciaService.getSecuencia(seq);
		return Response.ok(secuencia).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/descargar-archivo/{archivo}/{tipo}")
	public Response descargar(@PathParam("archivo") String archivo,@PathParam("tipo") String tipo) throws SQLException, IOException{
		DescargarFichero df= new DescargarFichero();
		String respuesta = df.descargar(archivo,tipo);
		return Response.ok(respuesta).header("Access-Control-Allow-Origin", "*").build();
	}
	
}
