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
		config.setJdbcUrl("jdbc:mysql://127.0.0.1/dofus_global?user=root&password=&useSSL=false");
		config.setMaximumPoolSize(16);
		config.setPoolName("Pool-Hikari");
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
