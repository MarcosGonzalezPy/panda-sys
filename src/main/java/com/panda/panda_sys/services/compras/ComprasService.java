package com.panda.panda_sys.services.compras;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.panda.panda_sys.util.NumberToLetterConverter;

public class ComprasService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;
	
	public boolean insertar(OrdenCompra ordenCompra) throws SQLException {
		OrdenCompraCabecera cabecera = ordenCompra.getCabecera();
		List<OrdenCompraDetalle> listaDetalle= ordenCompra.getDetalle();
		String sql=" insert into orden_compra_cabecera(codigo, sucursal, proveedor, condicion_compra, plazos, fecha_entrega, fecha_creacion, usuario, estado, proveedor_codigo) "
				+ " values ("+cabecera.getCodigo()+", '"+cabecera.getSucursal()+"', '"+cabecera.getProveedor()+"', '"+cabecera.getCondicionCompra()+"',"
						+"  '"+cabecera.getPlazos()+"', '"+cabecera.getFechaEntrega()+"', current_date , '"+cabecera.getUsuario()+"', 'ACTIVO', "+cabecera.getProveedorCodigo()+")";
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

		String sql = " select  codigo, sucursal,  proveedor,  condicion_compra, plazos,  fecha_entrega ,fecha_creacion,  usuario, estado, proveedor_codigo from orden_compra_cabecera ";

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
		sql +=" order by codigo desc ";
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
			entidad.setProveedorCodigo(rs.getLong("proveedor_codigo"));
			lista.add(entidad);
		}
		return lista;
	}

	
	public boolean anular(Integer codigo, String estado, String sucursal) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			
			
			String sql = "update orden_compra_cabecera set estado = 'ANULADO' where codigo =?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, codigo);
			ps.execute();

			if(!estado.equals("PENDIENTE")){
				String sql2 ="update registro_compra_cabecera set estado = 'ANULADO' where id =?";
				PreparedStatement ps2 = c.prepareStatement(sql2);
				ps2.setInt(1, codigo);
				ps2.execute();
				
				String sql3 ="select * from registro_compra_detalle where id = ?";
				PreparedStatement ps3 = c.prepareStatement(sql3);
				ps3.setInt(1, codigo);
				ResultSet rs3 = ps3.executeQuery();
				List<RegistroCompraDetalle> listaRegistroCompraDetalle = new ArrayList<>();
				while(rs3.next()){
					RegistroCompraDetalle entidad= new RegistroCompraDetalle();
					entidad.setCantidad(rs3.getInt("cantidad"));
					entidad.setCodigoArticulo(rs3.getInt("codigo_articulo"));
					entidad.setIva(rs3.getInt("iva"));
					entidad.setPrecio(rs3.getLong("precio"));
					listaRegistroCompraDetalle.add(entidad);
				}
				for(RegistroCompraDetalle entidad: listaRegistroCompraDetalle){
					String sql4 = "update inventario set cantidad = cantidad-? where codigo = ?"
							+ " and sucursal = ?";
					PreparedStatement ps4 = c.prepareStatement(sql4);
					ps4.setLong(1, entidad.getCantidad());
					ps4.setLong(2, codigo);
					ps4.setString(3, sucursal);
					ps4.execute();					
				}
				String sql5= "update fondo_debito set estado = 'ANULADO' where "
					+ "documento = 'COMPRA' and documento_numero = ?";
				PreparedStatement ps5 = c.prepareStatement(sql5);
				ps5.setString(1, codigo.toString());
				ps5.execute();
				

			}
			c.commit();
			c.close();
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
			c.close();
			return false;
		}
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
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			
			Long montoTotal = 0L;
			String sql = "insert into registro_compra_cabecera (id, condicion_compra, plazo, proveedor, sucursal, fecha_entrega, fecha, usuario, estado)"
				+ " values (?,?,?,?,?,?, current_date,?,'INGRESADO')";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setLong(1, cabecera.getId());
			ps.setString(2, cabecera.getCondicionCompra());
			ps.setString(3, cabecera.getPlazo());
			ps.setString(4, cabecera.getProveedor());
			ps.setString(5,cabecera.getSucursal());
			ps.setDate(6, cabecera.getFechaEntrega());
			ps.setString(7, cabecera.getUsuario());
			ps.execute();
			for(RegistroCompraDetalle detalle: listaDetalle){				
				String sql2 = "insert into registro_compra_detalle (id, codigo_articulo, cantidad, precio, iva, total, impuesto) "
						+ " values (?,?,?,?,?,?,?) ";
				PreparedStatement ps2 = c.prepareStatement(sql2);
				ps2.setLong(1, cabecera.getId());
				ps2.setLong(2, detalle.getCodigoArticulo());
				ps2.setLong(3, detalle.getCantidad());
				ps2.setLong(4, detalle.getPrecio());
				ps2.setLong(5, detalle.getIva());
				Long total = detalle.getCantidad() * detalle.getPrecio();
				montoTotal +=total;
				ps2.setLong(6, total);
				Long impuesto = null;
				if(detalle.getIva().equals(5)){
					impuesto = total /21;
				}else{
					impuesto = total /11; 
				}				
				ps2.setLong(7, impuesto);
				ps2.execute();
				
				String sql3=" select * from inventario where codigo =? and sucursal =?";
				PreparedStatement ps3 = c.prepareStatement(sql3);
				ps3.setLong(1, detalle.getCodigoArticulo());
				ps3.setString(2, cabecera.getSucursal());
				ResultSet rs3 = ps3.executeQuery();
				Long cantidad = null;
				while(rs3.next()){
					cantidad = rs3.getLong("cantidad");
				}
				if(cantidad != null){
					String sql4 = " update inventario set cantidad=cantidad+? where codigo=? and sucursal=?";
					PreparedStatement ps4 = c.prepareStatement(sql4);
					ps4.setLong(1, detalle.getCantidad());
					ps4.setLong(2, detalle.getCodigoArticulo());
					ps4.setString(3, cabecera.getSucursal());
					ps4.execute();
				}else{
					String sql4 = " insert into inventario (cantidad, codigo, sucursal, maximo, minimo)  values (?,?,?,0,0) ";
					PreparedStatement ps4 = c.prepareStatement(sql4);
					ps4.setLong(1, detalle.getCantidad());
					ps4.setLong(2, detalle.getCodigoArticulo());
					ps4.setString(3, cabecera.getSucursal());
					ps4.execute();					
				}
				
			}
			String sql5 = "update orden_compra_cabecera set estado = 'RECEPCIONADO' where codigo= ?";
			PreparedStatement ps5 = c.prepareStatement(sql5);
			ps5.setLong(1, cabecera.getId());
			ps5.execute();
			
			String glosa = NumberToLetterConverter.convertNumberToLetter(montoTotal);
			String sql6 = "insert into fondo_debito (estado, fecha,fecha_vencimiento,cliente, numero,monto,sucursal,documento, documento_numero, glosa) "
					+ "values ('PENDIENTE', current_date,current_date + interval '2 month',?,1,?,?,'COMPRA',?,?)";
			PreparedStatement ps6 = c.prepareStatement(sql6);
			ps6.setLong(1, cabecera.getProveedorCodigo());
			ps6.setLong(2, montoTotal);
			ps6.setString(3, cabecera.getSucursal());
			ps6.setLong(4, cabecera.getId());
			ps6.setString(5, glosa);
			ps6.execute();
			
			c.commit();
			c.close();
		} catch (Exception e) {
			System.out.println("ERRPR: "+e.getMessage());
			c.close();
			return false;
		}
		return true;
	}
}
