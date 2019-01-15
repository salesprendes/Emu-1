package objetos.entidades.personajes;

import java.util.Map;
import java.util.TreeMap;

import juego.enums.TipoStats;

public class Stats
{
	private Map<Integer, Integer> stats = new TreeMap<Integer, Integer>();

	public Stats(final boolean agregar_bases, final Personajes personaje)
	{
		stats = new TreeMap<Integer,Integer>();
		if(agregar_bases)
		{
			byte puntos_accion = personaje.get_Raza().get_Pa_Base();
			stats.put(TipoStats.AGREGAR_PA, personaje.get_Nivel() < 100 ? puntos_accion : puntos_accion + 1);
			stats.put(TipoStats.AGREGAR_PM, (int) personaje.get_Raza().get_Pm_Base());
			stats.put(TipoStats.AGREGAR_PROSPECCION, (int) personaje.get_Raza().get_Prospeccion_base());
			stats.put(TipoStats.AGREGAR_PODS, 1000);
			stats.put(TipoStats.AGREGAR_CRIATURAS_INVOCABLES, 1);
			stats.put(TipoStats.AGREGAR_INICIATIVA, (int) personaje.get_Raza().get_Iniciativa_base());
		}
	}

	public Stats(final Map<Integer, Integer> _stats, final boolean agregar_bases, final Personajes personaje)
	{
		stats = _stats;
		if(agregar_bases)
		{
			byte puntos_accion = personaje.get_Raza().get_Pa_Base();
			stats.put(TipoStats.AGREGAR_PA, personaje.get_Nivel() < 100 ? puntos_accion : puntos_accion + 1);
			stats.put(TipoStats.AGREGAR_PM, (int) personaje.get_Raza().get_Pm_Base());
			stats.put(TipoStats.AGREGAR_PROSPECCION, (int) personaje.get_Raza().get_Prospeccion_base());
			stats.put(TipoStats.AGREGAR_PODS, 1000);
			stats.put(TipoStats.AGREGAR_CRIATURAS_INVOCABLES, 1);
			stats.put(TipoStats.AGREGAR_INICIATIVA, (int) personaje.get_Raza().get_Iniciativa_base());
		}
	}

	public Stats(final Map<Integer, Integer> _stats)
	{
		stats = _stats;
	}

	public Stats()
	{
		stats = new TreeMap<Integer,Integer>();
	}

	public void fijarStat(int statID, int valor) 
	{
		if (valor < 0)
		{
			int exStatID = statID;
			statID = TipoStats.get_Stat_Opuesto(statID);
			if (statID != exStatID)
				valor = -valor;
		}
		if (valor <= 0)
			stats.remove(statID);
		else
			stats.put(statID, valor);
	}

	public void get_Agregar_Stat_Id(final int statID, final int valor)
	{
		if (stats.get(statID) != null)
			fijarStat(statID, stats.get(statID) + valor);
		else
			fijarStat(statID, valor);
	}
	
	public int get_Mostrar_Stat(final int statID) 
	{
		int valor = 0;
		if (stats.get(statID) != null)
			valor = stats.get(statID);
		
		switch (statID) 
		{
			case TipoStats.AGREGAR_PA:
				if (stats.get(TipoStats.AGREGAR_PA2) != null)
					valor += stats.get(TipoStats.AGREGAR_PA2);
				if (stats.get(TipoStats.RETIRAR_PA) != null)
					valor -= stats.get(TipoStats.RETIRAR_PA);
				if (stats.get(TipoStats.RETIRAR_PA_FIJO_NO_ESQUIVABLE) != null)
					valor -= stats.get(TipoStats.RETIRAR_PA_FIJO_NO_ESQUIVABLE);
			break;
		}
		
		return valor;
	}
}
