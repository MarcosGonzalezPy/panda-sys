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

import com.panda.panda_sys.model.personas.Clientes;
import com.panda.panda_sys.util.Conexion;

public class ClientesService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;

	public boolean insertar(Clientes cliente) throws SQLException {
		String sql = "insert into personas (cedula,nombre,apellido,fecha_nacimiento,nacionalidad,pais,ciudad,barrio,direccion, "
				+ " correo_electronico,ruc,sexo,telefono,celular_principal,estado) " + "values ( " + " "
				+ cliente.getCedula() + " ," + "UPPER ('" + cliente.getNombre() + "')," + "upper ('"
				+ cliente.getApellido() + "')," + "'" + cliente.getFechaNacimiento() + "'," + "'"
				+ cliente.getNacionalidad() + "'," + "'" + cliente.getPais() + "'," + "'" + cliente.getCiudad() + "',"
				+ "'" + cliente.getBarrio() + "'," + "'" + cliente.getDireccion() + "'," + "'"
				+ cliente.getCorreoElectronico() + "'," + "'" + cliente.getRuc() + "'," + "'" + cliente.getSexo() + "',"
				+ "'" + cliente.getTelefono() + "'," + "'" + cliente.getCelularPrincipal() + "'," + "'ACTIVO');";
		Statement statement = con.ObtenerConexion().createStatement();
		statement.execute(sql);
		return true;
	}
	
	public boolean insertarClientes(Clientes clientes) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			String sql = "insert into clientes (codigo,razon_social,limite_credito) "
					+ "values (?,UPPER(?),?);";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(clientes.getCodigo()));
			ps.setString(2, clientes.getRazonSocial());
			ps.setString(3, clientes.getLimiteCredito()); 
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

	public List<Clientes> listar(Clientes clientes, boolean complexQuery) throws SQLException {
		List<Clientes> lista = new ArrayList<Clientes>();
		String sql = " select * from clientes c, personas p where c.codigo = p.codigo ";
		if (complexQuery) {
			if (clientes.getNombre() != null) {
				sql = sql + " and (upper(nombre) || upper(apellido)  like '%" + clientes.getNombre() + "%' )";
			}
			if (clientes.getRuc() != null) {
				sql = sql + " and  (p.ruc = '" + clientes.getRuc() + "' or cedula = '" + clientes.getRuc() + "') ";
			}
		} else {
			if (clientes.getNombre() != null) {
				sql = sql + "and  p.nombre like '%" + clientes.getNombre() + "%'  ";
			}
			if (clientes.getRuc() != null) {
				sql = sql + " p.ruc = '" + clientes.getRuc() + "' ";
			}
		}
		if (clientes.getCodigo() != null) {
			sql = sql + " and  p.codigo = '" + clientes.getCodigo() + "' ";
		}
		if (clientes.getApellido() != null) {
			sql = sql + " p.apellido like '%" + clientes.getApellido() + "%' ";
		}

		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			Clientes entidad = new Clientes();
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
			entidad.setRazonSocial(rs.getString("razon_social"));
			entidad.setLimiteCredito(rs.getString("limite_credito"));
			lista.add(entidad);
		}

		return lista;
	}

	public List<Clientes> listar(Clientes clientes) throws SQLException {
		List<Clientes> lista = new ArrayList<Clientes>();
		String sql = " select * from clientes c, personas p where c.codigo = p.codigo ";

		if (clientes.getRuc() != null) {
			sql = sql + " and " + " (p.ruc = '" + clientes.getRuc() + "' ";
			if (!clientes.getRuc().contains("-")) {
				sql += " or cedula = '" + clientes.getRuc() + "' ";
			}
			sql += ")";
		}
		if (clientes.getNombre() != null) {
			sql = sql + " and (upper(nombre) || upper(apellido)  like '%" + clientes.getNombre() + "%' )";
		}

		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			Clientes entidad = new Clientes();
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
			entidad.setRazonSocial(rs.getString("razon_social"));
			entidad.setLimiteCredito(rs.getString("limite_credito"));
			lista.add(entidad);
		}

		return lista;
	}

}
