package com.panda.panda_sys.services.inventario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.inventario.Inventario;
import com.panda.panda_sys.model.inventario.InventarioParam;
import com.panda.panda_sys.model.inventario.RegistroAjuste;
import com.panda.panda_sys.model.inventario.RegistroInventario;
import com.panda.panda_sys.model.personas.Usuarios;
import com.panda.panda_sys.param.RolesParam;
import com.panda.panda_sys.util.Conexion;

public class InventarioService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;

	public boolean insertar(List<RegistroInventario> lista) throws SQLException {
		for (RegistroInventario param : lista) {
			boolean existe = false;
			Integer cantidad = null;
			String sql1 = " select * from inventario where codigo = " + param.getCodigoArticulo() + " and sucursal = '"
					+ param.getSucursal() + "'; ";
			Statement stmt1 = con.ObtenerConexion().createStatement();
			rs = stmt1.executeQuery(sql1);
			while (rs.next()) {
				existe = true;
				cantidad = rs.getInt("cantidad");
			}
			if (existe) {
				cantidad = cantidad + param.getCantidad();
				String sql3 = " update inventario set cantidad=" + cantidad + " where codigo = "
						+ param.getCodigoArticulo() + " and sucursal = '" + param.getSucursal() + "'; ";

				Statement stmt3 = con.ObtenerConexion().createStatement();
				stmt3.execute(sql3);
			} else {
				String sql2 = " insert into inventario (codigo, cantidad, sucursal, maximo, minimo)" + " values ("
						+ param.getCodigoArticulo() + "," + param.getCantidad() + ",'" + param.getSucursal()
						+ "', 0,0)";
				Statement stmt2 = con.ObtenerConexion().createStatement();
				stmt2.execute(sql2);
			}
			String sql4 = " insert into registro_inventario (id, codigo_articulo, cantidad, sucursal, fecha, usuario, precio, iva) values ("
					+ " " + param.getId() + ", " + " " + param.getCodigoArticulo() + ", " + param.getCantidad() + ", '"
					+ param.getSucursal() + "', current_date, '" + param.getUsuario() + "', " + param.getPrecio() + ", "
					+ param.getIva() + ")";
			Statement stmt4 = con.ObtenerConexion().createStatement();
			stmt4.execute(sql4);
		}
		return true;
	}

	public List<InventarioParam> listarInventario(InventarioParam param) throws SQLException {
		List<InventarioParam> lista = new ArrayList<InventarioParam>();

		String sql = " select  i.codigo, a.descripcion, i.cantidad, i.sucursal, i.maximo, i.minimo from inventario i, articulos a where i.codigo = a.codigo ";

		if (param.getCodigo() != null) {
			sql = sql + " and i.codigo = " + param.getCodigo() + "  ";
		}
		if (param.getSucursal() != null) {
			sql = sql + " and i.sucursal = '" + param.getSucursal() + "' ";
		}
		if (param.getDescripcion() != null) {
			sql = sql + " and a.descripcion like '%" + param.getDescripcion() + "%' ";
		}

		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			InventarioParam entidad = new InventarioParam();
			entidad.setCodigo(rs.getInt("codigo"));
			entidad.setDescripcion(rs.getString("descripcion"));
			entidad.setCantidad(rs.getInt("cantidad"));
			entidad.setSucursal(rs.getString("sucursal"));
			entidad.setMinimo(rs.getInt("minimo"));
			entidad.setMaximo(rs.getInt("maximo"));
			lista.add(entidad);
		}
		return lista;
	}

	public String registrarAjuste(List<RegistroAjuste> listaAjuste) throws SQLException {
		StringBuilder log = new StringBuilder();
		for (RegistroAjuste registroAjuste : listaAjuste) {
			try {
				boolean valido = true;
				Long cantidad = null;
				Statement stmt2 = con.ObtenerConexion().createStatement();
				String sql2 = "SELECT * from inventario  where codigo = "+registroAjuste.getCodigo()+" "
						+ " and sucursal = '"+registroAjuste.getSucursal()+"'";
				rs =stmt2.executeQuery(sql2);
				while(rs.next()){
					cantidad = rs.getLong("cantidad");
				}
				Long balance = cantidad + registroAjuste.getCantidad();
				if(balance>0){
					String sql3 ="update inventario set cantidad="+ balance +" where codigo = " + registroAjuste.getCodigo() + " and sucursal = '"+registroAjuste.getSucursal()+"'; ";
					Statement stmt3 = con.ObtenerConexion().createStatement();
					stmt3.execute(sql3);
				}else{
					valido =false;
					log.append("---Cantidad no  valida para Codigo: " + registroAjuste.getCodigo()
						+ ", Cantidad: " + registroAjuste.getCantidad() + "" + ", Sucursal: "
						+ registroAjuste.getSucursal() + ".---");
				}

				if (valido) {
					Statement stmt = con.ObtenerConexion().createStatement();
					String sql = "INSERT INTO registro_ajuste (codigo,cantidad,sucursal,fecha,usuario, autorizante, documento) " + " VALUES ("
							+ registroAjuste.getCodigo() + ", " + registroAjuste.getCantidad() + ", " + " '"
							+ registroAjuste.getSucursal() + "', current_date, '" + registroAjuste.getUsuario() + "', "
							+ " '"+registroAjuste.getAutorizante()+"','"+registroAjuste.getDocumento()+"')";
					stmt.execute(sql);
				}
			} catch (Exception e) {
				log.append("--- No se ha procesado correctamente. Codigo: " + registroAjuste.getCodigo()
						+ ", Cantidad: " + registroAjuste.getCantidad() + "" + ", Sucursal: "
						+ registroAjuste.getSucursal() + ". ---");
			}

		}
		return log.toString();
	}

}
