package database;

import java.sql.Connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConexionPool 
{
	private HikariDataSource dataSource;
	private Cuentas_DB cuentas;
	private Servidores_DB servidores;
	
	public void inicializar_Database() 
	{
		cuentas = new Cuentas_DB(dataSource);
		servidores = new Servidores_DB(dataSource);
	}
	
	public void cargar_Configuracion() 
	{
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://127.0.0.1/dofus_global");
		config.setUsername("root");
		config.setMaximumPoolSize(10);
		config.setMinimumIdle(2);
		config.setPoolName("Pool-Hikari");
		config.addDataSourceProperty("useSSL", false);
		config.addDataSourceProperty("cachePrepStmts", true);
		config.addDataSourceProperty("prepStmtCacheSize", 256);
		config.addDataSourceProperty("prepStmtCacheSqlLimit", 1024);
		config.addDataSourceProperty("useServerPrepStmts", true);
		dataSource = new HikariDataSource(config);
	}
	
	public Cuentas_DB get_Cuentas() 
	{ 
		return cuentas; 
	}
	
	public Servidores_DB get_Servidores() 
	{
		return servidores;
	}
	
	public HikariDataSource get_Data_Source()
	{
		return dataSource;
	}
	
	public boolean comprobar_conexion(HikariDataSource dataSource)
	{
		try 
		{
			Connection conexion = dataSource.getConnection();
			conexion.close();
			return true;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}
}
