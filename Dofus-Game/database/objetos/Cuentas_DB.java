package database.objetos;

import java.text.SimpleDateFormat;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import objetos.Cuentas;

public class Cuentas_DB extends DatabaseManager
{
	public Cuentas_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	final static SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
	
	public Cuentas cargar_Cuenta(final int id_cunta)
	{
		Cuentas cuenta = null;
		try
		{
			final Ejecucion_Query query = super.ejecutar_Query_Select("SELECT id, apodo, rango_cuenta, tiempo_abono, comunidad, baneado FROM cuentas WHERE id = " + id_cunta + ";");
			
			if(query.get_Rs().next())
				cuenta = new Cuentas(query.get_Rs().getInt(1), query.get_Rs().getString(2), query.get_Rs().getByte(3), formato_fecha.parse(query.get_Rs().getString(4)).getTime(), query.get_Rs().getByte(5), query.get_Rs().getBoolean(6));
			cerrar(query);
		}
		catch (final Exception e){}
		return cuenta;
	}
}
