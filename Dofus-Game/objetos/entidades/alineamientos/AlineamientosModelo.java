package objetos.entidades.alineamientos;

import java.util.concurrent.ConcurrentHashMap;

import objetos.mapas.Areas;

final public class AlineamientosModelo
{
	private final byte id;
	private final Areas area;
	private final boolean es_especial;

	private static final ConcurrentHashMap<Byte, AlineamientosModelo> alineamientos_cargados = new ConcurrentHashMap<Byte, AlineamientosModelo>();
	
	public AlineamientosModelo(final byte _id, Areas _area, boolean _es_especial)
	{
		id = _id;
		area = _area;
		es_especial = _es_especial;
		
		alineamientos_cargados.put(id, this);
	}

	public byte get_Id() 
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
	
	public static ConcurrentHashMap<Byte, AlineamientosModelo> get_Alineamientos_Cargados()
	{
		return alineamientos_cargados;
	}
	
	public static AlineamientosModelo get_Alineamientos_Cargados(final byte _alineamiento)
	{
		return alineamientos_cargados.get(_alineamiento);
	}
}
