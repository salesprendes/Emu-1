package objetos.entidades.personajes;

import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentHashMap;

import objetos.entidades.caracteristicas.Caracteristicas;

final public class Razas
{
	private final byte id;
	final private SortedMap<Integer, Caracteristicas> stats_base;
	private final short mapa_id_comienzo, celda_id_comienzo, vida_base;

	private static final ConcurrentHashMap<Byte, Razas> razas_cargadas = new ConcurrentHashMap<Byte, Razas>();

	public Razas(final byte _id, final SortedMap<Integer, Caracteristicas> _stats_base, final short _vida_base, final short _mapa_id_comienzo, final short _celda_id_comienzo)
	{
		id = _id;
		stats_base = _stats_base;
		vida_base = _vida_base;
		mapa_id_comienzo = _mapa_id_comienzo;
		celda_id_comienzo = _celda_id_comienzo;

		razas_cargadas.put(id, this);
	}

	public byte get_Id()
	{
		return id;
	}

	public Caracteristicas get_Stats_base(int nivel) 
	{
		for (Entry<Integer, Caracteristicas> caracteristica  : stats_base.entrySet()) 
		{
			if (caracteristica.getKey() <= nivel) 
			{
				return caracteristica.getValue();
			}
		}
		throw new IllegalArgumentException("Nivel no encontrado " + nivel + " para la raza: " + id);
	}

	public SortedMap<Integer, Caracteristicas> get_Stats_base()
	{
		return stats_base;
	}

	public short get_Vida_Base()
	{
		return vida_base;
	}

	public short get_Mapa_id_comienzo() 
	{
		return mapa_id_comienzo;
	}

	public short get_Celda_id_comienzo()
	{
		return celda_id_comienzo;
	}

	public static ConcurrentHashMap<Byte, Razas> get_Razas_Cargadas()
	{
		return razas_cargadas;
	}

	public static Razas get_Razas_Cargadas(byte raza_id) 
	{
		return razas_cargadas.get(raza_id);
	}
}
