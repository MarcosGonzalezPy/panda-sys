package com.panda.panda_sys.services.ventas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.ventas.Cajas;
import com.panda.panda_sys.model.ventas.SaldoCliente;
import com.panda.panda_sys.util.Conexion;

public class SaldoClienteService extends Conexion{
	
	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
	
	public List<SaldoCliente> listar(SaldoCliente saldoCliente) throws SQLException{
		List<SaldoCliente> lista = new ArrayList<SaldoCliente>();
		String sql = " select a.*, b.nombre||' '||b.apellido as nombre from fondo_credito a, personas b "
				+ " where a.cliente = b.codigo ";
		if(saldoCliente.getCliente()!= null){
			sql = sql + " and a.cliente ="+saldoCliente.getCliente()+" ";
		}
		if(saldoCliente.getNombre()!= null){
			sql = sql + " and b.nombre||' '||b.apellido  like '%"+saldoCliente.getNombre()+"%' ";
		}
		if(saldoCliente.getEstado()!= null){
			sql = sql + " and a.estado = '"+saldoCliente.getEstado()+"' ";
		}
		sql = sql + " order by a.codigo desc "; 
		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
        while(rs.next()){
        	SaldoCliente entidad = new SaldoCliente();        
        	entidad.setCodigo(rs.getInt("codigo"));
        	entidad.setEstado(rs.getString("estado"));
        	entidad.setFecha(rs.getDate("fecha"));
        	entidad.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
        	entidad.setNumero(rs.getString("numero"));
        	entidad.setSucursal(rs.getString("sucursal"));
        	entidad.setFechaPago(rs.getDate("fecha_pago"));
        	entidad.setNombre(rs.getString("nombre"));
        	entidad.setDias(rs.getInt("dias"));
        	entidad.setMonto(rs.getInt("monto"));
        	lista.add(entidad);
        }
		return lista;
	}
	

}
