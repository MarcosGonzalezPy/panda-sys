package com.panda.panda_sys.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Secuencia extends Conexion{	
	
	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
	
    public String getSecuencia(String secuencia) throws SQLException{
    	String seq = null;
    	String sql = " select nextval('"+secuencia+"') as secuencia; ";
    	Statement statement = con.ObtenerConexion().createStatement();
    	rs = statement.executeQuery(sql);
		 while(rs.next()){
			 seq = rs.getString("secuencia");
		 }
    	return seq;
    }

}
