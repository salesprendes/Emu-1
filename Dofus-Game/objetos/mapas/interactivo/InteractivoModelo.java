package objetos.mapas.interactivo;

import java.util.ArrayList;

import com.google.common.primitives.Shorts;

public class InteractivoModelo
{
	private final int recarga;
	private final short id, duracion;
	private final short[] gfx, habilidades;
	private final byte caminable, tipo, accion;

	private static ArrayList<InteractivoModelo> interactivos_modelo_cargados = new ArrayList<InteractivoModelo>();
	
	public InteractivoModelo(final short _id, final String _gfx, final byte _tipo, final byte _accion, final int _recarga, final short _duracion, final byte _caminable, final String _habilidades)
	{
		id = _id;
		
		if (!_gfx.equals("-1") && !_gfx.isEmpty())
		{
			String[] separador = _gfx.split(",");
			gfx = new short[separador.length];
			
			for(byte i = 0; i < gfx.length; ++i)
			{
				gfx[i] = Short.parseShort(separador[i]);
			}
		}
		else
			gfx = new short[0];
		
		tipo = _tipo;
		accion = _accion;
		recarga = _recarga;
		duracion = _duracion;
		caminable = _caminable;
		
		if (!_habilidades.equals("-1") && !_habilidades.isEmpty())
		{
			String[] separador = _habilidades.split(",");
			habilidades = new short[separador.length];
			
			for(byte i = 0; i < habilidades.length; ++i)
			{
				habilidades[i] = Short.parseShort(separador[i]);
			}
		}
		else
			habilidades = new short[0];
		
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
	
	public short[] get_Gfx()
	{
		return gfx;
	}
	
	public short[] get_Habilidades()
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
	
	public static InteractivoModelo get_Modelo_Por_Gfx(final short gfx_id) 
	{
		for (final InteractivoModelo interactivo : interactivos_modelo_cargados) 
		{
			if (Shorts.contains(interactivo.get_Gfx(), gfx_id))
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
