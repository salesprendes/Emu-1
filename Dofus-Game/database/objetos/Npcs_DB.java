package database.objetos;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import main.consola.Consola;
import objetos.entidades.npcs.NpcsModelo;

public class Npcs_DB extends DatabaseManager
{
	public Npcs_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	public void get_Cargar_Todos_Npcs_Modelo()
	{
		Ejecucion_Query query = null;
	
		try
		{
			query = ejecutar_Query_Select("SELECT * FROM npcs_modelo;");

			while(query.get_Rs().next())
			{
				//id(1), nombre(2), gfx(3), X(4), Y(5), sexo(6), color_1(7), color_2(8), color_3(9), acessorios(10), foto(11), pregunta(12), ventas(13)
				new NpcsModelo(query.get_Rs().getShort(1), query.get_Rs().getShort(3), query.get_Rs().getShort(4), query.get_Rs().getShort(5), query.get_Rs().getByte(6), query.get_Rs().getInt(7), query.get_Rs().getInt(8), query.get_Rs().getInt(9), query.get_Rs().getString(10), query.get_Rs().getShort(11), query.get_Rs().getInt(12), query.get_Rs().getString(13));
			}
			cerrar(query);
		}
		catch (final Exception e)
		{
			Consola.println("error sql: " + e);
		}
		finally 
		{
			cerrar(query);
		}
	}
}
