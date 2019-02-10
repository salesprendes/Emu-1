package database.objetos;

import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import objetos.Comunidades;

public class Comunidades_DB extends DatabaseManager
{
	public Comunidades_DB(HikariDataSource database_conexion) 
	{
        super(database_conexion);
    }
	
	public void cargar_Todas_Comunidades()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM comunidades;");

			while(query.get_Rs().next())
			{
				new Comunidades(query.get_Rs().getByte(1), query.get_Rs().getString(2));
			}
			cerrar(query);
		}
		catch (final SQLException e){}
	}
}
