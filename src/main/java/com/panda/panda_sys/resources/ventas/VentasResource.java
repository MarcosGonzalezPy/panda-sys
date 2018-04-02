package com.panda.panda_sys.resources.ventas;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.panda.panda_sys.model.FondoDebito;
import com.panda.panda_sys.model.inventario.InventarioParam;
import com.panda.panda_sys.model.ventas.Cajas;
import com.panda.panda_sys.model.ventas.Factura;
import com.panda.panda_sys.model.ventas.FacturaCabecera;
import com.panda.panda_sys.model.ventas.FacturaDetalle;
import com.panda.panda_sys.model.ventas.NotaCredito;
import com.panda.panda_sys.model.ventas.VentasStockPorSucursal;
import com.panda.panda_sys.param.inventario.ListaRegistroAjuste;
import com.panda.panda_sys.param.ventas.RegistrarPago;
import com.panda.panda_sys.services.catalogo.DominiosService;
import com.panda.panda_sys.services.inventario.InventarioService;
import com.panda.panda_sys.services.ventas.VentasService;

import jersey.repackaged.com.google.common.collect.ImmutableMap;

@Path("ventas/")
@Produces(MediaType.APPLICATION_JSON)
public class VentasResource {
	
	@GET
	@Path("ventas-stock-por-sucursal")
	public Response obtenerVentasStockPorSucursal(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException{
		List<VentasStockPorSucursal> lista = new ArrayList<VentasStockPorSucursal>();
		VentasService ventasService = new VentasService();
		VentasStockPorSucursal ventasStockPorSucursal = new VentasStockPorSucursal();
		if(paramJson!= null && !paramJson.equals("") && !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			ventasStockPorSucursal = mapper.readValue(paramJson, VentasStockPorSucursal.class);
		}	
		lista = ventasService.obtenerVentasStockPorSucursal(ventasStockPorSucursal);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("registrar-venta")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarVenta(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		Boolean respuesta = null;
		Factura factura = new Factura();
		if(paramJson!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			factura = mapper.readValue(paramJson, Factura.class);
		}	
		VentasService ventasService = new VentasService();
		respuesta = ventasService.registrarVenta(factura);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("listar-venta")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarVenta(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<FacturaCabecera> lista = new ArrayList<FacturaCabecera>();
		FacturaCabecera facturaCabecera = new FacturaCabecera();
		if(paramJson!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			facturaCabecera = mapper.readValue(paramJson, FacturaCabecera.class);
		}	
		VentasService ventasService = new VentasService();
		lista = ventasService.listarFacturas(facturaCabecera);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminar(@PathParam("id") String id) throws SQLException {		
		VentasService ventasService = new VentasService();
		String result = ventasService.eliminar(id);
		Gson gson = new Gson();
		Map<String, String> resultado = ImmutableMap.of("respuesta",result);
		String json = gson.toJson(resultado);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
		
	@GET
	@Path("listar-detalle-factura/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarDetalleFactura(@PathParam("id") String id) throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<FacturaDetalle> lista = new ArrayList<FacturaDetalle>();
		VentasService ventasService = new VentasService();
		lista = ventasService.listarDetalle(id);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("registrar-pago")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarPago(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		VentasService ventasService = new VentasService();
		RegistrarPago registrarPago = new RegistrarPago();
		if(paramJson!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			registrarPago = mapper.readValue(paramJson, RegistrarPago.class);
		}
		String result = ventasService.registrarPago(registrarPago);
		Gson gson = new Gson();
		Map<String, String> resultado = ImmutableMap.of("respuesta",result);
		String json = gson.toJson(resultado);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("anular-factura/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response anular(@PathParam("id") String id) throws SQLException {		
		VentasService ventasService = new VentasService();
		String result = ventasService.anularFactura(id);
		Gson gson = new Gson();
		Map<String, String> resultado = ImmutableMap.of("respuesta",result);
		String json = gson.toJson(resultado);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}
	
	@POST
	@Path("editar-factura")
	public Response editarFactura(@QueryParam("paramJson") String paramJson)
			throws JsonParseException, JsonMappingException, IOException, SQLException{
		Boolean respuesta = null;
		Factura factura = new Factura();
		if(paramJson!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			factura = mapper.readValue(paramJson, Factura.class);
		}	
		VentasService ventasService = new VentasService();
		respuesta = ventasService.editarFactura(factura);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
				.build();		
	}
	
	@GET
	@Path("listar-detalle-aprovado-reparacion/{secuencia}")
	public Response listarDetalleAprovadoReparacion(@PathParam("secuencia") Long secuencia){
		VentasService ventasService = new VentasService();
		List<FacturaDetalle> respuesta = ventasService.listarDetalleAprovadoReparacion(secuencia);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
				.build();	
		
	}
	
	@GET
	@Path("registrar-nota-credito")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarNotaCredito(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		VentasService ventasService = new VentasService();
		NotaCredito notaCredito = new NotaCredito();
		if(paramJson!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			notaCredito = mapper.readValue(paramJson, NotaCredito.class);
		}
		Boolean result = ventasService.registrarNotaCredito(notaCredito);
		Gson gson = new Gson();
		Map<String, Boolean> resultado = ImmutableMap.of("respuesta",result);
		String json = gson.toJson(resultado);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("listar-fondo-debito")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarFondoDebito(@QueryParam("paramJson") String paramJson) throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<FondoDebito> lista = new ArrayList<FondoDebito>();
		VentasService ventasService = new VentasService();
		FondoDebito fondoDebito = new FondoDebito();
		if(paramJson!= null && !paramJson.equals("")&& !paramJson.equals("{}")){
			ObjectMapper mapper = new ObjectMapper();
			fondoDebito = mapper.readValue(paramJson, FondoDebito.class);
		}
		lista = ventasService.listarFondoDebito(fondoDebito);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
	