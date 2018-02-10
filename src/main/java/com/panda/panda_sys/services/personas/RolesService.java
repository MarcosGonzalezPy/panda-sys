package com.panda.panda_sys.services.personas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.personas.Roles;
import com.panda.panda_sys.param.RolesParam;
import com.panda.panda_sys.util.Conexion;

public class RolesService extends Conexion{
	
	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
	
	public List<Roles> listar(Roles roles) throws SQLException{
		List<Roles> lista = new ArrayList<Roles>();
		String sql = "select * from roles ";
		if(roles!= null){
			if(roles.getRol()!= null){
				String conector = null;
				if(sql.contains("where")){
					conector = " and ";
				}else{
					conector = " where ";
				}
				sql = sql +conector+ " rol = '"+roles.getRol()+"' ";
			}
			
			if(roles.getModulo()!= null){
				String conector = null;
				if(sql.contains("where")){
					conector = " and ";
				}else{
					conector = " where ";
				}
				sql = sql+conector + " modulo = '"+roles.getModulo()+"' ";
			}
		}
		
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	Roles entidad = new Roles();
        	entidad.setRol(rs.getString("rol"));
        	entidad.setModulo(rs.getString("modulo"));
        	lista.add(entidad);
        }
		return lista;
	}

	public boolean eliminar(Roles roles) throws SQLException{
		String sql = "delete from roles where rol ='"+roles.getRol()+"' and modulo = '"+roles.getModulo()+"'";
		Statement statement = con.ObtenerConexion().createStatement();
		statement.execute(sql);	
		return true;
	}
	
	public List<Roles> listar() throws SQLException{
		List<Roles> lista = new ArrayList<Roles>();
		String sql = "select distinct rol from roles ";		
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	Roles entidad = new Roles();
        	entidad.setRol(rs.getString("rol"));
        	lista.add(entidad);
        }
		return lista;
	}
	
	public boolean insertar(RolesParam param) throws SQLException{
		List<String> listaModulos= param.getModulo();		
		for(String modulo: listaModulos){
			String sql = "insert into roles (rol,modulo) "
				+ "values ( UPPER('"+param.getRol()+"'), '"+modulo+"')";	
			Statement statement = con.ObtenerConexion().createStatement();
			statement.execute(sql);
		}
		return true;				
	}
	
	public boolean modificar(RolesParam param) throws SQLException{
		List<String> listaModulos= param.getModulo();
		String sql0="delete from roles where rol = '"+param.getRol()+"'";
		Statement statement0 = con.ObtenerConexion().createStatement();
		statement0.execute(sql0);
		for(String modulo: listaModulos){
			String sql = "insert into roles (rol,modulo) "
				+ "values ( '"+param.getRol()+"', '"+modulo+"')";	
			Statement statement = con.ObtenerConexion().createStatement();
			statement.execute(sql);
		}
		return true;				
	}
	
	public boolean  eliminar(String rol) throws SQLException{
		String sql ="delete from roles where rol = '"+rol+"'";
		Statement stmt = con.ObtenerConexion().createStatement();
		stmt.execute(sql);
		return true;
	}
		

}
