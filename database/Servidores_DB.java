package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import objetos.Servidores;

public class Servidores_DB extends DatabaseManager
{
	public Servidores_DB(HikariDataSource source) 
	{
        super(source);
    }
	
	public void cargar_Todos_Servidores()
	{
		try (final ResultSet rs = conexion_Y_Ejecucion("SELECT * FROM servidores;"))
		{
			while(rs.next())
			{
				new Servidores(rs.getInt(1), rs.getString(2), rs.getByte(3), rs.getByte(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
			}
			cerrar_ResultSet(rs);
		}
		catch (final SQLException e){}
	}
}
