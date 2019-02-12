package objetos.mapas;

import java.util.Map;
import java.util.TreeMap;

public class Areas 
{
	private final byte id;
	private SuperAreas super_area;
	private short[] cementerio;
	private final boolean necesita_abono;

	private static final Map<Byte, Areas> areas_cargadas = new TreeMap<Byte, Areas>();

	public Areas(final byte _id, final byte _super_area, final String _cementerio, final boolean _necesita_abono) 
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
	
	public byte get_Id()
	{
		return id;
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

	public static Map<Byte, Areas> get_Areas_Cargadas()
	{
		return areas_cargadas;
	}

	public static Areas get_Areas_Cargadas(final byte area_id)
	{
		return areas_cargadas.get(area_id);
	}
}
