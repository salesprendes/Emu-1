package database.objetos.transformer;

import juego.enums.TipoCaracteristica;
import objetos.entidades.caracteristicas.Caracteristicas;
import objetos.entidades.caracteristicas.DefaultCaracteristicas;
import objetos.entidades.caracteristicas.MutableCaracteristicas;

public class CaracteristicasTransformer
{
	final static private int SERIALIZADOR_BASE = 32;
	final static private String VALOR_SEPARADOR = ":";
	final static private String STATS_SEPARADOR = ";";

	public String serialize(Caracteristicas caracteristicas)
	{
		if (caracteristicas == null) 
		{
			return null;
		}

		StringBuilder sb = new StringBuilder();

		for (TipoCaracteristica caracteristica : TipoCaracteristica.values()) 
		{
			int valor = caracteristicas.get(caracteristica);
			if (valor != 0) 
			{
				sb.append(Integer.toString(caracteristica.get_Id(), SERIALIZADOR_BASE)).append(VALOR_SEPARADOR).append(Integer.toString(valor, SERIALIZADOR_BASE)).append(STATS_SEPARADOR);
			}
		}
		return sb.toString();
	}
	
	public MutableCaracteristicas unserialize(final String serializado) 
	{
        if (serializado == null) 
        {
            return null;
        }
        
        MutableCaracteristicas caracteristicas = new DefaultCaracteristicas();

        for (String stats : serializado.split(STATS_SEPARADOR)) 
        {
            if (!stats.isEmpty())
            {
            	String[] data = stats.split(VALOR_SEPARADOR, 2);
                caracteristicas.set(TipoCaracteristica.get_Id(Integer.parseInt(data[0], SERIALIZADOR_BASE)), Integer.parseInt(data[1], SERIALIZADOR_BASE));
            }
        }
        return caracteristicas;
    }
}
