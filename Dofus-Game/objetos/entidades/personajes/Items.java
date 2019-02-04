package objetos.entidades.personajes;

import java.util.Map.Entry;

import objetos.entidades.caracteristicas.Stats;
import objetos.items.ItemsModelo;

public class Items
{
	private int id, cantidad;
	private final ItemsModelo item_modelo; 
	private Stats stats;
	private byte posicion_inventario = -1;
	
	public Items (final int _id, final int id_modelo, final int _cantidad, final byte _posicion_inventario, final String _stats)
	{
		id = _id;
		item_modelo = ItemsModelo.get_Items_Cargados(id_modelo);
		cantidad = _cantidad;
		posicion_inventario = _posicion_inventario;
		stats = new Stats();
		get_Convertir_String_A_Stats(_stats);
	}
	
	public String get_Item_String()
	{	
		final String posicion = posicion_inventario == -1 ? "" : Integer.toHexString(posicion_inventario);
		return new StringBuilder(40).append(Integer.toHexString(id)).append('~').append(Integer.toHexString(item_modelo.get_Id())).append('~').append(Integer.toHexString(cantidad)).append('~').append(posicion).append('~').append(get_Convertir_Stats_A_String()).toString();
	}
	
	public ItemsModelo get_Item_modelo()
	{
		return item_modelo;
	}

	public int get_Cantidad()
	{
		return cantidad;
	}

	public void set_Cantidad(final int _cantidad)
	{
		cantidad = _cantidad;
	}

	public void get_Convertir_String_A_Stats(final String stats_string)
	{
		for(final String stat : stats_string.split(","))
		{
			if (!stat.isEmpty())
			{
				try 
				{
					final String[] separador = stat.split("#");
					int id_stat = ItemsModelo.get_Stat_Similar(Integer.parseInt(separador[0], 16));
					final int valor = Integer.parseInt(separador[1], 16);
					
					stats.get_Agregar_Stat_Id(id_stat, valor);
				}
				catch(final Exception e){};
			}
		}
	}
	
	public String get_Convertir_Stats_A_String()
	{
		final StringBuilder string_stats = new StringBuilder();
		
		for(final Entry<Integer, Integer> stat : stats.get_Stats().entrySet())
		{
			if(string_stats.length() > 0)
				string_stats.append(',');
			final String jet = "0d0+" + stat.getValue();
			
			string_stats.append(Integer.toString(stat.getKey(), 16)).append('#').append(Integer.toString(stat.getValue(), 16)).append("#0#0#").append(jet);
		}
		
		return string_stats.toString();
	}
}
