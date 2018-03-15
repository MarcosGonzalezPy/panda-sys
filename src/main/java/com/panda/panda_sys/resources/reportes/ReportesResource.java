package com.panda.panda_sys.resources.reportes;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.panda.panda_sys.model.reportes.Reportes;
import com.panda.panda_sys.services.reportes.ReportesService;

import net.sf.jasperreports.engine.JRException;

@Path("reportes")
public class ReportesResource {
	
	@GET
	@Path("/listar/{modulo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarReportes(@PathParam("modulo") String modulo) {
		ReportesService reportesService = new ReportesService();
		List<Reportes> respuesta=reportesService.listarReportes(modulo);
		Gson gson = new Gson();
		String json = gson.toJson(respuesta);	
		return Response.ok(json).header("Access-Control-Allow-Origin", "*").build();
	}

}
