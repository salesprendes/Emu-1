package objetos.entidades.monstruos;

import juego.enums.TipoCaracteristica;
import objetos.entidades.caracteristicas.Caracteristicas;
import objetos.entidades.caracteristicas.DefaultCaracteristicas;

public class MonstruoGradoModelo
{
	private final byte grado;
	private short nivel;
	private final MonstruosModelo monstruo_modelo;
	private final Caracteristicas stats = new DefaultCaracteristicas();
	
	private static TipoCaracteristica[] stats_ordenados =
	{
		TipoCaracteristica.RES_PORC_NEUTRAL, TipoCaracteristica.RES_PORC_TIERRA,
		TipoCaracteristica.RES_PORC_FUEGO, TipoCaracteristica.RES_PORC_AGUA, TipoCaracteristica.RES_PORC_AIRE,
		TipoCaracteristica.ESQUIVA_PERD_PA, TipoCaracteristica.ESQUIVA_PERD_PM, TipoCaracteristica.VITALIDAD,
		TipoCaracteristica.PUNTOS_ACCION, TipoCaracteristica.PUNTOS_MOVIMIENTO
	};
	
	public MonstruoGradoModelo(final byte _grado, final String _stats, final MonstruosModelo _monstruo_modelo)
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
							stats.set_Caracteristica(stats_ordenados[i], Integer.parseInt(valor));
						break;
					}
				}
				i++;
			} 
			catch (final Exception e) {}
		}
		
		stats.set_Caracteristica(TipoCaracteristica.VITALIDAD, 50 * nivel);
		stats.set_Caracteristica(TipoCaracteristica.PUNTOS_ACCION, 6);
		stats.set_Caracteristica(TipoCaracteristica.PUNTOS_MOVIMIENTO, 3);
		stats.set_Caracteristica(TipoCaracteristica.CRIATURAS_INVOCABLES, 1);
	}

	public byte get_Grado()
	{
		return grado;
	}

	public MonstruosModelo get_Monstruo_modelo()
	{
		return monstruo_modelo;
	}

	public Caracteristicas get_Stats()
	{
		return stats;
	}
}
