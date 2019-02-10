package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager
{
	protected HikariDataSource database_conexion;
	protected final Object bloqueo = new Object();
	
	public DatabaseManager(HikariDataSource _dataSource)
	{
		database_conexion = _dataSource;
	}
	
	protected void ejecutar_Update_O_Insert(String query)
	{
		synchronized (bloqueo) 
		{
			Connection conexion = null;
			Statement statement = null;
			try 
			{
				conexion = database_conexion.getConnection();
				statement = conexion.createStatement();
				statement.execute(query);
			}
			catch(final SQLException e) 
			{
				try
				{
					if (conexion != null) 
					{
						conexion.rollback();
					}
				} 
				catch (final Exception ignorar){}
			}
			finally 
			{
				cerrar_Statement(statement);
				cerrar_Conexion(conexion);
			}
		}
	}
	
	protected Ejecucion_Query ejecutar_Query_Select(String query) 
	{
		synchronized (bloqueo) 
		{
			Connection conexion = null;
			try 
			{
				conexion = database_conexion.getConnection();
				Statement statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				Ejecucion_Query result = new Ejecucion_Query(conexion, statement.executeQuery(query));
				return result;
			} 
			catch (final SQLException e) 
			{
				try
				{
					if (conexion != null) 
					{
						conexion.rollback();
					}
				} 
				catch (final Exception ignorar) {}
			}
			return null;
		}
	}
	
	protected void cerrar_PreparedStatement(PreparedStatement statement)
	{
		if (statement != null) 
		{
			try
			{
				statement.clearParameters();
				statement.close();
			} 
			catch(final Exception e) {}
		}
	}
	
	protected void cerrar_Statement(Statement statement) 
	{
		if(statement != null) 
		{
			try 
			{
				statement.close();
			} 
			catch(final Exception e) {}
		}
	}
	
	protected void cerrar_Conexion(Connection conexion)
	{
		if(conexion != null)
		{
			try 
			{
				conexion.close();
			}
			catch(final Exception e) {}
		}
	}
	
	protected void cerrar(Ejecucion_Query query)
	{
		if (query != null) 
		{
			try 
			{
				if (query.get_Rs() != null)
					query.get_Rs().close();
				if (query.get_conexion() != null)
					query.get_conexion().close();
			} 
			catch(final SQLException e) {}
		}
	}
	
	protected class Ejecucion_Query
	{
		private final Connection conexion;
		private final ResultSet rs;

		protected Ejecucion_Query(Connection _conexion, ResultSet _result_set)
		{
			conexion = _conexion;
			rs = _result_set;
		}
		
		public ResultSet get_Rs()
		{
			return rs;
		}
		
		public Connection get_conexion()
		{
			return conexion;
		}
	}
}
