package objetos.entidades.caracteristicas;

import java.util.EnumMap;
import java.util.Map;

import juego.enums.TipoCaracteristica;

final public class DefaultCaracteristicas implements Caracteristicas 
{
	final private Map<TipoCaracteristica, Integer> map = new EnumMap<TipoCaracteristica, Integer>(TipoCaracteristica.class);
	
	public int get_Caracteristica(TipoCaracteristica caracteristica)
	{
		return map.getOrDefault(caracteristica, 0);
	}

	public void set_Caracteristica(TipoCaracteristica caracteristica, int valor)
	{
		map.put(caracteristica, valor);
	}

	public void agregar_Caracteristica(TipoCaracteristica caracteristica, int valor)
	{
		map.put(caracteristica, map.getOrDefault(caracteristica, 0) + valor);
	}

	public boolean equals(Caracteristicas otro) 
	{
		for (TipoCaracteristica caracteristica : TipoCaracteristica.values()) 
		{
			if (get_Caracteristica(caracteristica) != otro.get_Caracteristica(caracteristica)) 
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
            int valor = get_Caracteristica(caracteristica);

            if (valor != 0) 
            {
                h += caracteristica.hashCode() ^ valor;
            }
        }
        return h;
    }
}
