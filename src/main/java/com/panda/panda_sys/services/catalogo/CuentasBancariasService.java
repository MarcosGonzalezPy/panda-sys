package com.panda.panda_sys.services.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.catalogo.CuentasBancarias;
import com.panda.panda_sys.model.catalogo.Servicios;
import com.panda.panda_sys.param.CuentasBancariasParam;
import com.panda.panda_sys.util.Conexion;

public class CuentasBancariasService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;

	public List<CuentasBancarias> listar(CuentasBancarias cuentasBancarias) throws SQLException {
		List<CuentasBancarias> lista = new ArrayList<CuentasBancarias>();
		String sql = "select * from cuenta_bancaria";
		if (cuentasBancarias.getCodigo() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " codigo = '" + cuentasBancarias.getCodigo() + "'  ";
		}
		if (cuentasBancarias.getEstado() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " estado = '" + cuentasBancarias.getEstado() + "' ";
		}

		if (cuentasBancarias.getBanco() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " UPPER(banco) like UPPER('%" + cuentasBancarias.getBanco() + "%') ";
		}

		if (cuentasBancarias.getNumero() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " numero like '%" + cuentasBancarias.getNumero() + "%' ";
		}

		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			CuentasBancarias entidad = new CuentasBancarias();
			entidad.setCodigo(Integer.parseInt(rs.getString("codigo")));
			entidad.setEstado(rs.getString("estado"));
			entidad.setBanco(rs.getString("banco"));
			entidad.setNumero(rs.getString("numero"));
			entidad.setUsuario(rs.getString("usuario"));
			entidad.setFechaCreacion(rs.getDate("fecha_creacion"));
			lista.add(entidad);
		}
		return lista;
	}

	public boolean insertar(CuentasBancarias cuentasBancarias) throws SQLException {
		Date fecha = new Date();
		try { 
			String sql = "insert into cuenta_bancaria ( estado, banco, numero, fecha_creacion, usuario) " + "values ( '"
					+ cuentasBancarias.getEstado() + "', UPPER('" + cuentasBancarias.getBanco() + "') , '"
					+ cuentasBancarias.getNumero() + "' ,  '" + fecha+ "' ,  '" + cuentasBancarias.getUsuario()
					+ "'  );";
			Statement statement = con.ObtenerConexion().createStatement();
			statement.execute(sql);
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return false;
		}

		return true;
	}

	public boolean eliminar(Integer codigo) throws SQLException {
		try {
			String sql = "delete from cuenta_bancaria where codigo = '" + codigo + "'  ";
			Statement stmt = con.ObtenerConexion().createStatement();
			stmt.execute(sql);
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return false;
		}
		return true;
	}

	public boolean modificar(CuentasBancarias cuentasBancarias) throws SQLException {
		Date fecha = new Date();
		Connection c = ObtenerConexion();
		try {
			String sql = "update cuenta_bancaria set estado = ?, banco = UPPER( ? ), numero = ?" + "where codigo = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, cuentasBancarias.getEstado());
			ps.setString(2, cuentasBancarias.getBanco());
			ps.setString(3, cuentasBancarias.getNumero());
			ps.setInt(4, cuentasBancarias.getCodigo());
			ps.execute();
			c.close();
		} catch (Exception e) {
			c.close();
			return false;
		}
		return true;
	}

}
