package objetos.items.efectos;

import java.util.HashMap;
import java.util.Map;

import objetos.items.ItemEfecto;

final public class EfectosMappers
{
	final private Map<Class<ItemEfecto>, EfectosMap<ItemEfecto>> mappers = new HashMap<Class<ItemEfecto>, EfectosMap<ItemEfecto>>();

	public EfectosMappers(EfectosMap<ItemEfecto>[] map) 
	{
		for (EfectosMap<ItemEfecto> mapper : map)
		{
			set_registrar(mapper);
		}
	}

	public <E extends ItemEfecto> EfectosMap<ItemEfecto> get(Class<E> tipo) 
	{
		return mappers.get(tipo);
	}

	private void set_registrar(EfectosMap<ItemEfecto> map) 
	{
		mappers.put(map.tipo(), map);
	}
}
