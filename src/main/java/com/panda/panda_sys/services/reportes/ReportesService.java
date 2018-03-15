package com.panda.panda_sys.services.reportes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.panda.panda_sys.model.reportes.Reportes;
import com.panda.panda_sys.util.Conexion;

public class ReportesService extends Conexion {

	public List<Reportes> listarReportes(String modulo){
		List<Reportes> lista = new ArrayList	<Reportes>();
		try {
			Connection c =ObtenerConexion();
			String sql = " select * from reportes where modulo = ? ";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, modulo);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Reportes entidad = new Reportes();
				entidad.setId(rs.getLong("id"));
				entidad.setModulo(rs.getString("modulo"));
				entidad.setPath(rs.getString("path"));
				entidad.setEstado(rs.getString("estado"));
				entidad.setNombre(rs.getString("nombre"));
				entidad.setDescripcion(rs.getString("descripcion"));
				lista.add(entidad);
			}			
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		return lista;
	}
}
