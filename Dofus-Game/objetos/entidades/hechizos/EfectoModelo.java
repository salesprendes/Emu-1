package objetos.entidades.hechizos;

public class EfectoModelo
{
	final private int efecto, minimo, maximo, especial, duracion, probabilidad;
	final private String texto;

	public EfectoModelo(final int _efecto, final int _minimo, final int _maximo, final int _especial, final int _duracion, final int _probabilidad, final String _texto) 
	{
		efecto = _efecto;
		minimo = _minimo;
		maximo = _maximo;
		especial = _especial;
		duracion = _duracion;
		probabilidad = _probabilidad;
		texto = _texto;
	}

	public int get_Efecto()
	{
		return efecto;
	}

	public int get_Minimo()
	{
		return minimo;
	}

	public int get_Maximo()
	{
		return maximo;
	}

	public int get_Especial()
	{
		return especial;
	}

	public int get_Duracion()
	{
		return duracion;
	}

	public int get_Probabilidad()
	{
		return probabilidad;
	}

	public String get_Texto()
	{
		return texto;
	}
}
