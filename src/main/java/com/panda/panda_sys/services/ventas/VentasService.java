package com.panda.panda_sys.services.ventas;

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

import com.panda.panda_sys.model.ventas.Factura;
import com.panda.panda_sys.model.ventas.FacturaCabecera;
import com.panda.panda_sys.model.ventas.FacturaDetalle;
import com.panda.panda_sys.model.ventas.VentasStockPorSucursal;
import com.panda.panda_sys.param.ventas.FormaPago;
import com.panda.panda_sys.param.ventas.RegistrarPago;
import com.panda.panda_sys.util.Conexion;
import com.panda.panda_sys.util.PandaException;
import com.panda.panda_sys.util.Secuencia;

public class VentasService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs;

	public List<VentasStockPorSucursal> obtenerVentasStockPorSucursal(VentasStockPorSucursal param)
			throws SQLException {
		List<VentasStockPorSucursal> lista = new ArrayList<VentasStockPorSucursal>();
		String sql1 = " select a.codigo, a.codigo_barra, a.descripcion, a.precio_unitario, a.grabado, a.moneda, b.cantidad, b.sucursal, 'P' as tipo "
				+ "from articulos a, inventario b where a.codigo = b.codigo " + " and sucursal = '"
				+ param.getSucursal() + "' ";
		String sql2 = " select codigo, null, descripcion, precio_unitario, grabado, 'GUARANIES', null, null, 'S' as tipo from servicios where codigo is not null ";
		if (param.getCodigo() != null && !param.getCodigo().equals("")) {
			sql1 = sql1 + " and a.codigo = " + param.getCodigo() + " ";
			sql2 = sql2 + " and codigo = " + param.getCodigo() + " ";
		}
		if (param.getCodigoBarra() != null && !param.getCodigo().equals("")) {
			sql1 = sql1 + " and a.codigo_barra = '" + param.getCodigo() + "' ";
		}
		if (param.getDescripcion() != null && !param.getDescripcion().equals("")) {
			sql1 = sql1 + " and a.descripcion like '%" + param.getDescripcion() + "%' ";
			sql2 = sql2 + " and descripcion like '%" + param.getDescripcion() + "%' ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		String sql = sql1 + " UNION " + sql2;
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			VentasStockPorSucursal entidad = new VentasStockPorSucursal();
			entidad.setCodigo(rs.getInt("codigo"));
			entidad.setCodigoBarra(rs.getString("codigo_barra"));
			entidad.setDescripcion(rs.getString("descripcion"));
			entidad.setPrecioUnitario(rs.getInt("precio_unitario"));
			entidad.setGrabado(rs.getInt("grabado"));
			entidad.setMoneda(rs.getString("moneda"));
			entidad.setCantidad(rs.getInt("cantidad"));
			entidad.setSucursal(rs.getString("sucursal"));
			entidad.setSucursal(rs.getString("sucursal"));
			entidad.setTipo(rs.getString("tipo"));
			lista.add(entidad);
		}
		return lista;
	}

	public boolean registrarVenta(Factura factura) throws SQLException {
		Connection c = ObtenerConexion();
		try {			
			c.setAutoCommit(false);
			FacturaCabecera fc = factura.getCabecera();
			String sql1 = "insert into factura_cabecera(numero_factura, cliente, ruc, telefono, sucursal, fecha, usuario, estado, codigo_persona)"
					+ "values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps1 = c.prepareStatement(sql1);
			ps1.setString(1, fc.getNumeroFactura());
			ps1.setString(2, fc.getCliente());
			ps1.setString(3, fc.getRuc());
			ps1.setString(4, fc.getTelefono());
			ps1.setString(5, fc.getSucursal());
			ps1.setDate(6, new Date(System.currentTimeMillis()));
			ps1.setString(7, fc.getUsuario());
			ps1.setString(8, fc.getEstado());
			ps1.setLong(9, fc.getCodigoPersona());
			ps1.execute();
						
			List<FacturaDetalle> listaDetalle = factura.getDetalle();
			for (FacturaDetalle det : listaDetalle) {
				String sql2="insert into factura_detalle(factura_id, codigo_articulo, cantidad, precio, iva, total, impuesto, tipo)"
						+ " values(?,?,?,?,?,?,?,?)";
				PreparedStatement ps2= c.prepareStatement(sql2);
				ps2.setString(1, fc.getNumeroFactura());
				ps2.setLong(2, Long.parseLong(det.getCodigoArticulo()));
				ps2.setLong(3, det.getCantidad());
				ps2.setLong(4, det.getPrecio());
				ps2.setLong(5, det.getIva());
				ps2.setLong(6, det.getTotal());
				ps2.setLong(7, det.getImpuesto());
				ps2.setString(8, det.getTipo());
				ps2.execute();
			}
			c.commit();
			c.close();
			return true;
		} catch (Exception e) {
			c.close();
			System.out.println("ERROR: " + e.getMessage());
			return false;
		}
	}

	public List<FacturaCabecera> listarFacturas(FacturaCabecera param) throws SQLException {
		List<FacturaCabecera> lista = new ArrayList<FacturaCabecera>();
		String sql = " select * from factura_cabecera " + " where  sucursal is not null ";
		if (param.getNumeroFactura() != null && !param.getNumeroFactura().equals("")) {
			sql = sql + " and numero_factura like '%" + param.getNumeroFactura() + "%' ";
		}
		if (param.getCliente() != null && !param.getCliente().equals("")) {
			sql = sql + " and cliente = '" + param.getCliente() + "' ";
		}
		if (param.getEstado() != null && !param.getEstado().equals("")) {
			sql = sql + " and estado = '" + param.getEstado() + "' ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		sql+=" order by numero_factura asc";
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			FacturaCabecera entidad = new FacturaCabecera();
			entidad.setNumeroFactura(rs.getString("numero_factura"));
			entidad.setTimbrado(rs.getString("timbrado"));
			entidad.setCliente(rs.getString("cliente"));
			entidad.setRuc(rs.getString("ruc"));
			entidad.setTelefono(rs.getString("telefono"));
			entidad.setSucursal(rs.getString("sucursal"));
			entidad.setCaja(rs.getInt("caja"));
			entidad.setCondicionCompra(rs.getString("condicion_compra"));
			entidad.setMedioPago(rs.getInt("medio_pago"));
			entidad.setCondicionPago("condicion_pago");
			entidad.setCuotas(rs.getInt("cuotas"));
			entidad.setFecha(rs.getDate("fecha"));
			entidad.setUsuario(rs.getString("usuario"));
			entidad.setEstado(rs.getString("estado"));
			entidad.setCodigoPersona(rs.getInt("codigo_persona"));
			entidad.setCajero(rs.getString("cajero"));
			lista.add(entidad);
		}
		return lista;
	}

	public String eliminar(String numeroFactura) throws SQLException {
		try {
			String sql = " delete from factura_cabecera where numero_factura= '" + numeroFactura + "' ";
			Statement statement = con.ObtenerConexion().createStatement();
			statement.execute(sql);
			String sql1 = " delete from factura_detalle  where factura_id= '" + numeroFactura + "' ";
			Statement statement2 = con.ObtenerConexion().createStatement();
			statement2.execute(sql1);
			return "ok";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public List<FacturaDetalle> listarDetalle(String numeroFactura) {
		List<FacturaDetalle> lista = new ArrayList<FacturaDetalle>();
		try {
			String sql = " select a.descripcion, fd.* from factura_detalle fd, (select a.codigo, a.codigo_barra, a.descripcion, a.precio_unitario, a.grabado, a.moneda, b.cantidad, b.sucursal "
					+ " from articulos a, inventario b where a.codigo = b.codigo   UNION  "
					+ " select codigo, null, descripcion, precio_unitario, grabado, 'GUARANIES', null, null from servicios) a "
					+ " where fd.codigo_articulo = a.codigo and factura_id='" + numeroFactura + "'";
			Statement statement = con.ObtenerConexion().createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				FacturaDetalle entidad = new FacturaDetalle();
				entidad.setDescripcion(rs.getString("descripcion"));
				entidad.setFacturaId(rs.getString("factura_id"));
				entidad.setCodigoArticulo(rs.getString("codigo_articulo"));
				entidad.setCantidad(rs.getInt("cantidad"));
				entidad.setPrecio(rs.getInt("precio"));
				entidad.setIva(rs.getInt("iva"));
				entidad.setTotal(rs.getInt("total"));
				entidad.setImpuesto(rs.getInt("impuesto"));
				entidad.setTipo(rs.getString("tipo"));
				entidad.setEstado(rs.getString("estado"));
				lista.add(entidad);
			}
			return lista;
		} catch (Exception e) {
			return null;
		}
	}

	public String registrarPago(RegistrarPago param) {
		try {
			Connection c = ObtenerConexion();
			c.setAutoCommit(false);
			Long codigoMedioPago = null;
			if (param.getCondicionCompra().equals("CONTADO")) {
				List<FormaPago> listaFormaPago = param.getListaFormaPago();
				for (FormaPago fp : listaFormaPago) {
					Secuencia secuencia = new Secuencia();
					codigoMedioPago = Long.parseLong(secuencia.getSecuencia("fondo_seq"));
					String sql1 = " insert into fondo (codigo,  monto ,  tipo,  marca_tarjeta,  estado,documento , numero_documento, fecha)"
							+ "values (?,?,?,?,?,?,?,?)";
					PreparedStatement p1 = c.prepareStatement(sql1);
					p1.setLong(1, codigoMedioPago);
					p1.setLong(2, fp.getImporte());
					p1.setString(3, fp.getMedioPago());
					p1.setString(4, fp.getMarcaTarjeta());
					p1.setString(5, "ACTIVO");
					p1.setString(6, "FACTU");
					p1.setString(7, param.getNumeroFactura());
					p1.setDate(8, new Date(System.currentTimeMillis()));
					p1.execute();
				}
				String sql2 = " update factura_cabecera set estado='COBRADO', timbrado= ?, "
						+ "caja= ?,condicion_compra=?, medio_pago=?, cuotas=?, cajero=?  where numero_factura= ?";
				PreparedStatement p2 = c.prepareStatement(sql2);
				p2.setString(1, param.getTimbrado());
				p2.setString(2, param.getCaja());
				p2.setString(3, param.getCondicionCompra());
				p2.setLong(4, codigoMedioPago);
				p2.setInt(5, (param.getCuotas() == null) ? 0 : param.getCuotas());
				p2.setString(6, param.getCajero());
				p2.setString(7, param.getNumeroFactura());
				p2.execute();
			} else if (param.getCondicionCompra().equals("CREDITO")) {
				String medidaPlazo;
				Integer cantidad;
				Date fechaPago = new Date(System.currentTimeMillis());
				if (param.getPlazo() != null && param.getPlazo() != null) {
					cantidad = buscarNumero(param.getPlazo());
					if (!(param.getPlazo().toUpperCase().indexOf("DIA") == -1)) {
						medidaPlazo = "D";
						fechaPago = sumarFecha(new Date(System.currentTimeMillis()), cantidad, 0);
					} else if (!(param.getPlazo().toUpperCase().indexOf("MES") == -1)) {
						medidaPlazo = "M";
						fechaPago = sumarFecha(new Date(System.currentTimeMillis()), 0, cantidad);
					} else {
						throw new PandaException("No se reconoce el tipo de plazo.");
					}

				}
				if (param.getCuotas() != null && param.getCuotas() > 0) {
					Long montoFraccionado = param.getMonto() / param.getCuotas();
					Long diferencia = param.getMonto() - (param.getCuotas() * montoFraccionado);
					for (int i = 1; i <= param.getCuotas(); i++) {
						Long montoFraccionadoAplicar = 0L;
						if (i == 1) {
							montoFraccionadoAplicar = montoFraccionado + diferencia;
						} else {
							montoFraccionadoAplicar = montoFraccionado;
						}
						String sql3 = "insert into fondo_credito(estado, fecha, fecha_vencimiento, cliente, numero, monto, sucursal, documento, documento_numero)"
								+ "values('PENDIENTE', current_date, ?,?,?,?,?,'FACTU',?)";
						PreparedStatement p3 = c.prepareStatement(sql3);
						p3.setDate(1, fechaPago);
						p3.setInt(2, param.getCliente());
						p3.setString(3, i + " de " + param.getCuotas());
						p3.setLong(4, montoFraccionadoAplicar);
						p3.setString(5, param.getSucursal());
						p3.setString(6, param.getNumeroFactura());
						p3.execute();
					}

				} else {
					String sql3 = "insert into fondo_credito(estado, fecha, fecha_vencimiento, cliente, numero, monto, sucursal, documento, documento_numero)"
							+ "values('PENDIENTE', current_date, ?,?,?,?,?,'FACTU',?)";
					PreparedStatement p3 = c.prepareStatement(sql3);
					p3.setDate(1, fechaPago);
					p3.setInt(2, param.getCliente());
					p3.setString(3, "1 de 1");
					p3.setLong(4, param.getMonto());
					p3.setString(5, param.getSucursal());
					p3.setString(6, param.getNumeroFactura());
					p3.execute();
				}
				// Actualizar las cabeceras cuando es una compra a creadito
				String sql2 = " update factura_cabecera set estado='PENDIENTE_PAGO', timbrado= ?, "
						+ "caja= ?,condicion_compra=?, cuotas=?, cajero=?  where numero_factura= ?";
				PreparedStatement p2 = c.prepareStatement(sql2);
				p2.setString(1, param.getTimbrado());
				p2.setString(2, param.getCaja());
				p2.setString(3, param.getCondicionCompra());
				p2.setInt(4, (param.getCuotas() == null) ? 0 : param.getCuotas());
				p2.setString(5, param.getCajero());
				p2.setString(6, param.getNumeroFactura());
				p2.execute();
			}

			List<FacturaDetalle> listaDetalle = listarDetalle(param.getNumeroFactura());
			for (FacturaDetalle det : listaDetalle) {
				if (!det.getTipo().equals("S")) {
					String sql3 = " update inventario  set cantidad = cantidad -? "
							+ " where codigo = ? and sucursal =? ";
					PreparedStatement p3 = c.prepareStatement(sql3);
					p3.setInt(1, det.getCantidad());
					Integer codigo = Integer.parseInt(det.getCodigoArticulo());
					p3.setInt(2, codigo);
					p3.setString(3, param.getSucursal());
					p3.execute();
				}
			}
			//TODO
			Long secuencia = null;
			String sql4 = " select * from circuito_servicio_cotizacion where numero_factura = ? ";
			PreparedStatement ps4 = c.prepareStatement(sql4);
			ps4.setString(1, param.getNumeroFactura());
			ResultSet rs4 = ps4.executeQuery();
			while(rs4.next()){
				secuencia = rs4.getLong("secuencia");
			}
			if(secuencia != null){
				Long paso= null;
				String sql0 = "select max(paso) from circuito_servicio where secuencia =? ";
	    		PreparedStatement ps0 =c.prepareStatement(sql0);
	    		ps0.setLong(1, secuencia);
	    		ResultSet rs0 = ps0.executeQuery();
	    		while(rs0.next()){
	    			paso= rs0.getLong("max");
	    		}
	    		
	    		String sql5 ="update circuito_servicio set es_ultimo = 'N' where paso=? and secuencia =?";
	    		PreparedStatement ps5 = c.prepareStatement(sql5);
	    		ps5.setLong(1, paso);
	    		ps5.setLong(2, secuencia);
	    		ps5.execute();
	    		
	    		String sql6= "insert into circuito_servicio (secuencia,estado,paso,lugar,responsable,fecha, es_ultimo) "
	    				+ "values (?,?,?,?,?,current_date, 'S');";	
	    		PreparedStatement ps6=c.prepareStatement(sql6); 
	    		ps6.setLong(1, secuencia);
	    		ps6.setString(2,"PAGADO");
	    		ps6.setLong(3, paso+1);
	    		ps6.setString(4, param.getSucursal());
	    		ps6.setString(5, param.getCajero());
	    		ps6.execute();
			}
			c.commit();
		} catch (Exception e) {
			return "error: " + e.getMessage();
		}
		return "ok";
	}

	public Integer buscarNumero(String cadena) {
		String cad = "";
		char[] arreglo = cadena.toCharArray();
		for (char caracter : arreglo) {
			if (Character.isDigit(caracter))
				cad = cad + caracter;
		}
		return Integer.valueOf(cad);
	}

	public Date sumarFecha(Date fecha, Integer dia, Integer mes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DATE, dia);
		calendar.add(Calendar.MONTH, mes);
		java.util.Date resultado = calendar.getTime();
		java.sql.Date sqlDate = new java.sql.Date(resultado.getTime());
		return sqlDate;
	}

	public String anularFactura(String id) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			
			c.setAutoCommit(false);
			FacturaCabecera facturaCabecera = new FacturaCabecera();
			String sql0 = " select * from factura_cabecera  where  numero_factura = ? ";
			PreparedStatement p0 = c.prepareStatement(sql0);
			p0.setString(1, id);
			ResultSet rs0 = p0.executeQuery();
			while(rs0.next()){
				facturaCabecera.setSucursal(rs0.getString("sucursal"));
				facturaCabecera.setCodigoPersona(rs0.getInt("codigo_persona"));
				facturaCabecera.setCliente(rs0.getString("cliente"));
				facturaCabecera.setCondicionCompra(rs0.getString("condicion_compra"));
			}
				
			String sql = " update factura_cabecera set estado = 'ANULADO' where numero_factura = ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, id);
			p.executeUpdate();
			String sql2 = " update fondo_credito set estado = 'ANULADO' where documento = 'FACTU' and documento_numero = ?";
			PreparedStatement p2 = c.prepareStatement(sql2);
			p2.setString(1, id);
			p2.executeUpdate();
			String sql3 = " update fondo set estado = 'ANULADO' where  numero_documento = ? and documento = 'FACTU' ";
			PreparedStatement p3 = c.prepareStatement(sql3);
			p3.setString(1, id);			
			p3.executeUpdate();
			
			String sql4 = " select * from factura_detalle where factura_id = ? ";
			PreparedStatement p4 = c.prepareStatement(sql4);
			p4.setString(1, id);
			p4.executeQuery();
			ResultSet rs4 = p4.executeQuery();
			Long monto = 0L;
			while(rs4.next()){
				monto = monto + rs4.getLong("total");
				String tipo = rs4.getString("tipo");
				if(tipo.equals("P")){
					Integer cantidad = rs4.getInt("cantidad");
					Integer codigoArticulo = rs4.getInt("codigo_articulo");
					String sql5 =" update inventario set cantidad =+? where codigo = ? and sucursal = ? ";
					PreparedStatement p5 = c.prepareStatement(sql5);
					p5.setInt(1, cantidad);
					p5.setInt(2, codigoArticulo);
					p5.setString(3, facturaCabecera.getSucursal());
					p5.executeUpdate();
				}
			}
			if(facturaCabecera.getCondicionCompra().equals("CONTADO")){
				String sql6 = " insert into fondo_debito(estado,fecha,cliente,monto,sucursal,documento,documento_numero, numero)"
						+ " values('PENDIENTE',current_date,?,?,?,'ANUFAC',?,?) ";
				PreparedStatement p6=c.prepareStatement(sql6);
				p6.setInt(1, facturaCabecera.getCodigoPersona());
				p6.setLong(2, monto);
				p6.setString(3, facturaCabecera.getSucursal());
				p6.setString(4, facturaCabecera.getNumeroFactura());
				p6.setInt(5, 1);
				p6.execute();
			}
			c.commit();			
		} catch (Exception e) {
			c.close();
			return "error";
		}
		return "ok";
	}
	
	public boolean editarFactura(Factura factura) throws SQLException{
		Connection c = ObtenerConexion();
		try {			
			c.setAutoCommit(false);
			FacturaCabecera fc = factura.getCabecera();
			List<FacturaDetalle> listaDetalle = factura.getDetalle();
			List<FacturaDetalle> listaDetalleEliminar = factura.getDetalleEliminar();
			String sql1 = " update factura_cabecera set telefono = ? "
					+ " where numero_factura = ? ";
			PreparedStatement ps1 = c.prepareStatement(sql1);
			ps1.setString(1, fc.getTelefono());
			ps1.setString(2, fc.getNumeroFactura());
			ps1.executeUpdate();
			
			for (FacturaDetalle fd : listaDetalle) {
				String sql2 = "insert into factura_detalle(factura_id, codigo_articulo,cantidad, precio, iva, total, impuesto, tipo) "
						+ "values (?,?,?,?,?,?,?,?)";
				PreparedStatement ps2 = c.prepareStatement(sql2);
				ps2.setString(1, fc.getNumeroFactura());
				ps2.setLong(2, Long.parseLong(fd.getCodigoArticulo()));
				ps2.setLong(3, fd.getCantidad());
				ps2.setLong(4, fd.getPrecio());
				ps2.setLong(5, fd.getIva());
				ps2.setLong(6, fd.getTotal());
				ps2.setLong(7, fd.getImpuesto());
				ps2.setString(8, fd.getTipo());
				ps2.execute();
			}
			for(FacturaDetalle fde: listaDetalleEliminar){
				String sql3 = " delete from factura_detalle where factura_id =? and codigo_articulo=?";
				PreparedStatement ps3 = c.prepareStatement(sql3);
				ps3.setString(1, fc.getNumeroFactura());
				ps3.setLong(2, Long.parseLong(fde.getCodigoArticulo()));
				ps3.execute();
			}
			c.commit();	
			c.close();
			return true;
		} catch (Exception e) {
			c.close();
			System.out.println("ERROR: " + e.getMessage());
			return false;
		}		
	}
	
	public List<FacturaDetalle> listarDetalleAprovadoReparacion(Long secuencia){
		List<FacturaDetalle> lista = new ArrayList<FacturaDetalle>();
		try {			
			Connection c= ObtenerConexion();
			String sql = "select a.descripcion, fd.* from factura_detalle fd, (select codigo,descripcion from servicios union "
					+ "select codigo, descripcion from articulos)a "
					+ "where a.codigo = fd.codigo_articulo "
					+ "and fd.factura_id = (select numero_factura from circuito_servicio_cotizacion where secuencia = ?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setLong(1, secuencia);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				FacturaDetalle facturaDetalle= new FacturaDetalle();
				facturaDetalle.setDescripcion(rs.getString("descripcion"));
				facturaDetalle.setFacturaId(rs.getString("factura_id"));
				facturaDetalle.setCantidad(rs.getInt("cantidad"));
				facturaDetalle.setCodigoArticulo(rs.getString("codigo_articulo"));
				facturaDetalle.setPrecio(rs.getInt("precio"));
				facturaDetalle.setIva(rs.getInt("iva"));
				facturaDetalle.setTotal(rs.getInt("total"));
				facturaDetalle.setImpuesto(rs.getInt("impuesto"));
				facturaDetalle.setTipo(rs.getString("tipo"));
				facturaDetalle.setEstado(rs.getString("estado"));
				lista.add(facturaDetalle);
			}
		} catch (Exception e) {
			System.out.println("ERROR :"+e.getMessage());
		}
		return lista;
	}

}
