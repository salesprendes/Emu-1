package database;

import java.sql.Connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import database.objetos.Comunidades_DB;
import database.objetos.Cuentas_DB;
import database.objetos.Servidores_DB;
import main.Configuracion;
import main.consola.Consola;

public class ConexionPool 
{
	private HikariDataSource dataSource;
	
	//Objetos
	private final Cuentas_DB cuentas;
	private final Servidores_DB servidores;
	private final Comunidades_DB comunidades;
	
	public ConexionPool()
	{
		cargar_Configuracion();
		
		Consola.print("Conectando a la base de datos: ");
		cargar_Configuracion();
		
		if(comprobar_conexion(dataSource))
			Consola.println("correcta");
		
		cuentas = new Cuentas_DB(dataSource);
		servidores = new Servidores_DB(dataSource);
		comunidades = new Comunidades_DB(dataSource);
	}
	
	public void cargar_Configuracion()
	{
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://" + Configuracion.DATABASE_IP_LOGIN + "/" + Configuracion.DATABASE_NOMBRE_LOGIN);
		config.setUsername(Configuracion.DATABASE_USUARIO_LOGIN);
		config.setPassword(Configuracion.DATABASE_PASSWORD_LOGIN);
		config.setMaximumPoolSize(10);
		config.setMinimumIdle(2);
		config.setPoolName("Pool-Hikari");
		config.addDataSourceProperty("useSSL", false);
		config.addDataSourceProperty("cachePrepStmts", true);
		config.addDataSourceProperty("prepStmtCacheSize", 250);
		config.addDataSourceProperty("prepStmtCacheSqlLimit", 1024);
		config.addDataSourceProperty("useServerPrepStmts", true);
		config.addDataSourceProperty("cacheServerConfiguration", true);
		config.addDataSourceProperty("useLocalSessionState", true);
		dataSource = new HikariDataSource(config);
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
			Consola.println("incorrecta");
			System.exit(0);
			return false;
		}
	}
	
	public Cuentas_DB get_Cuentas() 
	{ 
		return cuentas; 
	}
	
	public Servidores_DB get_Servidores() 
	{
		return servidores;
	}
	
	public Comunidades_DB get_Comunidades() 
	{
		return comunidades;
	}
}
