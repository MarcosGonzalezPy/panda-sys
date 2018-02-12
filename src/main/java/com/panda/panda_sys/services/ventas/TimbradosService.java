package com.panda.panda_sys.services.ventas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.panda.panda_sys.model.ventas.Timbrados;
import com.panda.panda_sys.util.Conexion;

public class TimbradosService extends Conexion {

	Conexion con;
	ResultSet rs = null;

	public List<Timbrados> listar(Timbrados timbrados) throws SQLException {
		List<Timbrados> lista = new ArrayList<Timbrados>();
		try {

			String sql = "select * from timbrados ";
			Statement st = con.ObtenerConexion().createStatement();
			if (timbrados.getCodigo() != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " codigo=" + timbrados.getCodigo() + " ";
			}
			if (timbrados.getEstado() != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " estado='" + timbrados.getEstado() + "' ";
			}
			rs = st.executeQuery(sql);
			while (rs.next()) { // mientras haya registros
				Timbrados entidad = new Timbrados();
				entidad.setCodigo(rs.getInt("codigo"));
				entidad.setInicioVigencia(rs.getDate("inicio_vigencia"));
				entidad.setFinVigencia(rs.getDate("fin_vigencia"));
				entidad.setEstado(rs.getString("estado"));

				lista.add(entidad);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return lista;
	}

	public boolean insertar(Timbrados timbrados) throws SQLException {
		String sql = "insert into timbrados (codigo, inicio_vigencia, fin_vigencia, estado) " + "values ("
				+ timbrados.getCodigo() + ", '" + timbrados.getInicioVigencia() + "', '"
				+ timbrados.getFinVigencia() + "', '" + timbrados.getEstado() + "')";
		Statement statement = con.ObtenerConexion().createStatement();
		statement.execute(sql);
		return true;
	}

	public boolean eliminar(String codigo) throws SQLException {
		String sql = "delete from timbrados where codigo = '" + codigo + "'  ";
		Statement stmt = con.ObtenerConexion().createStatement();
		stmt.execute(sql);
		return true;
	}

}
