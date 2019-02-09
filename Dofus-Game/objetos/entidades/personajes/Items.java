package objetos.entidades.personajes;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import objetos.entidades.caracteristicas.Stats;
import objetos.entidades.hechizos.EfectoModelo;
import objetos.items.ItemsModelo;
import objetos.items.tipos.Objevivo;

public class Items
{
	private int id, cantidad;
	protected ItemsModelo item_modelo; 
	protected Stats stats;
	private byte posicion_inventario = -1;
	private ArrayList<EfectoModelo> efectos_normales;
	protected boolean es_objevivo = false;
	
	public static final int[] efectos_armas = {81, 82, 83, 84, 85, 86, 87, 88, 89, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 108};
	public static final int[] stat_texto = {7, 10, 146, 148, 188, 197, 201, 221, 222, 229, 230, 333, 501, 513, 600, 602, 603, 604, 614, 615, 616, 620, 622, 624, 627, 640, 641, 642, 643, 645, 647, 648, 649, 669, 699, 700, 701, 705, 710, 715, 716, 717, 720, 724, 725, 730, 751, 760, 765, 791, 795, 800, 805, 806, 807, 808, 810, 811, 813, 825, 900, 901, 902, 905, 915, 930, 931, 932, 933, 934, 935, 936, 937, 939, 940, 946, 947, 948, 949, 950, 960, 961, 962, 963, 964, 970, 971, 972, 973, 974, 983, 985, 986, 987, 988, 989, 990, 992, 994, 996, 997, 998, 999};
	public static final int[] stat_repetible = {623, 628, 717, 500, 814};
	
	public Items(final int _id, final int id_modelo, final int _cantidad, final byte _posicion_inventario, final String _stats)
	{
		id = _id;
		item_modelo = ItemsModelo.get_Items_Cargados(id_modelo);
		cantidad = _cantidad;
		posicion_inventario = _posicion_inventario;
		stats = new Stats();
		get_Convertir_String_A_Stats(_stats);
	}

	public Items(final int _id, final int id_modelo, final int _cantidad, final byte _posicion_inventario, final Stats _stats, ArrayList<EfectoModelo> _efectos_normales)
	{
		id = _id;
		item_modelo = ItemsModelo.get_Items_Cargados(id_modelo);
		cantidad = _cantidad;
		posicion_inventario = _posicion_inventario;
		stats = _stats;
		efectos_normales = _efectos_normales;
	}

	public String get_Item_String()
	{	
		if(es_objevivo && Objevivo.get_Objevivo(this).get_Esta_fusionado())
			return "";
	
		final String posicion = posicion_inventario == -1 ? "" : Integer.toHexString(posicion_inventario);
		return new StringBuilder(40).append(Integer.toHexString(id)).append('~').append(Integer.toHexString(item_modelo.get_Id())).append('~').append(Integer.toHexString(cantidad)).append('~').append(posicion).append('~').append(get_Convertir_Stats_A_String()).toString();
	}

	public int get_Id()
	{
		return id;
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

	public byte get_Posicion_inventario()
	{
		return posicion_inventario;
	}

	public ArrayList<EfectoModelo> get_Efectos_normales()
	{
		return efectos_normales;
	}

	public void set_Efectos_normales(final ArrayList<EfectoModelo> _efectos_normales)
	{
		efectos_normales = _efectos_normales;
	}

	public void set_Posicion_inventario(final byte _posicion_inventario)
	{
		posicion_inventario = _posicion_inventario;
	}

	public Stats get_Stats()
	{
		return stats;
	}

	public void set_Stats(Stats _stats)
	{
		stats = _stats;
	}

	public void get_Convertir_String_A_Stats(final String stats_string)
	{
		stats.clear();
		efectos_normales = null;
		for(final String stat : stats_string.split(","))
		{
			if (!stat.isEmpty())
			{
				try 
				{
					final String[] separador = stat.split("#");
					final short stat_id = ItemsModelo.get_Stat_Similar(Short.parseShort(separador[0], 16));
					final int valor = Integer.parseInt(separador[1], 16);

					if (stat_id >= 281 && stat_id <= 294)
						stats.set_Stat_Hechizo(stat);
					else if(IntStream.of(efectos_armas).anyMatch(x -> x == stat_id))
					{
						if(efectos_normales == null)
							efectos_normales = new ArrayList<EfectoModelo>();
					}
					else if(IntStream.of(stat_texto).anyMatch(x -> x == stat_id))
						stats.set_Stat_Texto(stat_id, stat, true);
					else if(IntStream.of(stat_repetible).anyMatch(x -> x == stat_id))
						stats.set_Stat_Repetido(stat);
					else
						stats.get_Agregar_Stat_Id(stat_id, valor);
				}
				catch(final Exception e){};
			}
		}
	}

	public String get_Convertir_Stats_A_String()
	{
		final StringBuilder string_stats = new StringBuilder();

		for(final Entry<Short, Integer> stat : stats.get_Stats().entrySet())
		{
			if(string_stats.length() > 0)
				string_stats.append(',');
			final String jet = "0d0+" + stat.getValue();

			string_stats.append(Integer.toString(stat.getKey(), 16)).append('#').append(Integer.toString(stat.getValue(), 16)).append("#0#0#").append(jet);
		}

		return string_stats.toString();
	}
}
