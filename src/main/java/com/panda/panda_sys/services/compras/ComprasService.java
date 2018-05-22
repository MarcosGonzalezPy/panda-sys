package com.panda.panda_sys.services.compras;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.compras.NotaDebito;
import com.panda.panda_sys.model.compras.NotaDebitoCabecera;
import com.panda.panda_sys.model.compras.NotaDebitoDetalle;
import com.panda.panda_sys.model.compras.OrdenCompra;
import com.panda.panda_sys.model.compras.OrdenCompraCabecera;
import com.panda.panda_sys.model.compras.OrdenCompraDetalle;
import com.panda.panda_sys.model.ventas.FacturaCabecera;
import com.panda.panda_sys.param.compras.OrdenCompraDetalleParam;
import com.panda.panda_sys.param.compras.RegistroCompraDetalleParam;
import com.panda.panda_sys.util.Conexion;
import com.panda.panda_sys.util.NumberToLetterConverter;
import com.panda.panda_sys.util.PandaException;
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
			System.out.println("PANDA ERROR: "+e.getMessage());
			return false;
		}
		return true;
	}
	
	public List<OrdenCompraCabecera> listarOrdenCompra(OrdenCompraCabecera param) throws SQLException {
		List<OrdenCompraCabecera> lista = new ArrayList<OrdenCompraCabecera>();

		String sql = " select  codigo, sucursal,  proveedor,  condicion_compra, plazo,  fecha_entrega ,fecha_creacion,  usuario, estado, proveedor_codigo, ruc,numero_factura, timbrado from orden_compra_cabecera ";

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
			entidad.setPlazo(rs.getInt("plazo"));
			entidad.setFechaEntrega(rs.getDate("fecha_entrega"));
			entidad.setFechaCreacion(rs.getDate("fecha_creacion"));
			entidad.setUsuario(rs.getString("usuario"));
			entidad.setEstado(rs.getString("estado"));
			entidad.setProveedorCodigo(rs.getLong("proveedor_codigo"));
			entidad.setRuc(rs.getString("ruc"));
			entidad.setTimbrado(rs.getLong("timbrado"));
			entidad.setNumeroFactura(rs.getLong("numero_factura"));
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
				
				String sql3 ="select * from orden_compra_detalle where codigo = ?";
				PreparedStatement ps3 = c.prepareStatement(sql3);
				ps3.setInt(1, codigo);
				ResultSet rs3 = ps3.executeQuery();
				List<OrdenCompraDetalle> listaRegistroCompraDetalle = new ArrayList<>();
				while(rs3.next()){
					OrdenCompraDetalle entidad= new OrdenCompraDetalle();
					entidad.setCantidad(rs3.getInt("cantidad"));
					entidad.setCodigoArticulo(rs3.getInt("codigo_articulo"));
					entidad.setIva(rs3.getInt("iva"));
					entidad.setPrecio(rs3.getLong("precio"));
					listaRegistroCompraDetalle.add(entidad);
				}
				for(OrdenCompraDetalle entidad: listaRegistroCompraDetalle){
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
				ps5.setLong(1, codigo);
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
	
	public boolean recepcionCompra(OrdenCompra ordenCompra) throws SQLException {
		OrdenCompraCabecera cabecera = ordenCompra.getCabecera();
		List<OrdenCompraDetalle> listaDetalle = ordenCompra.getDetalle();		
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			
			Long montoTotal = 0L;
			String sql = "update orden_compra_cabecera set condicion_compra=?, plazo=?,estado='RECEPCIONADO',"
					+ " numero_factura=?, fecha_recepcion=current_date, timbrado =?, usuario_recepcion=?, ruc=? where codigo =?  ";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, cabecera.getCondicionCompra());
			ps.setLong(2, cabecera.getPlazo()==null? 0: cabecera.getPlazo());
			ps.setLong(3, cabecera.getNumeroFactura());
			ps.setLong(4, cabecera.getTimbrado());
			ps.setString(5, cabecera.getUsuarioRecepcion());
			ps.setString(6, cabecera.getRuc());
			ps.setLong(7, cabecera.getCodigo());
			ps.execute();
			
			for(OrdenCompraDetalle detalle: listaDetalle){				
				String sql2 = "update orden_compra_detalle  set cantidad=?, precio=?, iva=?, total=?,  impuesto=? "
						+ " where codigo=? and codigo_articulo=? ";
				PreparedStatement ps2 = c.prepareStatement(sql2);
				ps2.setLong(1, detalle.getCantidad());
				ps2.setLong(2, detalle.getPrecio());
				ps2.setLong(3, detalle.getIva());
				ps2.setLong(4, detalle.getTotal());
				ps2.setLong(5, detalle.getImpuesto());
				ps2.setLong(6, cabecera.getCodigo());
				ps2.setLong(7, detalle.getCodigoArticulo());
				montoTotal +=detalle.getTotal(); 
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
			Date fechaVencimiento =new Date(System.currentTimeMillis());
			if(cabecera.getPlazo()!=null && cabecera.getPlazo()>0)
			fechaVencimiento = sumarDias(new Date(System.currentTimeMillis()), cabecera.getPlazo());
			
			String glosa = NumberToLetterConverter.convertNumberToLetter(montoTotal);
			String sql6 = "insert into fondo_debito (estado, fecha,fecha_vencimiento,cliente, numero,monto,sucursal,documento, documento_numero, glosa) "
					+ "values ('PENDIENTE', current_date,?,?,1,?,?,'COMPRA',?,?)";
			PreparedStatement ps6 = c.prepareStatement(sql6);
			ps6.setDate(1, fechaVencimiento);
			ps6.setLong(2, cabecera.getProveedorCodigo());
			ps6.setLong(3, montoTotal);
			ps6.setString(4, cabecera.getSucursal());
			ps6.setLong(5, cabecera.getCodigo());
			ps6.setString(6, glosa);
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
			String sql =" select rcd.*, a.descripcion from orden_compra_detalle rcd, articulos a where a.codigo = rcd.codigo_articulo  and rcd.codigo = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setLong(1, codigo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				RegistroCompraDetalleParam entidad = new RegistroCompraDetalleParam();
				entidad.setId(rs.getInt("codigo"));
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
			String sql = "select * from orden_compra_cabecera where codigo = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setLong(1,cabecera.getNumeroRegistroCompra());
			ResultSet rs= ps.executeQuery();
			OrdenCompraCabecera rcc = new OrdenCompraCabecera();
			while(rs.next()){				
				rcc.setCodigo(rs.getInt("codigo"));
				rcc.setCondicionCompra(rs.getString("condicion_compra"));
				rcc.setPlazo(rs.getInt("plazo"));
				rcc.setProveedor(rs.getString("proveedor"));
				rcc.setSucursal(rs.getString("sucursal"));
				rcc.setFechaEntrega(rs.getDate("fecha_entrega"));
				rcc.setFechaCreacion(rs.getDate("fecha_creacion"));
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
			ps2.setLong(2, rcc.getCodigo());
			ps2.setString(3, rcc.getSucursal());
			ps2.setString(4, cabecera.getUsuario());
			ps2.setString(5, glosa);
			ps2.execute();
			
			for(NotaDebitoDetalle entidadDetalle: detalle){
				String sql3="insert into nota_debito_detalle(numero_registro_compra,codigo_articulo,"
					+ "cantidad,precio,iva,total,impuesto,tipo)"
					+ " values (?,?,?,?,?,?,?,?)";
				PreparedStatement ps3 =c.prepareStatement(sql3);
				ps3.setLong(1, cabecera.getNumeroRegistroCompra());
				ps3.setLong(2, entidadDetalle.getCodigoArticulo());
				ps3.setLong(3, entidadDetalle.getCantidad());
				ps3.setLong(4, entidadDetalle.getPrecio());
				ps3.setLong(5, entidadDetalle.getIva());
				ps3.setLong(6, entidadDetalle.getTotal());
				ps3.setLong(7, entidadDetalle.getImpuesto());
				ps3.setString(8, entidadDetalle.getTipo());
				ps3.execute();	
				
				String sql4= "select * from orden_compra_detalle where codigo=?"
						+ " and codigo_articulo=?";
				PreparedStatement ps4 = c.prepareStatement(sql4);
				ps4.setLong(1, rcc.getCodigo());
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
			
			String sql8 = "update orden_compra_cabecera set estado='NOTACRE' where codigo = ?";
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
	
    public static Date sumarDias(Date fecha, int dias){        
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(fecha);    
	    calendar.add(Calendar.DAY_OF_YEAR, dias);                     
	    return (Date) calendar.getTime();
    }
    
    public String anularNC(FacturaCabecera fc) throws SQLException{
    	Connection c = ObtenerConexion();
    	try {
			c.setAutoCommit(false);
			String sql1 = "select * from fondo_credito where documento ='NOTACRE' and documento_numero =?";
			PreparedStatement ps1 = c.prepareStatement(sql1);
			ps1.setString(1, fc.getNumeroFactura());
			ResultSet rs1=  ps1.executeQuery();
			while(rs.next()){
				String estado = rs.getString("estado");
				if(estado.equals("PENDIENTE")){
					throw new PandaException("Debe reversar primero el pago antes de anular la NC.");
				}
			}
			String sql2="undate fondo_credito set estado ='ANULADO' where documento ='NOTACRE' and documento_numero =? ";
			PreparedStatement ps2 = 
			c.commit();
			c.close();
		} catch (Exception e) {
			c.close();
			System.out.println("ERROR: "+e.getMessage());
			return "ERROR: "+e.getMessage();
		}
    	return "OK PROBAR";
    }
	
    public List<OrdenCompraDetalleParam> listarNotaCredito(Long id) throws SQLException{
    	List<OrdenCompraDetalleParam> lista= new ArrayList<OrdenCompraDetalleParam>();
    	Connection c= ObtenerConexion();
    	try {
    		c.setAutoCommit(false);
    		String sq1="select b.*, a.descripcion from articulos a, nota_debito_detalle b where a.codigo = b.codigo_articulo and numero_registro_compra=?";
    		PreparedStatement ps1 =c.prepareStatement(sq1);
    		ps1.setLong(1, id);
    		ResultSet rs1=ps1.executeQuery();
    		while(rs1.next()){
    			OrdenCompraDetalleParam entidad = new OrdenCompraDetalleParam();
    			entidad.setCodigo(rs1.getInt("id"));
    			entidad.setCantidad(rs1.getInt("cantidad"));
    			entidad.setCodigoArticulo(rs1.getInt("codigo_articulo"));
    			entidad.setDescripcion(rs1.getString("descripcion"));
    			entidad.setIva(rs1.getInt("iva"));
    			entidad.setTotal(rs1.getLong("total"));
    			entidad.setImpuesto(rs1.getLong("impuesto"));
    			entidad.setPrecio(rs1.getLong("precio"));
    			lista.add(entidad);
    		}
    		c.commit();
			c.close();
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
			c.close();
		}
    	return lista;
    }
    
    public NotaDebitoCabecera obtenerNotaCreditoCabecera(Long id) throws SQLException{
    	NotaDebitoCabecera entidad = new NotaDebitoCabecera();
    	Connection c= ObtenerConexion();
    	try {
    		String sql = "select * from nota_debito_cabecera where numero_registro_compra = ?";
    		PreparedStatement ps= c.prepareStatement(sql);
    		ps.setLong(1, id);
    		ResultSet rs= ps.executeQuery();
    		while(rs.next()){
    			entidad.setId(rs.getLong("id"));
    			entidad.setNumeroRegistroCompra(rs.getLong("numero_restro_compra"));
    			entidad.setSucursal(rs.getString("sucursal"));
    			entidad.setUsuario(rs.getString("usuario"));
    			entidad.setEstado(rs.getString("estado"));
    			entidad.setFecha(rs.getDate("fecha"));
    			entidad.setGlosa(rs.getString("glosa"));
    		}
			c.close();
		} catch (Exception e) {
			System.out.println("ERROR PANDA: "+e.getMessage());
			c.close();
		}
    	return entidad;
    }
}
