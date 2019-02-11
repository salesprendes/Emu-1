package database.objetos;

import java.text.SimpleDateFormat;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import main.Configuracion;
import main.consola.Consola;
import objetos.cuentas.Cuentas;

public class Cuentas_DB extends DatabaseManager
{
	final static SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");

	public Cuentas_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}

	public void get_Cargar_Todas()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM cuentas;");

			while(query.get_Rs().next())
			{
				//id(1), apodo(4), uid(5), ip(6), rango_cuenta(7), comunidad(9), baneado(10)
				new Cuentas(query.get_Rs().getInt(1), query.get_Rs().getString(4), query.get_Rs().getString(5), query.get_Rs().getString(6), query.get_Rs().getByte(7), query.get_Rs().getByte(9), query.get_Rs().getBoolean(10));
			}
			cerrar(query);
		}
		catch (final Exception e)
		{
			Consola.println("error sql: " + e);
		}
	}

	public Cuentas get_Cargar_Por_Id(final int id_cuenta)
	{
		try
		{
			Cuentas cuenta = null;
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM cuentas WHERE id = " + id_cuenta + ";");
			if(query.get_Rs().next())
			{
				//id(1), apodo(4), uid(5), ip(6), rango_cuenta(7), comunidad(9), baneado(10)
				cuenta = new Cuentas(query.get_Rs().getInt(1), query.get_Rs().getString(4), query.get_Rs().getString(5), query.get_Rs().getString(6), query.get_Rs().getByte(7), query.get_Rs().getByte(9), query.get_Rs().getBoolean(10));
			}
			cerrar(query);
			return cuenta;
		}
		catch (final Exception e)
		{
			Consola.println("error sql: " + e);
		}
		return null;
	}
	
	public long get_Abono(final int id_cuenta)
	{
		try
		{
			long tiempo = 0;
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT abono FROM cuentas WHERE id = " + id_cuenta + ";");
			if(query.get_Rs().next())
			{
				//id(1), apodo(4), uid(5), ip(6), rango_cuenta(7), tiempo_abono(8), comunidad(9), baneado(10)
				tiempo = formato_fecha.parse(query.get_Rs().getString(1)).getTime();
			}
			cerrar(query);
			return tiempo;
		}
		catch (final Exception e)
		{
			Consola.println("error sql: " + e);
		}
		
		return 0;
	}
	
	public void get_Actualizar_Campo(final String campo, final String valor, final int cuenta_id)
	{
		ejecutar_Update("UPDATE cuentas SET "  + campo + " = '" + valor + "' WHERE id = " + cuenta_id + ";");
	}

	public boolean get_Puede_Migrar(final int id_cuenta) 
	{
		try 
		{
			boolean necesita_migracion = false;
			
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT migracion FROM cuentas WHERE id = " + id_cuenta + ";");

			if(query.get_Rs().next())
				necesita_migracion = query.get_Rs().getBoolean(1);
			
			cerrar(query);
			return necesita_migracion;
		} 
		catch (final Exception e)
		{
			Consola.println("error sql: " + e);
		}
		
		return false;
	}
	
	public byte get_Max_Pjs_Creacion(final int id_cuenta) 
	{
		try 
		{
			byte max_pjs_creacion = 0;
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT personajes_total FROM cuentas WHERE id = " + id_cuenta + ";");

			if(query.get_Rs().next())
				max_pjs_creacion = query.get_Rs().getByte(1);
			
			cerrar(query);
			return max_pjs_creacion;
		} 
		catch(Exception e){}
		
		return 0;
	}
	
	public String get_Lista_Servidores_Otros_Personajes(int cuenta_id) 
	{
		try
		{
			StringBuilder servidores = new StringBuilder();
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT servidor_id FROM personajes WHERE cuenta_id = "+ cuenta_id + " AND NOT servidor_id = " + Configuracion.SERVIDOR_ID + ";");
			
			while(query.get_Rs().next())
			{
				if(servidores.toString().isEmpty())
					servidores.append(query.get_Rs().getInt(1));
				else
					servidores.append(',').append(query.get_Rs().getInt(1));
					
			}
			cerrar(query);
			return servidores.toString();
		} 
		catch(Exception e){}
		
		return "";
	}
}
