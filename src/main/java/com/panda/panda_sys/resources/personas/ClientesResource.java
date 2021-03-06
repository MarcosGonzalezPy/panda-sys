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
import com.panda.panda_sys.model.personas.Clientes;
import com.panda.panda_sys.services.personas.ClientesService;
import com.panda.panda_sys.services.personas.PersonasService;

@Path("/personas/clientes")
@Produces(MediaType.APPLICATION_JSON)
public class ClientesResource {

	@POST
	@Path("/insertar")
	public Response insertar(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, IOException {
		ClientesService clientesService = new ClientesService();
		Clientes clientes = new Clientes();
		if (!paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			clientes = mapper.readValue(paramJson, Clientes.class);
		}
		boolean result = clientesService.insertarClientes(clientes);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

	@GET
	@Path("complex")
	public Response listarComplex(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Clientes> lista = new ArrayList<Clientes>();
		Clientes valor = new Clientes();
		if (paramJson != null && !paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, Clientes.class);
		}
		ClientesService clientesService = new ClientesService();
		lista = clientesService.listar(valor, true);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("listar")
	public Response listar(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Clientes> lista = new ArrayList<Clientes>();
		Clientes valor = new Clientes();
		if (paramJson != null && !paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, Clientes.class);
		}
		ClientesService clientesService = new ClientesService();
		lista = clientesService.listar(valor, true);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/modificar")
	public Response editar(@QueryParam("paramJson") String paramJson)
			throws JsonParseException, JsonMappingException, IOException, SQLException {
		Boolean respuesta = null;
		Clientes clientes = new Clientes();
		if (paramJson != null && !paramJson.equals("") && !paramJson.equals("{}")) {
			ObjectMapper mapper = new ObjectMapper();
			clientes = mapper.readValue(paramJson, Clientes.class);
		}
		ClientesService clientesService = new ClientesService();
		respuesta = clientesService.modificar(clientes);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

	@GET
	@Path("/eliminar/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(@PathParam("codigo") Integer codigo) throws SQLException {
		ClientesService clientesService = new ClientesService();
		boolean result = clientesService.eliminar(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

}
