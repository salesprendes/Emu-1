package database.objetos.transformer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import juego.enums.Efectos;
import objetos.items.ItemsModeloEfecto;

final public class ItemEfectosTransformer implements Transformer<List<ItemsModeloEfecto>>
{
	public String serializar(final List<ItemsModeloEfecto> valor)
	{
		if (valor == null || valor.isEmpty()) 
		{
            return "";
        }
		
		StringBuilder sb = new StringBuilder(valor.size() * 8);//total valor * linea de stats limite
		
		for (ItemsModeloEfecto entry : valor) 
		{
            if (sb.length() > 0) 
            {
                sb.append(',');
            }
            sb.append(Integer.toString(entry.get_Efecto().get_Id(), 16)).append('#').append(Integer.toString(entry.get_Minimo(), 16)).append('#').append(Integer.toString(entry.get_Maximo(), 16)).append('#').append(Integer.toString(entry.get_Especial(), 16)).append('#').append(entry.get_Texto());
        }
		
		return sb.toString();
	}

	public List<ItemsModeloEfecto> deserializar(String serializador)
	{
		if (serializador == null || serializador.isEmpty()) 
		{
            return new ArrayList<>();
        }
		System.out.println(serializador);
		
		List<ItemsModeloEfecto> efectos = new ArrayList<ItemsModeloEfecto>();
		
		for (String separador : StringUtils.split(serializador, ",")) 
		{
			String[] args = StringUtils.split(separador, "#", 5);
			
			if (args.length < 4)
                throw new IllegalArgumentException("no se puede desarializar el efecto: " + serializador);
			
			efectos.add(new ItemsModeloEfecto(Efectos.get_Id(Integer.parseInt(args[0], 16)), Integer.parseInt(args[1], 16), Integer.parseInt(args[2], 16), Integer.parseInt(args[3], 16), args.length > 4 ? args[4] : ""));
		}
		return efectos;
	}
}
