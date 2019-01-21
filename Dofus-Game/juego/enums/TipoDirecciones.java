package juego.enums;

import java.util.function.Function;

public enum TipoDirecciones
{
	ESTE(mapa_anchura -> 1),
	SUR_ESTE(mapa_anchura -> mapa_anchura),
	SUR(mapa_anchura -> 2 * mapa_anchura - 1),
	SUR_OESTE(mapa_anchura -> mapa_anchura - 1),
	OESTE(mapa_anchura -> -1),
	NOROESTE(mapa_anchura -> -mapa_anchura),
	NORTE(mapa_anchura -> -(2 * mapa_anchura - 1)),
	NORESTE(mapa_anchura -> -(mapa_anchura - 1));

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

	public TipoDirecciones get_Opuesto()
	{
		return TipoDirecciones.values()[(ordinal() + 4) % 8];
	}

	public TipoDirecciones get_Ortogonal() 
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
