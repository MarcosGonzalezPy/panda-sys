package com.panda.panda_sys.resources.ventas;

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
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.panda.panda_sys.model.Cheque;
import com.panda.panda_sys.services.ventas.ChequeService;

@Path("ventas/cheque")
public class ChequeResource {

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, IOException {
		List<Cheque> lista = new ArrayList<Cheque>();
		ChequeService chequeService = new ChequeService();
		Cheque cheque = new Cheque();
		if (paramJson != null && !paramJson.equals("") && !paramJson.equals("{}")) {
			ObjectMapper mapper = new ObjectMapper();
			cheque = mapper.readValue(paramJson, Cheque.class);
		}
		lista = chequeService.listar(cheque);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/insertar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, IOException {
		ChequeService chequeService = new ChequeService();
		Cheque cheque = new Cheque();
		if (!paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			cheque = mapper.readValue(paramJson, Cheque.class);
		}
		boolean result = chequeService.insertar(cheque);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(@PathParam("id") int id) throws SQLException {
		ChequeService chequeService = new ChequeService();
		boolean result = chequeService.eliminar(id);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
