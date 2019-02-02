package objetos.items.efectos;

import java.util.List;

import objetos.items.ItemEfecto;
import objetos.items.ItemsModeloEfecto;

public interface EfectosMap <E extends ItemEfecto>
{
	public E get_crear(ItemsModeloEfecto efectos, boolean maximizado);
	
	default public E get_crear(ItemsModeloEfecto efectos) 
	{
        return get_crear(efectos, false);
    }
	
	public List<E> get_crear(List<ItemsModeloEfecto> efectos, boolean maximizado);
	
	default public List<E> get_crear(List<ItemsModeloEfecto> efectos) 
	{
        return get_crear(efectos, false);
    }
	
	public Class<E> tipo();
}
