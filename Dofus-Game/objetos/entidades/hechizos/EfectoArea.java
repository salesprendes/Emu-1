package objetos.entidades.hechizos;

import juego.enums.TipoEfectoArea;

public class EfectoArea
{
	final private TipoEfectoArea tipo;
	final private int tamano;

	public EfectoArea(final TipoEfectoArea _tipo, final int _tamano)
	{
		tipo = _tipo;
		tamano = _tamano;
	}

	public TipoEfectoArea get_Tipo() 
	{
		return tipo;
	}

	public int get_Tamano() 
	{
		return tamano;
	}

	public String toString()
	{
		return tipo.name() + "(" + tamano + ")";
	}

	public boolean equals(Object o) 
	{
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		EfectoArea area = (EfectoArea) o;

		return tamano == area.get_Tamano() && tipo == area.get_Tipo();
	}
}
