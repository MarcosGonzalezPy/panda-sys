package com.panda.panda_sys.resources.compras;

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
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.panda.panda_sys.model.compras.NotaDebito;
import com.panda.panda_sys.model.compras.OrdenCompra;
import com.panda.panda_sys.model.compras.OrdenCompraCabecera;
import com.panda.panda_sys.model.compras.RegistroCompra;
import com.panda.panda_sys.param.compras.OrdenCompraDetalleParam;
import com.panda.panda_sys.param.compras.RegistroCompraDetalleParam;
import com.panda.panda_sys.services.compras.ComprasService;


@Path("/compras")
public class ComprasResource {
	
	
	@GET
	@Path("/insertar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		ComprasService comprasService = new ComprasService();
		OrdenCompra param = new OrdenCompra();
		if(paramJson!= null && !paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			param = mapper.readValue(paramJson, OrdenCompra.class);
		}
		Boolean respuesta = comprasService.insertar(param);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<OrdenCompraCabecera> lista = new ArrayList<OrdenCompraCabecera>();
		OrdenCompraCabecera inventarioParam = new OrdenCompraCabecera();
		if(paramJson!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			inventarioParam = mapper.readValue(paramJson, OrdenCompraCabecera.class);
		}	
		ComprasService comprasService = new ComprasService();
		lista = comprasService.listarOrdenCompra(inventarioParam);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/anular")
	@Produces(MediaType.APPLICATION_JSON)
	public Response anular(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {		
		ComprasService comprasService = new ComprasService();
		OrdenCompraCabecera param = new OrdenCompraCabecera();
		if(paramJson!= null && !paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			param = mapper.readValue(paramJson, OrdenCompraCabecera.class);
		}
		boolean result = comprasService.anular(param.getCodigo(), param.getEstado(), param.getSucursal());
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/listar-detalle/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarDetalle(@PathParam("codigo") Integer codigo) throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<OrdenCompraDetalleParam> lista = new ArrayList<OrdenCompraDetalleParam>();
		ComprasService comprasService = new ComprasService();
		lista = comprasService.listarOrdenCompraDetalle(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/registro-compra/insertar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertarRegistroCompra(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		ComprasService comprasService = new ComprasService();
		RegistroCompra param = new RegistroCompra();
		if(paramJson!= null && !paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			param = mapper.readValue(paramJson, RegistroCompra.class);
		}
		Boolean respuesta = comprasService.recepcionCompra(param);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/listar-detalle-registro/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarDetalleRegistro(@PathParam("codigo") Integer codigo) throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<RegistroCompraDetalleParam> lista = new ArrayList<RegistroCompraDetalleParam>();
		ComprasService comprasService = new ComprasService();
		lista = comprasService.listarRegistroCompraDetalle(codigo);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/registrarn-nota-debito")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarNotaDebito(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, IOException {
		ComprasService comprasService = new ComprasService();
		NotaDebito param = new NotaDebito();
		if(paramJson!= null && !paramJson.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			param = mapper.readValue(paramJson, NotaDebito.class);
		}
		Boolean respuesta = comprasService.registrarNotaDebito(param);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
