package objetos.entidades.alineamientos;

import java.util.concurrent.ConcurrentHashMap;

import objetos.entidades.personajes.Personajes;

public class Dones
{
	private final byte id, nivel;
	
	private static final ConcurrentHashMap<Byte, Dones> dones_cargados = new ConcurrentHashMap<Byte, Dones>();

	
	public Dones(final byte _id, final byte _nivel)
	{
		id = _id;
		nivel = _nivel;
	}
	
	public int get_ID() 
	{
		return id;
	}
	
	public int get_Nivel()
	{
		return nivel;
	}
}
