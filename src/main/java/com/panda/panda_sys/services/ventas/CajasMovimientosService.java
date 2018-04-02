package com.panda.panda_sys.services.ventas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.ventas.CajasMovimientos;
import com.panda.panda_sys.util.Conexion;
import com.panda.panda_sys.util.PandaException;

public class CajasMovimientosService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;

	public String insertar(CajasMovimientos cajasMovimientos) throws SQLException {
		try {
			if (listarCodigoCaja(cajasMovimientos.getCodigo()).size() > 0 && listarPorUsuario(cajasMovimientos.getUsuario()).size() > 0) {
				throw new PandaException("Debe cerrar la caja, antes de habilitarla. El usuario ya esta asignado a una caja.");
			}
			if (listarCodigoCaja(cajasMovimientos.getCodigo()).size() > 0) {
				throw new PandaException("Debe cerrar la caja, antes de habilitarla.");
			}
			if (listarPorUsuario(cajasMovimientos.getUsuario()).size() > 0) {
				throw new PandaException("El usuario ya esta asignado a una caja.");
			}
			String sql = "insert into cajas_movimientos (estado,codigo_caja,usuario,monto_apertura, usuario_creacion, fecha_apertura)"
					+ "values (upper('" + cajasMovimientos.getEstado() + "')," + cajasMovimientos.getCodigoCaja()
					+ " , " + " '" + cajasMovimientos.getUsuario() + "', " + cajasMovimientos.getMontoApertura() + ", "
					+ " '" + cajasMovimientos.getUsuarioCreacion() + "', current_date);";
			Statement statement = con.ObtenerConexion().createStatement();

			statement.execute(sql);
			return "S";
		} catch (Exception e) {
			return e.getMessage().toString();
		}
	}

	public List<CajasMovimientos> listar(CajasMovimientos cajasMovimientos) {
		List<CajasMovimientos> lista = new ArrayList<CajasMovimientos>();
		try {
			String sql = "select * from cajas_movimientos ";
			if (cajasMovimientos.getCodigo() != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " codigo = " + cajasMovimientos.getCodigo() + " ";
			}
			if (cajasMovimientos.getEstado() != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " UPPER(estado) = UPPER('" + cajasMovimientos.getEstado() + "') ";
			}
			Statement statement = con.ObtenerConexion().createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				CajasMovimientos entidad = new CajasMovimientos();
				entidad.setCodigo(rs.getInt("codigo"));
				entidad.setEstado(rs.getString("estado"));
				entidad.setCodigoCaja(rs.getString("codigo_caja"));
				entidad.setUsuario(rs.getString("usuario"));
				entidad.setMontoApertura(rs.getInt("monto_apertura"));
				entidad.setUsuarioCreacion(rs.getString("usuario_creacion"));
				entidad.setFechaApertura(rs.getDate("fecha_apertura"));
				entidad.setFechaCierre(rs.getDate("fecha_cierre"));
				lista.add(entidad);
			}
			return lista;
		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
		}
		return lista;
	}

	public List<CajasMovimientos> listarCodigoCaja(Integer codigo) {
		List<CajasMovimientos> lista = new ArrayList<CajasMovimientos>();
		try {
			String sql = "select * from cajas_movimientos a, cajas b where b.codigo = a.codigo_caja and a.estado = 'ABIERTO' ";

			if (codigo != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " codigo = " + codigo + " ";
			}
			Statement statement = con.ObtenerConexion().createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				CajasMovimientos entidad = new CajasMovimientos();
				entidad.setCodigo(rs.getInt("codigo"));
				entidad.setUsuario(rs.getString("usuario"));
				lista.add(entidad);
			}
			return lista;
		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
		}
		return lista;
	}

	public List<CajasMovimientos> listarPorUsuario(String usuario) {
		List<CajasMovimientos> lista = new ArrayList<CajasMovimientos>();
		try {
			String sql = "select * from cajas_movimientos a, cajas b where b.codigo = a.codigo_caja and a.estado = 'ABIERTO' ";

			if (usuario != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " usuario = '" + usuario + "'  ";
			}
			Statement statement = con.ObtenerConexion().createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				CajasMovimientos entidad = new CajasMovimientos();
				entidad.setCodigo(rs.getInt("codigo"));
				entidad.setUsuario(rs.getString("usuario"));
				lista.add(entidad);
			}
			return lista;
		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
		}
		return lista;
	}

}
