package com.panda.panda_sys.services.personas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.catalogo.CuentasBancarias;
import com.panda.panda_sys.model.catalogo.Servicios;
import com.panda.panda_sys.model.personas.Personas;
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
				sql = sql + " and " + " (p.nombre like '%" + personas.getNombre() + "%'  or apellido like '%"
						+ personas.getNombre() + "%' )";
			}
			if (personas.getRuc() != null) {
				sql = sql + " and " + " (p.ruc = '" + personas.getRuc() + "' or cedula = '" + personas.getRuc() + "') ";
			}

		} else {
			sql = sql + " and " + " p.nombre like '%" + personas.getNombre() + "%'  ";
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
		Personas personas = new Personas();
		try {
			if (proveedores.getCodigo() != null || proveedores.getCedula() != null || proveedores.getRuc() != null
					|| proveedores.getNombre() != null) {
				List<Personas> listaPersonas = new ArrayList<Personas>();

				personas.setCodigo(proveedores.getCodigo());
				personas.setCedula(proveedores.getCedula());
				personas.setRuc(proveedores.getRuc());
				personas.setNombre(proveedores.getNombre());
				listaPersonas = personasService.listarPersonas(personas);

				if (listaPersonas.isEmpty()) {
					personas.setCedula(proveedores.getCedula());
					personas.setNombre(proveedores.getNombre());
					personas.setApellido(proveedores.getApellido());
					personas.setFechaNacimiento(proveedores.getFechaNacimiento());
					personas.setNacionalidad(proveedores.getNacionalidad());
					personas.setPais(proveedores.getPais());
					personas.setCiudad(proveedores.getCiudad());
					personas.setBarrio(proveedores.getBarrio());
					personas.setDireccion(proveedores.getDireccion());
					personas.setCorreoElectronico(proveedores.getCorreoElectronico());
					personas.setRuc(proveedores.getRuc());
					personas.setSexo(proveedores.getSexo());
					personas.setTelefono(proveedores.getTelefono());
					personas.setCelularPrincipal(proveedores.getCelularPrincipal());
					personas.setCelularSecundario(proveedores.getCelularSecundario());
					personas.setEstado(proveedores.getEstado());

					personasService.insertarPersonas(personas);

				}
			}
			String sql = "insert into proveedores (representante_nombre, representante_telefono, representante_celular, pagina_web, obs) "
					+ "values ( UPPER('" + proveedores.getRepresentanteNombre() + "') , (" + proveedores.getTelefono()
					+ "),(" + proveedores.getRepresentanteCelular() + "), (" + proveedores.getPaginaWeb() + " ),("
					+ proveedores.getObs() + ");";
			Statement statement = con.ObtenerConexion().createStatement();
			statement.execute(sql);
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return false;
		}

		return true;
	} 
}
