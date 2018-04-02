package com.panda.panda_sys.services.personas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.personas.Clientes;
import com.panda.panda_sys.model.personas.UsuarioSucursal;
import com.panda.panda_sys.util.Conexion;

public class UsuarioSucursalService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;

	public List<UsuarioSucursal> listarUsuarioSucursal(UsuarioSucursal usuarioSucursal) throws SQLException {

		List<UsuarioSucursal> lista = new ArrayList<UsuarioSucursal>();
		String sql = " select * from usuario_sucursal ";
		if (usuarioSucursal.getUsuario() != null) {
			sql = sql + " where  usuario = '" + usuarioSucursal.getUsuario() + "'  ";
		}
		if (usuarioSucursal.getSucursal() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " sucursal = '" + usuarioSucursal.getSucursal() + "' ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			UsuarioSucursal entidad = new UsuarioSucursal();
			entidad.setUsuario(rs.getString("usuario"));
			entidad.setSucursal(rs.getString("sucursal"));
			lista.add(entidad);
		}
		return lista;
	}

	public boolean insertar(String usuario, String sucursal) throws SQLException {
		String sql = "insert into usuario_sucursal (usuario,sucursal) " + "values (('" + usuario + "'),UPPER('"
				+ sucursal + "'));";
		Statement statement = con.ObtenerConexion().createStatement();
		statement.execute(sql);
		return true;
	}

	public boolean eliminar(String usuario) throws SQLException {
		String sql = "delete from usuario_sucursal where usuario = '" + usuario + "'  ";
		Statement stmt = con.ObtenerConexion().createStatement();
		stmt.execute(sql);
		return true;
	}

	public boolean modificar(UsuarioSucursal usuarioSucursal) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			String sql = "update usuario_sucursal set " + "sucursal= ?" + "where usuario = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, usuarioSucursal.getSucursal());
			ps.setString(2, usuarioSucursal.getUsuario());
			ps.execute();

			c.close();
			return true;
		} catch (Exception e) {
			c.close();
			return false;
		}
	}

}