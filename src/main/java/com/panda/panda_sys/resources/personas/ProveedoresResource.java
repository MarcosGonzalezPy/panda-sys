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
import com.panda.panda_sys.model.personas.Proveedores;
import com.panda.panda_sys.services.personas.ProveedoresService;

@Path("/personas/proveedores/")
@Produces(MediaType.APPLICATION_JSON)
public class ProveedoresResource {

	@GET
	@Path("complex")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarComplex(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Proveedores> lista = new ArrayList<Proveedores>();
		Proveedores valor = new Proveedores();
		if (paramJson != null && !paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, Proveedores.class);
		}
		ProveedoresService proveedoresService = new ProveedoresService();
		lista = proveedoresService.listar(valor, true);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarSinParametros()
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Proveedores> lista = new ArrayList<Proveedores>();
		ProveedoresService proveedoresService = new ProveedoresService();
		Proveedores param = new Proveedores();
		lista = proveedoresService.listar(param, false);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}


	@POST
	@Path("/insertar")
	public Response insertar(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, IOException {
		ProveedoresService proveedoresService = new ProveedoresService();
		Proveedores proveedores = new Proveedores();
		if (!paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			proveedores = mapper.readValue(paramJson, Proveedores.class);
		}
		boolean result = proveedoresService.insertar(proveedores);
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
		Proveedores proveedores = new Proveedores();
		if (paramJson != null && !paramJson.equals("") && !paramJson.equals("{}")) {
			ObjectMapper mapper = new ObjectMapper();
			proveedores = mapper.readValue(paramJson, Proveedores.class);
		}
		ProveedoresService proveedoresService = new ProveedoresService();
		respuesta = proveedoresService.modificar(proveedores);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

	@GET
	@Path("/eliminar/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(@PathParam("codigo") Integer codigo) throws SQLException {
		ProveedoresService proveedoresService = new ProveedoresService();
		boolean result = proveedoresService.eliminar(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

}
