package com.panda.panda_sys.services.ventas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.ventas.Cajas;
import com.panda.panda_sys.model.ventas.CajasMovimientos;
import com.panda.panda_sys.util.Conexion;

public class CajasMovimientosService extends Conexion{	
	
	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
	
	public boolean insertar(CajasMovimientos cajasMovimientos) throws SQLException{
		try {
			String sql = "insert into cajas_movimientos (estado,codigo_caja,usuario,monto_apertura, usuario_creacion, fecha_apertura)"
				+ "values (upper('"+cajasMovimientos.getEstado()+"'),"+cajasMovimientos.getCodigoCaja()+" , "
				+ " '"+cajasMovimientos.getUsuario()+"', "+cajasMovimientos.getMontoApertura()+", " + " '"+cajasMovimientos.getUsuarioCreacion()+"', current_date);";
			Statement statement = con.ObtenerConexion().createStatement();
			statement.execute(sql);	
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	
	public List<CajasMovimientos> listar(CajasMovimientos cajasMovimientos){
		List<CajasMovimientos> lista = new ArrayList<CajasMovimientos>();
		try {
			String sql = "select * from cajas_movimientos ";
			if(cajasMovimientos.getCodigo()!= null){
				String conector = null;
				if(sql.contains("where")){
					conector = " and ";
				}else{
					conector = " where ";
				}
				sql = sql +conector+ " codigo = "+cajasMovimientos.getCodigo()+" ";
			}
			if(cajasMovimientos.getEstado()!= null){
				String conector = null;
				if(sql.contains("where")){
					conector = " and ";
				}else{
					conector = " where ";
				}
				sql = sql+conector + " UPPER(estado) = UPPER('"+cajasMovimientos.getEstado()+"') ";
			}
			Statement statement = con.ObtenerConexion().createStatement();
			rs = statement.executeQuery(sql);
	        while(rs.next()){
	        	CajasMovimientos entidad = new CajasMovimientos();        
	        	entidad.setCodigo(rs.getInt("codigo"));
	        	entidad.setEstado(rs.getString("estado"));
	        	entidad.setCodigoCaja(rs.getString("codigo_caja"));
	        	entidad.setUsuario(rs.getString("usuario"));
	        	entidad.setMontoApertura(rs.getInt("monto_apertura"));
	        	entidad.setUsuarioCreacion(rs.getString("usuario_creacion"));
	        	entidad.setFechaApertura(rs.getDate("fecha_apertura"));
	        	entidad.setFechaCierre(rs.getDate("fecha_cierre"));
	        	lista.add(entidad);
	        }
			return lista;
		} catch (Exception e) {
			System.out.println("ERROR "+e.getMessage());
		}
		return lista;
	}

}
