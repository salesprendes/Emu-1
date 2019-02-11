package objetos.mapas.interactivo;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.common.primitives.Ints;

public class InteractivoModelo
{
	private final int recarga;
	private final short id, duracion;
	private final int[] gfx, habilidades;
	private final byte caminable, tipo, accion;

	private static ArrayList<InteractivoModelo> interactivos_modelo_cargados = new ArrayList<InteractivoModelo>();
	
	public InteractivoModelo(final short _id, final String _gfx, final byte _tipo, final byte _accion, final int _recarga, final short _duracion, final byte _caminable, final String _habilidades)
	{
		id = _id;
		
		if (!_gfx.equals("-1") && !_gfx.isEmpty())
			gfx = Arrays.stream(_gfx.split(",")).mapToInt(Integer::parseInt).toArray();
		else
			gfx = new int[0];
		
		tipo = _tipo;
		accion = _accion;
		recarga = _recarga;
		duracion = _duracion;
		caminable = _caminable;
		
		if (!_habilidades.equals("-1") && !_habilidades.isEmpty())
			habilidades = Arrays.stream(_habilidades.split(",")).mapToInt(Integer::parseInt).toArray();
		else
			habilidades = new int[0];
		
		interactivos_modelo_cargados.add(this);
	}
	
	public short get_Id()
	{
		return id;
	}
	
	public int get_Recarga()
	{
		return recarga * 2;
	}
	
	public short get_Duracion()
	{
		return duracion;
	}
	
	public int[] get_Gfx()
	{
		return gfx;
	}
	
	public int[] get_Habilidades()
	{
		return habilidades;
	}
	
	public byte get_Caminable()
	{
		return caminable;
	}
	
	public byte get_Tipo()
	{
		return tipo;
	}
	
	public byte get_Accion()
	{
		return accion;
	}
	
	public boolean get_Es_Caminable()
	{
		return (caminable & 2) == 2;
	}
	
	public static InteractivoModelo get_Modelo_Por_Gfx(final int gfx) 
	{
		for (final InteractivoModelo interactivo : interactivos_modelo_cargados) 
		{
			if (Ints.contains(interactivo.get_Gfx(), gfx))
				return interactivo;
		}
		return null;
	}
	
	public static ArrayList<InteractivoModelo> get_Interactivos_Modelos_Cargados()
	{
		return interactivos_modelo_cargados;
	}

	public static InteractivoModelo get_Interactivos_Modelos_Cargados(final short interactivo_modelo_id)
	{
		return interactivos_modelo_cargados.get(interactivo_modelo_id);
	}
}
