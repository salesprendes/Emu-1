package objetos.entidades.monstruos;

import juego.enums.TipoStats;
import objetos.entidades.caracteristicas.Stats;

public class MonstruoGradoModelo
{
	private final byte grado;
	private short nivel;
	private final int base_experiencia;
	private final MonstruosModelo monstruo_modelo;
	private final Stats stats = new Stats();
	
	private static short[] stats_ordenados =
	{
		TipoStats.AGREGAR_RES_PORC_NEUTRAL, TipoStats.AGREGAR_RES_PORC_TIERRA,
		TipoStats.AGREGAR_RES_PORC_FUEGO, TipoStats.AGREGAR_RES_PORC_AGUA, TipoStats.AGREGAR_RES_PORC_AIRE,
		TipoStats.AGREGAR_ESQUIVA_PERD_PA, TipoStats.AGREGAR_ESQUIVA_PERD_PM, TipoStats.AGREGAR_VITALIDAD,
		TipoStats.AGREGAR_PA, TipoStats.AGREGAR_PM
	};
	
	public MonstruoGradoModelo(final byte _grado, final String _stats, final int experiencia, final MonstruosModelo _monstruo_modelo)
	{
		grado = _grado;
		monstruo_modelo = _monstruo_modelo;
		
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
							stats.get_Agregar_Stat_Id(stats_ordenados[i], Integer.parseInt(valor));
						break;
					}
				}
				i++;
			} 
			catch (final Exception e) {}
		}
		
		if (stats.get_Stats().get(TipoStats.AGREGAR_VITALIDAD) == null)
			stats.get_Stats().put(TipoStats.AGREGAR_VITALIDAD, 50 * nivel);
		if (stats.get_Stats().get(TipoStats.AGREGAR_PA) == null)
			stats.get_Stats().put(TipoStats.AGREGAR_PA, 6);
		if (stats.get_Stats().get(TipoStats.AGREGAR_PM) == null)
			stats.get_Stats().put(TipoStats.AGREGAR_PM, 3);
		if (stats.get_Stats().get(TipoStats.AGREGAR_CRIATURAS_INVOCABLES) == null)
			stats.get_Stats().put(TipoStats.AGREGAR_CRIATURAS_INVOCABLES, 1);
		
		base_experiencia = experiencia;
	}

	public byte get_Grado()
	{
		return grado;
	}

	public MonstruosModelo get_Monstruo_modelo()
	{
		return monstruo_modelo;
	}

	public Stats get_Stats()
	{
		return stats;
	}
	
	public short get_Nivel()
	{
		return nivel;
	}

	public int get_Base_experiencia()
	{
		return base_experiencia;
	}
}
