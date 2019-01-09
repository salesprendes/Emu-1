package objetos.alineamientos;

import java.util.concurrent.ConcurrentHashMap;

import objetos.mapas.Areas;

public class Alineamientos 
{
	private final short id;
	private final Areas area;
	private boolean es_especial = false;

	private static final ConcurrentHashMap<Short, Alineamientos> alineamientos_cargados = new ConcurrentHashMap<Short, Alineamientos>();
	
	public Alineamientos(final short _id, Areas _area, boolean _es_especial)
	{
		id = _id;
		area = _area;
		es_especial = _es_especial;
		
		alineamientos_cargados.put(id, this);
	}

	public short get_Id() 
	{
		return id;
	}

	public Areas get_Area() 
	{
		return area;
	}

	public boolean get_Es_especial() 
	{
		return es_especial;
	}

	public void set_Es_especial(boolean _es_especial) 
	{
		es_especial = _es_especial;
	}
	
	public static ConcurrentHashMap<Short, Alineamientos> get_Alineamientos_Cargados()
	{
		return alineamientos_cargados;
	}
	
	public static Alineamientos get_Alineamientos_Cargados(final short area)
	{
		return alineamientos_cargados.get(area);
	}
}
