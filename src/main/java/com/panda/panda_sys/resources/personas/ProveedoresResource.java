package com.panda.panda_sys.resources.personas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.panda.panda_sys.model.personas.Proveedores;
import com.panda.panda_sys.services.personas.ProveedoresService;

@Path("/personas/proveedores/")
public class ProveedoresResource {
	
	@GET
	@Path("complex")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarComplex(@QueryParam("paramjson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Proveedores> lista = new ArrayList<Proveedores>();
		Proveedores valor = new Proveedores();
		if(paramJson!= null && !paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, Proveedores.class);
		}	
		ProveedoresService proveedoresService = new ProveedoresService();
		lista = proveedoresService.listar(valor, true);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}


}
