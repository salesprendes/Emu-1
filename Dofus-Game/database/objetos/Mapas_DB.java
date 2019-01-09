package database.objetos;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import objetos.mapas.Mapas;

public class Mapas_DB extends DatabaseManager
{
	public Mapas_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	public void get_Cargar_Todos_Mapas()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM mapas;");

			while(query.get_Rs().next())
			{
				//id (short 1), fecha (String 2), anchura (byte 3), altura (byte 4), celdas_pelea (String 5)
				//key (String 6), data (String 7), capacidades (short 8), x (short 9), y (short 10), subarea (short 11)
				new Mapas(query.get_Rs().getShort(1), query.get_Rs().getString(2), query.get_Rs().getByte(3), query.get_Rs().getByte(4), query.get_Rs().getString(5), query.get_Rs().getString(6), query.get_Rs().getString(7), query.get_Rs().getShort(8), query.get_Rs().getShort(9), query.get_Rs().getShort(10), query.get_Rs().getShort(11));
			}
			cerrar(query);
		}
		catch (final Exception e)
		{
			System.out.println("ERROR SQL: " + e.toString());
		}
	}
}
