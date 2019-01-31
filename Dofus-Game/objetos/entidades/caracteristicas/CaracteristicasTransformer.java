package objetos.entidades.caracteristicas;

import juego.enums.TipoCaracteristica;

public class CaracteristicasTransformer
{
	final static private int SERIALIZADOR_BASE = 32;
	final static private String VALOR_SEPARADOR = ":";
	final static private String STATS_SEPARADOR = ";";

	public static String serializar(Caracteristicas caracteristicas)
	{
		if (caracteristicas == null) 
		{
			return null;
		}

		StringBuilder sb = new StringBuilder();

		for (TipoCaracteristica caracteristica : TipoCaracteristica.values()) 
		{
			int valor = caracteristicas.get_Caracteristica(caracteristica);
			if (valor != 0) 
			{
				sb.append(Integer.toString(caracteristica.get_Id(), SERIALIZADOR_BASE)).append(VALOR_SEPARADOR).append(Integer.toString(valor, SERIALIZADOR_BASE)).append(STATS_SEPARADOR);
			}
		}
		return sb.toString();
	}
	
	public static Caracteristicas deserializar(final String serializado) 
	{
        if (serializado == null) 
        {
            return null;
        }
        
        Caracteristicas caracteristicas = new DefaultCaracteristicas();

        for (String stats : serializado.split(STATS_SEPARADOR)) 
        {
            if (!stats.isEmpty())
            {
            	String[] separador = stats.split(VALOR_SEPARADOR, 2);
                caracteristicas.set_Caracteristica(TipoCaracteristica.get_Id(Integer.parseInt(separador[0], SERIALIZADOR_BASE)), Integer.parseInt(separador[1], SERIALIZADOR_BASE));
            }
        }
        return caracteristicas;
    }
}
