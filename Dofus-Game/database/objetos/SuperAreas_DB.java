package database.objetos;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import objetos.mapas.SuperAreas;

public class SuperAreas_DB extends DatabaseManager
{
	public SuperAreas_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	public void get_Cargar_Todas_Super_Areas()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM super_areas;");

			while(query.get_Rs().next())
			{
				//id(1)
				new SuperAreas(query.get_Rs().getShort(1));
			}
			cerrar(query);
		}
		catch (final Exception e){}
	}
}
