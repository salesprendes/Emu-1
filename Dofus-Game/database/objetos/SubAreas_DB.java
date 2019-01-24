package database.objetos;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import objetos.mapas.SubAreas;

public class SubAreas_DB extends DatabaseManager
{
	public SubAreas_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	public void get_Cargar_Todas_Sub_Areas()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM sub_areas;");

			while(query.get_Rs().next())
			{
				//id(1), Nombre(2), es_conquistable(3), area(4)
				new SubAreas(query.get_Rs().getShort(1), query.get_Rs().getBoolean(3), query.get_Rs().getShort(4));
			}
			cerrar(query);
		}
		catch (final Exception e){}
	}
}
