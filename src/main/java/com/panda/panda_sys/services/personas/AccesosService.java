package com.panda.panda_sys.services.personas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.catalogo.Dominios;
import com.panda.panda_sys.model.personas.Accesos;
import com.panda.panda_sys.util.Conexion;

public class AccesosService extends Conexion{
	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
    
	public Accesos login(String usuario, String pass) throws SQLException{
		String sql = "select * from accesos where usuario ='"+usuario+"' and contrasenha= '"+pass+"'";
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		Accesos entidad = null;
        while(rs.next()){
        	entidad = new Accesos();
        	entidad.setUsuario(rs.getString("usuario"));
        	entidad.setContrasenha(rs.getString("contrasenha")); 
        	entidad.setRol(rs.getString("rol"));
        }
        String sql2 = "select * from roles where rol = '"+entidad.getRol()+"'";
        ResultSet rs2 = statement.executeQuery(sql2);
        List<String> modulos = new ArrayList<String>();
        while(rs2.next()){
        	modulos.add(rs2.getString("modulo"));
        }
        entidad.setModulos(modulos);
		return entidad;
	}

}
