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

import com.panda.panda_sys.model.personas.Proveedores;
import com.panda.panda_sys.util.Conexion;

public class ProveedoresService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;
	private PersonasService personasService;

	public List<Proveedores> listar(Proveedores personas, boolean complexQuery) throws SQLException {
		List<Proveedores> lista = new ArrayList<Proveedores>();
		String sql = " select * from personas p, proveedores pr  " + " where p.codigo = pr.codigo ";
		if (complexQuery) {
			if (personas.getNombre() != null) {
				sql = sql + " and " + " (p.nombre like  upper('%" + personas.getNombre()
						+ "%')  or apellido like  upper('%" + personas.getNombre() + "%') )";
			}
			if (personas.getRuc() != null) {
				sql = sql + " and " + " (p.ruc = '" + personas.getRuc() +  "' ";
				if(!personas.getRuc().contains("-")){
					sql+=" or cedula = '" + personas.getRuc() +"' ";
				}
				sql+=")";
			}

		} else {
			sql = sql + " and " + " p.nombre like  upper('%" + personas.getNombre() + "%' ) ";
		}
		if (personas.getRepresentanteNombre() != null) {
			sql = sql + " and " + " (pr.representante_nombre like  upper('%" + personas.getRepresentanteNombre()
					+ "%' ) )";
		}

		if (personas.getCodigo() != null) {
			sql = sql + " and " + " p.codigo = '" + personas.getCodigo() + "' ";
		}

		if (personas.getApellido() != null) {
			sql = sql + " and " + " p.apellido like '%" + personas.getApellido() + "%' ";
		}

		if (personas.getRuc() != null) {
			sql = sql + " and " + " p.ruc like '%" + personas.getRuc() + "%' ";
		} 

		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			Proveedores entidad = new Proveedores();
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
			entidad.setRepresentanteNombre(rs.getString("representante_nombre"));
			entidad.setRepresentanteTelefono(rs.getString("representante_telefono"));
			entidad.setRepresentanteCelular(rs.getString("representante_celular"));
			entidad.setPaginaWeb(rs.getString("pagina_web"));
			entidad.setObs(rs.getString("obs"));
			lista.add(entidad);
		}

		return lista;
	}

	public boolean insertar(Proveedores proveedores) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			String sql = "insert into proveedores (codigo, representante_nombre, representante_telefono, representante_celular, pagina_web, obs) "
					+ "values (?,UPPER(?),?,?,?,UPPER(?));";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(proveedores.getCodigo()));
			ps.setString(2, proveedores.getRepresentanteNombre());
			ps.setString(3, proveedores.getRepresentanteTelefono());
			ps.setString(4, proveedores.getRepresentanteCelular());
			ps.setString(5, proveedores.getPaginaWeb());
			ps.setString(6, proveedores.getObs());
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

	public boolean eliminar(Integer codigo) throws SQLException {
		String sql = "delete from proveedores where codigo = '" + codigo + "'  ";
		Statement stmt = con.ObtenerConexion().createStatement();
		stmt.execute(sql);
		return true;
	}

	public boolean modificar(Proveedores proveedores) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			String sql = "update proveedores set " + "  representante_nombre=UPPER( ? ),  representante_telefono= ? ,"
					+ "  representante_celular=?, pagina_web=? ,  obs=UPPER( ? )" + "  where codigo = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, proveedores.getRepresentanteNombre());
			ps.setString(2, proveedores.getRepresentanteTelefono());
			ps.setString(3, proveedores.getRepresentanteCelular());
			ps.setString(4, proveedores.getPaginaWeb());
			ps.setString(5, proveedores.getObs());
			ps.setInt(6, Integer.parseInt( proveedores.getCodigo()));
			ps.execute();

			c.close();
			return true;
		} catch (Exception e) {
			c.close();
			return false;
		}
	}
}
