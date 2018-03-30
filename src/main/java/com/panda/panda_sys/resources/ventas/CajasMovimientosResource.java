package com.panda.panda_sys.resources.ventas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import com.google.gson.Gson;
import com.panda.panda_sys.model.ventas.CajasMovimientos;
import com.panda.panda_sys.services.ventas.CajasMovimientosService;

@Path("ventas/cajas-movimientos")
public class CajasMovimientosResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		List<CajasMovimientos> lista = new ArrayList<CajasMovimientos>();
		CajasMovimientosService cajasMovimientosService  = new CajasMovimientosService();
		CajasMovimientos cajasMovimientos = new CajasMovimientos();
		if(paramJson!=null){
			ObjectMapper mapper = new ObjectMapper();
			cajasMovimientos = mapper.readValue(paramJson, CajasMovimientos.class);
		}	
		lista = cajasMovimientosService.listar(cajasMovimientos);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		CajasMovimientosService cajasMovimientosService  = new CajasMovimientosService();
		CajasMovimientos cajasMovimientos = new CajasMovimientos();
		if(paramJson!=null && !paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			cajasMovimientos = mapper.readValue(paramJson, CajasMovimientos.class);
		}	
		String result = cajasMovimientosService.insertar(cajasMovimientos);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

}
