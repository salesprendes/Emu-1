package database;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.zaxxer.hikari.HikariDataSource;

import objetos.Cuentas;
import objetos.Servidores;

public class Cuentas_DB extends DatabaseManager
{
	public Cuentas_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	final static SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"); 
	
	public Cuentas cargar_Cuenta(final String nombre_usuario)
	{
		Cuentas cuenta = null;
		try
		{
			final Ejecucion_Query query = super.ejecutar_Query_Select("SELECT * FROM cuentas WHERE usuario = '" + nombre_usuario + "';");
			
			if(query.get_Rs().next())
				cuenta = new Cuentas(query.get_Rs().getInt(1), query.get_Rs().getString(2), query.get_Rs().getString(3), query.get_Rs().getString(4), query.get_Rs().getByte(5), formato_fecha.parse(query.get_Rs().getString(6)).getTime(), query.get_Rs().getByte(7), query.get_Rs().getBoolean(8));
			cerrar(query);
		}
		catch (final Exception e){}
		return cuenta;
	}
	
	public String get_Obtener_Cuenta_Campo_String(final String campo, final String nombre_usuario)
	{
		try
		{
			final Ejecucion_Query query = super.ejecutar_Query_Select("SELECT " + campo + " FROM cuentas WHERE usuario = '" + nombre_usuario + "';");
			
			query.get_Rs().next();
			String valor = query.get_Rs().getString(1);
			cerrar(query);
			return valor;
		}
		catch (final SQLException e){}
		return null;
	}
	
	public int get_Contar_Personajes_Servidor(Cuentas cuenta, Servidores servidor)
	{
		int total_personajes_servidor = 0;
		try
		{
			final Ejecucion_Query query = super.ejecutar_Query_Select("SELECT COUNT(id) FROM personajes WHERE servidor_id = " + servidor.get_Id() + " AND usuario = '" + cuenta.get_Usuario() + "';");
			
			query.get_Rs().next();
			total_personajes_servidor = query.get_Rs().getInt(1);
			cerrar(query);
		}
		catch (final SQLException e){}
		return total_personajes_servidor;
	}
	
	public boolean get_Existe_Campo_Cuenta(final String campo, final String campo_condicion, final String nombre_condicion)
	{
		try
		{
			final Ejecucion_Query query = super.ejecutar_Query_Select("SELECT " + campo + " FROM cuentas WHERE " + campo_condicion + " = '" + nombre_condicion + "';");
			
			boolean existe = query.get_Rs().next();
			cerrar(query);
			return existe;
		}
		catch (final SQLException e){}
		return false;
	}
	
	public String get_Paquete_Buscar_Servidores(final String nombre)
	{
		StringBuilder paquete = new StringBuilder();
		try
		{
			final Ejecucion_Query query = super.ejecutar_Query_Select("SELECT servidor_id, c.id from cuentas c JOIN personajes p ON c.id = p.cuenta_id WHERE c.apodo = '" + nombre + "' or p.nombre = '" + nombre + "' GROUP BY p.servidor_id;");
			
			while(query.get_Rs().next())
			{
				paquete.append(query.get_Rs().getString(1)).append(",").append(query.get_Rs().getString(2)).append(";");
			}
			cerrar(query);
		}
		catch (final SQLException e){}
		return paquete.toString().isEmpty() ? "null" : paquete.toString();
	}
}
