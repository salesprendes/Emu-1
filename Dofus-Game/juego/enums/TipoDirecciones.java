package juego.enums;

import java.util.function.Function;

public enum TipoDirecciones
{
	DERECHA(mapa_anchura -> 1),
	ABAJO_DERECHA(mapa_anchura -> mapa_anchura),
	ABAJO(mapa_anchura -> 2 * mapa_anchura - 1),
	ABAJO_IZQUIERDA(mapa_anchura -> mapa_anchura - 1),
	IZQUIERDA(mapa_anchura -> -1),
	ARRIBA_IZQUIERDA(mapa_anchura -> -mapa_anchura),
	ARRIBA(mapa_anchura -> -(2 * mapa_anchura - 1)),
	ARRIBA_DERECHA(mapa_anchura -> -(mapa_anchura - 1));

	final private Function<Integer, Integer> siguiente_celda;

	private TipoDirecciones(Function<Integer, Integer> _siguiente_celda) 
	{
		siguiente_celda = _siguiente_celda;
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
}
