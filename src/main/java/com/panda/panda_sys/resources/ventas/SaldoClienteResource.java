package com.panda.panda_sys.resources.ventas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.panda.panda_sys.model.ventas.SaldoCliente;
import com.panda.panda_sys.services.ventas.SaldoClienteService;

@Path("/saldo-cliente/")
public class SaldoClienteResource {
	
	@GET
	@Path("listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		List<SaldoCliente> lista = new ArrayList<SaldoCliente>();
		SaldoClienteService service  = new SaldoClienteService();
		SaldoCliente saldoCliente = new SaldoCliente();
		if(paramJson!= null && !paramJson.equals("") && !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			saldoCliente = mapper.readValue(paramJson, SaldoCliente.class);
		}	
		lista = service.listar(saldoCliente);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	

}
