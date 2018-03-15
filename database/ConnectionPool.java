package database;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool 
{
	static public interface Task<T> 
	{
		public T perform(Connection connection) throws SQLException;
	}
	
	public void initialize() throws SQLException;
	public Connection acquire() throws SQLException;
	public void release(Connection connection);
	
	default public <T> T execute(Task<T> task) throws SQLException
	{

		Connection connection = acquire();
		try 
		{
			return task.perform(connection);
		} 
		finally 
		{
			release(connection);
		}
	}
}