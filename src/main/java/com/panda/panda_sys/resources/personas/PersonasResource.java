package com.panda.panda_sys.resources.personas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.panda.panda_sys.model.personas.Personas;
import com.panda.panda_sys.services.personas.PersonasService;

@Path("/personas/personas")
@Produces(MediaType.APPLICATION_JSON)
public class PersonasResource {

	@GET
	@Path("/complex/{paramJson}")
	public Response listarComplex(@PathParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Personas> lista = new ArrayList<Personas>();
		Personas valor = new Personas();

		if (paramJson != null && !paramJson.equals("") && !paramJson.equals("{}")) {
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, Personas.class);
		}
		PersonasService personasService = new PersonasService();
		lista = personasService.listar(valor, true);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/insertar")
	public Response insertar(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, IOException {
		PersonasService personasService = new PersonasService();
		Personas personas = new Personas();
		if (!paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			personas = mapper.readValue(paramJson, Personas.class);
		}
		boolean result = personasService.insertarPersonas(personas);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

	@POST
	@Path("/modificar")
	public Response editar(@QueryParam("paramJson") String paramJson)
			throws JsonParseException, JsonMappingException, IOException, SQLException {
		Boolean respuesta = null;
		Personas personas = new Personas();
		if (paramJson != null && !paramJson.equals("") && !paramJson.equals("{}")) {
			ObjectMapper mapper = new ObjectMapper();
			personas = mapper.readValue(paramJson, Personas.class);
		}
		PersonasService personasService = new PersonasService();
		respuesta = personasService.modificarPersonas(personas);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

	@GET
	@Path("/eliminar-id/{cedula}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(@PathParam("cedula") Integer cedula) throws SQLException {
		PersonasService personasService = new PersonasService();
		boolean result = personasService.eliminarPersonas(cedula);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

}
