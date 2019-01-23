package objetos.entidades.caracteristicas;

import java.util.EnumMap;
import java.util.Map;

import juego.enums.TipoCaracteristica;

final public class DefaultCaracteristicas implements MutableCaracteristicas 
{
	final private Map<TipoCaracteristica, Integer> map = new EnumMap<TipoCaracteristica, Integer>(TipoCaracteristica.class);
	
	public int get(TipoCaracteristica caracteristica)
	{
		return map.getOrDefault(caracteristica, 0);
	}

	public void set(TipoCaracteristica caracteristica, int valor)
	{
		map.put(caracteristica, valor);
	}

	public void agregar(TipoCaracteristica caracteristica, int valor)
	{
		map.put(caracteristica, map.getOrDefault(caracteristica, 0) + valor);
	}

	public boolean equals(Object obj) 
	{
		if (obj == this) 
		{
			return true;
		}

		if (obj instanceof Caracteristicas) 
		{
			return equals((Caracteristicas) obj);
		}
		return false;
	}

	public boolean equals(Caracteristicas otro) 
	{
		for (TipoCaracteristica caracteristica : TipoCaracteristica.values()) 
		{
			if (get(caracteristica) != otro.get(caracteristica)) 
			{
				return false;
			}
		}
		return true;
	}
	
	public int hashCode() 
    {
        int h = 0;

        for (TipoCaracteristica caracteristica : TipoCaracteristica.values()) 
        {
            int valor = get(caracteristica);

            if (valor != 0) 
            {
                h += caracteristica.hashCode() ^ valor;
            }
        }
        return h;
    }
}
