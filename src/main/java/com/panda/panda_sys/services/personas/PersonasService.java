package com.panda.panda_sys.services.personas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.catalogo.Dominios;
import com.panda.panda_sys.model.personas.Personas;
import com.panda.panda_sys.util.Conexion;

public class PersonasService extends Conexion{
	
	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
    
	public List<Personas> listar(Personas personas, boolean complexQuery) throws SQLException{
		List<Personas> lista = new ArrayList<Personas>();
		String sql = "select * from personas";
		if(complexQuery){
			if(personas.getNombre()!= null){
				String conector = null;
				if(sql.contains("where")){
					conector = " and ";
				}else{
					conector = " where ";
				}
				sql = sql+conector + " nombre like '%"+personas.getNombre()+"%'  or apellido like '%"+personas.getNombre()+"%' ";
			}
			if(personas.getRuc()!= null){
				String conector = null;
				if(sql.contains("where")){
					conector = " and ";
				}else{
					conector = " where ";
				}
				sql = sql+conector + " ruc = '"+personas.getRuc()+"' or cedula = '"+personas.getRuc()+"' ";
			}
			
		}else{
			if(personas.getNombre()!= null){
				String conector = null;
				if(sql.contains("where")){
					conector = " and ";
				}else{
					conector = " where ";
				}
				sql = sql+conector + " nombre like '%"+personas.getNombre()+"%'  ";
			}
			
		}
		
		if(personas.getCodigo()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql +conector+ " codigo = '"+personas.getCodigo()+"' ";
		}
		
		if(personas.getApellido()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " apellido like '%"+personas.getApellido()+"%' ";
		}
		
		if(personas.getRuc()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " ruc like '%"+personas.getRuc()+"%' ";
		}
		
		
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	Personas entidad = new Personas();
        	entidad.setCodigo(rs.getString("codigo"));
        	entidad.setCedula(rs.getString("cedula"));
        	entidad.setNombre(rs.getString("nombre"));
        	entidad.setApellido(rs.getString("apellido"));
        	entidad.setRuc(rs.getString("ruc"));
        	//entidad.setFechaNacimiento(new Date(rs.getString("fecha_nacimiento")));
        	
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
				+ "values ('"+codigo+"','"+descripcion+"');";
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
