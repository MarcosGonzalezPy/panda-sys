package com.panda.panda_sys.services.pagos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panda.panda_sys.model.Cheques;
import com.panda.panda_sys.model.ventas.SaldoCliente;
import com.panda.panda_sys.util.Conexion;
import com.panda.panda_sys.util.NumberToLetterConverter;

public class PagosService extends Conexion{
	
	Conexion con;
    Map<String, String> resultado = new HashMap<String, String>();
    ResultSet rs = null;
	
	public List<SaldoCliente> listar(SaldoCliente saldoCliente, String tipo) throws SQLException{
		String from = "";
		String where= "";
		String select = "";
		if(tipo.equals("clientes")){
			select +=", b.nombre||' '||b.apellido as nombre ";
			from+=" , clientes c ";
			where +=" and a.cliente = c.codigo ";
		}else if(tipo.equals("proveedores")){
			select +=" , p.representante_nombre as nombre ";
			from+=" ,  proveedores p ";
			where+=" and a.cliente = p.codigo ";
		}
		String sql = " select a.* "+select+" from fondo_debito a, personas b "+from
				+ " where a.cliente = b.codigo  "+where;
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
		List<SaldoCliente> lista = cargar(rs);
		return lista;
	}
	
	private List<SaldoCliente> cargar(ResultSet rs) throws SQLException{
		List<SaldoCliente> lista = new ArrayList<SaldoCliente>();
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
        	entidad.setDocumento(rs.getString("documento"));
        	entidad.setDocumentoNumero(rs.getString("documento_numero"));
        	lista.add(entidad);
        }
		return lista;
	}
	
	public Boolean generarCheque(Cheques cheque) throws SQLException{
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			String sql1 ="update fondo_debito set estado = 'PAGADO' where documento=? and documento_numero=? ";
			PreparedStatement ps1 = c.prepareStatement(sql1);
			ps1.setString(1, cheque.getDocumento());
			ps1.setString(2, cheque.getDocumentoNumero());
			ps1.execute();
			String sql2 = "insert into cheques (monto,numero_cheque,estado,banco,fecha,glosa,codigo_persona,"
				+ "nombre_apellido,tipo,documento, documento_numero) values "
				+ "(?,?,'RETIRADO',?,current_timestamp,?,?,?,'EMITIDO',?,?)";
			PreparedStatement ps2 = c.prepareStatement(sql2);
			ps2.setLong(1, cheque.getMonto());
			ps2.setLong(2, cheque.getNumeroCheque());
			String glosa = NumberToLetterConverter.convertNumberToLetter(cheque.getMonto()); 
			ps2.setString(3, glosa);
			ps2.setLong(4, cheque.getCodigoPersona());
			ps2.setString(5, cheque.getNombreApellido());
			ps2.setString(6, cheque.getDocumento());
			ps2.setString(7, cheque.getDocumentoNumero());
			ps2.execute();
			c.commit();
			c.close();
		} catch (Exception e) {
			c.close();
			System.out.println("ERROR: "+e.getMessage());
		}
		return true;
	}
}
