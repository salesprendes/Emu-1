package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.zaxxer.hikari.HikariDataSource;

import objetos.Cuentas;
import objetos.Servidores;

public class Cuentas_DB extends DatabaseManager
{
	public Cuentas_DB(HikariDataSource source) 
	{
		super(source);
	}
	
	final static SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"); 
	
	public Cuentas get_Cuenta(final String nombre_usuario)
	{
		Cuentas cuenta = null;
		try (final ResultSet rs = conexion_Y_Ejecucion("SELECT * FROM cuentas WHERE usuario = '" + nombre_usuario + "';"))
		{
			rs.next();
			cuenta = new Cuentas(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getByte(5), formato_fecha.parse(rs.getString(6)).getTime(), rs.getByte(7), rs.getBoolean(8));
			cerrar_ResultSet(rs);
		}
		catch (final SQLException | ParseException e){} 
		return cuenta;
	}
	
	public String get_Obtener_Cuenta_Campo_String(final String campo, final String nombre_usuario)
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
	
	public int get_Contar_Personajes_Servidor(Cuentas cuenta, Servidores servidor)
	{
		int total_personajes_servidor = 0;
		try (final ResultSet rs = conexion_Y_Ejecucion("SELECT COUNT(id) FROM personajes WHERE servidor_id = " + servidor.get_Id() + " AND usuario = '" + cuenta.get_Usuario() + "';"))
		{
			rs.next();
			total_personajes_servidor = rs.getInt(1);
			cerrar_ResultSet(rs);
		}
		catch (final SQLException e){}
		return total_personajes_servidor;
	}
	
	public boolean get_Existe_Cuenta(final String nombre_usuario)
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
	
	public boolean get_Cuenta_Baneada(final String nombre_usuario)
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
	
	public boolean get_Apodo_Existe(final String apodo)
	{
		try (final ResultSet rs = conexion_Y_Ejecucion("SELECT * FROM cuentas WHERE apodo = '" + apodo + "';"))
		{
			boolean existe = rs.next();
			cerrar_ResultSet(rs);
			return existe;
		}
		catch (final SQLException e){}
		return false;
	}
	
	public String get_Personaje_Servidores(final String nombre)
	{
		StringBuilder paquete = new StringBuilder();
		try (final ResultSet rs = conexion_Y_Ejecucion("SELECT servidor_id, c.id from cuentas c JOIN personajes p ON c.id = p.cuenta_id WHERE c.apodo = '" + nombre + "' or p.nombre = '" + nombre + "' GROUP BY p.servidor_id;"))
		{
			while(rs.next())
			{
				paquete.append(rs.getString(1)).append(",").append(rs.getString(2)).append(";");
			}
			cerrar_ResultSet(rs);
		}
		catch (final SQLException e){}
		return paquete.toString().isEmpty() ? "null" : paquete.toString();
	}
}
