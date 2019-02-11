package database.objetos;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import main.consola.Consola;
import objetos.entidades.personajes.Razas;

public class Razas_DB extends DatabaseManager
{
	public Razas_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	public void get_Cargar_Todas_Razas()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM razas;");

			while(query.get_Rs().next())
			{
				//id(1), Nombre(2), pa(3), pm(4), vida(5), iniciativa(6), pods(7), prospeccion(8), mapa_inicial(9), celda_inicial(10)
				new Razas(query.get_Rs().getByte(1), query.get_Rs().getByte(3), query.get_Rs().getByte(4), query.get_Rs().getByte(5), query.get_Rs().getShort(6), query.get_Rs().getShort(7), query.get_Rs().getShort(8), query.get_Rs().getShort(9), query.get_Rs().getShort(10));
			}
			cerrar(query);
		}
		catch (final Exception e)
		{
			Consola.println("ERROR SQL: " + e.toString());
		}
	}
}
