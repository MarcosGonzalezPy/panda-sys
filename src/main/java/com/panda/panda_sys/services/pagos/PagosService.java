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
import com.panda.panda_sys.model.FondoDebito;
import com.panda.panda_sys.model.ventas.SaldoCliente;
import com.panda.panda_sys.util.Conexion;
import com.panda.panda_sys.util.NumberToLetterConverter;
import com.panda.panda_sys.util.PandaException;
import com.panda.panda_sys.util.Secuencia;

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
		String sql = " select a.*, b.codigo as codigo_persona "+select+" from fondo_debito a, personas b "+from
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
        	entidad.setDocumento(rs.getString("documento"));
        	entidad.setMonto(rs.getInt("monto"));
        	entidad.setDocumentoNumero(rs.getString("documento_numero"));
        	entidad.setCodigoPersona(rs.getLong("codigo_persona"));
        	lista.add(entidad);
        }
		return lista;
	}
	
	public String generarCheque(Cheques cheque, String usuario) throws SQLException{
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			String sql0 = "select fondo from cuenta_bancaria where banco=? and numero=?";
			PreparedStatement ps0=c.prepareStatement(sql0);
			ps0.setString(1, cheque.getBanco());
			ps0.setString(2, cheque.getCuentaBancaria());
			ResultSet rs0= ps0.executeQuery();
			Long montoDisponible = 0L;
			while(rs0.next()){
				montoDisponible+=rs0.getLong("fondo");
			}
			if(montoDisponible<cheque.getMonto()){
				throw new PandaException("No existe fondo suficiente en la cuenta.");
			}
			Secuencia secuencia= new Secuencia();
			Long sec = Long.parseLong(secuencia.getSecuencia("nota_debito_cabecera_id_seq"));
			String sql2 = "insert into cheques (monto,numero_cheque,estado,banco,fecha,glosa,codigo_persona,"
				+ "nombre_apellido,tipo,codigo, documento, documento_numero) values "
				+ "(?,?,'RETIRADO',?,current_timestamp,?,?,?,'EMITIDO',?,?,?)";
			PreparedStatement ps2 = c.prepareStatement(sql2);
			ps2.setLong(1, cheque.getMonto());
			ps2.setLong(2, cheque.getNumeroCheque());
			String glosa = NumberToLetterConverter.convertNumberToLetter(cheque.getMonto()); 
			ps2.setString(3, glosa);
			ps2.setLong(4, cheque.getCodigoPersona());
			ps2.setString(5, cheque.getNombreApellido());
			ps2.setLong(6, sec);
			ps2.setString(7, cheque.getDocumento());
			ps2.setLong(8, cheque.getDocumentoNumero());
			ps2.execute();
							
			String sql1 ="update fondo_debito set estado = 'PAGADO', salida_documento='CHEQUE', salida_documento_numero=? where documento=? and documento_numero=? ";
			PreparedStatement ps1 = c.prepareStatement(sql1);
			ps1.setString(1, cheque.getDocumento());
			ps1.setLong(2, cheque.getDocumentoNumero());
			ps1.setLong(3, sec);				
			ps1.execute();		
								
			String sql3 = "update cuenta_bancaria set cantidad=cantidad-? where banco=? and numero=?";
			PreparedStatement ps3 = c.prepareStatement(sql3);
			ps3.setLong(1, cheque.getMonto());
			ps3.setString(2, cheque.getBanco());
			ps3.setString(3, cheque.getCuentaBancaria());
			ps3.execute();
			
			String sql4 = "insert into movimientos_cuenta_bancario(estado, codigo_cuenta_bancaria,fecha,usuario,monto,documento,numero_documento,tipo, documento, documento_numero)"
				+ "values('ACTIVO',?,current_timestamp,?,?,?,?,?)";
			PreparedStatement ps4= c.prepareStatement(sql4);
			ps4.setString(1,cheque.getCuentaBancaria());
			ps4.setString(2, usuario);
			ps4.setLong(3, cheque.getMonto());
			ps4.setString(4, "EMISION");
			ps4.setString(5, cheque.getDocumento());
			ps4.setLong(6, cheque.getDocumentoNumero());
			c.commit();
			c.close();
		} catch (Exception e) {
			c.close();
			System.out.println("ERROR: "+e.getMessage());
			return e.getMessage();
		}
		return "OK";
	}
}
