package com.panda.panda_sys.services.catalogo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.catalogo.Valores;
import com.panda.panda_sys.util.Conexion;

public class ValoresService extends Conexion{
	
	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
    
    public List<Valores> listar(Valores valor) throws SQLException{
		List<Valores> lista = new ArrayList<Valores>();
		String sql = "select * from valores";
		if(valor.getDominio()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql +conector+ " UPPER(dominio) like upper('%"+valor.getDominio()+"%') ";
		}
		if(valor.getValor()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " UPPER(valor) like UPPER('%"+valor.getValor()+"%') ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	Valores entidad = new Valores();
        	entidad.setId(rs.getString("id"));
        	entidad.setDominio(rs.getString("dominio"));
        	entidad.setValor(rs.getString("valor"));        	
        	lista.add(entidad);
        }

		return lista;
	}
    
    public List<Valores> listar() throws SQLException{
		List<Valores> lista = new ArrayList<Valores>();
		String sql = "select * from valores";
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	Valores entidad = new Valores();
        	entidad.setId(rs.getString("id"));
        	entidad.setDominio(rs.getString("dominio"));
        	entidad.setValor(rs.getString("valor"));        	
        	lista.add(entidad);
        }

		return lista;
	}
	
	public boolean eliminar(int id) throws SQLException{
		String sql = "delete from valores where id ="+id;
		Statement statement = con.ObtenerConexion().createStatement();
		statement.execute(sql);	
		return true;
	}
	
	public boolean insertar(String dominio, String valor) throws SQLException{
		String sql = "insert into valores (dominio,valor) "
				+ "values (UPPER('"+dominio+"'),UPPER('"+valor+"'));";
		Statement statement = con.ObtenerConexion().createStatement();
		statement.execute(sql);	
		return true;		
	}
	
	public boolean existe(int id) throws SQLException{
		String valor = null;
		String sql = "select id from valores where id ="+id;
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
