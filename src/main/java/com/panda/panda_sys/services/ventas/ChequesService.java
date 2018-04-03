package com.panda.panda_sys.services.ventas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.Cheques;
import com.panda.panda_sys.util.Conexion;
import com.panda.panda_sys.util.NumberToLetterConverter;
import com.panda.panda_sys.util.Secuencia;

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

		sql += " order by estado desc ";
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			Cheques entidad = new Cheques();

			String glosa = NumberToLetterConverter.convertNumberToLetter(rs.getString("monto"));
			
			entidad.setCodigo(rs.getInt("codigo"));
			entidad.setMonto(rs.getInt("monto"));
			entidad.setNumeroCheque(rs.getInt("numero_cheque"));
			entidad.setEstado(rs.getString("estado"));
			entidad.setBanco(rs.getString("banco"));
			entidad.setFecha(rs.getDate("fecha"));
			entidad.setGlosa(glosa);
			lista.add(entidad);
		}
		return lista;
	}

	public boolean insertar(Cheques cheques) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			String glosa = NumberToLetterConverter.convertNumberToLetter(cheques.getMonto());

			String sql = "insert into cheques (monto,numero_cheque,estado,banco,fecha,glosa) "
					+ "values (?,?,?,?,?,?);";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, cheques.getMonto());
			ps.setInt(2, cheques.getNumeroCheque());
			ps.setString(3, cheques.getEstado());
			ps.setString(4, cheques.getBanco());
			ps.setDate(5, cheques.getFecha());
			ps.setString(6, glosa);
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

			String glosa = NumberToLetterConverter.convertNumberToLetter(cheques.getMonto());
			
			String sql = "update cheques set " + "monto=?,numero_cheque=?,estado=?,banco=?,fecha=?,glosa=?"
					+ "where codigo = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, cheques.getMonto());
			ps.setInt(2, cheques.getNumeroCheque());
			ps.setString(3, cheques.getEstado());
			ps.setString(4, cheques.getBanco());
			ps.setDate(5, cheques.getFecha());
			ps.setString(6, glosa);
			ps.setInt(7, cheques.getCodigo());
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

	public boolean modificarEstadoDeCheques(List<Cheques> listaCheques) throws SQLException {
		Connection c = ObtenerConexion();

		try {
			for (Cheques lista : listaCheques) {
				c.setAutoCommit(false);
				String sql = "update cheques set estado='COBRADO' where codigo = ?";
				PreparedStatement ps = c.prepareStatement(sql);
				ps.setInt(1, lista.getCodigo());
				ps.execute();

				/* Inserta en la tabla Fondos */
				Secuencia secuencia = new Secuencia();
				String secuenciaFondo = secuencia.getSecuencia("fondo_seq");

				String sql2 = "insert into fondo (codigo, monto, tipo, estado, fecha, documento, numero_documento) "
						+ "values (?,?,?,?,current_date,?,?);";

				PreparedStatement ps2 = c.prepareStatement(sql2);
				ps2.setInt(1, Integer.parseInt(secuenciaFondo));
				ps2.setInt(2, lista.getMonto());
				ps2.setString(3, "EFECTIVO");
				ps2.setString(4, "ACTIVO");
				ps2.setString(5, "CHEQUE");
				ps2.setString(6, String.valueOf(lista.getNumeroCheque()));
				ps2.execute();
				c.commit();
				c.close();
			}
		} catch (Exception e) {
			c.close();
			return false;
		}
		return true;
	}

}
