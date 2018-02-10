package com.panda.panda_sys.services.compras;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.compras.OrdenCompra;
import com.panda.panda_sys.model.compras.OrdenCompraCabecera;
import com.panda.panda_sys.model.compras.OrdenCompraDetalle;
import com.panda.panda_sys.model.compras.RegistroCompra;
import com.panda.panda_sys.model.compras.RegistroCompraCabecera;
import com.panda.panda_sys.model.compras.RegistroCompraDetalle;
import com.panda.panda_sys.model.inventario.Inventario;
import com.panda.panda_sys.model.inventario.InventarioParam;
import com.panda.panda_sys.model.inventario.RegistroInventario;
import com.panda.panda_sys.model.personas.Usuarios;
import com.panda.panda_sys.param.RolesParam;
import com.panda.panda_sys.param.compras.OrdenCompraDetalleParam;
import com.panda.panda_sys.util.Conexion;

public class ComprasService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;
	
	public boolean insertar(OrdenCompra ordenCompra) throws SQLException {
		OrdenCompraCabecera cabecera = ordenCompra.getCabecera();
		List<OrdenCompraDetalle> listaDetalle= ordenCompra.getDetalle();
		String sql=" insert into orden_compra_cabecera(codigo, sucursal, proveedor, condicion_compra, plazos, fecha_entrega, fecha_creacion, usuario, estado) "
				+ " values ("+cabecera.getCodigo()+", '"+cabecera.getSucursal()+"', '"+cabecera.getProveedor()+"', '"+cabecera.getCondicionCompra()+"',"
						+"  '"+cabecera.getPlazos()+"', '"+cabecera.getFechaEntrega()+"', current_date , '"+cabecera.getUsuario()+"', 'ACTIVO')";
		Statement stmt = con.ObtenerConexion().createStatement();
		stmt.execute(sql);
		for(OrdenCompraDetalle detalle: listaDetalle){
			String sql2= " insert into orden_compra_detalle(codigo, codigo_articulo, cantidad, iva) values ("
					+ " "+cabecera.getCodigo()+", "+detalle.getCodigoArticulo()+", "+detalle.getCantidad()+", "+detalle.getIva()+") ";
			Statement stmt1 =con.ObtenerConexion().createStatement();
			stmt1.execute(sql2);
		}		
		return true;
	}
	
	public List<OrdenCompraCabecera> listarOrdenCompra(OrdenCompraCabecera param) throws SQLException {
		List<OrdenCompraCabecera> lista = new ArrayList<OrdenCompraCabecera>();

		String sql = " select  codigo, sucursal,  proveedor,  condicion_compra, plazos,  fecha_entrega ,fecha_creacion,  usuario, estado from orden_compra_cabecera ";

		if (param.getEstado() != null) {
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql + conector +"  estado = upper('" + param.getEstado() + "')  ";
		}
		if (param.getCodigo() != 0) {
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql + conector +"  codigo = " + param.getCodigo() + "  ";
		}
		if (param.getSucursal() != null) {
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql +conector+ "  sucursal = '" + param.getSucursal() + "' ";
		}
		if (param.getProveedor() != null) {
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql +conector + " proveedor like '%" + param.getProveedor() + "%' ";
		}

		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			OrdenCompraCabecera entidad = new OrdenCompraCabecera();
			entidad.setCodigo(rs.getInt("codigo"));
			entidad.setSucursal(rs.getString("sucursal"));
			entidad.setProveedor(rs.getString("proveedor"));
			entidad.setCondicionCompra(rs.getString("condicion_compra"));
			entidad.setPlazos(rs.getString("plazos"));
			entidad.setFechaEntrega(rs.getDate("fecha_entrega"));
			entidad.setFechaCreacion(rs.getDate("fecha_creacion"));
			entidad.setUsuario(rs.getString("usuario"));
			entidad.setEstado(rs.getString("estado"));
			lista.add(entidad);
		}
		return lista;
	}

	
	public boolean eliminar(Integer codigo) throws SQLException {
//		String sql = "delete from orden_compra_cabecera where codigo ="+codigo;
//		Statement stmt = con.ObtenerConexion().createStatement();
//		stmt.execute(sql);
//		String sql2 = "delete from orden_compra_detalle where codigo ="+codigo;
//		Statement stmt2 = con.ObtenerConexion().createStatement();
//		stmt2.execute(sql2);
		String sql = "update orden_compra_cabecera set estado = 'ANULADO' where codigo ="+codigo;
		Statement stmt = con.ObtenerConexion().createStatement();
		stmt.execute(sql);
		return true;
	}

	
	public List<OrdenCompraDetalleParam> listarOrdenCompraDetalle(Integer codigo) throws SQLException{
		List<OrdenCompraDetalleParam> lista = new ArrayList<OrdenCompraDetalleParam>();
		String sql =" select ocd.*, a.descripcion from orden_compra_detalle ocd, articulos a where a.codigo = ocd.codigo_articulo"
				+ " and ocd.codigo = "+codigo;
		Statement stmt = con.ObtenerConexion().createStatement();
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			OrdenCompraDetalleParam entidad = new OrdenCompraDetalleParam();
			entidad.setCodigo(rs.getInt("codigo"));
			entidad.setCantidad(rs.getInt("cantidad"));
			entidad.setCodigoArticulo(rs.getInt("codigo_articulo"));
			entidad.setDescripcion(rs.getString("descripcion"));
			entidad.setIva(rs.getInt("iva"));
			lista.add(entidad);
		}
		return lista;
		
	}
	
	public boolean recepcionCompra(RegistroCompra registroCompra) throws SQLException {
		RegistroCompraCabecera cabecera = registroCompra.getCabecera();
		List<RegistroCompraDetalle> listaDetalle = registroCompra.getDetalle();

		String fechaEntrega =  new SimpleDateFormat("yyyy-MM-dd").format(cabecera.getFechaEntrega());
		String sql = "insert into registro_compra_cabecera (id, condicion_compra, plazo, proveedor, sucursal, fecha_entrega, fecha, usuario, estado)"
				+ " values ( "+cabecera.getId()+", '"+cabecera.getCondicionCompra()+"', '"+cabecera.getPlazo()+"', '"+cabecera.getProveedor()+"',"
						+ " '"+cabecera.getSucursal()+"', '"+fechaEntrega+"', current_date, '"+cabecera.getUsuario()+"', 'INGRESADO')";
		Statement stmt = con.ObtenerConexion().createStatement();
		stmt.execute(sql);
		for(RegistroCompraDetalle detalle: listaDetalle){
			boolean existe =false;
			Integer cantidad= null;
			String sql2 = "insert into registro_compra_detalle (id, codigo_articulo, cantidad, precio, iva) values ("+cabecera.getId()+", "
					+ " "+detalle.getCodigoArticulo()+", "+detalle.getCantidad()+","+detalle.getPrecio()+",10) ";

			Statement stmt2 = con.ObtenerConexion().createStatement();
			stmt2.execute(sql2);
			
			String sql3=" select * from inventario where codigo = " + detalle.getCodigoArticulo() + " and sucursal = '"+cabecera.getSucursal()+"'; ";
			Statement stmt3 = con.ObtenerConexion().createStatement();
			rs = stmt3.executeQuery(sql3);
			while (rs.next()) {
				existe = true;
				cantidad = rs.getInt("cantidad");
			}
			if(existe){
				cantidad = cantidad + detalle.getCantidad();
				String sql4 = " update inventario set cantidad="+ cantidad +" where codigo = " + detalle.getCodigoArticulo() + " and sucursal = '"+cabecera.getSucursal()+"'; ";
			Statement stmt4 = con.ObtenerConexion().createStatement();
			stmt4.execute(sql4);
			} else {
				String sql5 = " insert into inventario (codigo, cantidad, sucursal, maximo, minimo)" + " values ("
						+ detalle.getCodigoArticulo() + "," + detalle.getCantidad() + ",'" + cabecera.getSucursal()
						+ "', 0,0)";
				Statement stmt5 = con.ObtenerConexion().createStatement();
				stmt5.execute(sql5);
			}
		}
		String sql6 = "update orden_compra_cabecera set estado = 'RECEPCIONADO' where codigo= "+cabecera.getId();
		Statement stmt6 = con.ObtenerConexion().createStatement();
		stmt6.execute(sql6);
		return true;
	}
}
