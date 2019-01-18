package objetos;

import java.util.Map;
import java.util.TreeMap;

public class Experiencia
{
	final private short objevivos, alineamientos; // no superara 32.000 (2 bytes)
	final private int oficios, dragopavos;
	final private long personajes, gremios, encarnaciones;
	
	final private static Map<Integer, Experiencia> experiencias_cargadas = new TreeMap<Integer, Experiencia>();
	
	public Experiencia(final long _personajes, final int _oficios, final int _dragopavos, final short _objevivos, final short _alineamientos, final int _encarnaciones)
	{
		personajes = _personajes;
		oficios = _oficios;
		dragopavos = _dragopavos;
		objevivos = _objevivos;
		alineamientos = _alineamientos;
		gremios = _personajes * 10;
		encarnaciones = _encarnaciones;
	}
	
	public static Map<Integer, Experiencia> get_Experiencias_Cargadas()
	{
		return experiencias_cargadas;
	}

	public static Experiencia get_Razas_Cargadas(final int nivel_experiencia) 
	{
		return experiencias_cargadas.get(nivel_experiencia);
	}

	public long get_Personajes()
	{
		return personajes;
	}

	public int get_Oficios()
	{
		return oficios;
	}

	public int get_Dragopavos()
	{
		return dragopavos;
	}

	public short get_Objevivos()
	{
		return objevivos;
	}

	public short get_Alineamientos()
	{
		return alineamientos;
	}

	public long get_Gremios()
	{
		return gremios;
	}

	public long get_Encarnaciones()
	{
		return encarnaciones;
	}
	
	public static long get_Experiencia_Personajes(int nivel) 
	{
		if (nivel > 200) 
			return Long.MAX_VALUE;
		else if (nivel < 1) 
			nivel = 1;
		
		return experiencias_cargadas.get(nivel).get_Personajes();
	}
	
	public static int get_Experiencia_Alineamientos(int nivel) 
	{
		if (nivel > 10) 
			nivel = 10;
		else if (nivel < 1)
			nivel = 1;
		
		return experiencias_cargadas.get(nivel).get_Alineamientos();
	}
}
