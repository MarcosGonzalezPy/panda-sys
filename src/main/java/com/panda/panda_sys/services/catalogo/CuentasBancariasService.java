package com.panda.panda_sys.services.catalogo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.catalogo.CuentasBancarias;
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
			sql = sql + conector + " UPPER(estado) like UPPER('%" + cuentasBancarias.getEstado() + "%') ";
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
			sql = sql + conector + " UPPER(numero) like UPPER('%" + cuentasBancarias.getNumero() + "%') ";
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

}
