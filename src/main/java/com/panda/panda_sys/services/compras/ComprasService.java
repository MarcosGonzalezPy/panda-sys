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

import com.panda.panda_sys.model.compras.NotaDebito;
import com.panda.panda_sys.model.compras.NotaDebitoCabecera;
import com.panda.panda_sys.model.compras.NotaDebitoDetalle;
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
import com.panda.panda_sys.param.compras.RegistroCompraDetalleParam;
import com.panda.panda_sys.util.Conexion;
import com.panda.panda_sys.util.NumberToLetterConverter;
import com.panda.panda_sys.util.Secuencia;

public class ComprasService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;
	
	public boolean insertar(OrdenCompra ordenCompra) throws SQLException {
		OrdenCompraCabecera ca= ordenCompra.getCabecera();
		List<OrdenCompraDetalle> listaDetalle= ordenCompra.getDetalle();
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			String sql1 = "insert into orden_compra_cabecera(codigo, sucursal, proveedor, fecha_creacion, fecha_entrega, usuario, estado, proveedor_codigo)"
					+ "values (?,?,?,current_timestamp,?,?,?,?)";
			PreparedStatement ps1= c.prepareStatement(sql1);
			ps1.setLong(1, ca.getCodigo());
			ps1.setString(2, ca.getSucursal());
			ps1.setString(3, ca.getProveedor());
			ps1.setDate(4, ca.getFechaEntrega());
			ps1.setString(5, ca.getUsuario());
			ps1.setString(6, "ACTIVO");
			ps1.setLong(7, ca.getProveedorCodigo());
			ps1.execute();
			
			for(OrdenCompraDetalle detalle: listaDetalle){
				String sql2= " insert into orden_compra_detalle(codigo, codigo_articulo, cantidad, iva) values (?,?,?,?)";
				PreparedStatement ps2 = c.prepareStatement(sql2);
				ps2.setLong(1, ca.getCodigo());
				ps2.setLong(2, detalle.getCodigoArticulo());
				ps2.setLong(3, detalle.getCantidad());
				ps2.setLong(4, detalle.getIva());
				ps2.execute();
			}					
			c.commit();
			c.close();
		} catch (Exception e) {
			c.close();
			return false;
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
					ps4.setLong(2, entidad.getCodigoArticulo());
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
	
	public List<RegistroCompraDetalleParam> listarRegistroCompraDetalle(Integer codigo) throws SQLException{
		List<RegistroCompraDetalleParam> lista = new ArrayList<RegistroCompraDetalleParam>();
		Connection c = ObtenerConexion();
		try {
			String sql =" select rcd.*, a.descripcion from registro_compra_detalle rcd, articulos a where a.codigo = rcd.codigo_articulo  and rcd.id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setLong(1, codigo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				RegistroCompraDetalleParam entidad = new RegistroCompraDetalleParam();
				entidad.setId(rs.getInt("id"));
				entidad.setCodigoArticulo(rs.getInt("codigo_articulo"));
				entidad.setCantidad(rs.getInt("cantidad"));
				entidad.setPrecio(rs.getLong("precio"));
				entidad.setIva(rs.getInt("iva"));
				entidad.setTotal(rs.getLong("total"));
				entidad.setImpuesto(rs.getLong("impuesto"));
				entidad.setDescripcion(rs.getString("descripcion"));
				
				lista.add(entidad);
			}
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
		}		
		return lista;		
	}
	
	public Boolean registrarNotaDebito(NotaDebito notaDebito) throws SQLException{
		NotaDebitoCabecera cabecera = notaDebito.getCabecera();
		List<NotaDebitoDetalle> detalle = notaDebito.getDetalle();
		Connection c= ObtenerConexion();
		try {
			c.setAutoCommit(false);
			String sql = "select * from registro_compra_cabecera where id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setLong(1,cabecera.getNumeroRegistroCompra());
			ResultSet rs= ps.executeQuery();
			RegistroCompraCabecera rcc = new RegistroCompraCabecera();
			while(rs.next()){				
				rcc.setId(rs.getInt("id"));
				rcc.setCondicionCompra(rs.getString("condicion_compra"));
				rcc.setPlazo(rs.getString("plazo"));
				rcc.setProveedor(rs.getString("proveedor"));
				rcc.setSucursal(rs.getString("sucursal"));
				rcc.setFechaEntrega(rs.getDate("fecha_entrega"));
				rcc.setFecha(rs.getDate("fecha"));
				rcc.setUsuario(rs.getString("usuario"));
				rcc.setEstado(rs.getString("estado"));
				rcc.setProveedorCodigo(rs.getLong("proveedor_codigo"));
			}
			Secuencia secuencia= new Secuencia();
			Long montoTotal = 0l;
			for(NotaDebitoDetalle entidad: detalle){
				montoTotal+=entidad.getTotal();
			}
			Long sec = Long.parseLong(secuencia.getSecuencia("nota_debito_cabecera_id_seq"));
			String glosa = NumberToLetterConverter.convertNumberToLetter(montoTotal);
			String sql2 = "insert into nota_debito_cabecera"
				+ "(id,numero_registro_compra,sucursal,usuario,estado,fecha,glosa)"
				+ " values (?,?,?,?,'ACTIVO', current_timestamp,?)";
			PreparedStatement ps2= c.prepareStatement(sql2);
			ps2.setLong(1, sec);
			ps2.setLong(2, rcc.getId());
			ps2.setString(3, rcc.getSucursal());
			ps2.setString(4, cabecera.getUsuario());
			ps2.setString(5, glosa);
			ps2.execute();
			
			for(NotaDebitoDetalle entidadDetalle: detalle){
				String sql3="insert into nota_debito_detalle(numero_registro_compra,codigo_articulo,"
					+ "cantidad,precio,iva,total,impuesto,tipo)"
					+ " values (?,?,?,?,?,?,?,?)";
				PreparedStatement ps3 =c.prepareStatement(sql3);
				ps3.setLong(1, sec);
				ps3.setLong(2, entidadDetalle.getCodigoArticulo());
				ps3.setLong(3, entidadDetalle.getCantidad());
				ps3.setLong(4, entidadDetalle.getPrecio());
				ps3.setLong(5, entidadDetalle.getIva());
				ps3.setLong(6, entidadDetalle.getTotal());
				ps3.setLong(7, entidadDetalle.getImpuesto());
				ps3.setString(8, entidadDetalle.getTipo());
				ps3.execute();	
				
				String sql4= "select * from registro_compra_detalle where id=?"
						+ " and codigo_articulo=?";
				PreparedStatement ps4 = c.prepareStatement(sql4);
				ps4.setLong(1, rcc.getId());
				ps4.setLong(2, entidadDetalle.getCodigoArticulo());
				ResultSet rs4 = ps4.executeQuery();
				Long cantidadCompra = 0L;
				while(rs4.next()){
					cantidadCompra = rs4.getLong("cantidad");
				}
				if(cantidadCompra> entidadDetalle.getCantidad()){
					Long diferencia = cantidadCompra-entidadDetalle.getCantidad();
					String sql5 = "update inventario set cantidad = cantidad-? where codigo=?"
							+ " and sucursal=?";
					PreparedStatement ps5= c.prepareStatement(sql5);
					ps5.setLong(1,diferencia);
					ps5.setLong(2, entidadDetalle.getCodigoArticulo());
					ps5.setString(3, rcc.getSucursal());
					ps5.execute();					
				}
			}
			String sql7=" update registro_compra_cabecera set estado = 'CON ND' where id = ?";
			PreparedStatement ps7 =c.prepareStatement(sql7);
			ps7.setLong(1, cabecera.getNumeroRegistroCompra());
			ps7.execute();
			
			String sql8 = "update orden_compra_cabecera set estado='CON ND' where codigo = ?";
			PreparedStatement ps8= c.prepareStatement(sql8);
			ps8.setLong(1, cabecera.getNumeroRegistroCompra());
			ps8.execute();
			
			String sql9 =" insert into fondo_credito (estado, fecha, fecha_vencimiento, cliente, numero, monto, sucursal, documento, documento_numero, glosa)"
					+ "values ('PENDIENTE',CURRENT_TIMESTAMP,  current_date + interval '1 month',?,1,?,?,'NOTADEB',?,?) ";
			PreparedStatement ps9 = c.prepareStatement(sql9);
			ps9.setLong(1, rcc.getProveedorCodigo());
			ps9.setLong(2, montoTotal);
			ps9.setString(3, rcc.getSucursal());
			ps9.setLong(4, cabecera.getNumeroRegistroCompra());
			ps9.setString(5, glosa);
			ps9.execute();
			
			c.commit();
			c.close();
		} catch (Exception e) {
			System.out.println("Error al registrar nota de debito."+e.getMessage());
			c.close();
			return false;
		}
		return true;
	}
	
	
}
