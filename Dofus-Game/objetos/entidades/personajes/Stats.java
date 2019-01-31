package objetos.entidades.personajes;

import java.util.Map;
import java.util.TreeMap;

import juego.enums.TipoStats;

public class Stats
{
	private Map<Integer, Integer> stats = new TreeMap<Integer, Integer>();

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

	public void get_Agregar_Stat_Id(final int stat_id, final int valor)
	{
		if (stats.get(stat_id) != null)
			fijarStat(stat_id, stats.get(stat_id) + valor);
		else
			fijarStat(stat_id, valor);
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
			
			case TipoStats.AGREGAR_PM :// PM
				if (stats.get(TipoStats.AGREGAR_PM2) != null)
					valor += stats.get(TipoStats.AGREGAR_PM2);
				if (stats.get(TipoStats.RETIRAR_PM) != null) 
					valor -= stats.get(TipoStats.RETIRAR_PM);
				if (stats.get(TipoStats.RETIRAR_PM_FIJO) != null)
					valor -= stats.get(TipoStats.RETIRAR_PM_FIJO);
			break;
			
			case TipoStats.AGREGAR_ALCANCE :// alcance
				if (stats.get(TipoStats.RETIRAR_ALCANCE) != null)
					valor -= stats.get(TipoStats.RETIRAR_ALCANCE);
			break;
		}
		
		return valor;
	}
	
	public Map<Integer, Integer> get_Stats()
	{
		return stats;
	}
}
