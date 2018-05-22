package com.panda.panda_sys.services.catalogo;

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

import javax.print.attribute.standard.NumberOfDocuments;

import com.panda.panda_sys.model.catalogo.CuentasBancarias;
import com.panda.panda_sys.model.catalogo.NumerosCheque;
import com.panda.panda_sys.model.servicios.CircuitoServicioIngreso;
import com.panda.panda_sys.util.Conexion;

public class NumerosChequeService extends Conexion {

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;

	public List<NumerosCheque> listar(NumerosCheque numerosCheque) throws SQLException {
		List<NumerosCheque> lista = new ArrayList<NumerosCheque>();
		String sql = "select * from numeros_cheque";

		if (numerosCheque.getNumeroCuentaBancaria() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " numero_cuenta_bancaria = '" + numerosCheque.getNumeroCuentaBancaria() + "'  ";
		}

		if (numerosCheque.getBanco() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " banco ='" + numerosCheque.getBanco() + "' ";
		}
		if (numerosCheque.getEstado() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " estado = '" + numerosCheque.getEstado() + "' ";
		}

		if (numerosCheque.getNumeroDesde() != null) {
			String conector = null;
			if (sql.contains("where")) {
				conector = " and ";
			} else {
				conector = " where ";
			}
			sql = sql + conector + " numero = " + numerosCheque.getNumeroDesde() + " ";
		}

		Statement statement = con.ObtenerConexion().createStatement();
		rs = statement.executeQuery(sql);
		while (rs.next()) {
			NumerosCheque entidad = new NumerosCheque();
			entidad.setCodigo(Integer.parseInt(rs.getString("codigo")));
			entidad.setEstado(rs.getString("estado"));
			entidad.setBanco(rs.getString("banco"));
			entidad.setNumeroCuentaBancaria(rs.getLong("numero_cuenta_bancaria"));
			entidad.setNumeroDesde(rs.getInt("numero"));
			entidad.setFechaCreacion(rs.getDate("fecha_creacion"));
			entidad.setUsuario(rs.getString("usuario"));
			lista.add(entidad);
		}
		return lista;
	}

	public String insertar(NumerosCheque entidad) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);
			
			for (Integer i = entidad.getNumeroDesde(); i <= entidad.getNumeroHasta(); i++) {
				Integer numeroIterado = i;

				String sql = "insert into numeros_cheque (estado,banco,numero_cuenta_bancaria,numero, fecha_creacion, usuario) "
						+ "values ('DISPONIBLE',?,?,?,CURRENT_TIMESTAMP,?);";
				PreparedStatement ps = c.prepareStatement(sql);
				ps.setString(1, entidad.getBanco());
				ps.setLong(2, entidad.getNumeroCuentaBancaria());
				ps.setInt(3, numeroIterado);
				ps.setString(4, entidad.getUsuario());

				ps.execute();

			}
			c.commit();
			c.close();
			return "OK";
		} catch (Exception e) {
			c.close();
			System.out.println("ERROR " + e.getMessage());
			return e.getMessage();
		}

	}

	public boolean eliminar(String banco,Integer numeroCuentaBancaria,Integer numeroDesde,Integer numeroHasta) throws SQLException {
		try {

			for (Integer i = numeroDesde; i <= numeroHasta; i++) {
				Integer numeroIterado = i;
				
			String sql = "delete from numeros_cheque "
					+ "where banco = '" + banco 
					+ "' and numero_cuenta_bancaria = '" + numeroCuentaBancaria 
					+ "'  and numero = '" + numeroIterado + "'  ";
			Statement stmt = con.ObtenerConexion().createStatement();
			stmt.execute(sql);
			}
			
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return false;
		}
		return true;
	}
 

}
