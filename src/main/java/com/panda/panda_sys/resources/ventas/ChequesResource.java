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
import com.panda.panda_sys.model.Cheques;
import com.panda.panda_sys.param.ventas.ChequesParam;
import com.panda.panda_sys.services.ventas.ChequesService;

@Path("ventas/cheque")
@Produces(MediaType.APPLICATION_JSON)
public class ChequesResource {

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, IOException {
		List<Cheques> lista = new ArrayList<Cheques>();
		ChequesService chequeService = new ChequesService();
		Cheques cheque = new Cheques();
		if (paramJson != null && !paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			cheque = mapper.readValue(paramJson, Cheques.class);
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
		ChequesService chequesService = new ChequesService();
		Cheques cheques = new Cheques();
		if (!paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			cheques = mapper.readValue(paramJson, Cheques.class);
		}
		boolean result = chequesService.insertar(cheques);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();

	}

	@POST
	@Path("/modificar")
	public Response editar(@QueryParam("paramJson") String paramJson)
			throws JsonParseException, JsonMappingException, IOException, SQLException {
		Boolean respuesta = null;
		Cheques cheques = new Cheques();
		if (paramJson != null && !paramJson.equals("") && !paramJson.equals("{}")) {
			ObjectMapper mapper = new ObjectMapper();
			cheques = mapper.readValue(paramJson, Cheques.class);
		}
		ChequesService chequesService = new ChequesService();
		respuesta = chequesService.modificar(cheques);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

	@GET
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(@PathParam("codigo") Integer codigo) throws SQLException {
		ChequesService chequeService = new ChequesService();
		boolean result = chequeService.eliminar(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

	@POST
	@Path("/modificar-lista")
	public Response modificarEstadoDeCheques(@QueryParam("paramJson") String paramJson)
			throws JsonParseException, JsonMappingException, IOException, SQLException {
		Boolean respuesta = null;
		ChequesParam chequesParam = new ChequesParam();
		if (paramJson != null && !paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			chequesParam = mapper.readValue(paramJson, ChequesParam.class);
		}
		ChequesService chequesService = new ChequesService();
		respuesta = chequesService.modificarEstadoDeCheques(chequesParam.getListaCheques());
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

}
