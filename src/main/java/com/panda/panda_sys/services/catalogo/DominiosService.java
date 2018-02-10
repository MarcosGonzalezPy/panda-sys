package com.panda.panda_sys.services.catalogo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.catalogo.Dominios;
import com.panda.panda_sys.util.Conexion;



public class DominiosService extends Conexion{
    
	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
    
	public List<Dominios> listar(Dominios dominios) throws SQLException{
		List<Dominios> lista = new ArrayList<Dominios>();
		String sql = "select * from dominios";
		if(dominios.getCodigo()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql +conector+ " UPPER(codigo) like upper('%"+dominios.getCodigo()+"%') ";
		}
		if(dominios.getDescripcion()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " UPPER(descripcion) like UPPER('%"+dominios.getDescripcion()+"%') ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	Dominios entidad = new Dominios();
        	entidad.setId(Integer.parseInt(rs.getString("id")));
        	entidad.setCodigo(rs.getString("codigo"));
        	entidad.setDescripcion(rs.getString("descripcion"));        	
        	lista.add(entidad);
        }

		return lista;
	}
	
	public List<Dominios> listar() throws SQLException{
		List<Dominios> lista = new ArrayList<Dominios>();
		String sql = "select * from dominios";		
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	Dominios entidad = new Dominios();
        	entidad.setId(Integer.parseInt(rs.getString("id")));
        	entidad.setCodigo(rs.getString("codigo"));
        	entidad.setDescripcion(rs.getString("descripcion"));        	
        	lista.add(entidad);
        }

		return lista;
	}
	
	public boolean eliminar(int id) throws SQLException{
		if(existe(id)){
			String sql = "delete from dominios where id ="+id;
			Statement statement = con.ObtenerConexion().createStatement();
			statement.execute(sql);
			if(existe(id)){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}

	}
	
	public boolean insertar(String codigo, String descripcion) throws SQLException{
		String sql = "insert into dominios (codigo,descripcion) "
				+ "values ( upper('"+codigo+"'),upper('"+descripcion+"'));";
		Statement statement = con.ObtenerConexion().createStatement();
		statement.execute(sql);	
		return true;		
	}
	
	public boolean existe(int id) throws SQLException{
		String valor = null;
		String sql = "select id from dominios where id ="+id;
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		 while(rs.next()){
			 valor = rs.getString("id");
		 }
		if(valor!= null){
			return true;
		}else{
			return false;
		}
	}

}
