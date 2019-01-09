package objetos.mapas;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class SuperAreas
{
	private final short id;
	private final ArrayList<Areas> _areas = new ArrayList<Areas>();
	
	private static final ConcurrentHashMap<Short, SuperAreas> super_areas_cargadas = new ConcurrentHashMap<Short, SuperAreas>();
	
	public SuperAreas(final short _id)
	{
		id = _id;
		super_areas_cargadas.put(id, this);
	}
	
	public void get_Agregar_Area(final Areas area) 
	{
		_areas.add(area);
	}
	
	public short get_Id()
	{
		return id;
	}
	
	public static ConcurrentHashMap<Short, SuperAreas> get_SuperAreas_Cargadas()
	{
		return super_areas_cargadas;
	}
	
	public static SuperAreas get_SuperAreas_Cargadas(final short super_area)
	{
		return super_areas_cargadas.get(super_area);
	}
}
