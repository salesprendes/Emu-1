package objetos.entidades.personajes;

import java.util.concurrent.ConcurrentHashMap;

final public class Razas
{
	private final byte id, pa_base, pm_base, vida_base;
	private final short iniciativa_base, prospeccion_base, mapa_id_comienzo, celda_id_comienzo, pods_base;
	
	private static final ConcurrentHashMap<Byte, Razas> razas_cargadas = new ConcurrentHashMap<Byte, Razas>();
	
	public Razas(final byte _id, final byte _pa_base, final byte _pm_base, final byte _vida_base, final short _iniciativa_base, final short _pods_base, final short _prospeccion_base, final short _mapa_id_comienzo, final short _celda_id_comienzo)
	{
		id = _id;
		pa_base = _pa_base;
		pm_base = _pm_base;
		vida_base = _vida_base;
		iniciativa_base = _iniciativa_base;
		pods_base = _pods_base;
		prospeccion_base = _prospeccion_base;
		mapa_id_comienzo = _mapa_id_comienzo;
		celda_id_comienzo = _celda_id_comienzo;
		
		razas_cargadas.put(id, this);
	}

	public byte get_Id()
	{
		return id;
	}

	public byte get_Pa_Base() 
	{
		return pa_base;
	}

	public byte get_Pm_Base() 
	{
		return pm_base;
	}

	public byte get_Vida_base()
	{
		return vida_base;
	}

	public short get_Iniciativa_base() 
	{
		return iniciativa_base;
	}

	public int get_Pods_base()
	{
		return pods_base;
	}

	public short get_Prospeccion_base()
	{
		return prospeccion_base;
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