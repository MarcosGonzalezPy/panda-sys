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
			if(fondoCredito.getCliente()!= null && ! fondoCredito.getEstado().equals("")){
				String conector = null;
				if(sql.contains("where")){
					conector = " and ";
				}else{
					conector = " where ";
				}
				sql += conector + " cliente= ? ";
			}
			PreparedStatement ps= c.prepareStatement(sql);
			if(fondoCredito.getEstado()!= null && !fondoCredito.getEstado().equals("")){
				numeroParametro++;
				ps.setString(numeroParametro, fondoCredito.getEstado());
			}
			if(fondoCredito.getCliente()!= null && ! fondoCredito.getEstado().equals("")){
				numeroParametro++;
				ps.setLong(numeroParametro, fondoCredito.getCliente());
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

}
