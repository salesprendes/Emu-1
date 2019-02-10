package objetos.mapas;

import java.util.concurrent.ConcurrentHashMap;

public class Areas 
{
	private final short id;
	private SuperAreas super_area;
	private short[] cementerio;
	private final boolean necesita_abono;

	private static final ConcurrentHashMap<Short, Areas> areas_cargadas = new ConcurrentHashMap<Short, Areas>();

	public Areas(final short _id, final byte _super_area, final String _cementerio, final boolean _necesita_abono) 
	{
		id = _id;
		super_area = SuperAreas.get_SuperAreas_Cargadas(_super_area);
		if (super_area == null) 
		{
			super_area = new SuperAreas(_super_area);
		}
		try
		{
			cementerio = new short[]{Short.parseShort(_cementerio.split(";")[0]), Short.parseShort(_cementerio.split(";")[1])};
		}
		catch (final Exception e)
		{
			cementerio = new short[]{8534, 196};
		}
		necesita_abono = _necesita_abono;
		areas_cargadas.put(id, this);
	}

	public SuperAreas get_Super_area() 
	{
		return super_area;
	}

	public short[] get_Cementerio()//MapaID, CeldaID
	{
		return cementerio;
	}

	public boolean get_Necesita_Abono()
	{
		return necesita_abono;
	}

	public static ConcurrentHashMap<Short, Areas> get_Areas_Cargadas()
	{
		return areas_cargadas;
	}

	public static Areas get_Areas_Cargadas(final short area_id)
	{
		return areas_cargadas.get(area_id);
	}
}
