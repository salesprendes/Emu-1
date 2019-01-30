package database.objetos;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import main.consola.Consola;
import objetos.entidades.alineamientos.AlineamientosModelo;
import objetos.entidades.personajes.Dones;
import objetos.entidades.personajes.Especialidades;
import objetos.mapas.Areas;

public class Alineamientos_DB extends DatabaseManager
{
	public Alineamientos_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}

	public void get_Cargar_Todos_Alineamientos()
	{
		Ejecucion_Query query = null;
		
		try
		{
			query = ejecutar_Query_Select("SELECT * FROM alineamientos;");

			while(query.get_Rs().next())
			{
				//Id(1), Area(2), Especial(3)
				new AlineamientosModelo(query.get_Rs().getByte(1), Areas.get_Areas_Cargadas(query.get_Rs().getShort(2)), query.get_Rs().getBoolean(3));
			}
		}
		catch (final Exception e)
		{
			Consola.println("ERROR SQL: " + e.toString());
		}
		finally 
		{
			cerrar(query);
		}
	}
	
	public void get_Cargar_Todas_Especialidades()
	{
		Ejecucion_Query query = null;
		
		try
		{
			query = ejecutar_Query_Select("SELECT * FROM especialidades;");

			while(query.get_Rs().next())
			{
				//id(1), nombre(2), orden(3), nivel(4), dones(5)
				new Especialidades(query.get_Rs().getInt(1), query.get_Rs().getByte(3), query.get_Rs().getByte(4), query.get_Rs().getString(5));
			}
		}
		catch (final Exception e)
		{
			Consola.println("ERROR SQL: " + e.toString());
		}
		finally 
		{
			cerrar(query);
		}
	}
	
	public void get_Cargar_Todos_Dones()
	{
		Ejecucion_Query query = null;
		
		try
		{
			query = ejecutar_Query_Select("SELECT * FROM dones;");

			while(query.get_Rs().next())
			{
				//id(1), stat(2)
				Dones.get_Dones().put(query.get_Rs().getByte(1), query.get_Rs().getShort(3));
			}
			cerrar(query);
		}
		catch (final Exception e)
		{
			Consola.println("ERROR SQL: " + e.toString());
		}
		finally 
		{
			cerrar(query);
		}
	}
}
