package com.panda.panda_sys.resources.inventario;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.panda.panda_sys.model.inventario.InventarioParam;
import com.panda.panda_sys.model.inventario.RegistroInventario;
import com.panda.panda_sys.model.personas.Usuarios;
import com.panda.panda_sys.param.inventario.ListaInventarioParam;
import com.panda.panda_sys.param.inventario.ListaRegistroAjuste;
import com.panda.panda_sys.param.inventario.ListaRegistroInventarioParam;
import com.panda.panda_sys.services.inventario.InventarioService;
import com.panda.panda_sys.services.personas.UsuariosService;
import com.panda.panda_sys.services.servicios.ServiciosService;
import com.panda.panda_sys.util.Secuencia;


@Path("/inventario")
public class InventarioResource {
	
	@GET
	@Path("/secuencia-registro-inventario")
	public Response secuencia() throws SQLException{
		Secuencia servicio = new Secuencia();
		String secuencia = servicio.getSecuencia("registro_inventario_seq");
		return Response.ok(secuencia).header("Access-Control-Allow-Origin", "*").build();
	}
		
	
	
	@GET
	@Path("/registro-inventario/insertar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		InventarioService inventarioService = new InventarioService();
		ListaRegistroInventarioParam param = new ListaRegistroInventarioParam();
		if(paramJson!= null && !paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			param = mapper.readValue(paramJson, ListaRegistroInventarioParam.class);
		}
		inventarioService.insertar(param.getLista());
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<InventarioParam> lista = new ArrayList<InventarioParam>();
		InventarioParam inventarioParam = new InventarioParam();
		if(paramJson!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			inventarioParam = mapper.readValue(paramJson, InventarioParam.class);
		}	
		InventarioService inventarioService = new InventarioService();
		lista = inventarioService.listarInventario(inventarioParam);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/registrar-ajuste")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarAjuste(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		String respuesta = new String();
		ListaRegistroAjuste listaRegistroAjuste = new ListaRegistroAjuste();
		if(paramJson!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			listaRegistroAjuste = mapper.readValue(paramJson, ListaRegistroAjuste.class);
		}	
		InventarioService inventarioService = new InventarioService();
		respuesta = inventarioService.registrarAjuste(listaRegistroAjuste.getLista());
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
