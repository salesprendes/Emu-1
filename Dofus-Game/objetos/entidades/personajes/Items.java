package objetos.entidades.personajes;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import database.objetos.transformer.ItemEfectosTransformer;
import objetos.items.ItemsModelo;
import objetos.items.ItemsModeloEfecto;

public class Items
{
	private int id, cantidad;
	private final ItemsModelo item_modelo;
	final private List<ItemsModeloEfecto> efectos;
	private byte posicion_inventario = -1;
	
	final static private ItemEfectosTransformer efectos_transformador = new ItemEfectosTransformer();
	private static ConcurrentHashMap<Integer, Items> items_personajes = new ConcurrentHashMap<Integer, Items>();
	
	public Items (final int _id, final int id_modelo, final int _cantidad, final byte _posicion_inventario, List<ItemsModeloEfecto> _efectos)
	{
		id = _id;
		item_modelo = ItemsModelo.get_Items_Cargados(id_modelo);
		cantidad = _cantidad;
		posicion_inventario = _posicion_inventario;
		efectos = _efectos;
	}
	
	public String get_Item_String()
	{	
		final String posicion = posicion_inventario == -1 ? "" : Integer.toHexString(posicion_inventario);
		return new StringBuilder(40).append(Integer.toHexString(id)).append('~').append(Integer.toHexString(item_modelo.get_Id())).append('~').append(Integer.toHexString(cantidad)).append('~').append(posicion).append('~').append(efectos_transformador.serializar(efectos)).toString();
	}
	
	public static Items get_item(final int id) 
	{
		return items_personajes.get(id);
	}
	
	public static ConcurrentHashMap<Integer, Items> get_item()
	{
		return items_personajes;
	}

	public List<ItemsModeloEfecto> get_Efectos()
	{
		return efectos;
	}
}
