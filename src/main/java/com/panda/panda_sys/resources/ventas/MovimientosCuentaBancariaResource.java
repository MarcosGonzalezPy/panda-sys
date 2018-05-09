package com.panda.panda_sys.resources.ventas;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.panda.panda_sys.model.Cheques;
import com.panda.panda_sys.services.ventas.MovimientosCuentaBancariaService;

@Path("ventas/movimientos-cuenta-bancaria")
@Produces(MediaType.APPLICATION_JSON)
public class MovimientosCuentaBancariaResource {

	@GET
	@Path("/depositar-cheques/{numero}/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response depositarCheques(@PathParam("numero") String numero, @PathParam("usuario") String usuario, @QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, IOException {
		MovimientosCuentaBancariaService service = new MovimientosCuentaBancariaService();
		Cheques cheques = new Cheques();
		if (!paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			cheques = mapper.readValue(paramJson, Cheques.class);
		}
		boolean result = service.movimientoDepositarCheques(cheques, numero, usuario);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();

	}

}
