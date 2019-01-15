package database;

import java.sql.Connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import database.objetos.Alineamientos_DB;
import database.objetos.Areas_DB;
import database.objetos.Cuentas_DB;
import database.objetos.Experiencia_DB;
import database.objetos.Mapas_DB;
import database.objetos.Personajes_DB;
import database.objetos.Razas_DB;
import database.objetos.SubAreas_DB;
import database.objetos.SuperAreas_DB;
import main.Configuracion;

public class ConexionPool 
{
	private HikariDataSource game_database;
	private HikariDataSource login_database;
	
	//objetos
	private Cuentas_DB cuentas;
	private Personajes_DB personajes;
	private SuperAreas_DB super_areas;
	private Areas_DB areas;
	private SubAreas_DB sub_areas;
	private Mapas_DB mapas;
	private Razas_DB razas;
	private Alineamientos_DB alineamientos;
	private Experiencia_DB experiencia;
	
	public void iniciar_Database() 
	{
		cuentas = new Cuentas_DB(login_database);
		personajes = new Personajes_DB(login_database);
		super_areas = new SuperAreas_DB(game_database);
		areas = new Areas_DB(game_database);
		sub_areas = new SubAreas_DB(game_database);
		mapas = new Mapas_DB(game_database);
		razas = new Razas_DB(game_database);
		alineamientos = new Alineamientos_DB(game_database);
		experiencia = new Experiencia_DB(game_database);
	}
	
	public Cuentas_DB get_Cuentas()
	{
		return cuentas;
	}
	
	public Personajes_DB get_Personajes()
	{
		return personajes;
	}

	public SuperAreas_DB get_Super_areas() 
	{
		return super_areas;
	}

	public Areas_DB get_Areas()
	{
		return areas;
	}

	public Alineamientos_DB get_Alineamientos()
	{
		return alineamientos;
	}

	public SubAreas_DB get_Sub_areas() 
	{
		return sub_areas;
	}

	public Mapas_DB get_Mapas() 
	{
		return mapas;
	}

	public Razas_DB get_Razas() 
	{
		return razas;
	}

	public Experiencia_DB get_Experiencia()
	{
		return experiencia;
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
