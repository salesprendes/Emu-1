package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager
{
	protected HikariDataSource database_conexion;
	
	public DatabaseManager(HikariDataSource _dataSource)
	{
		database_conexion = _dataSource;
	}
	
	public ResultSet conexion_Y_Ejecucion(String query) throws SQLException
	{
		final Statement statement = database_conexion.getConnection().createStatement();
		final ResultSet resultSet = statement.executeQuery(query);
		statement.setQueryTimeout(300);
		return resultSet;
	}

	public static void cerrar_ResultSet(ResultSet rs) throws SQLException
	{
		rs.getStatement().close();
		rs.close();
	}
}
