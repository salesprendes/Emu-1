package objetos.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsModelo
{
	final private int id, kamas;
	final private byte tipo;
	final private short nivel, pods, set;
	final private List<ItemsModeloEfecto> efectos;
	final private String condiciones, informacion_armas;
	final private boolean es_magueable, es_eterea;

	private static final Map<Integer, ItemsModelo> items_cargados = new HashMap<Integer, ItemsModelo>();

	public ItemsModelo(final int _id, final byte _tipo, final short _nivel, final List<ItemsModeloEfecto> _efectos, final short _pods, final int _kamas, final short _set, final boolean _es_magueable, final boolean _es_eterea, final String _informacion_armas, final String _condiciones)
	{
		id = _id;
		tipo = _tipo;
		nivel = _nivel;
		efectos = _efectos;
		pods = _pods;
		kamas = _kamas;
		set = _set;
		es_magueable = _es_magueable;
		es_eterea = _es_eterea;
		informacion_armas = _informacion_armas;
		condiciones = _condiciones;
		
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
	
	public short get_Set()
	{
		return set;
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

	public List<ItemsModeloEfecto> get_Efectos()
	{
		return efectos;
	}

	public String get_Informacion_armas()
	{
		return informacion_armas;
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
