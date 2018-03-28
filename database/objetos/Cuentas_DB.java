package database.objetos;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import objetos.Comunidades;
import objetos.Cuentas;

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
				cuenta = new Cuentas(query.get_Rs().getInt(1), query.get_Rs().getString(2), query.get_Rs().getString(3), query.get_Rs().getString(4), query.get_Rs().getByte(5), formato_fecha.parse(query.get_Rs().getString(6)).getTime(), Comunidades.get_Comunidades().get(query.get_Rs().getByte(7)), query.get_Rs().getBoolean(8));
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
			
			String valor = null;
			if(query.get_Rs().next())
			{
				valor = query.get_Rs().getString(1);
			}
			cerrar(query);
			return valor;
		}
		catch (final SQLException e){}
		return null;
	}
	
	public String get_Contar_Personajes_Servidor(Cuentas cuenta)
	{
		StringBuilder paquete = new StringBuilder();
		try
		{
			final Ejecucion_Query query = super.ejecutar_Query_Select("SELECT p.servidor_id, COUNT(p.id) FROM personajes p JOIN cuentas c ON p.cuenta_id = c.id WHERE c.usuario = '" + cuenta.get_Usuario() + "' GROUP BY p.servidor_id;");
			
			while(query.get_Rs().next())
			{
				paquete.append('|').append(query.get_Rs().getInt(1)).append(',').append(query.get_Rs().getInt(2));
			}
			cerrar(query);
		}
		catch (final SQLException e){}
		return paquete.toString();
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
	
	public boolean get_Comprobar_Campo_Cuenta_Booleano(final String campo, final String campo_condicion, final String nombre_condicion)
	{
		try
		{
			final Ejecucion_Query query = super.ejecutar_Query_Select("SELECT " + campo + " FROM cuentas WHERE " + campo_condicion + " = '" + nombre_condicion + "';");
			
			query.get_Rs().next();
			boolean comprobacion = query.get_Rs().getBoolean(1);
			cerrar(query);
			return comprobacion;
		}
		catch (final SQLException e){}
		return false;
	}
	
	public String get_Paquete_Buscar_Servidores(final String nombre)
	{
		StringBuilder paquete = new StringBuilder();
		try
		{
			final Ejecucion_Query query = super.ejecutar_Query_Select("SELECT servidor_id, count(p.id) from cuentas c JOIN personajes p ON c.id = p.cuenta_id WHERE c.apodo = '" + nombre + "' or p.nombre = '" + nombre + "' GROUP BY p.servidor_id;");

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
