package com.panda.panda_sys.services.reportes;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.panda.panda_sys.util.Conexion;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;

public class ReportesService extends Conexion {

	public byte[] exportar(String idProceso, String codRechazo, String formato) throws JRException, SQLException {
		formato = "PDF";
		Connection c = ObtenerConexion();

		// obtenemos el reporte
		ClassLoader cL = getClass().getClassLoader();
		InputStream path = cL.getResourceAsStream("C:\\reporetes\\pagare.jasper");
		// InputStream logo =
		// cL.getResourceAsStream("imagen/personal-logo.jpg");

		Map<String, Object> parametros = new HashMap<String, Object>();
		/*
		parametros.put("id_proceso", idProceso);
		parametros.put("codigo_rechazo", codRechazo);
		parametros.put("imagen", logo);
		*/
		// generamos reporte
		if ("PDF".equals(formato))
			return JasperRunManager.runReportToPdf(path, parametros, c);
		else {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			JRXlsExporter exporter = new JRXlsExporter();
			JasperPrint jasperPrint = JasperFillManager.fillReport(path, parametros, c);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
			exporter.exportReport();
			return byteArrayOutputStream.toByteArray();
		}

	}

}
