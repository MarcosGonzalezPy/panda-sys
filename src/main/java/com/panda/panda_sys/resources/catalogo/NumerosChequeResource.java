package com.panda.panda_sys.resources.catalogo;

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
import com.panda.panda_sys.model.catalogo.NumerosCheque;
import com.panda.panda_sys.services.catalogo.NumerosChequeService;

@Path("/catalogo/numeros-cheque")
@Produces(MediaType.APPLICATION_JSON)
public class NumerosChequeResource {

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<NumerosCheque> lista = new ArrayList<NumerosCheque>();
		NumerosCheque numerosCheque = new NumerosCheque();
		if (paramJson!=null && !paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			numerosCheque = mapper.readValue(paramJson, NumerosCheque.class);
		}
		NumerosChequeService service = new NumerosChequeService();
		lista = service.listar(numerosCheque);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/insertar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, IOException {
		NumerosChequeService service = new NumerosChequeService();
		NumerosCheque pojo = new NumerosCheque();
		if (!paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			pojo = mapper.readValue(paramJson, NumerosCheque.class);
		}
		service.insertar(pojo);
		return Response.ok().header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/eliminar/{banco}/{numeroCuentaBancaria}/{numeroDesde}/{numeroHasta}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(
			@PathParam("banco") String banco,
			@PathParam("numeroCuentaBancaria") int numeroCuentaBancaria, 
			@PathParam("numeroDesde") int numeroDesde,
			@PathParam("numeroHasta") int numeroHasta)
			throws SQLException {

		NumerosChequeService service = new NumerosChequeService();
		boolean result = service.eliminar(banco, numeroCuentaBancaria, numeroDesde, numeroHasta);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
