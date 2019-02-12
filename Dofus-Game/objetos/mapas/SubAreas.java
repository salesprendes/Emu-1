package objetos.mapas;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class SubAreas 
{
	private final short id;
	private final boolean es_conquistable;
	private Areas area;
	private final ArrayList<Mapas> _mapas = new ArrayList<Mapas>();
	
	private static final Map<Short, SubAreas> sub_areas_cargadas = new TreeMap<Short, SubAreas>();
	
	public SubAreas(final short _id, final boolean _es_conquistable, final byte _area) 
	{
		id = _id;
		es_conquistable = _es_conquistable;
		area = Areas.get_Areas_Cargadas(_area);
		
		sub_areas_cargadas.put(id, this);
	}

	public short get_Id()
	{
		return id;
	}

	public boolean get_Es_conquistable()
	{
		return es_conquistable;
	}

	public Areas get_Area()
	{
		return area;
	}

	public void set_Area(Areas _area)
	{
		area = _area;
	}
	
	public ArrayList<Mapas> get_Mapas() 
	{
		return _mapas;
	}
	
	public void get_Agregar_Mapa(final Mapas mapa) 
	{
		_mapas.add(mapa);
	}

	public static Map<Short, SubAreas> get_Sub_Areas_Cargadas() 
	{
		return sub_areas_cargadas;
	}
	
	public static SubAreas get_Sub_Areas_Cargadas(final short sub_area) 
	{
		return sub_areas_cargadas.get(sub_area);
	}
}
