package objetos.entidades.personajes;

import java.util.Map;
import java.util.TreeMap;

import juego.enums.TipoCaracteristica;
import objetos.entidades.caracteristicas.Caracteristicas;
import objetos.entidades.caracteristicas.DefaultCaracteristicas;

public class Dones
{
	private final byte id;
	private final short nivel;
	private final Caracteristicas stats = new DefaultCaracteristicas();
	
	private static final Map<Byte, Short> dones_cargados = new TreeMap<Byte, Short>();
	
	public Dones(final byte _id, final short _nivel, TipoCaracteristica stat, int valor)
	{
		id = _id;
		nivel = _nivel;
		if (stat.get_Id() > -1 && valor > 0) 
		{
			stats.set_Caracteristica(stat, valor);
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
	
	public Caracteristicas get_Stats() 
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
