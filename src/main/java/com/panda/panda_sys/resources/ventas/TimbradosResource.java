package com.panda.panda_sys.resources.ventas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import com.panda.panda_sys.model.catalogo.Servicios;
import com.panda.panda_sys.model.personas.Usuarios;
import com.panda.panda_sys.model.ventas.Timbrados;
import com.panda.panda_sys.services.personas.UsuariosService;
import com.panda.panda_sys.services.servicios.ServiciosService;
import com.panda.panda_sys.services.ventas.TimbradosService;


@Path("ventas/timbrados/")
public class TimbradosResource {

	@GET
	@Path("listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarDetalleFactura(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Timbrados> lista = new ArrayList<Timbrados>();
		TimbradosService timbradosService = new TimbradosService();
		Timbrados timbrados = new Timbrados();
		
		if(paramJson!= null && !paramJson.equals("") && !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			timbrados = mapper.readValue(paramJson, Timbrados.class);
		}
		
		lista = timbradosService.listar(timbrados);
		
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/insertar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		TimbradosService timbradosService = new TimbradosService();
		Timbrados timbrados = new Timbrados();
		if(!paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			timbrados = mapper.readValue(paramJson, Timbrados.class);
		}	
		timbradosService.insertar(timbrados);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/eliminar-id/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarById(@PathParam("codigo") String codigo) throws SQLException{
		TimbradosService timbradosService = new TimbradosService();
		boolean result = timbradosService.eliminar(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/modificar")
	public Response editar(@QueryParam("paramJson") String paramJson)
			throws JsonParseException, JsonMappingException, IOException, SQLException{
		Boolean respuesta = null;
		Timbrados timbrados= new Timbrados();
		if(paramJson!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			timbrados = mapper.readValue(paramJson, Timbrados.class);
		}	
		TimbradosService timbradosService = new TimbradosService();
		respuesta = timbradosService.modificar(timbrados);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
				.build();		
	}


}
