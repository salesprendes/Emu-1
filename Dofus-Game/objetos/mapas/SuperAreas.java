package objetos.mapas;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class SuperAreas
{
	private final byte id;
	private final ArrayList<Areas> _areas = new ArrayList<Areas>();
	
	private static final ConcurrentHashMap<Byte, SuperAreas> super_areas_cargadas = new ConcurrentHashMap<Byte, SuperAreas>();
	
	public SuperAreas(final byte _id)
	{
		id = _id;
		super_areas_cargadas.put(id, this);
	}
	
	public void get_Agregar_Area(final Areas area) 
	{
		_areas.add(area);
	}
	
	public byte get_Id()
	{
		return id;
	}
	
	public static ConcurrentHashMap<Byte, SuperAreas> get_SuperAreas_Cargadas()
	{
		return super_areas_cargadas;
	}
	
	public static SuperAreas get_SuperAreas_Cargadas(final byte super_area)
	{
		return super_areas_cargadas.get(super_area);
	}
}
