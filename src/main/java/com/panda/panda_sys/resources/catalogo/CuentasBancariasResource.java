package com.panda.panda_sys.resources.catalogo;

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
import com.panda.panda_sys.model.catalogo.CuentasBancarias;
import com.panda.panda_sys.model.personas.UsuarioSucursal;
import com.panda.panda_sys.services.catalogo.CuentasBancariasService;
import com.panda.panda_sys.services.personas.UsuariosService;

@Path("/catalogo/cuentas-bancarias")
public class CuentasBancariasResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<CuentasBancarias> lista = new ArrayList<CuentasBancarias>();
		CuentasBancarias valor = new CuentasBancarias();
		if (paramJson != null && !paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, CuentasBancarias.class);
		}
		CuentasBancariasService cuentasBancariasService = new CuentasBancariasService();
		lista = cuentasBancariasService.listar(valor);

		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
