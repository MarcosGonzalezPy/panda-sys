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
import com.panda.panda_sys.model.personas.UsuarioSucursal;
import com.panda.panda_sys.model.personas.Usuarios;
import com.panda.panda_sys.services.personas.UsuariosService;

@Path("/personas/usuarios/")
@Produces(MediaType.APPLICATION_JSON)
public class UsuariosResource {

	@POST
	@Path("/insertar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, IOException {
		UsuariosService usuariosService = new UsuariosService();
		Usuarios usuarios = new Usuarios();
		if (!paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			usuarios = mapper.readValue(paramJson, Usuarios.class);
		}

		boolean result = usuariosService.insertar(usuarios);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();

	}

	@GET
	@Path("complex/{paramJson}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarComplex(@PathParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Usuarios> lista = new ArrayList<Usuarios>();
		Usuarios valor = new Usuarios();
		if (!paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, Usuarios.class);
		}
		UsuariosService usuariosService = new UsuariosService();
		lista = usuariosService.listar(valor, true);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("listarCodigo/{paramJson}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarCodigo(@PathParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Usuarios> lista = new ArrayList<Usuarios>();
		Usuarios valor = new Usuarios();
		if (!paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, Usuarios.class);
		}
		UsuariosService usuariosService = new UsuariosService();
		lista = usuariosService.listarPorCodigo(valor);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("usuario-sucursal")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarUsuarioSucursal(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<UsuarioSucursal> lista = new ArrayList<UsuarioSucursal>();
		UsuarioSucursal valor = new UsuarioSucursal();
		if (paramJson != null && !paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, UsuarioSucursal.class);
		}
		UsuariosService usuariosService = new UsuariosService();
		lista = usuariosService.listarUsuarioSucursal(valor);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/modificar")
	public Response editar(@QueryParam("paramJson") String paramJson)
			throws JsonParseException, JsonMappingException, IOException, SQLException {
		Boolean respuesta = null;
		Usuarios usuarios = new Usuarios();
		UsuariosService usuariosService = new UsuariosService();
		if (paramJson != null && !paramJson.equals("") && !paramJson.equals("{}")) {
			ObjectMapper mapper = new ObjectMapper();
			usuarios = mapper.readValue(paramJson, Usuarios.class);
		}
		respuesta = usuariosService.modificar(usuarios);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

	@GET
	@Path("/eliminar/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(@PathParam("codigo") Integer codigo) throws SQLException {

		UsuariosService usuariosService = new UsuariosService();
		boolean result = usuariosService.eliminar(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

}
