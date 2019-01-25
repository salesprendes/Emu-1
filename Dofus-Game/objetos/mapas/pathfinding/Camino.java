package objetos.mapas.pathfinding;

import juego.enums.TipoDirecciones;
import objetos.mapas.Celdas;

final public class Camino
{
	final private Celdas celda;
	final private TipoDirecciones direccion;

	public Camino(Celdas _celda, TipoDirecciones _direccion)
	{
		celda = _celda;
		direccion = _direccion;
	}

	public Celdas get_Celda() 
	{
		return celda;
	}

	public TipoDirecciones get_Direccion() 
	{
		return direccion;
	}
	
	public String toString()
	{
		return "celda id: " + celda.get_Id() + " direccion: " + direccion;
	}
}
