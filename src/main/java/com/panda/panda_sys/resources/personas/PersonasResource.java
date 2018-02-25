package com.panda.panda_sys.resources.personas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.panda.panda_sys.model.personas.Personas;
import com.panda.panda_sys.services.personas.PersonasService;

@Path("/personas/personas")
public class PersonasResource {
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@PathParam("paramjson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Personas> lista = new ArrayList<Personas>();
		Personas valor = new Personas();
		if(!paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, Personas.class);
		}	
		PersonasService personasService = new PersonasService();
		lista = personasService.listar(valor, false);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("complex/{paramjson}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarComplex(@PathParam("paramjson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Personas> lista = new ArrayList<Personas>();
		Personas valor = new Personas();
		if(!paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, Personas.class);
		}	
		PersonasService personasService = new PersonasService();
		lista = personasService.listar(valor, true);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	

}
