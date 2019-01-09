package objetos;

import java.util.concurrent.ConcurrentHashMap;

final public class Comunidades 
{
	private final byte id;
	private final String nombre;
	private static final ConcurrentHashMap<Byte, Comunidades> comunidades = new ConcurrentHashMap<Byte, Comunidades>();
	
	public Comunidades(final byte _id, final String _nombre)
	{
		id = _id;
		nombre = _nombre;
		comunidades.put(id, this);
	}

	public byte get_Id() 
	{
		return id;
	}

	public String get_Nombre() 
	{
		return nombre;
	}
	
	public static ConcurrentHashMap<Byte, Comunidades> get_Comunidades()
	{
		return comunidades;
	}
}
