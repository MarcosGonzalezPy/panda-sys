package com.panda.panda_sys.services.ventas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.Cheque;
import com.panda.panda_sys.util.Conexion;

public class ChequeService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;

	public List<Cheque> listar(Cheque cheque) throws SQLException {
		List<Cheque> lista = new ArrayList<Cheque>();
		String sql = "select * from cajas ";
		if (cheque.getCodigo() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " codigo = " + cheque.getCodigo() + " ";
		}
		if (cheque.getCodigoCuentaBancaria() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " codigo_cuenta_bancaria like ('%" + cheque.getCodigoCuentaBancaria() + "%')";
		}
		if (cheque.getCliente() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " cliente = ('" + cheque.getCliente() + "') ";
		}
		if (cheque.getFecha() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " fecha = ('" + cheque.getFecha() + "') ";
		}
		if (cheque.getFechaCobro() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " fecha_cobro = ('" + cheque.getFechaCobro() + "') ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			Cheque entidad = new Cheque();
			entidad.setCodigo(rs.getInt("codigo"));
			entidad.setCodigoCuentaBancaria(rs.getInt("codigoCuentaBancaria"));
			entidad.setCliente(rs.getInt("cliente"));
			entidad.setFecha(rs.getDate("fecha"));
			entidad.setFechaCobro(rs.getDate("fechaCobro"));
			lista.add(entidad);
		}
		return lista;
	}

	public boolean insertar(Cheque cheque) throws SQLException {
		try {
			String sql = "insert into cheque (codigo,codigo_cuenta_bancaria, cliente, monto, glosa, fecha, fecha_cobro)"
					+ "values (" + cheque.getCodigo() + ",('" + cheque.getCodigoCuentaBancaria() + "'), ('"
					+ cheque.getCliente() + "')," + " ('" + cheque.getMonto() + "'), ('" + cheque.getGlosa() + "'), ('"
					+ cheque.getFecha() + "'), ('" + cheque.getFechaCobro() + "'));";
			Statement statement = con.ObtenerConexion().createStatement();
			statement.execute(sql);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean eliminar(int codigo) throws SQLException {
		if (existe(codigo)) {
			String sql = "delete from cheque where codigo =" + codigo;
			Statement statement = con.ObtenerConexion().createStatement();
			statement.execute(sql);
			if (existe(codigo)) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public boolean existe(int codigo) throws SQLException {
		String valor = null;
		String sql = "select codigo from cheque where codigo =" + codigo;
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			valor = rs.getString("codigo");
		}
		if (valor != null) {
			return true;
		} else {
			return false;
		}
	}

}
