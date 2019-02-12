package objetos.entidades.alineamientos;

import java.util.HashMap;
import java.util.Map;

import objetos.mapas.Areas;

final public class AlineamientosModelo
{
	private final byte id;
	private final short[] zaapis;
	private final Areas area;

	private static final Map<Byte, AlineamientosModelo> alineamientos_cargados = new HashMap<Byte, AlineamientosModelo>();
	
	public AlineamientosModelo(final byte _id, final Areas _area, final String _zaapis)
	{
		id = _id;
		area = _area;
		
		if (!_zaapis.equals("-1") && !_zaapis.isEmpty())
		{
			String[] separador = _zaapis.split(",");
			zaapis = new short[separador.length];
			
			for(byte i = 0; i < zaapis.length; ++i)
			{
				zaapis[i] = Short.parseShort(separador[i]);
			}
		}
		else
			zaapis = new short[0];

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

	public short[] get_Zaapis()
	{
		return zaapis;
	}
	
	public static Map<Byte, AlineamientosModelo> get_Alineamientos_Cargados()
	{
		return alineamientos_cargados;
	}
	
	public static AlineamientosModelo get_Alineamientos_Cargados(final byte _alineamiento)
	{
		return alineamientos_cargados.get(_alineamiento);
	}
}
