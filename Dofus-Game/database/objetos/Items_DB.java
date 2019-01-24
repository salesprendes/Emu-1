package database.objetos;

import java.util.List;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import database.objetos.transformer.ItemEfectosTransformer;
import database.objetos.transformer.Transformer;
import main.consola.Consola;
import objetos.items.ItemsModelo;
import objetos.items.ItemsModeloEfecto;

public class Items_DB extends DatabaseManager
{
	final private Transformer<List<ItemsModeloEfecto>> efectos_transformados = new ItemEfectosTransformer();
	
	public Items_DB(final HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}
	
	public void get_Cargar_Todos_Items()
	{
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM items_modelo;");
			
			while(query.get_Rs().next())
			{
				//id (1), tipo(2), nivel(4), stats(5), pods(6), kamas(7), set(8), es_magueable(8), es_etereo(9), stats_armas(10), condiciones(11)
				new ItemsModelo(query.get_Rs().getInt(1), query.get_Rs().getByte(2), query.get_Rs().getShort(4), efectos_transformados.deserializar(query.get_Rs().getString(5)), query.get_Rs().getShort(6), query.get_Rs().getInt(7), query.get_Rs().getShort(8), query.get_Rs().getBoolean(9), query.get_Rs().getBoolean(10), query.get_Rs().getString(11), query.get_Rs().getString(12));
			}
			cerrar(query);
		}
		catch (final Exception e)
		{
			Consola.println("Error: " + e);
		}
	}
}
