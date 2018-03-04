package com.panda.panda_sys.services.reportes;

import java.sql.Connection;
import java.sql.SQLException;

import com.panda.panda_sys.util.Conexion;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class GenerateSimpleReport extends Conexion{
	
	public  byte[]  reporte() throws JRException, SQLException {
		Connection c = ObtenerConexion();
		JasperReport jasperReport = null; //JasperCompileManager.compileReport("C:\\reportes\\pagare.jrxml");
		String path = "C:\\reportes\\pagare.jrxml"; 
		jasperReport = (JasperReport) JRLoader.loadObjectFromFile(path);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, c);
		JasperViewer jv = new JasperViewer(jasperPrint);
		jv.setVisible(true);
		return JasperRunManager.runReportToPdf(path, null, c);
 	}

}
