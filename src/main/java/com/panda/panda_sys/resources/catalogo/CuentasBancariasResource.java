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
import com.panda.panda_sys.model.catalogo.CuentasBancarias;
import com.panda.panda_sys.model.catalogo.Servicios;
import com.panda.panda_sys.model.personas.UsuarioSucursal;
import com.panda.panda_sys.param.CuentasBancariasParam;
import com.panda.panda_sys.services.catalogo.CuentasBancariasService;
import com.panda.panda_sys.services.personas.UsuariosService;
import com.panda.panda_sys.services.servicios.ServiciosService;

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

	@GET
	@Path("/insertar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, IOException {
		CuentasBancariasService cuentasBancariasService = new CuentasBancariasService();
		CuentasBancarias cuentasBancarias  = new CuentasBancarias();
		if (!paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			cuentasBancarias= mapper.readValue(paramJson, CuentasBancarias.class);
		}
		boolean result = cuentasBancariasService.insertar(cuentasBancarias);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@POST
	@Path("/modificar")
	public Response editar(@QueryParam("paramJson") String paramJson)
			throws JsonParseException, JsonMappingException, IOException, SQLException{
		Boolean respuesta = null;
		CuentasBancarias cuentasBancarias = new CuentasBancarias();
		if(paramJson!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			cuentasBancarias = mapper.readValue(paramJson, CuentasBancarias.class);
		}	
		CuentasBancariasService cuentasBancariasService = new CuentasBancariasService();
		respuesta = cuentasBancariasService.modificar(cuentasBancarias);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
				.build();		
	}

	@GET
	@Path("/eliminar-id/{codigo}")
	public Response eliminarById(@PathParam("codigo") Integer codigo) throws SQLException {
		CuentasBancariasService cuentasBancariasService = new CuentasBancariasService();
		boolean result = cuentasBancariasService.eliminar(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
}
