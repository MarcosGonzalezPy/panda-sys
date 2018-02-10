package com.panda.panda_sys.resources.reportes;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.panda.panda_sys.services.reportes.ReportesService;

import net.sf.jasperreports.engine.JRException;

@Path("reportes")
public class ReportesResource {
	
	@GET
	@Path("/prueba")
	@Produces(MediaType.APPLICATION_JSON)
	public Response acceder() throws SQLException, JRException {
		ReportesService reportesService = new ReportesService();
		byte[] respuesta =reportesService.exportar(null, null, null);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);	
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
