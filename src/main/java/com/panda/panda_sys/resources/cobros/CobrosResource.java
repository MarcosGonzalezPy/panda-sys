package com.panda.panda_sys.resources.cobros;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
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
import com.panda.panda_sys.model.FondoCredito;
import com.panda.panda_sys.model.cobros.Cobros;
import com.panda.panda_sys.model.cobros.ReciboCabecera;
import com.panda.panda_sys.services.cobros.CobrosService;

import jersey.repackaged.com.google.common.collect.ImmutableMap;

@Path("cobros/")
@Produces(MediaType.APPLICATION_JSON)
public class CobrosResource {
	
	@GET
	@Path("listar-fondo-credito")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarFondoCredito(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
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
	
	@GET
	@Path("cobrar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cobros(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		CobrosService cobrosService = new CobrosService();
		Cobros cobros = new Cobros();
		if(cobros!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			cobros = mapper.readValue(paramJson, Cobros.class);
		}
		String result = cobrosService.cobrar(cobros);
		Gson gson = new Gson();
		Map<String, String> resultado = ImmutableMap.of("respuesta",result);
		String json = gson.toJson(resultado);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("listar-recibo-cabecera/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarReciboCabecera(@PathParam("codigo") Long codigo) throws SQLException{
		CobrosService cobrosService = new CobrosService();
		List<ReciboCabecera> lista = cobrosService.listarReciboCabecera(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("anular-cobro/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response anularCobro(@PathParam("codigo") Long codigo) throws SQLException{
		CobrosService cobrosService = new CobrosService();
		String result = cobrosService.anularCobro(codigo);
		Gson gson = new Gson();
		Map<String, String> resultado = ImmutableMap.of("respuesta",result);
		String json = gson.toJson(resultado);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
