package com.panda.panda_sys.resources.catalogo;

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
import com.panda.panda_sys.model.catalogo.Articulos;
import com.panda.panda_sys.param.ArticulosParam;
import com.panda.panda_sys.services.catalogo.ArticulosService;
import com.panda.panda_sys.services.catalogo.ValoresService;

@Path("/catalogo/articulos")
public class ArticulosResource {
	
	@GET
	@Path("/{paramjson}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@PathParam("paramjson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Articulos> lista = new ArrayList<Articulos>();		
		Articulos articulos = new Articulos();
		if(!paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			articulos = mapper.readValue(paramJson, Articulos.class);
		}		
		ArticulosService ArticulosService = new ArticulosService();
		lista = ArticulosService.listar(articulos);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("insert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		ArticulosService articulosService = new ArticulosService();
		ArticulosParam articulosParam = new ArticulosParam();
		if(!paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			articulosParam = mapper.readValue(paramJson, ArticulosParam.class);
		}	
		articulosService.insertar(articulosParam);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("delete/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(@PathParam("codigo") int codigo) throws SQLException {		
		ArticulosService articulosService = new ArticulosService();
		boolean result = articulosService.eliminar(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
