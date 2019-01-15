package database.objetos;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
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
				//id(1), Nombre(2), pa(3), pm(4), vida(5), iniciativa(6), prospeccion(7), mapa_inicial(8), celda_inicial(9)
				new Razas(query.get_Rs().getByte(1), query.get_Rs().getByte(3), query.get_Rs().getByte(4), query.get_Rs().getByte(5), query.get_Rs().getShort(6), query.get_Rs().getShort(7), query.get_Rs().getShort(8), query.get_Rs().getShort(9));
			}
			cerrar(query);
		}
		catch (final Exception e){}
	}
}
