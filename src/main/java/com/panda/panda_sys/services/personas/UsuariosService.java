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

import com.panda.panda_sys.model.personas.UsuarioSucursal;
import com.panda.panda_sys.model.personas.Usuarios;
import com.panda.panda_sys.util.Conexion;

public class UsuariosService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;

	public boolean insertar(Usuarios usuarios) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			String sql = "insert into usuarios (codigo, usuario, contrasenha, rol, resetear) "
					+ "values (?, ? ,?,UPPER(?),UPPER(?));";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(usuarios.getCodigo()));
			ps.setString(2, usuarios.getUsuario());
			ps.setString(3, usuarios.getContrasenha());
			ps.setString(4, usuarios.getRol());
			ps.setString(5, usuarios.getResetear());
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

	public List<Usuarios> listar(Usuarios personas, boolean complexQuery) throws SQLException {
		List<Usuarios> lista = new ArrayList<Usuarios>();
		String sql = " select * from personas p, usuarios u" + "  where p.codigo = u.codigo";
		if (complexQuery) {
			if (personas.getNombre() != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " (p.nombre like '%" + personas.getNombre() + "%'  or apellido like '%"
						+ personas.getNombre() + "%' )";

				/*
				 * sql = sql + conector + " (p.nombre like  upper('%" +
				 * personas.getNombre() + "%')  or apellido like  upper('%" +
				 * personas.getNombre() + "%') )";
				 */
			}
			if (personas.getRuc() != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " (p.ruc = '" + personas.getRuc() + "' or cedula = '" + personas.getRuc()
						+ "') ";
			} else if (personas.getCodigo() != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " p.codigo = '" + personas.getCodigo() + "' ";
			}

		} else if (personas.getCodigo() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " p.codigo = '" + personas.getCodigo() + "' ";
		}

		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			Usuarios entidad = new Usuarios();
			entidad.setCodigo(rs.getString("codigo"));
			entidad.setCedula(rs.getInt("cedula"));
			entidad.setNombre(rs.getString("nombre"));
			entidad.setApellido(rs.getString("apellido"));
			entidad.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
			entidad.setNacionalidad(rs.getString("nacionalidad"));
			entidad.setPais(rs.getString("pais"));
			entidad.setCiudad(rs.getString("ciudad"));
			entidad.setBarrio(rs.getString("barrio"));
			entidad.setDireccion(rs.getString("direccion"));
			entidad.setCorreoElectronico(rs.getString("correo_electronico"));
			entidad.setRuc(rs.getString("ruc"));
			entidad.setSexo(rs.getString("sexo").trim());
			entidad.setTelefono(rs.getString("telefono"));
			entidad.setCelularPrincipal(rs.getString("celular_principal"));
			entidad.setCelularSecundario(rs.getString("celular_secundario"));
			entidad.setEstado(rs.getString("estado"));
			entidad.setUsuario(rs.getString("usuario"));
			entidad.setContrasenha(rs.getString("contrasenha"));
			entidad.setRol(rs.getString("rol"));
			entidad.setResetear(rs.getString("resetear"));
			lista.add(entidad);
		}

		return lista;
	}

	public List<Usuarios> listarPorCodigo(Usuarios personas) throws SQLException {
		List<Usuarios> lista = new ArrayList<Usuarios>();
		String sql = " select * from personas p, usuarios u" + "  where p.codigo = u.codigo";
		if (personas.getCodigo() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " p.codigo = '" + personas.getCodigo() + "' ";
		}

		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			Usuarios entidad = new Usuarios();
			entidad.setCodigo(rs.getString("codigo"));
			entidad.setCedula(rs.getInt("cedula"));
			entidad.setNombre(rs.getString("nombre"));
			entidad.setApellido(rs.getString("apellido"));
			entidad.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
			entidad.setNacionalidad(rs.getString("nacionalidad"));
			entidad.setPais(rs.getString("pais"));
			entidad.setCiudad(rs.getString("ciudad"));
			entidad.setBarrio(rs.getString("barrio"));
			entidad.setDireccion(rs.getString("direccion"));
			entidad.setCorreoElectronico(rs.getString("correo_electronico"));
			entidad.setRuc(rs.getString("ruc"));
			entidad.setSexo(rs.getString("sexo").trim());
			entidad.setTelefono(rs.getString("telefono"));
			entidad.setCelularPrincipal(rs.getString("celular_principal"));
			entidad.setCelularSecundario(rs.getString("celular_secundario"));
			entidad.setEstado(rs.getString("estado"));
			entidad.setUsuario(rs.getString("usuario"));
			entidad.setContrasenha(rs.getString("contrasenha"));
			entidad.setRol(rs.getString("rol"));
			entidad.setResetear(rs.getString("resetear"));
			lista.add(entidad);
		}

		return lista;
	}

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

	public boolean modificar(Usuarios usuarios) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			String sql = "update usuarios set  rol=UPPER( ? )  where codigo = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, usuarios.getRol());
			ps.setInt(2, Integer.parseInt(usuarios.getCodigo()));
			ps.execute();

			c.close();
			return true;
		} catch (Exception e) {
			c.close();
			return false;
		}
	}

	public boolean eliminar(Integer codigo) throws SQLException {
		String sql = "delete from usuarios where codigo = '" + codigo + "'  ";
		Statement stmt = con.ObtenerConexion().createStatement();
		stmt.execute(sql);
		return true;
	}

}
