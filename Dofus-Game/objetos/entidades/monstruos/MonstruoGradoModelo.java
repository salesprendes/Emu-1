package objetos.entidades.monstruos;

import java.util.Map;
import java.util.TreeMap;

import juego.enums.TipoStats;
import objetos.entidades.personajes.Stats;

public class MonstruoGradoModelo
{
	private final byte grado;
	private short nivel;
	private final MonstruosModelos monstruo_modelo;
	private final Stats stats;
	
	private static int[] stats_ordenados =
	{
		TipoStats.AGREGAR_RES_PORC_NEUTRAL, TipoStats.AGREGAR_RES_PORC_TIERRA,
		TipoStats.AGREGAR_RES_PORC_FUEGO, TipoStats.AGREGAR_RES_PORC_AGUA, TipoStats.AGREGAR_RES_PORC_AIRE,
		TipoStats.AGREGAR_ESQUIVA_PERD_PA, TipoStats.AGREGAR_ESQUIVA_PERD_PM, TipoStats.AGREGAR_VITALIDAD,
		TipoStats.AGREGAR_PA, TipoStats.AGREGAR_PM
	};
	
	public MonstruoGradoModelo(final byte _grado, final String _stats, final MonstruosModelos _monstruo_modelo)
	{
		grado = _grado;
		monstruo_modelo = _monstruo_modelo;
		
		Map<Integer, Integer> map_stats = new TreeMap<Integer, Integer>();
		int i = 0;
		for (String valor : _stats.split(","))
		{
			try 
			{
				if (!valor.isEmpty())
				{
					switch (i) 
					{
						case 0:
							nivel = Short.parseShort(valor);
						break;

						default:
							map_stats.put(stats_ordenados[i], Integer.parseInt(valor));
						break;
					}
				}
				i++;
			} 
			catch (final Exception e) {}
		}
		
		if (map_stats.get(TipoStats.AGREGAR_VITALIDAD) == null) 
			map_stats.put(TipoStats.AGREGAR_VITALIDAD, 50 * nivel);
		if (map_stats.get(TipoStats.AGREGAR_PA) == null) 
			map_stats.put(TipoStats.AGREGAR_PA, 6);
		if (map_stats.get(TipoStats.AGREGAR_PM) == null) 
			map_stats.put(TipoStats.AGREGAR_PM, 3);
		if (map_stats.get(TipoStats.AGREGAR_CRIATURAS_INVOCABLES) == null) 
			map_stats.put(TipoStats.AGREGAR_CRIATURAS_INVOCABLES, 1);

		stats = new Stats(map_stats);
	}
}
