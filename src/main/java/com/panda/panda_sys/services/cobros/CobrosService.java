package com.panda.panda_sys.services.cobros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.panda.panda_sys.model.FondoCredito;
import com.panda.panda_sys.util.Conexion;

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

}
