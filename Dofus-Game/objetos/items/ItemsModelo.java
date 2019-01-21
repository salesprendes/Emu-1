package objetos.items;

import java.util.HashMap;
import java.util.Map;

import juego.enums.TipoStats;

public class ItemsModelo
{
	private final int id, kamas;
	private final short nivel, pods;
	private short porcentaje_golpe_critico;
	private final byte tipo;
	private byte coste_pa, bonus_golpe_critico;
	private String stats, condiciones;
	private final boolean es_magueable, es_eterea;
	private boolean es_dos_manos;

	private static final Map<Integer, ItemsModelo> items_cargados = new HashMap<Integer, ItemsModelo>();

	public ItemsModelo(final int _id, final byte _tipo, final short _nivel, final String _stats, final short _pods, final int _kamas, final boolean _es_magueable, final boolean _es_eterea, final String stats_armas, final String _condiciones)
	{
		id = _id;
		tipo = _tipo;
		nivel = _nivel;
		stats = _stats;
		pods = _pods;
		kamas = _kamas;
		es_magueable = _es_magueable;
		es_eterea = _es_eterea;
		condiciones = _condiciones;

		if (!stats.isEmpty())
		{
			try 
			{
				if (!stats_armas.isEmpty()) 
				{
					final String[] separador = stats_armas.split(",");
					bonus_golpe_critico = Byte.parseByte(separador[0]);
					coste_pa = Byte.parseByte(separador[1]);
					//byte alcanze_minimo = Byte.parseByte(separador[2]);
					//byte alcanze_maximo = Byte.parseByte(separador[3]);
					porcentaje_golpe_critico = Short.parseShort(separador[4]);
					//short porcentaje_fallo_critico = Short.parseShort(separador[5]);
					//boolean lanzado_en_linea = Boolean.parseBoolean(separador[6]);
					//boolean necesita_linea_vision = Boolean.parseBoolean(separador[7]);
					es_dos_manos = Boolean.parseBoolean(separador[8]);
				}
			}
			catch (final Exception e){}
		}

		items_cargados.put(id, this);
	}

	public int get_Id()
	{
		return id;
	}

	public short get_Porcentaje_golpe_critico()
	{
		return porcentaje_golpe_critico;
	}

	public void set_Porcentaje_golpe_critico(short porcentaje_golpe_critico)
	{
		this.porcentaje_golpe_critico = porcentaje_golpe_critico;
	}

	public byte get_Coste_pa()
	{
		return coste_pa;
	}

	public void set_Coste_pa(byte coste_pa)
	{
		this.coste_pa = coste_pa;
	}

	public byte get_Bonus_golpe_critico()
	{
		return bonus_golpe_critico;
	}

	public void set_Bonus_golpe_critico(byte bonus_golpe_critico)
	{
		this.bonus_golpe_critico = bonus_golpe_critico;
	}

	public String get_Stats()
	{
		return stats;
	}

	public void set_Stats(String stats)
	{
		this.stats = stats;
	}

	public String get_Condiciones()
	{
		return condiciones;
	}

	public void set_Condiciones(String condiciones)
	{
		this.condiciones = condiciones;
	}

	public boolean get_Es_dos_manos()
	{
		return es_dos_manos;
	}

	public void set_Es_dos_manos(boolean es_dos_manos)
	{
		this.es_dos_manos = es_dos_manos;
	}

	public int get_Kamas()
	{
		return kamas;
	}

	public short get_Nivel()
	{
		return nivel;
	}

	public short get_Pods()
	{
		return pods;
	}

	public byte get_Tipo()
	{
		return tipo;
	}

	public boolean get_Es_magueable()
	{
		return es_magueable;
	}

	public boolean get_Es_eterea()
	{
		return es_eterea;
	}

	public static int get_Stat_Similar(int statID) 
	{
		switch (statID) 
		{
			case TipoStats.AGREGAR_PA2:
				return TipoStats.AGREGAR_PA;
				
			case TipoStats.AGREGAR_DANOS2:
				return TipoStats.AGREGAR_DANOS;
				
			case TipoStats.AGREGAR_PM2:
				return TipoStats.AGREGAR_PM;
				
			case TipoStats.AGREGAR_DANOS_DEVUELTOS:
				return TipoStats.AGREGAR_REENVIA_DANOS;
		}
		return statID;
	}

	public static Map<Integer, ItemsModelo> get_Items_Cargados()
	{
		return items_cargados;
	}

	public static ItemsModelo get_Items_Cargados(int item_id) 
	{
		return items_cargados.get(item_id);
	}
}
