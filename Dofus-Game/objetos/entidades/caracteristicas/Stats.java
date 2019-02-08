package objetos.entidades.caracteristicas;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import juego.enums.TipoStats;
import objetos.entidades.personajes.Personajes;

public class Stats
{
	private Map<Short, Integer> stats_id = new TreeMap<Short, Integer>();
	private ArrayList<String> stats_hechizos;
	private ArrayList<String> stats_repetidos;
	private Map<Integer, String> stats_texto;

	public Stats(){}//CONSTRUCTOR BASE
	
	public void set_Stats_Base(final Map<Short, Integer> _stats, final Personajes _personaje) 
	{
		if (_stats != null)
		{
			stats_id.clear();
			stats_id.putAll(_stats);
		}
		
		if(_personaje != null)
		{
			byte puntos_accion = _personaje.get_Raza().get_Pa_Base();
			stats_id.put(TipoStats.AGREGAR_PA, _personaje.get_Nivel() < 100 ? puntos_accion : puntos_accion + 1);
			stats_id.put(TipoStats.AGREGAR_PM, (int) _personaje.get_Raza().get_Pm_Base());
			stats_id.put(TipoStats.AGREGAR_PROSPECCION, (int) _personaje.get_Raza().get_Prospeccion_base());
			stats_id.put(TipoStats.AGREGAR_PODS, (int) _personaje.get_Raza().get_Pods_base());
			stats_id.put(TipoStats.AGREGAR_CRIATURAS_INVOCABLES, 1);
			stats_id.put(TipoStats.AGREGAR_INICIATIVA, (int) _personaje.get_Raza().get_Iniciativa_base());
		}
	}

	public void set_Fijar_Stat(short stat_id, int valor) 
	{
		if (valor < 0)
		{
			final int anterior_stat_id = stat_id;
			stat_id = TipoStats.get_Stat_Opuesto(stat_id);
			
			if (stat_id != anterior_stat_id)
				valor = -valor;
		}
		if (valor <= 0)
			stats_id.remove(stat_id);
		else
			stats_id.put(stat_id, valor);
	}

	public void get_Agregar_Stat_Id(final short stat_id, final int valor)
	{
		if (stats_id.get(stat_id) != null)
			set_Fijar_Stat(stat_id, stats_id.get(stat_id) + valor);
		else
			set_Fijar_Stat(stat_id, valor);
	}
	
	public int get_Mostrar_Stat_Complemento(final short statID) 
	{
		int valor = get_Mostrar_Stat(statID);
		switch (statID) 
		{
			case TipoStats.AGREGAR_RES_FIJA_PVP_TIERRA:
				valor += get_Mostrar_Stat(TipoStats.AGREGAR_RES_FIJA_TIERRA);
			break;
				
			case TipoStats.AGREGAR_RES_FIJA_PVP_AGUA:
				valor += get_Mostrar_Stat(TipoStats.AGREGAR_RES_FIJA_AGUA);
			break;
				
			case TipoStats.AGREGAR_RES_FIJA_PVP_AIRE:
				valor += get_Mostrar_Stat(TipoStats.AGREGAR_RES_FIJA_AIRE);
			break;
				
			case TipoStats.AGREGAR_RES_FIJA_PVP_FUEGO:
				valor += get_Mostrar_Stat(TipoStats.AGREGAR_RES_FIJA_FUEGO);
			break;
				
			case TipoStats.AGREGAR_RES_FIJA_PVP_NEUTRAL:
				valor += get_Mostrar_Stat(TipoStats.AGREGAR_RES_FIJA_NEUTRAL);
			break;
				
			case TipoStats.AGREGAR_RES_PORC_PVP_TIERRA:
				valor += get_Mostrar_Stat(TipoStats.AGREGAR_RES_PORC_TIERRA);
			break;
				
			case TipoStats.AGREGAR_RES_PORC_PVP_AGUA :
				valor += get_Mostrar_Stat(TipoStats.AGREGAR_RES_PORC_AGUA);
			break;
				
			case TipoStats.AGREGAR_RES_PORC_PVP_AIRE :
				valor += get_Mostrar_Stat(TipoStats.AGREGAR_RES_PORC_AIRE);
			break;
				
			case TipoStats.AGREGAR_RES_PORC_PVP_FUEGO :
				valor += get_Mostrar_Stat(TipoStats.AGREGAR_RES_PORC_FUEGO);
			break;
				
			case TipoStats.AGREGAR_RES_PORC_PVP_NEUTRAL:
				valor += get_Mostrar_Stat(TipoStats.AGREGAR_RES_PORC_NEUTRAL);
			break;
				
			case TipoStats.AGREGAR_ESQUIVA_PERD_PA:
				valor += get_Mostrar_Stat(TipoStats.AGREGAR_SABIDURIA) / 4;
			break;
				
			case TipoStats.AGREGAR_ESQUIVA_PERD_PM:
				valor += get_Mostrar_Stat(TipoStats.AGREGAR_SABIDURIA) / 4;
			break;
				
			case TipoStats.AGREGAR_PROSPECCION:
				valor += get_Mostrar_Stat(TipoStats.AGREGAR_SUERTE) / 10;
				valor = (int)Math.ceil(valor);
			break;
				
			default :
				valor = get_Mostrar_Stat(statID);
			break;
		}
		return valor;
	}
	
	public int get_Mostrar_Stat(final short statID) 
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
			
			case TipoStats.AGREGAR_INTELIGENCIA:
				if (stats_id.get(TipoStats.RETIRAR_INTELIGENCIA) != null)
					valor -= stats_id.get(TipoStats.RETIRAR_INTELIGENCIA);

				if (stats_id.get(TipoStats.AGREGA_STAT_INTELIGENCIA) != null)
					valor += stats_id.get(TipoStats.AGREGA_STAT_INTELIGENCIA);
			break;
			
			case TipoStats.AGREGAR_CURAS:
				if (stats_id.get(TipoStats.RETIRAR_CURAS) != null) 
					valor -= stats_id.get(TipoStats.RETIRAR_CURAS);
			break;
		}
		
		return valor;
	}
	
	public Map<Short, Integer> get_Stats()
	{
		return stats_id;
	}
	
	public boolean get_Stat_Id(final short stat) 
	{
		return stats_id.get(stat) != null;
	}
	
	public void set_Stat_Hechizo(String str) 
	{
		if (stats_hechizos == null)
			stats_hechizos = new ArrayList<>();
		
		stats_hechizos.add(str);
	}
	
	public void set_Stat_Repetido(String str) 
	{
		if (stats_repetidos == null)
			stats_repetidos = new ArrayList<>();
		stats_repetidos.add(str);
	}
	
	public void set_Stat_Texto(int statID, String str, boolean completo) 
	{
		if (stats_texto == null)
			stats_texto = new TreeMap<>();
		
		if (str.isEmpty())
			stats_texto.remove(statID);
		else
			stats_texto.put(statID, completo ? str.split("#", 2)[1] : str);
	}
	
	public ArrayList<String> get_Stat_Repetidos() 
	{
		return stats_repetidos;
	}
	
	public ArrayList<String> get_Stat_Hechizo()
	{
		return stats_hechizos;
	}
	
	public String get_Stat_Texto(final int stat)
	{
		if (stats_texto == null || stats_texto.get(stat) == null) 
			return "";
		
		return stats_texto.get(stat);
	}
	
	public void clear() 
	{
		stats_id.clear();
		stats_hechizos = null;
		stats_repetidos = null;
		stats_texto = null;
	}
}