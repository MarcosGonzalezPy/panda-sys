package com.panda.panda_sys.resources.catalogo;

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
import com.panda.panda_sys.model.catalogo.Dominios;
import com.panda.panda_sys.model.catalogo.Valores;
import com.panda.panda_sys.services.catalogo.DominiosService;
import com.panda.panda_sys.services.catalogo.ValoresService;

@Path("/catalogo/valores")
public class ValoresResource {

	//DREPECADO
	/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar() throws SQLException {
		List<Valores> lista = new ArrayList<Valores>();
		ValoresService valoresService = new ValoresService();
		lista = valoresService.listar();
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	*/
	
	@GET
	@Path("/{paramjson}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@PathParam("paramjson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Valores> lista = new ArrayList<Valores>();
		Valores valor = new Valores();
		if(!paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, Valores.class);
		}	
		ValoresService valoresService = new ValoresService();
		lista = valoresService.listar(valor);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(@PathParam("id") int id) throws SQLException {		
		ValoresService valoresService = new ValoresService();
		boolean result = valoresService.eliminar(id);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("insert/{codigo}/{descripcion}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@PathParam("codigo") String codigo,
			@PathParam("descripcion") String descripcion
			) throws SQLException {
		ValoresService valoresService = new ValoresService();
		valoresService.insertar(codigo, descripcion);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("existe/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response existe(@PathParam("id") int id) throws SQLException {
		ValoresService valoresService = new ValoresService();
		boolean existe  = valoresService.existe(id);
		Gson gson = new Gson();
		String json = gson.toJson(existe);
		return Response.ok(json	).header("Access-Control-Allow-Origin", "*").build();
	}


}
