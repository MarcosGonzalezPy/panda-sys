package com.panda.panda_sys.resources.pagos;

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
import com.panda.panda_sys.model.Cheques;
import com.panda.panda_sys.model.FondoDebito;
import com.panda.panda_sys.model.pagos.Pagos;
import com.panda.panda_sys.model.pagos.PagosCabecera;
import com.panda.panda_sys.model.ventas.SaldoCliente;
import com.panda.panda_sys.services.pagos.PagosService;

import jersey.repackaged.com.google.common.collect.ImmutableMap;

@Path("/pagos/")
public class PagosResource {
	
	@GET
	@Path("listar/{tipo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("paramJson") String paramJson, @PathParam("tipo") String tipo) throws SQLException, JsonParseException, IOException {
		List<SaldoCliente> lista = new ArrayList<SaldoCliente>();
		PagosService service  = new PagosService();
		SaldoCliente saldoCliente = new SaldoCliente();
		if(paramJson!= null && !paramJson.equals("") && !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			saldoCliente = mapper.readValue(paramJson, SaldoCliente.class);
		}	
		lista = service.listar(saldoCliente, tipo);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("generar-cheque/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response generarCheque(@QueryParam("paramJson") String paramJson, @PathParam("usuario") String usuario) throws SQLException, JsonParseException, IOException {		
		PagosService service  = new PagosService();
		Cheques cheque = new Cheques();
		if(paramJson!= null && !paramJson.equals("") && !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			cheque = mapper.readValue(paramJson, Cheques.class);
		}	
		String respuesta = service.generarCheque(cheque, usuario);
		Gson gson = new Gson();
		Map<String, String> resultado = ImmutableMap.of("respuesta",respuesta);
		String json = gson.toJson(resultado);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("efectivizar/{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response efectivizar(@QueryParam("paramJson") String paramJson, @PathParam("usuario") String usuario) throws SQLException, JsonParseException, IOException {		
		PagosService service  = new PagosService();
		FondoDebito fondoDebito = new FondoDebito();
		if(paramJson!= null && !paramJson.equals("") && !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			fondoDebito = mapper.readValue(paramJson, FondoDebito.class);
		}	
		String respuesta = service.efectivizar(fondoDebito);
		Gson gson = new Gson();
		Map<String, String> resultado = ImmutableMap.of("respuesta",respuesta);
		String json = gson.toJson(resultado);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("pagar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pagar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		PagosService pagosService = new PagosService();
		Pagos pagos = new Pagos();
		if(pagos!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			pagos = mapper.readValue(paramJson, Pagos.class);
		}
		String result = pagosService.pagar(pagos);
		Gson gson = new Gson();
		Map<String, String> resultado = ImmutableMap.of("respuesta",result);
		String json = gson.toJson(resultado);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("anular-pago/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response anularCobro(@PathParam("codigo") Long codigo) throws SQLException{
		PagosService pagosService = new PagosService();
		String result = pagosService.anularPago(codigo);
		Gson gson = new Gson();
		Map<String, String> resultado = ImmutableMap.of("respuesta",result);
		String json = gson.toJson(resultado);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("listar-pagos-cabecera/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listaPagosCabecera(@PathParam("codigo") Long codigo) throws SQLException{
		PagosService pagosService = new PagosService();
		List<PagosCabecera> lista = pagosService.listaPagosCabecera(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
