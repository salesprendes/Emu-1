package objetos.entidades.personajes;

import java.util.Map;
import java.util.TreeMap;

public class Dones
{
	private final byte id;
	private final short nivel;
	private final Stats stats = new Stats();
	
	private static final Map<Byte, Short> dones_cargados = new TreeMap<Byte, Short>();
	
	public Dones(final byte _id, final short _nivel, short stat_id, int valor)
	{
		id = _id;
		nivel = _nivel;
		if (stat_id > 0 && valor > 0) 
		{
			stats.get_Agregar_Stat_Id(stat_id, valor);
		}
	}
	
	public int get_Id() 
	{
		return id;
	}
	
	public short get_Nivel()
	{
		return nivel;
	}
	
	public Stats get_Stats() 
	{
		return stats;
	}
	
	public static Map<Byte, Short> get_Dones() 
	{
		return dones_cargados;
	}

	public static Short get_Don_Stat(final byte don_id) 
	{
		return dones_cargados.get(don_id);
	}
}
