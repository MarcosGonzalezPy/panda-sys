package com.panda.panda_sys.resources.cobros;

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
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.panda.panda_sys.model.FondoCredito;
import com.panda.panda_sys.services.cobros.CobrosService;

@Path("cobros/")
@Produces(MediaType.APPLICATION_JSON)
public class CobrosResource {
	
	@GET
	@Path("listar-fondo-credito")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarFondoDebito(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<FondoCredito> lista = new ArrayList<FondoCredito>();
		CobrosService cobrosService = new CobrosService();
		FondoCredito fondoCredito = new FondoCredito();
		if(paramJson!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			fondoCredito = mapper.readValue(paramJson, FondoCredito.class);
		}
		lista = cobrosService.listarFondoCredito(fondoCredito);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
