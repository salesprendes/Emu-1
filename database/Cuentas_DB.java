package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import objetos.Cuentas;

public class Cuentas_DB extends DatabaseManager
{
	final static SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"); 
	
	public static Cuentas get_Cuenta(final String nombre_usuario)
	{
		Cuentas cuenta = null;
		try (final ResultSet rs = conexion_Y_Ejecucion("SELECT * FROM cuentas WHERE usuario = '" + nombre_usuario + "';"))
		{
			rs.next();
			cuenta = new Cuentas(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), formato_fecha.parse(rs.getString(5)).getTime(), rs.getBoolean(6));
			cerrar_ResultSet(rs);
		}
		catch (final SQLException | ParseException e){} 
		return cuenta;
	}
	
	public static String get_Existe_Cuenta_Campo_String(final String campo, final String nombre_usuario)
	{
		try (final ResultSet rs = conexion_Y_Ejecucion("SELECT " + campo + " FROM cuentas WHERE usuario = '" + nombre_usuario + "';"))
		{
			rs.next();
			String valor = rs.getString(1);
			cerrar_ResultSet(rs);
			return valor;
		}
		catch (final SQLException e){}
		return null;
	}
	
	public static boolean get_Existe_Cuenta(final String nombre_usuario)
	{
		try (final ResultSet rs = conexion_Y_Ejecucion("SELECT usuario FROM cuentas WHERE usuario = '" + nombre_usuario + "';"))
		{
			boolean existe_cuenta = rs.next();
			cerrar_ResultSet(rs);
			return existe_cuenta;
		}
		catch (final SQLException e){}
		return false;
	}
	
	public static boolean get_Cuenta_Baneada(final String nombre_usuario)
	{
		try (final ResultSet rs = conexion_Y_Ejecucion("SELECT baneado FROM cuentas WHERE usuario = '" + nombre_usuario + "';"))
		{
			rs.next();
			boolean cuenta_baneada = rs.getBoolean(1);
			cerrar_ResultSet(rs);
			return cuenta_baneada;
		}
		catch (final SQLException e){}
		return false;
	}
	
	public static boolean eliminar_Cuenta_Id(final int id)
	{
		try (final PreparedStatement p = database_conexion.prepareStatement("DELETE FROM cuentas WHERE id = " + id + ";"))
		{
			p.executeUpdate();
			cerrar_PreparedStatement(p);
			return true;
		}
		catch (final SQLException e){}
		return false;
	}
}
