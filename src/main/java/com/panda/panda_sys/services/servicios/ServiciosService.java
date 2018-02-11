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
import com.panda.panda_sys.model.servicios.CircuitoServicioIngreso;
import com.panda.panda_sys.util.Conexion;

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
        	entidad.setCodigo(rs.getString("codigo"));
        	entidad.setDescripcion(rs.getString("descripcion"));
        	entidad.setPrecioUnitario(rs.getString("precio_unitario"));
        	entidad.setPrecioUnitario(rs.getString("precio_unitario"));
        	lista.add(entidad);
        }

		return lista;
	}
    
    public boolean ingresarEquipo(CircuitoServicioIngreso entidad) throws SQLException{
    	Connection c = ObtenerConexion();
    	try {
    		c.setAutoCommit(false);
    		String sql = "insert into circuito_servicio (secuencia,estado,paso,lugar,responsable,fecha,observacion) "
    				+ "values (?,?,?,?,?,current_date,?);";	
    		PreparedStatement ps=c.prepareStatement(sql); 
    		ps.setInt(1, Integer.parseInt(entidad.getSecuencia()));
    		ps.setString(2, entidad.getEstado());
    		ps.setInt(3,  Integer.parseInt(entidad.getPaso()));
    		ps.setString(4, entidad.getLugar());
    		ps.setString(5, entidad.getResponsable());
    		ps.setString(6, entidad.getObservacion());
    		ps.execute();	
    		String sql2= "insert into circuito_servicio_ingreso (secuencia, paso, cliente, correo, encargado,telefono, detalle_equipo, detalle_trabajo)"
    				+ " values (?,?,?,?,?,?,?,?);";
    		PreparedStatement ps2 = c.prepareStatement(sql2);
    		ps2.setInt(1, Integer.parseInt(entidad.getSecuencia()));
    		ps2.setInt(2, Integer.parseInt(entidad.getPaso()));
    		ps2.setLong(3, entidad.getCliente());
    		ps2.setString(4, entidad.getCorreo());
    		ps2.setLong(5, entidad.getEncargado());
    		ps2.setString(6, entidad.getTelefono());
    		ps2.setString(7, entidad.getDetalleEquipo());
    		ps2.setString(8, entidad.getDetalleTrabajo());
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
		String sql = "select cs.* from circuito_servicio cs, circuito_servicio_ingreso csi where cs.secuencia = csi.secuencia ";
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
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	CircuitoServicio entidad = new CircuitoServicio();
        	entidad.setSecuencia(rs.getString("secuencia"));
        	entidad.setEstado(rs.getString("estado"));
        	entidad.setPaso(rs.getString("paso"));
        	entidad.setLugar(rs.getString("lugar"));
        	entidad.setResponsable(rs.getString("responsable"));
        	entidad.setFecha(rs.getString("fecha"));    
        	entidad.setObservacion(rs.getString("observacion"));
        	lista.add(entidad);
        }

		return lista;
	}
    
    public boolean cotizacion(CircuitoServicioIngreso entidad) throws SQLException{
    	Statement statement = con.ObtenerConexion().createStatement();
    	try {    	
    		String sql = "insert into circuito_servicio (secuencia,estado,paso,lugar,responsable,fecha,observacion) "
    				+ "values ( "
    				+ " "+entidad.getSecuencia()+" ,"
    				+ " 'PENDIENTE_APROVACION',"
    				+ " '"+entidad.getPaso()+"',"
    				+ "'"+entidad.getLugar()+"',"
    				+ "'"+entidad.getResponsable()+"',"
    				+ "'"+new Date(System.currentTimeMillis())+"',"
    				+ "'"+entidad.getObservacion()+"'"
    				+ ");";
    		
    		statement.execute(sql);	
	
    		return true;
		} catch (Exception e) {
			return false;
		}
		
	}

    public CircuitoServicioIngreso obtenerIngreso(Long secuencia) throws SQLException{
    	CircuitoServicioIngreso entidad = new CircuitoServicioIngreso();
    	Connection c = ObtenerConexion();
    	try {
    		String sql = " select  cs.observacion, p.nombre ||' '||p.apellido \"cliente_nombre_apellido\","
    				+ " pp.nombre ||' '||pp.apellido \"encargado_nombre_apellido\",c.* "
    				+ " from personas p, circuito_servicio_ingreso c, personas pp, circuito_servicio cs "
    				+ " where c.cliente = p.codigo  and c.encargado = pp.codigo "
    				+ " and cs.secuencia = c.secuencia and c.secuencia = ? ";
    		PreparedStatement ps =c.prepareStatement(sql);
    		ps.setLong(1, secuencia);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()){
    			entidad.setCliente(rs.getLong("cliente"));
    			entidad.setClienteNombreApellido(rs.getString("cliente_nombre_apellido"));
    			entidad.setEncargado(rs.getLong("encargado"));
    			entidad.setEncargadoNombreApellido("encargado_nombre_apellido");
    			entidad.setCorreo(rs.getString("correo"));
    			entidad.setTelefono(rs.getString("telefono"));
    			entidad.setDetalleEquipo(rs.getString("detalle_equipo"));
    			entidad.setDetalleTrabajo(rs.getString("detalle_trabajo"));
    			entidad.setObservacion(rs.getString("observacion"));
    		}
    		c.close();    		
		} catch (Exception e) {
			System.out.println("ERROR "+e.getMessage());
			c.close();
		}
    	return entidad;
    }

}
