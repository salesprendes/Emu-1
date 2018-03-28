package database.objetos;

import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import objetos.Comunidades;
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
			final Ejecucion_Query query = super.ejecutar_Query_Select("SELECT * FROM servidores;");

			while(query.get_Rs().next())
			{
				new Servidores(query.get_Rs().getInt(1), query.get_Rs().getString(2), Comunidades.get_Comunidades().get(query.get_Rs().getByte(3)), query.get_Rs().getByte(4), query.get_Rs().getString(5), query.get_Rs().getString(6), query.get_Rs().getString(7), query.get_Rs().getString(8), query.get_Rs().getString(9));
			}
			cerrar(query);
		}
		catch (final SQLException e){}
	}
}