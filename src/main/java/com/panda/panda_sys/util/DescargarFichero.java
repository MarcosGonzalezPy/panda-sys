package com.panda.panda_sys.util;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DescargarFichero extends Conexion{
	
	public String comprasCabecera(Long cantidad, Long total){
		return"1\t201807\t1\t911\t211\t4618764\t2\tMARCOS GONZALEZ"
			+ "\t0\t0\t0\t"+cantidad+"\t"+total+"\tNO\t2\n";
	}
	
	public String comprasDetalle(String ruc, String dv, String nombre, String timbrado, String numeroFactura,
			String fecha, Long monto, Long iva, int contado, int cuotas){
		return "2\t"+ruc+"\t"+dv+"\t"+nombre+"\t"+timbrado+"\t1\t"+numeroFactura
			+"\t"+fecha+"\t"+monto+"\t"+iva+"\t0\t0\t0\t0\t"+contado+"\t"+cuotas+"\n";
	}
	
	public String ventasDetalle(String ruc, String dv, String nombre, String numeroFactura, String fecha,
			Long monto, Long iva, Long total, int codicionCredito, int cuotas, String timbrado){
		return "2\t"+ruc+"\t"+dv+"\t"+nombre+"\t1\t"+numeroFactura+"\t"+fecha+"\t"+monto+"\t"+iva+"\t0\t0\t0\t"+total+"\t"+cuotas+"\t"+timbrado+"\n"; 
	}
	
	public String compra() throws SQLException{
		Connection c=ObtenerConexion();
		String archivo="";
		try {
			String detalle="";
			Long cantidad=0l;
			Long montoTotal=0l;
			String sql="select p.ruc, p.nombre, occ.timbrado, occ.numero_factura, to_char(fecha_creacion, 'DD/MM/YYYY')as fecha, occ.monto, occ.condicion_compra, occ.plazo as cuotas "
				+ "from personas p, orden_compra_cabecera occ "
				+ "where occ.proveedor_codigo = p.codigo and occ.estado = 'RECEPCIONADO' "
				+ "and to_char(fecha_creacion, 'MM/YYYY') = to_char(current_date, 'MM/YYYY')";
			PreparedStatement ps=c.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				String ruc = rs.getString("ruc").substring(0, rs.getString("ruc").length()-2);
				String dv = rs.getString("ruc").substring(rs.getString("ruc").length()-1,rs.getString("ruc").length());
				Long iva= rs.getLong("monto")/11;
				Long montoSinIva = rs.getLong("monto")-iva;
				montoTotal +=montoSinIva; //rs.getLong("monto");
				String numeroFactura = rs.getString("numero_factura"); //"001-001-"+ rellenar(rs.getString("numero_factura"));
				int contado= rs.getString("condicion_compra").equals("CONTADO")?1:0;
				detalle+=comprasDetalle(ruc, dv, rs.getString("nombre"), rs.getString("timbrado"),numeroFactura , rs.getString("fecha"), 
						montoSinIva, iva, contado, rs.getInt("cuotas"));
				cantidad++;
			}
			String cabecera =comprasCabecera(cantidad, montoTotal);			
			archivo=cabecera+detalle;
			c.close();
		} catch (Exception e) {
			System.out.println("ERROR: "+e.getMessage());
			c.close();
			return e.getMessage();
		}

		return archivo;
	}
	
	public String ventas() throws SQLException{
		Connection c=ObtenerConexion();
		String archivo="";
		try {
			String detalle="";
			Long cantidad=0l;
			Long montoTotal=0l;
			String sql=" select p.ruc, p.nombre, fc.punto_expedicion,fc.numero_factura,to_char(fecha, 'DD/MM/YYYY') as fecha, fc.monto, fc.timbrado, fc.condicion_compra,"
					+ " fc.cuotas "
				+ " from factura_cabecera fc, personas p "
				+ " where fc.codigo_persona = p.codigo "
				+ "and fc.estado = 'FACTURADO' "
				+ "and to_char(fecha, 'MM/YYYY') = to_char(current_date, 'MM/YYYY')";
			PreparedStatement ps=c.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				String ruc = rs.getString("ruc").substring(0, rs.getString("ruc").length()-2);
				String dv = rs.getString("ruc").substring(rs.getString("ruc").length()-1,rs.getString("ruc").length());
				Long iva= rs.getLong("monto")/11;
				Long montoSinIva = rs.getLong("monto")-iva;
				montoTotal +=montoSinIva; 
				String numeroFactura = rs.getString("punto_expedicion")+"-"+ rellenar(rs.getString("numero_factura"));
				int contado= rs.getString("condicion_compra").equals("CONTADO")?1:0;
				detalle+=comprasDetalle(ruc, dv, rs.getString("nombre"), rs.getString("timbrado"),numeroFactura , rs.getString("fecha"), 
						montoSinIva, iva, contado, rs.getInt("cuotas"));
				cantidad++;
			}
			String cabecera =comprasCabecera(cantidad, montoTotal);
			archivo=cabecera+detalle;
			c.close();
		} catch (Exception e) {
			System.out.println("ERROR: "+e.getMessage());
			c.close();
			return e.getMessage();
		}
		return archivo;		
	}
	
	public String descargar(String archivo, String tipo) throws IOException{
		FileWriter file = new FileWriter("C:\\hechauka\\"+archivo+".txt");
	    try {
	    	if(tipo.equals("COMPRAS")){
	    		file.write(compra());
	    	}else{
	    		file.write(ventas());
	    	}
	    	
		} catch (Exception e) {
			System.out.println("Error al escribir.");
		}
	    file.flush();
	    file.close();
	    return "OK";
	}
	
	private String rellenar(String cadena){
		int cantidad = 7 - cadena.length();
		for(int i=0;i<cantidad;i++){
			cadena="0"+cadena;
		}
		return cadena;
	}

}
