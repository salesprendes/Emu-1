package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager
{
	protected static Connection database_conexion;
	
	public static final boolean ejecutar_Conexion()
	{
		try
		{
			database_conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1/dofus_global?user=root&password=&useSSL=false");
			database_conexion.setAutoCommit(true);
			return true;
		}
		catch (final SQLException e)
		{
			return false;
		}
	}
	
	public static synchronized ResultSet conexion_Y_Ejecucion(String query) throws SQLException
	{
		final Statement statement = database_conexion.createStatement();
		final ResultSet resultSet = statement.executeQuery(query);
		statement.setQueryTimeout(300);
		return resultSet;
	}

	public static void cerrar_ResultSet(ResultSet rs) throws SQLException
	{
		rs.getStatement().close();
		rs.close();
	}

	public static void cerrar_PreparedStatement(PreparedStatement p) throws SQLException
	{
		p.clearParameters();
		p.close();
	}
}
