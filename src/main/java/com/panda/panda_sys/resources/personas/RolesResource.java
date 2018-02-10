package com.panda.panda_sys.resources.personas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
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
import com.panda.panda_sys.model.personas.Roles;
import com.panda.panda_sys.param.RolesParam;
import com.panda.panda_sys.services.catalogo.ArticulosService;
import com.panda.panda_sys.services.personas.ClientesService;
import com.panda.panda_sys.services.personas.RolesService;

@Path("/personas/roles")
public class RolesResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		RolesService rolesService = new RolesService();
		List<Roles> lista = null;
		Roles roles = new Roles();
		if(paramJson!= null && !paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			roles = mapper.readValue(paramJson, Roles.class);
		}	
		lista = rolesService.listar(roles);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {		
		RolesService rolesService = new RolesService();
		List<Roles> lista = null;
		Roles roles = new Roles();
		if(paramJson!= null && !paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			roles = mapper.readValue(paramJson, Roles.class);
		}
		boolean result = rolesService.eliminar(roles);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/distinct")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarRol(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		RolesService rolesService = new RolesService();
		List<Roles> lista = null;
		lista = rolesService.listar();
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/insertar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		RolesService rolesService = new RolesService();
		RolesParam param = new RolesParam();
		if(!paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			param = mapper.readValue(paramJson, RolesParam.class);
		}	
		rolesService.insertar(param);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/modificar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		RolesService rolesService = new RolesService();
		RolesParam param = new RolesParam();
		if(!paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			param = mapper.readValue(paramJson, RolesParam.class);
		}	
		rolesService.modificar(param);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/eliminar-id/{rol}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarById(@PathParam("rol") String rol) throws SQLException{
		RolesService rolesService= new RolesService();
		boolean result = rolesService.eliminar(rol);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
