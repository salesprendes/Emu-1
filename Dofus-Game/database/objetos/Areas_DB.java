package database.objetos;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import objetos.mapas.Areas;

public class Areas_DB extends DatabaseManager
{
	public Areas_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	public void get_Cargar_Todas_Areas()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM areas;");

			while(query.get_Rs().next())
			{
				//id(1), Nombre(2), superareaID (3), cementerio(4)
				final Areas area = new Areas(query.get_Rs().getShort(1), query.get_Rs().getShort(3), query.get_Rs().getString(4));
				area.get_Super_area().get_Agregar_Area(area);
			}
			cerrar(query);
		}
		catch (final Exception e){}
	}
}
