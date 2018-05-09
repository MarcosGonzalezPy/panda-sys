package com.panda.panda_sys.services.pagos;

import java.sql.Connection;
import java.sql.Date;
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
		if(saldoCliente.getCodigo()!= null){
			sql = sql + " and a.codigo = '"+saldoCliente.getCodigo()+"' ";
		}
		if(saldoCliente.getDocumento()!= null){
			sql = sql + " and a.documento = '"+saldoCliente.getDocumento()+"' ";
		}
		if(saldoCliente.getDocumentoNumero()!= null){
			sql = sql + " and a.documento_numero = "+saldoCliente.getDocumentoNumero()+" ";
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
	
	public String efectivizar(FondoDebito fd) throws SQLException{
		Connection c= ObtenerConexion();
		try {
			Secuencia secuencia= new Secuencia();
			Long sec = Long.parseLong(secuencia.getSecuencia("efectivo_seq"));
			c.setAutoCommit(false);
			String sql1 ="update fondo_debito set estado = 'PAGADO', salida_documento='EFECTIVO', salida_documento_numero=? where documento=? and documento_numero=? ";
			PreparedStatement ps1 = c.prepareStatement(sql1);
			ps1.setLong(1, sec);
			ps1.setString(2, fd.getDocumento());
			ps1.setLong(3, fd.getDocumentoNumero());							
			ps1.execute();				
			if(fd.getDocumento().equals("COMPRA")){
				String sql2 = "update orden_compra_cabecera set estado = 'PAGADO' where codigo=?";
				PreparedStatement ps2 = c.prepareStatement(sql2);
				ps2.setLong(1, fd.getDocumentoNumero());
				ps2.execute();
			}else if(fd.getDocumento().equals("NOTACRE")){
				String sql6 = "update nota_credito_cabecera set estado = 'PAGADO' where numero_factura=?";
				PreparedStatement ps6 = c.prepareStatement(sql6);
				ps6.setString(1, fd.getDocumentoNumero().toString());
				ps6.execute();
			}else{
				throw new PandaException("Clase no parametrizada.");
			}
			c.commit();
			c.close();
		} catch (Exception e) {
			System.out.println("ERROR PANDA: "+e.getMessage());
			c.close();
			return e.getMessage();
		}
		return "OK";
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
				+ "nombre_apellido,codigo, documento, documento_numero,tipo) values "
				+ "(?,?,?,?,current_timestamp,?,?,?,?,?,?,?)";
			PreparedStatement ps2 = c.prepareStatement(sql2);
			ps2.setLong(1, cheque.getMonto());
			ps2.setLong(2, cheque.getNumeroCheque());
			ps2.setString(3, "RETIRADO");
			ps2.setString(4, cheque.getBanco());
			//ps2.setDate(5, new Date(System.currentTimeMillis()));
			String glosa = NumberToLetterConverter.convertNumberToLetter(cheque.getMonto()); 
			ps2.setString(5, glosa);
			ps2.setLong(6, cheque.getCodigoPersona());
			ps2.setString(7, cheque.getNombreApellido());
			ps2.setLong(8, sec);
			ps2.setString(9, cheque.getDocumento());
			ps2.setLong(10, cheque.getDocumentoNumero());
			ps2.setString(11, "EMITIDO");
			ps2.execute();
							
			String sql1 ="update fondo_debito set estado = 'PAGADO', salida_documento='CHEQUE', salida_documento_numero=? where documento=? and documento_numero=? ";
			PreparedStatement ps1 = c.prepareStatement(sql1);
			ps1.setLong(1, sec);
			ps1.setString(2, cheque.getDocumento());
			ps1.setLong(3, cheque.getDocumentoNumero());							
			ps1.execute();		
								
			String sql3 = "update cuenta_bancaria set fondo=fondo-? where banco=? and numero=?";
			PreparedStatement ps3 = c.prepareStatement(sql3);
			ps3.setLong(1, cheque.getMonto());
			ps3.setString(2, cheque.getBanco());
			ps3.setString(3, cheque.getCuentaBancaria());
			ps3.execute();
			
			String sql4 = "insert into movimientos_cuenta_bancaria(estado, codigo_cuenta_bancaria,fecha,usuario,monto, tipo,documento,numero_documento)"
				+ "values('ACTIVO',?,current_timestamp,?,?,?,?,?)";
			PreparedStatement ps4= c.prepareStatement(sql4);
			ps4.setString(1,cheque.getCuentaBancaria());
			ps4.setString(2, usuario);
			ps4.setLong(3, cheque.getMonto());
			ps4.setString(4, "EMISION");
			ps4.setString(5, cheque.getDocumento());
			ps4.setLong(6, cheque.getDocumentoNumero());			
			ps4.execute();
			
			String sql5 = "update numeros_cheque set estado = 'USADO' where numero=? and banco =?" ;
			PreparedStatement ps5 = c.prepareStatement(sql5);
			ps5.setLong(1, cheque.getNumeroCheque());
			ps5.setString(2, cheque.getBanco());
			ps5.execute();
			
			if(cheque.getDocumento().equals("COMPRA")){
				String sql6 = "update orden_compra_cabecera set estado = 'PAGADO' where codigo=?";
				PreparedStatement ps6 = c.prepareStatement(sql6);
				ps6.setLong(1, cheque.getDocumentoNumero());
				ps6.execute();
			}else if(cheque.getDocumento().equals("NOTACRE")){
				String sql6 = "update nota_credito_cabecera set estado = 'PAGADO' where numero_factura=?";
				PreparedStatement ps6 = c.prepareStatement(sql6);
				ps6.setString(1, cheque.getDocumentoNumero().toString());
				ps6.execute();
			}else{
				throw new PandaException("Clase no parametrizada.");
			}
			
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
