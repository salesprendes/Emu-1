package database.objetos;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import main.consola.Consola;
import objetos.entidades.Experiencia;

public class Experiencia_DB extends DatabaseManager
{
	public Experiencia_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	public void get_Cargar_Todas_Experiencia()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM experiencia;");

			while(query.get_Rs().next())
			{
				//nivel(1), personajes(2), oficios(3), dragopavos(4), objevivos(5), alineamientos(6), encarnacion(7)
				Experiencia.get_Experiencias_Cargadas().put(query.get_Rs().getInt(1), new Experiencia(query.get_Rs().getLong(2), query.get_Rs().getInt(3), query.get_Rs().getInt(4), query.get_Rs().getShort(5), query.get_Rs().getShort(6), query.get_Rs().getInt(7)));
			}
			cerrar(query);
		}
		catch (final Exception e)
		{
			Consola.println("ERROR SQL: " + e.toString());
		}
	}
}
