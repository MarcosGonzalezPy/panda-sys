package com.panda.panda_sys.services.ventas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.Cheques;
import com.panda.panda_sys.util.Conexion;

public class ChequesService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;

	public List<Cheques> listar(Cheques cheque) throws SQLException {
		List<Cheques> lista = new ArrayList<Cheques>();
		String sql = "select * from cheques ";
		if (cheque.getCodigo() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " codigo = " + cheque.getCodigo() + " ";
		}
		if (cheque.getMonto() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " monto " + cheque.getMonto() + " ";
		}
		if (cheque.getNumeroCheque() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " numero_cheque = " + cheque.getNumeroCheque() + " ";
		}
		if (cheque.getEstado() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " estado = ('" + cheque.getEstado() + "') ";
		}

		if (cheque.getBanco() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " banco = ('" + cheque.getBanco() + "') ";
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

		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			Cheques entidad = new Cheques();
			entidad.setCodigo(rs.getInt("codigo"));
			entidad.setMonto(rs.getInt("monto"));
			entidad.setNumeroCheque(rs.getInt("numero_cheque"));
			entidad.setEstado(rs.getString("estado"));
			entidad.setBanco(rs.getString("banco"));
			entidad.setFecha(rs.getDate("fecha"));
			lista.add(entidad);
		}
		return lista;
	}

	public boolean insertar(Cheques cheques) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			String sql = "insert into cheques (monto,numero_cheque,estado,banco,fecha) " + "values (?,?,?,?,?);";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, cheques.getMonto());
			ps.setInt(2, cheques.getNumeroCheque());
			ps.setString(3, cheques.getEstado());
			ps.setString(4, cheques.getBanco());
			ps.setDate(5, cheques.getFecha());
			ps.execute();

			c.commit();
			c.close();
			return true;
		} catch (Exception e) {
			c.close();
			System.out.println("ERROR " + e.getMessage());
			return false;
		}
	}

	public boolean modificar(Cheques cheques) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			String sql = "update cheques set " + "monto=?,numero_cheque=?,estado=?,banco=?,fecha=?"
					+ "where codigo = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, cheques.getMonto());
			ps.setInt(2, cheques.getNumeroCheque());
			ps.setString(3, cheques.getEstado());
			ps.setString(4, cheques.getBanco());
			ps.setDate(5, cheques.getFecha());
			ps.setInt(6, cheques.getCodigo());
			ps.execute();

			c.close();
			return true;
		} catch (Exception e) {
			c.close();
			return false;
		}
	}

	public boolean eliminar(int codigo) throws SQLException {
		String sql = "delete from cheques where codigo =" + codigo;
		Statement statement = con.ObtenerConexion().createStatement();
		statement.execute(sql);
		return true;
	}

}
