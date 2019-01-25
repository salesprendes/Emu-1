package objetos.entidades.personajes;

import java.util.ArrayList;
import java.util.List;

import juego.enums.Efectos;
import objetos.items.ItemsModelo;
import objetos.items.ItemsModeloEfecto;

public class Items
{
	private int id, cantidad;
	private final ItemsModelo item_modelo;
	final private List<ItemsModeloEfecto> efectos;
	private byte posicion_inventario = -1;
	
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
		return new StringBuilder(40).append(Integer.toHexString(id)).append('~').append(Integer.toHexString(item_modelo.get_Id())).append('~').append(Integer.toHexString(cantidad)).append('~').append(posicion).append('~').append(serializar(efectos)).toString();
	}

	public List<ItemsModeloEfecto> get_Efectos()
	{
		return efectos;
	}
	
	public static String serializar(final List<ItemsModeloEfecto> valor)
	{
		StringBuilder sb = new StringBuilder(valor.size() * 8);//total valor * linea de stats limite
	
		if (valor != null || !valor.isEmpty()) 
		{
			for (ItemsModeloEfecto efecto : valor) 
			{
	            if (sb.length() > 0)
	                sb.append(',');
	            
	            sb.append(Integer.toString(efecto.get_Efecto().get_Id(), 16)).append('#').append(Integer.toString(efecto.get_Minimo(), 16)).append('#').append(Integer.toString(efecto.get_Maximo(), 16)).append('#').append(Integer.toString(efecto.get_Especial(), 16)).append('#').append(efecto.get_Texto());
	        }
        }
		
		return sb.toString();
	}

	public static List<ItemsModeloEfecto> deserializar(String serializador)
	{
		if (serializador == null || serializador.isEmpty()) 
		{
            return new ArrayList<>();
        }
		
		List<ItemsModeloEfecto> efectos = new ArrayList<ItemsModeloEfecto>();
		
		for (String separador : serializador.split(",")) 
		{
			String[] args = separador.split("#", 5);
			
			if (args.length < 4)
                throw new IllegalArgumentException("no se puede desarializar el efecto: " + serializador);
			
			efectos.add(new ItemsModeloEfecto(Efectos.get_Id(Integer.parseInt(args[0], 16)), Integer.parseInt(args[1], 16), Integer.parseInt(args[2], 16), Integer.parseInt(args[3], 16), args.length > 4 ? args[4] : ""));
		}
		return efectos;
	}
}
