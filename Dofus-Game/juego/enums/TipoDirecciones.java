package juego.enums;

import java.util.function.Function;

public enum TipoDirecciones
{
	DERECHA(mapa_anchura -> 1, 0.06f, 0.20f, 0.15f),
	ABAJO_DERECHA(mapa_anchura -> mapa_anchura, 0.07f, 0.23f, 0.17f),
	ABAJO(mapa_anchura -> 2 * mapa_anchura - 1, 0.06f, 0.20f, 0.15f),
	ABAJO_IZQUIERDA(mapa_anchura -> mapa_anchura - 1, 0.06f, 0.20f, 0.15f),
	IZQUIERDA(mapa_anchura -> -1, 0.06f, 0.20f, 0.15f),
	ARRIBA_IZQUIERDA(mapa_anchura -> -mapa_anchura, 0.07f, 0.23f, 0.17f),
	ARRIBA(mapa_anchura -> -(2 * mapa_anchura - 1), 0.06f, 0.20f, 0.15f),
	ARRIBA_DERECHA(mapa_anchura -> -(mapa_anchura - 1), 0.06f, 0.20f, 0.15f);

	final private Function<Integer, Integer> siguiente_celda;
	final private float velocidad_caminando;
	final private float velocidad_montura;
	final private float velocidad_corriendo;

	private TipoDirecciones(Function<Integer, Integer> _siguiente_celda, final float _velocidad_caminando, final float _velocidad_montura, final float _velocidad_corriendo) 
	{
		siguiente_celda = _siguiente_celda;
		velocidad_caminando = _velocidad_caminando;
		velocidad_montura= _velocidad_montura;
		velocidad_corriendo = _velocidad_corriendo;
	}

	public char get_Direccion_Char()
	{
		return (char) (ordinal() + 'a');
	}

	public static TipoDirecciones get_Direccion_Desde_Char(char c)
	{
		return values()[c - 'a'];
	}

	public TipoDirecciones get_Opuesto_4_Direcciones()
	{
		return TipoDirecciones.values()[(ordinal() + 4) % 8];
	}

	public TipoDirecciones get_Ortogonal_2_Direcciones() 
	{
		return TipoDirecciones.values()[(ordinal() + 2) % 8];
	}

	/**
	 * Una dirección restringida puede ser usada en la lucha, o por sprites de monstruos.
	 * La dirección restringida no permite las diagonales
	 */
	public boolean get_Restringido()
	{
		return ordinal() % 2 == 1;
	}

	public int get_Siguiente_Celda(int mapa_anchura) 
	{
		return siguiente_celda.apply(mapa_anchura);
	}

	public float get_Velocidad_Caminando()
	{
		return velocidad_caminando;
	}

	public float get_Velocidad_Montura() 
	{
		return velocidad_montura;
	}

	public float get_Velocidad_Corriendo() 
	{
		return velocidad_corriendo;
	}

}
