package database.objetos;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import main.consola.Consola;
import objetos.entidades.npcs.NpcsModelo;
import objetos.entidades.npcs.NpcsUbicacion;
import objetos.mapas.Mapa;

public class Npcs_DB extends DatabaseManager
{
	public Npcs_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	public void get_Cargar_Todos_Npcs_Modelo()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM npcs_modelo;");

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
	}
	
	public short get_Cargar_Todos_Npcs_Ubicacion()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM npcs_ubicacion;");
			short contador = 0;
			
			//mapa(1), celda(2), npc_modelo(3), orientacion(4), nombre(5)
			while(query.get_Rs().next())
			{
				final NpcsModelo npc_modelo = NpcsModelo.get_Npcs_Cargados(query.get_Rs().getShort(3));
				if(npc_modelo != null)
				{
					Mapa mapa = Mapa.get_Mapas_Cargados(query.get_Rs().getShort(1));
					mapa.get_Agregar_Entidad(new NpcsUbicacion(npc_modelo, mapa.get_Siguiente_Id_Entidad(), mapa.get_Celda(query.get_Rs().getShort(2)), query.get_Rs().getByte(4)));
				}
				contador++;	
			}
			cerrar(query);
			return contador;
		}
		catch (final Exception e)
		{
			Consola.println("error sql: " + e);
		}
		return 0;
	}
}
