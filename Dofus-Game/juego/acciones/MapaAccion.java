package juego.acciones;

import objetos.entidades.personajes.Personajes;

public class MapaAccion implements JuegoAcciones
{
	private final Personajes personaje;
	private final String args;
	private final short id;
	private short celda, accion;

	public MapaAccion(short _id, final Personajes _personaje, final String _args) 
	{
		id = _id;
		personaje = _personaje;
		args = _args;
	}

	public boolean get_Esta_Iniciado()
	{
		try 
		{
			celda = Short.parseShort(args.split(";")[0]);
			accion = Short.parseShort(args.split(";")[1]);
			return true;
		} 
		catch(Exception e) 
		{
			return false;
		}
	}

	public void get_Cancelar(String args)
	{

	}

	public void get_Correcto(String args)
	{

	}

	public int get_Id()
	{
		return 0;
	}

	public int get_Accion_id()
	{
		return 0;
	}
}
