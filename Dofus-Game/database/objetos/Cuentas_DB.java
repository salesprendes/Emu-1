package database.objetos;

import java.text.SimpleDateFormat;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import main.Configuracion;
import objetos.cuentas.Cuentas;

public class Cuentas_DB extends DatabaseManager
{
	final static SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");

	public Cuentas_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}

	public void get_Cargar_Todas_Cuentas()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM cuentas;");

			while(query.get_Rs().next())
			{
				//id(1), apodo(4), uid(5), ip(6), rango_cuenta(7), tiempo_abono(8), comunidad(9), baneado(10)
				new Cuentas(query.get_Rs().getInt(1), query.get_Rs().getString(4), query.get_Rs().getString(5), query.get_Rs().getString(6), query.get_Rs().getByte(7), formato_fecha.parse(query.get_Rs().getString(8)).getTime(), query.get_Rs().getByte(9), query.get_Rs().getBoolean(10));
			}
			cerrar(query);
		}
		catch (final Exception e){}
	}

	public Cuentas get_Cargar_Cuenta_Id(final int id_cuenta)
	{
		Cuentas cuenta = null;
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM cuentas WHERE id = " + id_cuenta + ";");
			if(query.get_Rs().next())
			{
				//id(1), apodo(4), uid(5), ip(6), rango_cuenta(7), tiempo_abono(8), comunidad(9), baneado(10)
				cuenta = new Cuentas(query.get_Rs().getInt(1), query.get_Rs().getString(4), query.get_Rs().getString(5), query.get_Rs().getString(6), query.get_Rs().getByte(7), formato_fecha.parse(query.get_Rs().getString(8)).getTime(), query.get_Rs().getByte(9), query.get_Rs().getBoolean(10));
			}
			cerrar(query);
		}
		catch (final Exception e){}
		return cuenta;
	}
	
	public void get_Actualizar_Campo(final String campo, final String valor, final int cuenta_id)
	{
		ejecutar_Update_Insert("UPDATE cuentas SET "  + campo + " = '" + valor + "' WHERE id = " + cuenta_id + ";");
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
		catch(Exception e){}
		return false;
	}
	
	public String get_Lista_Servidores_Otros_Personajes(int cuenta_id) 
	{
		StringBuilder servidores = new StringBuilder();
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT servidor_id FROM personajes WHERE cuenta_id = "+ cuenta_id + " AND NOT servidor_id = " + Configuracion.SERVIDOR_ID + ";");
			
			while(query.get_Rs().next())
			{
				if(servidores.toString().isEmpty())
					servidores.append(query.get_Rs().getInt(1));
				else
					servidores.append(',').append(query.get_Rs().getInt(1));
					
			}
			cerrar(query);
		} 
		catch(Exception e){}
		return servidores.toString();
	}
}
