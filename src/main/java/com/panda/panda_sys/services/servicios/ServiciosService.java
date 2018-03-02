package com.panda.panda_sys.services.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.catalogo.Servicios;
import com.panda.panda_sys.model.servicios.CircuitoServicio;
import com.panda.panda_sys.model.servicios.CircuitoServicioCotizacion;
import com.panda.panda_sys.model.servicios.CircuitoServicioIngreso;
import com.panda.panda_sys.model.ventas.FacturaDetalle;
import com.panda.panda_sys.util.Conexion;
import com.panda.panda_sys.util.Secuencia;

public class ServiciosService extends Conexion{
	
	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
    
    public String getSecuencia() throws SQLException{
    	String secuencia = null;
    	String sql = " select nextval('servicio_seq') as secuencia; ";
    	Statement statement = con.ObtenerConexion().createStatement();
    	rs = statement.executeQuery(sql);
		 while(rs.next()){
			 secuencia = rs.getString("secuencia");
		 }
    	return secuencia;
    }
    
    public List<Servicios> listar(Servicios servicios) throws SQLException{
		List<Servicios> lista = new ArrayList<Servicios>();
		String sql = "select * from servicios ";
		if(servicios.getCodigo()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql +conector+ " codigo like '%"+servicios.getCodigo()+"%' ";
		}
		if(servicios.getDescripcion()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " descripcion like '%"+servicios.getDescripcion()+"%' ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	Servicios entidad = new Servicios();
        	entidad.setCodigo(rs.getInt("codigo"));
        	entidad.setDescripcion(rs.getString("descripcion"));
        	entidad.setPrecioUnitario(rs.getInt("precio_unitario"));
        	entidad.setGrabado(rs.getInt("grabado"));
        	entidad.setEstado(rs.getString("estado"));
        	lista.add(entidad);
        }

		return lista;
	}
    
    public boolean ingresarEquipo(CircuitoServicioIngreso entidad) throws SQLException{
    	Connection c = ObtenerConexion();
    	try {
    		c.setAutoCommit(false);
    		String sql = "insert into circuito_servicio (secuencia,estado,paso,lugar,responsable,fecha,observacion,es_ultimo) "
    				+ "values (?,?,?,?,?,current_date,?,'S');";	
    		PreparedStatement ps=c.prepareStatement(sql); 
    		ps.setLong(1, entidad.getSecuencia());
    		ps.setString(2, entidad.getEstado());
    		ps.setLong(3, entidad.getPaso());
    		ps.setString(4, entidad.getLugar());
    		ps.setString(5, entidad.getResponsable());
    		ps.setString(6, entidad.getObservacion());
    		ps.execute();	
    		String sql2= "insert into circuito_servicio_ingreso (secuencia, paso, cliente, correo, encargado,telefono, detalle_equipo, detalle_trabajo, codigo_persona)"
    				+ " values (?,?,?,?,?,?,?,?,?);";
    		PreparedStatement ps2 = c.prepareStatement(sql2);
    		ps2.setLong(1, entidad.getSecuencia());
    		ps2.setLong(2, entidad.getPaso());
    		ps2.setString(3, entidad.getCliente());
    		ps2.setString(4, entidad.getCorreo());
    		ps2.setString(5, entidad.getEncargado().toUpperCase());
    		ps2.setString(6, entidad.getTelefono());
    		ps2.setString(7, entidad.getDetalleEquipo());
    		ps2.setString(8, entidad.getDetalleTrabajo());
    		ps2.setLong(9, entidad.getCodigoPersona());
    		ps2.execute();
    		c.commit();
    		c.close();
    		return true;
		} catch (Exception e) {
			c.close();
			System.out.println("ERROR "+e.getMessage());
			return false;
		}
		
	}

    public List<CircuitoServicio> listarCircuito(CircuitoServicioIngreso circuitoServicioIngreso, String aux) throws SQLException{
		List<CircuitoServicio> lista = new ArrayList<CircuitoServicio>();
		String sql = "select cs.secuencia, cs.estado, cs.paso , cs.lugar, cs.responsable, cs.fecha, cs.observacion from  circuito_servicio cs, "
			+ "circuito_servicio_ingreso csi where cs.secuencia = csi.secuencia"
			+ " and cs.es_ultimo = 'S' ";
		if(aux!= null && !aux.equals("")){
			sql = sql +aux;
		} 
		if(circuitoServicioIngreso.getEstado()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql +conector+ " cs.estado = '"+ circuitoServicioIngreso.getEstado()+"' ";
		}
		if(circuitoServicioIngreso.getCliente()!= null){
			String conector = null;
			if(sql.contains("where")){
				conector = " and ";
			}else{
				conector = " where ";
			}
			sql = sql+conector + " upper(csi.cliente) like UPPER('%"+circuitoServicioIngreso.getCliente()+"%') ";
		}
		Statement statement = con.ObtenerConexion().createStatement();
		sql +=" order by secuencia desc ";
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	CircuitoServicio entidad = new CircuitoServicio();
        	entidad.setSecuencia(rs.getLong("secuencia"));
        	entidad.setEstado(rs.getString("estado"));
        	entidad.setPaso(rs.getLong("paso"));
        	entidad.setLugar(rs.getString("lugar"));
        	entidad.setResponsable(rs.getString("responsable"));
        	entidad.setFecha(rs.getString("fecha"));    
        	entidad.setObservacion(rs.getString("observacion"));
        	lista.add(entidad);
        }

		return lista;
	}
    
    public CircuitoServicioIngreso obtenerCircuitoServicioIngreso(Long secuencia) throws SQLException{
    	CircuitoServicioIngreso entidad= new CircuitoServicioIngreso();
    	Connection c= ObtenerConexion();
    	try{    		
			String sql = "select * from circuito_servicio_ingreso where paso =1 and secuencia =? ";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setLong(1, secuencia);
			ResultSet rs= ps.executeQuery();
			while(rs.next()){
				entidad.setSecuencia(rs.getLong("secuencia"));
				entidad.setPaso(rs.getLong("paso"));
				entidad.setCliente(rs.getString("cliente"));
				entidad.setCorreo(rs.getString("correo"));
				entidad.setEncargado(rs.getString("encargado"));											
				entidad.setTelefono(rs.getString("telefono"));
    			entidad.setDetalleEquipo(rs.getString("detalle_equipo"));
    			entidad.setDetalleTrabajo(rs.getString("detalle_trabajo"));
    			entidad.setCodigoPersona(rs.getLong("codigo_persona"));	
			}
			c.close();
		} catch (Exception e) {
			c.close();
			System.out.println("Error: "+e.getMessage());
		}
    	return entidad;
    }
    
    
//    public CircuitoServicioIngreso obtenerIngreso(Long secuencia) throws SQLException{
//    	CircuitoServicioIngreso entidad = new CircuitoServicioIngreso();
//    	Connection c = ObtenerConexion();
//    	try {
//    		String sql = " select  cs.observacion, p.nombre ||' '||p.apellido \"cliente_nombre_apellido\","
//    				+ " pp.nombre ||' '||pp.apellido \"encargado_nombre_apellido\",c.* "
//    				+ " from personas p, circuito_servicio_ingreso c, personas pp, circuito_servicio cs "
//    				+ " where c.cliente = p.codigo  and c.encargado = pp.codigo "
//    				+ " and cs.secuencia = c.secuencia and c.secuencia = ? ";
//    		PreparedStatement ps =c.prepareStatement(sql);
//    		ps.setLong(1, secuencia);
//    		ResultSet rs = ps.executeQuery();
//    		while(rs.next()){
//    			entidad.setCliente(rs.getString("cliente"));
//    			entidad.setCodigoPersona(rs.getLong("cliente_nombre_apellido"));
//    			entidad.setEncargado(rs.getString("encargado"));
//    			entidad.setCorreo(rs.getString("correo"));
//    			entidad.setTelefono(rs.getString("telefono"));
//    			entidad.setDetalleEquipo(rs.getString("detalle_equipo"));
//    			entidad.setDetalleTrabajo(rs.getString("detalle_trabajo"));
//    			entidad.setObservacion(rs.getString("observacion"));
//    		}
//    		c.close();    		
//		} catch (Exception e) {
//			System.out.println("ERROR "+e.getMessage());
//			c.close();
//		}
//    	return entidad;
//    }
    
	public boolean insertarServicio(Servicios servicios) throws SQLException {
		try {
			String sql = "insert into servicios ( descripcion, precio_unitario, grabado, estado) " + "values ( UPPER('" + servicios.getDescripcion() + "') ,('"
					+ servicios.getPrecioUnitario() + "'),('" + servicios.getGrabado() + "'), UPPER('"
					+ servicios.getEstado() + "') );";
			Statement statement = con.ObtenerConexion().createStatement();
			statement.execute(sql);
		} catch (Exception e) {
			System.out.println("ERROR: " +e.getMessage());
			return false;
		}

		return true;
	}

	public boolean eliminarServicio(Integer codigo) throws SQLException {
		String sql = "delete from servicios where codigo = '" + codigo + "'  ";
		Statement stmt = con.ObtenerConexion().createStatement();
		stmt.execute(sql);
		return true;
	}

	public boolean modificarServicios(Servicios servicios) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			String sql = "update servicios set descripcion = UPPER( ? ), estado = ?" + "where codigo = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, servicios.getDescripcion());
			ps.setString(2, servicios.getEstado());
			ps.setInt(3, servicios.getCodigo());
			ps.execute();

			c.close();
			return true;
		} catch (Exception e) {
			c.close();
			return false;
		}
	}
	
	
	public Boolean crearCotizacion(CircuitoServicioCotizacion entidad) throws SQLException{
		CircuitoServicio cs = entidad.getCircuitoServicio();
		List<FacturaDetalle> fd = entidad.getListaDetalle();
    	Connection c = ObtenerConexion();
    	Long paso=0L;
    	try {
    		c.setAutoCommit(false);
    		String sql0 = "select max(paso) from circuito_servicio where secuencia =? ";
    		PreparedStatement ps0 =c.prepareStatement(sql0);
    		ps0.setLong(1, cs.getSecuencia());
    		ResultSet rs0 = ps0.executeQuery();
    		while(rs0.next()){
    			paso= rs0.getLong("max")+1;
    		}
    		
    		
    		String sql = "insert into circuito_servicio (secuencia,estado,paso,lugar,responsable,fecha,observacion,es_ultimo) "
    				+ "values (?,?,?,?,?,current_date,?, 'S');";	
    		PreparedStatement ps=c.prepareStatement(sql); 
    		ps.setLong(1, cs.getSecuencia());
    		ps.setString(2,"COTIZADO");
    		ps.setLong(3, paso);
    		ps.setString(4, cs.getLugar());
    		ps.setString(5, cs.getResponsable());
    		ps.setString(6, cs.getObservacion());
    		ps.execute();
    		
    		Secuencia secuencia = new Secuencia();
    		String numeroFactura = secuencia.getSecuencia("factura_seq");
    		String sql2 = "insert into circuito_servicio_cotizacion (secuencia,paso,numero_factura) values(?,?,?)";
    		PreparedStatement ps2 = c.prepareStatement(sql2);
    		ps2.setLong(1, cs.getSecuencia());
    		ps2.setLong(2,paso);
    		ps2.setString(3,numeroFactura);
    		ps2.execute();
    		
    		String sql3 = "insert into factura_detalle (factura_id, codigo_articulo, cantidad, precio, iva, total, impuesto, tipo, estado) values (?,?,?,?,?,?,?,?,?)";
    		for(FacturaDetalle det: fd){
    			PreparedStatement ps3 =c.prepareStatement(sql3);
    			ps3.setString(1, numeroFactura);
    			ps3.setInt(2, Integer.parseInt(det.getCodigoArticulo()));
    			ps3.setLong(3, det.getCantidad());
    			ps3.setLong(4, det.getPrecio());
    			ps3.setLong(5, det.getIva());
    			ps3.setLong(6, det.getTotal());
    			ps3.setLong(7, det.getImpuesto());
    			ps3.setString(8, det.getTipo());
    			ps3.setString(9, "PENDIENTE");
    			ps3.execute();
    		}
    		
    		String sql4 ="update circuito_servicio set es_ultimo = 'N' where paso=? and secuencia =?";
    		PreparedStatement ps4 = c.prepareStatement(sql4);
    		ps4.setLong(1, paso-1);
    		ps4.setLong(2, cs.getSecuencia());
    		ps4.execute();
    		c.commit();
    		c.close();
    		return true;
    	}catch(Exception e){
			c.close();
			return false;
		}
	}
	
	public Boolean aprobar(CircuitoServicio cs) throws SQLException{
		Long secuencia = cs.getSecuencia();
		Connection c = ObtenerConexion();
		Long paso=0L;
		String numeroFactura = "";
		try {
			c.setAutoCommit(false);
    		String sql0 = "select max(paso) from circuito_servicio where secuencia =? ";
    		PreparedStatement ps0 =c.prepareStatement(sql0);
    		ps0.setLong(1, secuencia);
    		ResultSet rs0 = ps0.executeQuery();
    		while(rs0.next()){
    			paso= rs0.getLong("max")+1;
    		}
    		
    		String sql = "insert into circuito_servicio (secuencia,estado,paso,lugar,responsable,fecha, es_ultimo) "
    				+ "values (?,?,?,?,?,current_date, 'S');";	
    		PreparedStatement ps=c.prepareStatement(sql); 
    		ps.setLong(1, secuencia);
    		ps.setString(2,"APROBADO");
    		ps.setLong(3, paso);
    		ps.setString(4, cs.getLugar());
    		ps.setString(5, cs.getResponsable());
    		ps.execute();
    		
    		String sql3 = "select * from circuito_servicio_cotizacion where secuencia =? ";
    		PreparedStatement ps3 =c.prepareStatement(sql3);
    		ps3.setLong(1,secuencia);
    		ResultSet rs3 = ps3.executeQuery();
    		while(rs3.next()){
    			numeroFactura=rs3.getString("numero_factura");
    		}
    		
    		String sql2 = "update factura_detalle set estado = 'APROBADO' where factura_id = ? ";
    		PreparedStatement ps2=c.prepareStatement(sql2);
    		ps2.setString(1,numeroFactura);
    		ps2.execute();
    		

    		String sql4 ="update circuito_servicio set es_ultimo = 'N' where paso=? and secuencia =?";
    		PreparedStatement ps4 = c.prepareStatement(sql4);
    		ps4.setLong(1, paso-1);
    		ps4.setLong(2, cs.getSecuencia());
    		ps4.execute();
			c.commit();			
			c.close();
		} catch (Exception e) {
			c.close();
			return false;
		}
		return true;
	}
	
	public Boolean reparar(CircuitoServicio cs) throws SQLException{
		Long secuencia = cs.getSecuencia();
		Connection c = ObtenerConexion();
		Long paso=0L;
		String numeroFactura = "";
		try {
			c.setAutoCommit(false);
    		String sql0 = "select max(paso) from circuito_servicio where secuencia =? ";
    		PreparedStatement ps0 =c.prepareStatement(sql0);
    		ps0.setLong(1, secuencia);
    		ResultSet rs0 = ps0.executeQuery();
    		while(rs0.next()){
    			paso= rs0.getLong("max")+1;
    		}
    		
    		String sql = "insert into circuito_servicio (secuencia,estado,paso,lugar,responsable,fecha, es_ultimo) "
    				+ "values (?,?,?,?,?,current_date, 'S');";	
    		PreparedStatement ps=c.prepareStatement(sql); 
    		ps.setLong(1, secuencia);
    		ps.setString(2,"REPARADO");
    		ps.setLong(3, paso);
    		ps.setString(4, cs.getLugar());
    		ps.setString(5, cs.getResponsable());
    		ps.execute();
    		
    		String sql3 = "select * from circuito_servicio_cotizacion where secuencia =? ";
    		PreparedStatement ps3 =c.prepareStatement(sql3);
    		ps3.setLong(1,secuencia);
    		ResultSet rs3 = ps3.executeQuery();
    		while(rs3.next()){
    			numeroFactura=rs3.getString("numero_factura");
    		}
    		
    		String sql2 = "update factura_detalle set estado = 'REPARADO' where factura_id = ? ";
    		PreparedStatement ps2=c.prepareStatement(sql2);
    		ps2.setString(1,numeroFactura);
    		ps2.execute();
    		

    		String sql4 ="update circuito_servicio set es_ultimo = 'N' where paso=? and secuencia =?";
    		PreparedStatement ps4 = c.prepareStatement(sql4);
    		ps4.setLong(1, paso-1);
    		ps4.setLong(2, cs.getSecuencia());
    		ps4.execute();
			c.commit();			
			c.close();
		} catch (Exception e) {
			c.close();
			return false;
		}
		return true;
	}
	
	public Boolean rechazar(CircuitoServicio cs) throws SQLException{
		Long secuencia = cs.getSecuencia();
		Connection c = ObtenerConexion();
		Long paso=0L;
		String numeroFactura = "";
		try {
			c.setAutoCommit(false);
    		String sql0 = "select max(paso) from circuito_servicio where secuencia =? ";
    		PreparedStatement ps0 =c.prepareStatement(sql0);
    		ps0.setLong(1, secuencia);
    		ResultSet rs0 = ps0.executeQuery();
    		while(rs0.next()){
    			paso= rs0.getLong("max")+1;
    		}
    		
    		String sql = "insert into circuito_servicio (secuencia,estado,paso,lugar,responsable,fecha, es_ultimo) "
    				+ "values (?,?,?,?,?,current_date, 'S');";	
    		PreparedStatement ps=c.prepareStatement(sql); 
    		ps.setLong(1, secuencia);
    		ps.setString(2,"RECHAZADO");
    		ps.setLong(3, paso);
    		ps.setString(4, cs.getLugar());
    		ps.setString(5, cs.getResponsable());
    		ps.execute();
    		
    		String sql3 = "select * from circuito_servicio_cotizacion where secuencia =? ";
    		PreparedStatement ps3 =c.prepareStatement(sql3);
    		ps3.setLong(1,secuencia);
    		ResultSet rs3 = ps3.executeQuery();
    		while(rs3.next()){
    			numeroFactura=rs3.getString("numero_factura");
    		}
    		
    		String sql2 = "update factura_detalle set estado = 'RECHAZADO' where factura_id = ? ";
    		PreparedStatement ps2=c.prepareStatement(sql2);
    		ps2.setString(1,numeroFactura);
    		ps2.execute();
    		

    		String sql4 ="update circuito_servicio set es_ultimo = 'N' where paso=? and secuencia =?";
    		PreparedStatement ps4 = c.prepareStatement(sql4);
    		ps4.setLong(1, paso-1);
    		ps4.setLong(2, cs.getSecuencia());
    		ps4.execute();
			c.commit();			
			c.close();
		} catch (Exception e) {
			c.close();
			return false;
		}
		return true;
	}

	
	public Boolean anularCircuito(Long secuencia) throws SQLException{
		Connection c= ObtenerConexion();
		try {
			c.setAutoCommit(false);
			//TODO
			c.commit();
			c.close();
		} catch (Exception e) {
			c.close();
			System.out.println("Error al anular el servicio: "+e.getMessage());
		}
		return true;
	}

}
