package com.panda.panda_sys.services.reportes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ser.StdSerializers.UtilDateSerializer;

import com.panda.panda_sys.model.personas.Proveedores;
import com.panda.panda_sys.model.reportes.ReporteParametros;
import com.panda.panda_sys.model.reportes.Reportes;
import com.panda.panda_sys.model.ventas.FacturaDetalle;
import com.panda.panda_sys.param.reportes.ReportesCompuesto;
import com.panda.panda_sys.services.personas.PersonasService;
import com.panda.panda_sys.util.Conexion;
import com.panda.panda_sys.util.Secuencia;

public class ReportesService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;

	public List<Reportes> listarReportes(String modulo) {
		List<Reportes> lista = new ArrayList<Reportes>();
		try {
			Connection c = ObtenerConexion();
			String sql = " select * from reportes where modulo = ? ";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, modulo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
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
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}

	public List<Reportes> listarABM(Reportes reportes) throws SQLException {
		List<Reportes> lista = new ArrayList<Reportes>();
		String sql = "select * from reportes";
		String conector = null;

		if (reportes.getModulo() != null) {
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " modulo like  upper('%" + reportes.getModulo() + "%' ) ";
		}
		if (reportes.getPath() != null) {
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " ( path like ('%" + reportes.getNombre() + "%') )";
		}
		if (reportes.getEstado() != null) {
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " ( estado like ('%" + reportes.getEstado() + "%') )";
		}
		if (reportes.getNombre() != null) {
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " ( nombre like ('%" + reportes.getNombre() + "%') )";
		}
		if (reportes.getDescripcion() != null) {
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " descripcion like ('%" + reportes.getDescripcion() + "%') ";
		}

		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			Reportes entidad = new Reportes();
			entidad.setId(rs.getLong("id"));
			entidad.setModulo(rs.getString("modulo"));
			entidad.setPath(rs.getString("path"));
			entidad.setEstado(rs.getString("estado"));
			entidad.setNombre(rs.getString("nombre"));
			entidad.setDescripcion(rs.getString("descripcion"));
			lista.add(entidad);
		}

		return lista;
	}

	public boolean insertarABM(Reportes reportes) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			String sql = "insert into reportes (modulo,path,estado,nombre,descripcion) "
					+ "values (UPPER(?),?,UPPER(?),UPPER(?),UPPER(?));";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, reportes.getModulo());
			ps.setString(2, reportes.getPath());
			ps.setString(3, reportes.getEstado());
			ps.setString(4, reportes.getNombre());
			ps.setString(5, reportes.getDescripcion());
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

	public boolean modificarABM(Reportes reportes) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			String sql = "update reportes set "
					+ "  modulo=UPPER( ? ),  path= ? ,  estado=UPPER( ? ), nombre=UPPER( ? ),  descripcion=UPPER( ? )"
					+ "  where id = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, reportes.getModulo());
			ps.setString(2, reportes.getPath());
			ps.setString(3, reportes.getEstado());
			ps.setString(4, reportes.getNombre());
			ps.setString(5, reportes.getDescripcion());
			ps.setLong(6, reportes.getId());
			ps.execute();

			c.close();
			return true;
		} catch (Exception e) {
			c.close();
			return false;
		}
	}

	public boolean eliminarABM(Long id) throws SQLException {
		String sql = "delete from reportes where id = '" + id + "'  ";
		Statement stmt = con.ObtenerConexion().createStatement();
		stmt.execute(sql);
		return true;
	}

	public boolean insertarReportesCompuestos(ReportesCompuesto reportesCompuesto) throws SQLException {
		Reportes rep = reportesCompuesto.getReportes();
		List<ReporteParametros> listaParametros = reportesCompuesto.getListaParametros();

		try {
			Connection c = ObtenerConexion();
			c.setAutoCommit(false);
			Secuencia secuenciaService = new Secuencia();
			String secuencia = secuenciaService.getSecuencia("reportes_id_seq");

			String sql = "insert into reportes (modulo,path,estado,nombre,descripcion,id) "
					+ "values ( ? ,?, ? , ? , ? ,?);";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, rep.getModulo());
			ps.setString(2, rep.getPath());
			ps.setString(3, rep.getEstado());
			ps.setString(4, rep.getNombre());
			ps.setString(5, rep.getDescripcion());
			ps.setLong(6, Long.parseLong(secuencia));
			ps.execute();

			if (listaParametros != null) {
				for (ReporteParametros det : listaParametros) {
					String sql2 = "insert into reporte_parametros(reporte_id, parametro, estado, tipo_dato)"
							+ " values(?,?,?,?)";
					PreparedStatement ps2 = c.prepareStatement(sql2);
					ps2.setLong(1, Long.parseLong(secuencia));
					ps2.setString(2, det.getParametro());
					ps2.setString(3, rep.getEstado());
					ps2.setString(4, det.getTipoDato());
					ps2.execute();
				}
			}

			c.commit();
			c.close();
			return true;
		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
			return false;
		}
	}

}
