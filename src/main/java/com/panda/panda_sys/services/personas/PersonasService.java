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

import com.panda.panda_sys.model.catalogo.Dominios;
import com.panda.panda_sys.model.personas.Personas;
import com.panda.panda_sys.util.Conexion;

public class PersonasService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;

	public List<Personas> listar(Personas personas, boolean complexQuery) throws SQLException {
		List<Personas> lista = new ArrayList<Personas>();
		String sql = "select * from personas";
		if (complexQuery) {
			if (personas.getNombre() != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " nombre like upper('%" + personas.getNombre() + "%')  or apellido like upper('%"
						+ personas.getNombre() + "%') ";
			}
			if (personas.getRuc() != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " ruc = '" + personas.getRuc() + "' or cedula = '" + personas.getRuc() + "' ";
			}

		} else {
			if (personas.getNombre() != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " nombre like upper('%" + personas.getNombre() + "%')  ";
			}
			if (personas.getRuc() != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " ruc = '" + personas.getRuc() + "' ";
			}
			if (personas.getCedula() != null) {
				String conector = null;
				if (sql.contains("where")) {
					conector = " and ";
				} else {
					conector = " where ";
				}
				sql = sql + conector + " cedula =  " + personas.getCedula() + "  ";
			}

		}

		if (personas.getCodigo() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " codigo = '" + personas.getCodigo() + "' ";
		}

		if (personas.getApellido() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " apellido like upper('%" + personas.getApellido() + "%') ";
		}

		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			Personas entidad = new Personas();
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
			entidad.setSexo(rs.getString("sexo"));
			entidad.setTelefono(rs.getString("telefono"));
			entidad.setCelularPrincipal(rs.getString("celular_principal"));
			entidad.setCelularSecundario(rs.getString("celular_secundario"));
			entidad.setEstado(rs.getString("estado"));

			lista.add(entidad);
		}

		return lista;
	}

	public List<Dominios> listar() throws SQLException {
		List<Dominios> lista = new ArrayList<Dominios>();
		String sql = "select * from dominios";
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			Dominios entidad = new Dominios();
			entidad.setId(Integer.parseInt(rs.getString("id")));
			entidad.setCodigo(rs.getString("codigo"));
			entidad.setDescripcion(rs.getString("descripcion"));
			lista.add(entidad);
		}

		return lista;
	}

	public boolean eliminar(int id) throws SQLException {
		if (existe(id)) {
			String sql = "delete from dominios where id =" + id;
			Statement statement = con.ObtenerConexion().createStatement();
			statement.execute(sql);
			if (existe(id)) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}

	}

	public boolean insertar(String codigo, String descripcion) throws SQLException {
		String sql = "insert into dominios (codigo,descripcion) " + "values ('" + codigo + "','" + descripcion + "');";
		Statement statement = con.ObtenerConexion().createStatement();
		statement.execute(sql);
		return true;
	}

	public boolean existe(int id) throws SQLException {
		String valor = null;
		String sql = "select id from dominios where id =" + id;
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			valor = rs.getString("id");
		}
		if (valor != null) {
			return true;
		} else {
			return false;
		}
	}

	public List<Personas> listarPersonas(Personas personas) throws SQLException {
		List<Personas> lista = new ArrayList<Personas>();
		String sql = "select * from personas ";
		if (personas.getCodigo() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " codigo like  " + personas.getCodigo() + " ";
		}
		if (personas.getCedula() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " cedula  " + personas.getCedula() + " ";
		}
		if (personas.getRuc() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " ruc  " + personas.getRuc() + "  ";
		}
		if (personas.getNombre() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " nombre  like '%" + personas.getNombre() + "%'  ";
		}
		if (personas.getApellido() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " apellido  like '%" + personas.getApellido() + "%'  ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			Personas entidad = new Personas();
			entidad.setCodigo(rs.getString("codigo"));
			entidad.setCedula(rs.getInt("cedula"));
			entidad.setRuc(rs.getString("ruc"));
			entidad.setNombre(rs.getString("nombre"));
			entidad.setApellido(rs.getString("apellido"));
			lista.add(entidad);
		}

		return lista;
	}

	public boolean insertarPersonas(Personas personas) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			String sql = "insert into personas (cedula,  nombre,  apellido,  fecha_nacimiento,  nacionalidad,"
					+ "  pais,  ciudad,  barrio,  direccion,  correo_electronico,  ruc, sexo,  telefono,  celular_principal,  celular_secundario, estado ) "
					+ "values (?,UPPER( ? ),UPPER( ? ),?,?,?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, personas.getCedula());
			ps.setString(2, personas.getNombre());
			ps.setString(3, personas.getApellido());
			ps.setDate(4, personas.getFechaNacimiento());
			ps.setString(5, personas.getNacionalidad());
			ps.setString(6, personas.getPais());
			ps.setString(7, personas.getCiudad());
			ps.setString(8, personas.getBarrio());
			ps.setString(9, personas.getDireccion());
			ps.setString(10, personas.getCorreoElectronico());
			ps.setString(11, personas.getRuc());
			ps.setString(12, personas.getSexo());
			ps.setString(13, personas.getTelefono());
			ps.setString(14, personas.getCelularPrincipal());
			ps.setString(15, personas.getCelularSecundario());
			ps.setString(16, personas.getEstado()); 
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

	public boolean eliminarPersonas(Integer cedula) throws SQLException {
		String sql = "delete from personas where cedula = '" + cedula + "'  ";
		Statement stmt = con.ObtenerConexion().createStatement();
		stmt.execute(sql);
		return true;
	}

	public boolean modificarPersonas(Personas personas) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			String sql = "update personas set "
					+ "cedula= ?,  nombre=UPPER( ? ),  apellido=UPPER( ? ),  fecha_nacimiento=?,  "
					+ "nacionalidad=UPPER( ? ), pais=UPPER( ? ),  ciudad=UPPER( ? ),  barrio=UPPER( ? ),  direccion=UPPER( ? ),  "
					+ "correo_electronico=?,  ruc=?, sexo=UPPER( ? ),  telefono=?,  celular_principal=?,  celular_secundario=?, estado=UPPER( ? )"
					+ "where codigo = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, personas.getCedula());
			ps.setString(2, personas.getNombre());
			ps.setString(3, personas.getApellido());
			ps.setDate(4, personas.getFechaNacimiento());
			ps.setString(5, personas.getNacionalidad());
			ps.setString(6, personas.getPais());
			ps.setString(7, personas.getCiudad());
			ps.setString(8, personas.getBarrio());
			ps.setString(9, personas.getDireccion());
			ps.setString(10, personas.getCorreoElectronico());
			ps.setString(11, personas.getRuc());
			ps.setString(12, personas.getSexo());
			ps.setString(13, personas.getTelefono());
			ps.setString(14, personas.getCelularPrincipal());
			ps.setString(15, personas.getCelularSecundario());
			ps.setString(16, personas.getEstado());
			ps.setInt(17, Integer.parseInt(personas.getCodigo()));
			ps.execute();

			c.close();
			return true;
		} catch (Exception e) {
			c.close();
			return false;
		}
	}
}
