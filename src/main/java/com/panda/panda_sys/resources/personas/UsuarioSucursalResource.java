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
import com.panda.panda_sys.model.personas.UsuarioSucursal;
import com.panda.panda_sys.services.personas.ClientesService;
import com.panda.panda_sys.services.personas.UsuarioSucursalService;
import com.panda.panda_sys.services.personas.UsuariosService;

@Path("/personas/usuario-sucursal/")
public class UsuarioSucursalResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarUsuarioSucursal(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<UsuarioSucursal> lista = new ArrayList<UsuarioSucursal>();
		UsuarioSucursal valor = new UsuarioSucursal();
		if (paramJson != null && !paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, UsuarioSucursal.class);
		}
		UsuarioSucursalService usuariosSucursalService = new UsuarioSucursalService();
		lista = usuariosSucursalService.listarUsuarioSucursal(valor);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("insertar/{usuario}/{sucursal}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@PathParam("usuario") String usuario, 
			@PathParam("sucursal") String sucursal)
			throws SQLException, JsonParseException, IOException {
		UsuarioSucursalService usuarioSucursalService = new UsuarioSucursalService();
		usuarioSucursalService.insertar(usuario, sucursal);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	} 
	
	@GET
	@Path("/eliminar-id/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarById(@PathParam("usuario") String usuario) throws SQLException{
		UsuarioSucursalService usuarioSucursalService = new UsuarioSucursalService();
		boolean result = usuarioSucursalService.eliminar(usuario);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/modificar")
	public Response editar(@QueryParam("paramJson") String paramJson)
			throws JsonParseException, JsonMappingException, IOException, SQLException {
		Boolean respuesta = null;
		UsuarioSucursal pojo = new UsuarioSucursal();
		if (paramJson != null && !paramJson.equals("") && !paramJson.equals("{}")) {
			ObjectMapper mapper = new ObjectMapper();
			pojo = mapper.readValue(paramJson, UsuarioSucursal.class);
		}
		UsuarioSucursalService usuarioSucursalService = new UsuarioSucursalService();
		respuesta = usuarioSucursalService.modificar(pojo);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

}