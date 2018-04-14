package com.panda.panda_sys.services.ventas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.panda.panda_sys.model.Cheques;
import com.panda.panda_sys.services.catalogo.CuentasBancariasService;
import com.panda.panda_sys.util.Conexion;

public class MovimientosCuentaBancariaService extends Conexion {

	private ChequesService chequesService;

	private CuentasBancariasService cuentasBancariasService;

	Conexion con;
	Map<String, String> resultado = new HashMap<String, String>();
	ResultSet rs = null;

	public boolean movimientoDepositarCheques(Cheques cheques, String numero, String usuario) throws SQLException {
		Connection c = ObtenerConexion();
		try {
			c.setAutoCommit(false);

			String sql = "insert into movimientos_cuenta_bancaria "
					+ "(estado, codigo_cuenta_bancaria, fecha, usuario, monto, documento, numero_documento, tipo) "
					+ "values (?,?,current_date,?,?,?,?,?);";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "ACTIVO");
			ps.setString(2, numero);
			// ps.setDate(2, current_date);
			ps.setString(3, usuario);
			ps.setInt(4, cheques.getMonto());
			ps.setString(5, "CHEQUE");
			ps.setInt(6, cheques.getCodigo());
			ps.setString(7, "INGRESO");

			ps.execute();

			/* Actualizar el estado dentro de la tabla cheques */
			String sql2 = "update cheques set estado='DEPOSITADO' where codigo = ?";
			PreparedStatement ps2 = c.prepareStatement(sql2);
			ps2.setInt(1, cheques.getCodigo());
			ps2.execute();
			
			/* Actualizar el monto dentro de la tabla cuenta_bancaria */
			String sql3 = "update cuenta_bancaria  set monto = monto + ? where codigo = ?";
			PreparedStatement ps3 = c.prepareStatement(sql3);
			ps3.setInt(1, cheques.getMonto());
			ps3.setInt(2, cheques.getCodigo());
			ps3.execute();

			c.commit();
			c.close();
			return true;
		} catch (Exception e) {
			c.close();
			System.out.println("ERROR " + e.getMessage());
			return false;
		}
	}
}
