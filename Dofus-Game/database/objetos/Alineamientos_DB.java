package database.objetos;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import objetos.entidades.alineamientos.AlineamientosModelo;
import objetos.mapas.Areas;

public class Alineamientos_DB extends DatabaseManager
{
	public Alineamientos_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}

	public void get_Cargar_Todos_Alineamientos()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM alineamientos;");

			while(query.get_Rs().next())
			{
				//Id(1), Area(2), Especial(3)
				new AlineamientosModelo(query.get_Rs().getByte(1), Areas.get_Areas_Cargadas(query.get_Rs().getShort(2)), query.get_Rs().getBoolean(3));
			}
			cerrar(query);
		}
		catch (final Exception e){}
	}
	
	public void get_Cargar_Todos_Dones()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM dones;");

			while(query.get_Rs().next())
			{
				//Id(1), Area(2), Especial(3)
				new AlineamientosModelo(query.get_Rs().getByte(1), Areas.get_Areas_Cargadas(query.get_Rs().getShort(2)), query.get_Rs().getBoolean(3));
			}
			cerrar(query);
		}
		catch (final Exception e){}
	}
}
