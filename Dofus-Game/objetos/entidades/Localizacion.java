package objetos.entidades;

import juego.enums.TipoDirecciones;
import objetos.mapas.Celdas;
import objetos.mapas.Mapas;

public class Localizacion
{
	private Mapas mapa;
	private Celdas celda;
	private TipoDirecciones orientacion;
	
	public Localizacion(final Mapas _mapa, final Celdas _celda, final TipoDirecciones _orientacion) 
	{
		mapa = _mapa;
		celda = _celda;
		orientacion = _orientacion;
	}
	
	public Localizacion(final TipoDirecciones _orientacion) 
	{
		orientacion = _orientacion;
	}

	public Mapas get_Mapa()
	{
		return mapa;
	}

	public void set_Mapa(final Mapas _mapa)
	{
		mapa = _mapa;
	}

	public Celdas get_Celda()
	{
		return celda;
	}

	public void set_Celda(final Celdas _celda)
	{
		celda = _celda;
	}

	public TipoDirecciones get_Orientacion()
	{
		return orientacion;
	}

	public void set_Orientacion(final TipoDirecciones _orientacion)
	{
		orientacion = _orientacion;
	}
	
	public boolean equals(Localizacion otro) 
	{
        return otro != null && otro.get_Celda() == celda && otro.get_Mapa() == mapa;
    }

	@Override
	public boolean equals(Object o) 
	{
		return this == o || (o instanceof Localizacion && equals((Localizacion) o));
	}
	
	@Override
    public int hashCode() 
	{
        int resultado = mapa.get_Id();
        resultado = 31 * resultado + celda.get_Id();
        return resultado;
    }
}
