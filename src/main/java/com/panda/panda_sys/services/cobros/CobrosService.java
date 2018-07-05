package com.panda.panda_sys.services.cobros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.panda.panda_sys.model.FondoCredito;
import com.panda.panda_sys.model.cobros.Cobros;
import com.panda.panda_sys.model.cobros.DetalleCobro;
import com.panda.panda_sys.model.cobros.ReciboCabecera;
import com.panda.panda_sys.util.Conexion;
import com.panda.panda_sys.util.NumberToLetterConverter;
import com.panda.panda_sys.util.Secuencia;

public class CobrosService extends Conexion{
	
	public List<FondoCredito> listarFondoCredito(FondoCredito fondoCredito) throws SQLException{
		List<FondoCredito> lista = new ArrayList<FondoCredito>();
		Connection c = ObtenerConexion();
		int numeroParametro = 0;
		try {
			String sql = "select * from fondo_credito ";
			if(fondoCredito.getEstado()!= null && !fondoCredito.getEstado().equals("")){
				sql += " where estado = ?";
			}
			if(fondoCredito.getCliente()!= null && ! fondoCredito.getCliente().equals("")){
				String conector = null;
				if(sql.contains("where")){
					conector = " and ";
				}else{
					conector = " where ";
				}
				sql += conector + " cliente= ? ";
			}
			if(fondoCredito.getCobroDetalle()!=null && !fondoCredito.getCobroDetalle().equals("")){
				String conector = null;
				if(sql.contains("where")){
					conector = " and ";
				}else{
					conector = " where ";
				}
				sql += conector + " cobro_detalle= ? ";
			}
			
			PreparedStatement ps= c.prepareStatement(sql);
			if(fondoCredito.getEstado()!= null && !fondoCredito.getEstado().equals("")){
				numeroParametro++;
				ps.setString(numeroParametro, fondoCredito.getEstado());
			}
			if(fondoCredito.getCliente()!= null && ! fondoCredito.getCliente().equals("")){
				numeroParametro++;
				ps.setLong(numeroParametro, fondoCredito.getCliente());
			}
			if(fondoCredito.getCobroDetalle()!=null && !fondoCredito.getCobroDetalle().equals("")){
				numeroParametro++;
				ps.setLong(numeroParametro, fondoCredito.getCobroDetalle());
			}
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				FondoCredito entidad = new FondoCredito();
				entidad.setCliente(rs.getLong("cliente"));
				entidad.setCodigo(rs.getLong("codigo"));
				entidad.setDias(rs.getLong("dias"));
				entidad.setDocumento(rs.getString("documento"));
				entidad.setDocumentoNumero(rs.getString("documento_numero"));
				entidad.setEstado(rs.getString("estado"));
				entidad.setFecha(rs.getDate("fecha"));
				entidad.setFechaPago(rs.getDate("fecha_pago"));
				entidad.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
				entidad.setGlosa(rs.getString("glosa"));
				entidad.setMonto(rs.getLong("monto"));
				entidad.setNumero(rs.getString("numero"));
				entidad.setSucursal(rs.getString("sucursal"));
				entidad.setCobroDetalle(rs.getLong("cobro_detalle"));
				lista.add(entidad);
			}
			c.close();
		} catch (Exception e) {
			System.out.println("ERROR : "+e.getMessage());
			c.close();
		}
		return lista;
	}
	
	public String cobrar(Cobros cobros) throws SQLException{
		ReciboCabecera rc = cobros.getReciboCabecera();
		List<DetalleCobro> listaDetalleCobro = cobros.getListaDetalleCobro();
		List<FondoCredito> listaFondoCredito = cobros.getListaFondoCredito();
		Connection c = ObtenerConexion();
		try {
			Long monto = 0l;
			c.setAutoCommit(false);
			
			Secuencia secuencia = new Secuencia();
			Long sec = Long.parseLong(secuencia.getSecuencia("detalle_cobro_seq"));

			for(DetalleCobro dc: listaDetalleCobro){
				monto += dc.getImporte();
				String sql2="INSERT INTO detalle_cobro "
						+ "(codigo, medio_pago, marca_tarjeta, importe, estado, usuario, fecha) VALUES(?,?,?,?,?,?, current_date)";
				PreparedStatement ps2=c.prepareStatement(sql2);
				ps2.setLong(1, sec);
				ps2.setString(2, dc.getMedioPago());
				ps2.setString(3, dc.getMarcaTarjeta());
				ps2.setLong(4, dc.getImporte());
				ps2.setString(5, "ACTIVO");
				ps2.setString(6, rc.getCajero());
				ps2.execute();
				
				if(dc.getMedioPago().contains("CRED-")){
					String documento = dc.getMedioPago().substring(5, dc.getMedioPago().length());
					String sql5 = "update fondo_debito set estado ='PAGADO' where documento=? and documento_numero =?";
					PreparedStatement ps6 = c.prepareStatement(sql5);
					ps6.setString(1, documento);
					ps6.setLong(2,Long.parseLong(dc.getMarcaTarjeta()));
					ps6.execute();					
				}
			}
			for(FondoCredito fc: listaFondoCredito){
				String sql3="UPDATE fondo_credito SET estado='COBRADO', fecha_pago=current_timestamp, dias=(select fecha_vencimiento - current_date from fondo_credito"
						+ " where codigo=? AND numero=?), "
						+ "cobro_detalle=? WHERE codigo=? AND numero=? ";
				PreparedStatement ps3=c.prepareStatement(sql3);
				ps3.setLong(1, fc.getCodigo());
				ps3.setString(2, fc.getNumero());
				ps3.setLong(3, sec);
				ps3.setLong(4, fc.getCodigo());
				ps3.setString(5, fc.getNumero());
				ps3.execute();
			}
			String glosa = NumberToLetterConverter.convertNumberToLetter(monto);
			String sql4="INSERT INTO recibo_cabecera (codigo_persona, nombre_persona, sucursal, caja, cajero, fecha, "
				+ "estado, codigo_pago, glosa, monto)VALUES(?,?,?,?,?,current_timestamp,'ACTIVO', ?,?,?)";
			PreparedStatement ps4 =c.prepareStatement(sql4);
			ps4.setLong(1, rc.getCodigoPersona());
			ps4.setString(2, rc.getNombrePersona());			
			ps4.setString(3, rc.getSucursal());
			ps4.setLong(4, rc.getCaja());
			ps4.setString(5, rc.getCajero());
			ps4.setLong(6, sec);
			ps4.setString(7, glosa);
			ps4.setLong(8, monto);
			ps4.execute();
			
			c.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
		c.close();
		return "OK";
	}
	
	public String anularCobro(Long detalleCobro) throws SQLException{
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			String sql1 ="update detalle_cobro set estado='ANULADO' where codigo=?";
			PreparedStatement ps1 =c.prepareStatement(sql1);
			ps1.setLong(1, detalleCobro);
			ps1.execute();
			
			String sql2="select * from detalle_cobro where codigo = ?";
			PreparedStatement ps2=c.prepareStatement(sql2);
			ps2.setLong(1, detalleCobro);
			ResultSet rs2= ps2.executeQuery();
			while(rs2.next()){
				String medioPago = rs2.getString("medio_pago");				
				if(medioPago.contains("CRED-")){
					String documento =medioPago.substring(5, medioPago.length());
					String sql3 = "update fondo_debito set estado ='PENDIENTE' where documento=? and documento_numero =?";
					PreparedStatement ps3 = c.prepareStatement(sql3);
					ps3.setString(1, documento);
					Long marcaTarjeta = Long.parseLong(rs2.getString("marca_tarjeta"));
					ps3.setLong(2, marcaTarjeta);
					ps3.execute();	
					
				}
			}
						
//			String sql4 ="select * from fondo_credito where cobro_detalle=?";
//			PreparedStatement ps4=c.prepareStatement(sql4);
//			ps4.setLong(1, detalleCobro);
//			ResultSet rs4 = ps4.executeQuery();
//			while(rs4.next()){
//				Long codigoFondoCredito = rs4.getLong("codigo");			
//			}
			String sql5 ="Update fondo_credito set estado='PENDIENTE', fecha_pago=null, "
					+ " dias = null, cobro_detalle=null where cobro_detalle=?";
			PreparedStatement ps5 = c.prepareStatement(sql5);
			ps5.setLong(1, detalleCobro);
			ps5.execute();
			
			String sql6 = "update recibo_cabecera set estado= 'ANULADO' where codigo_pago=?";
			PreparedStatement ps6 = c.prepareStatement(sql6);
			ps6.setLong(1, detalleCobro);
			ps6.execute();
			
			c.commit();
		} catch (Exception e) {
			System.out.println("ERROR: " +e.getMessage());
			return e.getMessage();
		}
		c.close();
		return "OK";
	}
	
	public List<DetalleCobro> listarDetalleCobro(Long codigo) throws SQLException{
		List<DetalleCobro> lista=new ArrayList<DetalleCobro>();
		Connection c=ObtenerConexion();
		try {			
			String sql1 = "select * from detalle_cobro wherer codigo = ?";
			PreparedStatement ps1=c.prepareStatement(sql1);
			ps1.setLong(1, codigo);
			ResultSet rs1= ps1.executeQuery();
			while(rs1.next()){
				DetalleCobro entidad = new DetalleCobro();
				entidad.setCodigo(rs1.getLong("codigo"));
				entidad.setEstado(rs1.getString("estado"));
				entidad.setImporte(rs1.getLong("importa"));
				entidad.setMarcaTarjeta(rs1.getString("marca_tarjeta"));
				entidad.setMedioPago(rs1.getString("medio_pago"));
				lista.add(entidad);
			}			
		} catch (Exception e) {
			System.out.println("ERROR: "+e.getMessage());		
		}
		c.close();
		return lista;
	} 
	
	public List<ReciboCabecera> listarReciboCabecera(Long codigoPago) throws SQLException{
		List<ReciboCabecera> lista =new ArrayList<ReciboCabecera>();
		Connection c= ObtenerConexion();
		try {
			String sql="select * from recibo_cabecera where codigo_pago = ?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setLong(1, codigoPago);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				ReciboCabecera rc = new ReciboCabecera();
				rc.setNombrePersona(rs.getString("nombre_persona"));
				rc.setCodigoPersona(rs.getLong("codigo_persona"));
				lista.add(rc);
			}
		} catch (Exception e) {
			System.out.println("ERROR: "+e.getMessage());
		}
		c.close();
		return lista;
	} 
	 

}
