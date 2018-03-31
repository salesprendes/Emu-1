package database;

import java.sql.Connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import database.objetos.Cuentas_DB;
import main.Configuracion;

public class ConexionPool 
{
	private HikariDataSource game_database;
	private HikariDataSource login_database;
	
	//objetos
	private Cuentas_DB cuentas;
	
	public void iniciar_Database() 
	{
		cuentas = new Cuentas_DB(login_database);
	}
	
	public Cuentas_DB get_Cuentas()
	{
		return cuentas;
	}
	
	public void cargar_Configuracion() 
	{
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://" + Configuracion.DATABASE_IP_LOGIN + "/" + Configuracion.DATABASE_NOMBRE_LOGIN);
		config.setUsername(Configuracion.DATABASE_USUARIO_LOGIN);
		config.setPassword(Configuracion.DATABASE_PASSWORD_LOGIN);
		config.setMaximumPoolSize(10);
		config.setMinimumIdle(1);
		config.setPoolName("Pool-Login");
		config.addDataSourceProperty("useSSL", false);
		config.addDataSourceProperty("cachePrepStmts", true);
		config.addDataSourceProperty("prepStmtCacheSize", 250);
		config.addDataSourceProperty("prepStmtCacheSqlLimit", 1024);
		config.addDataSourceProperty("useServerPrepStmts", true);
		config.addDataSourceProperty("cacheServerConfiguration", true);
		config.addDataSourceProperty("useLocalSessionState", true);
		login_database = new HikariDataSource(config);
		
		config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://" + Configuracion.DATABASE_IP_GAME + "/" + Configuracion.DATABASE_NOMBRE_GAME);
		config.setUsername(Configuracion.DATABASE_USUARIO_GAME);
		config.setPassword(Configuracion.DATABASE_PASSWORD_GAME);
		config.setMaximumPoolSize(16);
		config.setMinimumIdle(2);
		config.setPoolName("Pool-Game");
		config.addDataSourceProperty("useSSL", false);
		config.addDataSourceProperty("cachePrepStmts", true);
		config.addDataSourceProperty("prepStmtCacheSize", 256);
		config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
		config.addDataSourceProperty("useServerPrepStmts", true);
		config.addDataSourceProperty("cacheServerConfiguration", true);
		config.addDataSourceProperty("useLocalSessionState", true);
		game_database = new HikariDataSource(config);
	}
	
	public HikariDataSource get_Game_Database()
	{
		return game_database;
	}
	
	public HikariDataSource get_Login_Database()
	{
		return login_database;
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
