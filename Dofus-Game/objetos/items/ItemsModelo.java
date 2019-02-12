package objetos.items;

import java.util.HashMap;
import java.util.Map;

import juego.enums.TipoStats;
import main.consola.Consola;

public class ItemsModelo
{
	final private int id, kamas;
	final private byte tipo;
	final private short nivel, pods;
	final private String condiciones, stats;
	final private boolean es_magueable, es_eterea;
	private ArmasModelo informacion_armas;

	private static final Map<Integer, ItemsModelo> items_cargados = new HashMap<Integer, ItemsModelo>();

	public ItemsModelo(final int _id, final byte _tipo, final short _nivel, final String _stats, final short _pods, final int _kamas, final boolean _es_magueable, final boolean _es_eterea, final String _informacion_armas, final String _condiciones)
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

		if (!_informacion_armas.isEmpty() && get_Es_Arma(tipo)) 
		{
			try 
			{
				final String[] separador = _informacion_armas.split(",");
				informacion_armas = new ArmasModelo(Byte.parseByte(separador[0]), Byte.parseByte(separador[1]), Byte.parseByte(separador[2]), Byte.parseByte(separador[3]), Short.parseShort(separador[4]), Short.parseShort(separador[5]), Boolean.parseBoolean(separador[6]), Boolean.parseBoolean(separador[7]), Boolean.parseBoolean(separador[8]));
			}
			catch (final Exception e)
			{
				Consola.println("Error al crear el arma id: " + id);
			}
		}
		items_cargados.put(id, this);
	}

	public int get_Id()
	{
		return id;
	}

	public String get_Condiciones()
	{
		return condiciones;
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
	
	public String get_Stats()
	{
		return stats;
	}

	public boolean get_Es_magueable()
	{
		return es_magueable;
	}

	public boolean get_Es_eterea()
	{
		return es_eterea;
	}
	
	public ArmasModelo get_Informacion_armas()
	{
		return informacion_armas;
	}

	public static short get_Stat_Similar(short statID) 
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
	
	public int get_Tipo_Objevivo() 
	{
		try 
		{
			for (final String separador : stats.split(","))
			{
				final String[] stats = separador.split("#");
				final int stat_id = Integer.parseInt(stats[0], 16);
				if (stat_id == 973)
					return Integer.parseInt(stats[3], 16);
			}
		} 
		catch (final Exception e)
		{
			return 113;
		}
		return 113;
	}
	
	public boolean get_Es_Arma(final short tipo_objeto) 
	{
        switch (tipo_objeto) 
        {
            case 2://Arco
            case 3://Varita
            case 4://Baston
            case 5://Daga
            case 6://Espada
            case 7://Martillo
            case 8://Pala
            case 19://Hacha
            case 20://Herramienta
            case 21://Pico
            case 22://Guadaña
            case 83://Piedra de alma
            case 102://Ballesta
            case 114://Arma magica
            	return true;
        }
        return false;
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
