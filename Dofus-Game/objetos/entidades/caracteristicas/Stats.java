package objetos.entidades.caracteristicas;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import juego.enums.TipoStats;
import objetos.entidades.personajes.Personajes;

public class Stats
{
	private Map<Integer, Integer> stats_id = new TreeMap<Integer, Integer>();
	private ArrayList<String> stats_hechizos;
	private ArrayList<String> stats_repetidos;
	private Map<Integer, String> stats_textos;

	public Stats(){}
	
	public void set_Stats_Base(final Map<Integer, Integer> _stats, final Personajes _personaje) 
	{
		stats_id.clear();
		if (_stats != null)
			stats_id.putAll(_stats);
		
		if(_personaje != null)
		{
			byte puntos_accion = _personaje.get_Raza().get_Pa_Base();
			stats_id.put(TipoStats.AGREGAR_PA, _personaje.get_Nivel() < 100 ? puntos_accion : puntos_accion + 1);
			stats_id.put(TipoStats.AGREGAR_PM, (int) _personaje.get_Raza().get_Pm_Base());
			stats_id.put(TipoStats.AGREGAR_PROSPECCION, (int) _personaje.get_Raza().get_Prospeccion_base());
			stats_id.put(TipoStats.AGREGAR_PODS, _personaje.get_Raza().get_Pods_base());
			stats_id.put(TipoStats.AGREGAR_CRIATURAS_INVOCABLES, 1);
			stats_id.put(TipoStats.AGREGAR_INICIATIVA, (int) _personaje.get_Raza().get_Iniciativa_base());
		}
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
			stats_id.remove(statID);
		else
			stats_id.put(statID, valor);
	}

	public void get_Agregar_Stat_Id(final int stat_id, final int valor)
	{
		if (stats_id.get(stat_id) != null)
			fijarStat(stat_id, stats_id.get(stat_id) + valor);
		else
			fijarStat(stat_id, valor);
	}
	
	public int get_Mostrar_Stat_Complemento(final int statID) 
	{
		int val = get_Mostrar_Stat(statID);
		switch (statID) 
		{
			case TipoStats.AGREGAR_RES_FIJA_PVP_TIERRA:
				val += get_Mostrar_Stat(TipoStats.AGREGAR_RES_FIJA_TIERRA);
			break;
				
			case TipoStats.AGREGAR_RES_FIJA_PVP_AGUA:
				val += get_Mostrar_Stat(TipoStats.AGREGAR_RES_FIJA_AGUA);
			break;
				
			case TipoStats.AGREGAR_RES_FIJA_PVP_AIRE:
				val += get_Mostrar_Stat(TipoStats.AGREGAR_RES_FIJA_AIRE);
			break;
				
			case TipoStats.AGREGAR_RES_FIJA_PVP_FUEGO:
				val += get_Mostrar_Stat(TipoStats.AGREGAR_RES_FIJA_FUEGO);
			break;
				
			case TipoStats.AGREGAR_RES_FIJA_PVP_NEUTRAL:
				val += get_Mostrar_Stat(TipoStats.AGREGAR_RES_FIJA_NEUTRAL);
			break;
				
			case TipoStats.AGREGAR_RES_PORC_PVP_TIERRA:
				val += get_Mostrar_Stat(TipoStats.AGREGAR_RES_PORC_TIERRA);
			break;
				
			case TipoStats.AGREGAR_RES_PORC_PVP_AGUA :
				val += get_Mostrar_Stat(TipoStats.AGREGAR_RES_PORC_AGUA);
			break;
				
			case TipoStats.AGREGAR_RES_PORC_PVP_AIRE :
				val += get_Mostrar_Stat(TipoStats.AGREGAR_RES_PORC_AIRE);
				break;
				
			case TipoStats.AGREGAR_RES_PORC_PVP_FUEGO :
				val += get_Mostrar_Stat(TipoStats.AGREGAR_RES_PORC_FUEGO);
			break;
				
			case TipoStats.AGREGAR_RES_PORC_PVP_NEUTRAL:
				val += get_Mostrar_Stat(TipoStats.AGREGAR_RES_PORC_NEUTRAL);
			break;
				
			case TipoStats.AGREGAR_ESQUIVA_PERD_PA:
				val += get_Mostrar_Stat(TipoStats.AGREGAR_SABIDURIA) / 4;
			break;
				
			case TipoStats.AGREGAR_ESQUIVA_PERD_PM:
				val += get_Mostrar_Stat(TipoStats.AGREGAR_SABIDURIA) / 4;
			break;
				
			case TipoStats.AGREGAR_PROSPECCION:
				val += get_Mostrar_Stat(TipoStats.AGREGAR_SUERTE) / 10;
				val = (int)Math.ceil(val);
			break;
				
			default :
				val = get_Mostrar_Stat(statID);
			break;
		}
		return val;
	}
	
	public int get_Mostrar_Stat(final int statID) 
	{
		int valor = 0;
		if (stats_id.get(statID) != null)
			valor = stats_id.get(statID);
		
		switch (statID) 
		{
			case TipoStats.AGREGAR_PA:
				if (stats_id.get(TipoStats.AGREGAR_PA2) != null)
					valor += stats_id.get(TipoStats.AGREGAR_PA2);
				if (stats_id.get(TipoStats.RETIRAR_PA) != null)
					valor -= stats_id.get(TipoStats.RETIRAR_PA);
				if (stats_id.get(TipoStats.RETIRAR_PA_FIJO_NO_ESQUIVABLE) != null)
					valor -= stats_id.get(TipoStats.RETIRAR_PA_FIJO_NO_ESQUIVABLE);
			break;
			
			case TipoStats.AGREGAR_PM:
				if (stats_id.get(TipoStats.AGREGAR_PM2) != null)
					valor += stats_id.get(TipoStats.AGREGAR_PM2);
				if (stats_id.get(TipoStats.RETIRAR_PM) != null) 
					valor -= stats_id.get(TipoStats.RETIRAR_PM);
				if (stats_id.get(TipoStats.RETIRAR_PM_FIJO) != null)
					valor -= stats_id.get(TipoStats.RETIRAR_PM_FIJO);
			break;
			
			case TipoStats.AGREGAR_ALCANCE:
				if (stats_id.get(TipoStats.RETIRAR_ALCANCE) != null)
					valor -= stats_id.get(TipoStats.RETIRAR_ALCANCE);
			break;
			
			case TipoStats.AGREGAR_VITALIDAD:
				if (stats_id.get(TipoStats.AGREGAR_VIDA) != null)
					valor += stats_id.get(TipoStats.AGREGAR_VIDA);
				
				if (stats_id.get(TipoStats.RETIRAR_VITALIDAD) != null)
					valor -= stats_id.get(TipoStats.RETIRAR_VITALIDAD);
					
				if (stats_id.get(TipoStats.RETIRAR_PORC_PDV_TEMPORAL) != null)
					valor -= stats_id.get(TipoStats.RETIRAR_PORC_PDV_TEMPORAL);
				
				if (stats_id.get(TipoStats.AGREGA_STAT_VITALIDAD) != null)
					valor += stats_id.get(TipoStats.AGREGA_STAT_VITALIDAD);
			break;
			
			case TipoStats.AGREGAR_SABIDURIA:
				if (stats_id.get(TipoStats.AGREGA_STAT_SABIDURIA) != null)
					valor += stats_id.get(TipoStats.AGREGA_STAT_SABIDURIA);
				
				if (stats_id.get(TipoStats.RETIRAR_SABIDURIA) != null)
					valor -= stats_id.get(TipoStats.RETIRAR_SABIDURIA);
			break;
			
			case TipoStats.AGREGAR_INTELIGENCIA :
				if (stats_id.get(TipoStats.RETIRAR_INTELIGENCIA) != null)
					valor -= stats_id.get(TipoStats.RETIRAR_INTELIGENCIA);

				if (stats_id.get(TipoStats.AGREGA_STAT_INTELIGENCIA) != null)
					valor += stats_id.get(611);
			break;
		}
		
		return valor;
	}
	
	public Map<Integer, Integer> get_Stats()
	{
		return stats_id;
	}
	
	public boolean get_Stat_Id(final int stat) 
	{
		return stats_id.get(stat) != null;
	}
	
	public void clear() 
	{
		stats_id.clear();
		stats_hechizos = null;
		stats_repetidos = null;
		stats_textos = null;
	}
}