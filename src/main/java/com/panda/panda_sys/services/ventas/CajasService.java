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
import com.panda.panda_sys.param.CajaTimbrado;
import com.panda.panda_sys.util.Conexion;

public class CajasService extends Conexion{

	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
	
	public List<Cajas> listar(Cajas cajas) throws SQLException{
		List<Cajas> lista = new ArrayList<Cajas>();
		String sql = "select * from cajas ";
		if(cajas.getCodigo()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql +conector+ " codigo = "+cajas.getCodigo()+" ";
		}
		if(cajas.getNumero()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " UPPER(numero) like  UPPER('%"+cajas.getNumero()+"%')";
		}
		if(cajas.getEstado()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " UPPER(estado) = UPPER('"+cajas.getEstado()+"') ";
		}
		if(cajas.getSucursal()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " UPPER(sucursal) = UPPER('"+cajas.getSucursal()+"') ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	Cajas entidad = new Cajas();        
        	entidad.setCodigo(rs.getInt("codigo"));
        	entidad.setNumero(rs.getString("numero"));
        	entidad.setSucursal(rs.getString("sucursal"));
        	entidad.setEstado(rs.getString("estado"));
        	entidad.setExpedicion(rs.getString("expedicion"));
        	lista.add(entidad);
        }
		return lista;
	}
	
	public boolean eliminar(int codigo) throws SQLException{
		if(existe(codigo)){
			String sql = "delete from cajas where codigo ="+codigo;
			Statement statement = con.ObtenerConexion().createStatement();
			statement.execute(sql);
			if(existe(codigo)){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	public boolean existe(int codigo) throws SQLException{
		String valor = null;
		String sql = "select codigo from cajas where codigo ="+codigo;
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		 while(rs.next()){
			 valor = rs.getString("codigo");
		 }
		if(valor!= null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean insertar(Cajas cajas) throws SQLException{
		try {
			String sql = "insert into cajas (codigo,estado,numero, sucursal,expedicion)"
					+ "values ("+cajas.getCodigo()+",upper('"+cajas.getEstado()+"'), upper('"+cajas.getNumero()+"'),"
							+ " upper('"+cajas.getSucursal()+"'), upper('"+cajas.getExpedicion()+"'));";
			Statement statement = con.ObtenerConexion().createStatement();
			statement.execute(sql);	
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	
	public CajaTimbrado obtenerSucursalTimbrado(String usuario){
		CajaTimbrado cajaTimbrado =  new CajaTimbrado();
		try {
			String sql =" select a.*, b.expedicion, b.sucursal from cajas_movimientos a, cajas b "
				+ " where b.codigo = a.codigo_caja and a.estado = 'ABIERTO' "
				+ "and usuario = '"+usuario+"'";
			Statement statement = con.ObtenerConexion().createStatement();
			rs = statement.executeQuery(sql);
			while(rs.next()){
				cajaTimbrado.setCaja(rs.getString("codigo_caja"));
				cajaTimbrado.setSucursal(rs.getString("sucursal"));
				cajaTimbrado.setTimbrado(rs.getString("expedicion"));
			}
		} catch (Exception e) {
			return null;
		}
		return cajaTimbrado;
	}

}
