package com.panda.panda_sys.services.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.catalogo.Articulos;
import com.panda.panda_sys.param.ArticulosParam;
import com.panda.panda_sys.util.Conexion;

public class ArticulosService extends Conexion{
	
	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
    
    public List<Articulos> listar(Articulos articulos) throws SQLException{
		List<Articulos> lista = new ArrayList<Articulos>();
		String sql = "select * from articulos";
		if(articulos.getCodigo()!= null && !articulos.getCodigo().equals("")){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql +conector+ " codigo = "+articulos.getCodigo()+" ";
		}
		if(articulos.getDescripcion()!= null && !articulos.getDescripcion().equals("")){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " upper(descripcion) like upper('%"+articulos.getDescripcion()+"%') ";
		}
		if(articulos.getCodigoBarra()!= null && !articulos.getCodigoBarra().equals("")){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " upper(codigo_barra) like upper('%"+articulos.getCodigoBarra()+"%') ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	Articulos entidad = new Articulos();        	
        	entidad.setCodigo(rs.getString("codigo"));
        	entidad.setCodigoBarra(rs.getString("codigo_barra"));
        	entidad.setDescripcion(rs.getString("descripcion"));
        	entidad.setPrecioUnitario(rs.getString("precio_unitario"));
        	entidad.setGrabado(rs.getString("grabado"));
        	entidad.setMarca(rs.getString("marca"));
        	entidad.setModelo(rs.getString("modelo"));
        	entidad.setTipo(rs.getString("tipo"));
        	entidad.setMoneda(rs.getString("moneda"));
        	lista.add(entidad);
        }

		return lista;
	}
    
	public boolean insertar(ArticulosParam articulosParam) throws SQLException{
		String sql = "insert into articulos "
				+ " (codigo_barra,marca,modelo,tipo,descripcion,precio_unitario,grabado,moneda) "
				+ "values ( "
				+ "UPPER('"+articulosParam.getCodigoBarra()+"'),"
				+ "UPPER('"+articulosParam.getMarca()+"'),"
				+ "UPPER('"+articulosParam.getModelo()+"'),"
				+ "UPPER('"+articulosParam.getTipo()+"'),"
				+ "upper ('"+articulosParam.getDescripcion()+"'),"
				+ ""+articulosParam.getPrecioUnitario()+","
				+ ""+articulosParam.getGrabado()+","
				+ "UPPER('"+articulosParam.getMoneda()+"') "
				+ ");";
		Statement statement = con.ObtenerConexion().createStatement();
		statement.execute(sql);	
		/*
		String sql2 = "insert into stock (cantidad, sucursal, maximo, minimo)"
				+ "values ("
				+"0,"
				+"'MATRIZ',"
				+ " "+articulosParam.getCantidadMaxima()+" ,"
				+ articulosParam.getCantidadMinima()+ " "
				+ ");";
		
		Statement statement2 = con.ObtenerConexion().createStatement();
		statement2.execute(sql2);	+
		*/
		return true;		
	}
	
	public boolean eliminar(int codigo) throws SQLException{
		String sql = "delete from articulos where codigo ="+codigo;
		Statement statement = con.ObtenerConexion().createStatement();
		statement.execute(sql);	
		return true;
	}
	
	public boolean modificar(Articulos articulos) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			String sql = "update articulos set codigo_barra = ?, marca = ?, modelo = ?, tipo = ?, descripcion = ?, precio_unitario = ?, grabado= ?, moneda= ?" 
					+ "where codigo = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, articulos.getCodigoBarra());
			ps.setString(2, articulos.getMarca());
			ps.setString(3, articulos.getModelo());
			ps.setString(4, articulos.getTipo());
			ps.setString(5, articulos.getDescripcion());
			ps.setLong(6, Long.parseLong(articulos.getPrecioUnitario()));
			ps.setLong(7, Long.parseLong(articulos.getGrabado()));
			ps.setString(8, articulos.getMoneda());
			ps.setLong(9, Long.parseLong(articulos.getCodigo()));
			ps.execute();
			c.close();
		} catch (Exception e) {
			c.close();
			return false;
		}
		return true;
	}

}
