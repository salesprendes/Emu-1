package database.objetos;

import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import objetos.Servidores;

public class Servidores_DB extends DatabaseManager
{
	public Servidores_DB(HikariDataSource database_conexion) 
	{
        super(database_conexion);
    }

	public void cargar_Todos_Servidores()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM servidores;");

			while(query.get_Rs().next())
			{
				new Servidores(query.get_Rs().getInt(1), query.get_Rs().getByte(2), query.get_Rs().getByte(3), query.get_Rs().getBoolean(4), query.get_Rs().getString(5), query.get_Rs().getInt(6));
			}
			cerrar(query);
		}
		catch (final SQLException e){}
	}
}
