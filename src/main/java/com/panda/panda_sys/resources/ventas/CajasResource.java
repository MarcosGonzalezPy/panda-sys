package com.panda.panda_sys.resources.ventas;

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
import com.panda.panda_sys.model.catalogo.Articulos;
import com.panda.panda_sys.model.personas.Usuarios;
import com.panda.panda_sys.model.ventas.Cajas;
import com.panda.panda_sys.model.ventas.CajasMovimientos;
import com.panda.panda_sys.param.CajaTimbrado;
import com.panda.panda_sys.services.catalogo.ArticulosService;
import com.panda.panda_sys.services.personas.UsuariosService;
import com.panda.panda_sys.services.ventas.CajasService;

@Path("ventas/cajas")
public class CajasResource {
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		List<Cajas> lista = new ArrayList<Cajas>();
		CajasService cajasService  = new CajasService();
		Cajas cajas = new Cajas();
		if(paramJson!= null && !paramJson.equals("") && !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			cajas = mapper.readValue(paramJson, Cajas.class);
		}	
		lista = cajasService.listar(cajas);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(@PathParam("id") int id) throws SQLException {		
		CajasService cajasService = new CajasService();
		boolean result = cajasService.eliminar(id);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/insertar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		CajasService cajasService  = new CajasService();
		Cajas cajas = new Cajas();
		if(!paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			cajas = mapper.readValue(paramJson, Cajas.class);
		}	
		boolean result = cajasService.insertar(cajas);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/sucursal-timbrado")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerSucursalTimbrado(@QueryParam("paramJson") String usuario) throws SQLException, JsonParseException, IOException {
		CajaTimbrado cajaTimbrado = new CajaTimbrado();
		CajasService cajasService  = new CajasService();
		cajaTimbrado = cajasService.obtenerSucursalTimbrado(usuario);
		Gson gson = new Gson();
		String json = gson.toJson(cajaTimbrado);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/modificar")
	public Response editar(@QueryParam("paramJson") String paramJson)
			throws JsonParseException, JsonMappingException, IOException, SQLException{
		Boolean respuesta = null;
		Cajas cajas  = new Cajas();
		if(paramJson!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			cajas = mapper.readValue(paramJson, Cajas.class);
		}	
		CajasService cajasService  = new CajasService();
		respuesta = cajasService.modificar(cajas);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
				.build();		
	}
}
