package com.panda.panda_sys.resources.personas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.panda.panda_sys.model.catalogo.Dominios;
import com.panda.panda_sys.model.personas.Accesos;
import com.panda.panda_sys.services.catalogo.DominiosService;
import com.panda.panda_sys.services.personas.AccesosService;

@Path("/personas/accesos")
public class AccesosResource {
	
	@GET
	@Path("/{usuario}/{pass}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response acceder(@PathParam("usuario") String usuario,
			@PathParam("pass") String pass) throws SQLException {
		Accesos accesos = new Accesos();
		AccesosService accesosService = new AccesosService();
		accesos= accesosService.login(usuario, pass);
		Gson gson = new Gson();
		String json = gson.toJson(accesos);	
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
