package com.panda.panda_sys.resources.reportes;

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
import com.panda.panda_sys.model.reportes.ReporteParametros;
import com.panda.panda_sys.model.reportes.Reportes;
import com.panda.panda_sys.param.reportes.ReportesCompuesto;
import com.panda.panda_sys.services.personas.UsuarioSucursalService;
import com.panda.panda_sys.services.reportes.ReportesService;

@Path("reportes")
@Produces(MediaType.APPLICATION_JSON)
public class ReportesResource {

	@GET
	@Path("/listar/{modulo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarReportes(@PathParam("modulo") String modulo) {
		ReportesService reportesService = new ReportesService();
		List<Reportes> respuesta = reportesService.listarReportes(modulo);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("listarABM")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarABM(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<Reportes> lista = new ArrayList<Reportes>();
		Reportes valor = new Reportes();
		if (paramJson != null && !paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, Reportes.class);
		}
		ReportesService reportesService = new ReportesService();
		lista = reportesService.listarABM(valor);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/insertarABM")
	public Response insertarABM(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, IOException {
		ReportesService reportesService = new ReportesService();
		Reportes reportes = new Reportes();
		if (!paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			reportes = mapper.readValue(paramJson, Reportes.class);
		}
		boolean result = reportesService.insertarABM(reportes);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

	@POST
	@Path("/insertarReportesCompuestos")
	public Response insertarReportesCompuestos(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, IOException {
		ReportesService reportesService = new ReportesService();
		ReportesCompuesto reportesCompuesto = new ReportesCompuesto();
		if (!paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			reportesCompuesto = mapper.readValue(paramJson, ReportesCompuesto.class);
		}
		boolean result = reportesService.insertarReportesCompuestos(reportesCompuesto);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

	@POST
	@Path("/modificarReportesCompuestos")
	public Response editarReportesCompuestos(@QueryParam("paramJson") String paramJson)
			throws JsonParseException, JsonMappingException, IOException, SQLException {
		Boolean respuesta = null;
		ReportesCompuesto reportesCompuesto = new ReportesCompuesto();
		if (paramJson != null && !paramJson.equals("") && !paramJson.equals("{}")) {
			ObjectMapper mapper = new ObjectMapper();
			reportesCompuesto = mapper.readValue(paramJson, ReportesCompuesto.class);
		}
		ReportesService reportesService = new ReportesService();
		respuesta = reportesService.modificarReportesCompuestos(reportesCompuesto);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

	@POST
	@Path("/modificarABM")
	public Response editarABM(@QueryParam("paramJson") String paramJson)
			throws JsonParseException, JsonMappingException, IOException, SQLException {
		Boolean respuesta = null;
		Reportes reportes = new Reportes();
		if (paramJson != null && !paramJson.equals("") && !paramJson.equals("{}")) {
			ObjectMapper mapper = new ObjectMapper();
			reportes = mapper.readValue(paramJson, Reportes.class);
		}
		ReportesService reportesService = new ReportesService();
		respuesta = reportesService.modificarABM(reportes);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

	@GET
	@Path("/eliminarABM/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarABM(@PathParam("id") Long id) throws SQLException {
		ReportesService reportesService = new ReportesService();
		boolean result = reportesService.eliminarABM(id);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
	}

	@GET
	@Path("listarReporteParametros")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarReporteParametros(@QueryParam("paramJson") String paramJson)
			throws SQLException, JsonParseException, JsonMappingException, IOException {
		List<ReporteParametros> lista = new ArrayList<ReporteParametros>();
		ReporteParametros valor = new ReporteParametros();
		if (paramJson != null && !paramJson.equals("")) {
			ObjectMapper mapper = new ObjectMapper();
			valor = mapper.readValue(paramJson, ReporteParametros.class);
		}
		ReportesService reportesService = new ReportesService();
		lista = reportesService.listarReporteParametros(valor);
		Gson gson = new Gson();
		String json = gson.toJson(lista);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/eliminar-compuesto/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarReporteCompuesto(@PathParam("id") Integer id)
			throws SQLException {
		ReportesService reportesService = new ReportesService();
		boolean result = reportesService.eliminarReporteCompuesto(id);
		Gson gson = new Gson();
		String json = gson.toJson(result);
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
