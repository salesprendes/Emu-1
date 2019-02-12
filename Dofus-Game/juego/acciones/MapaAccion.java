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
			
			if(!personaje.get_Localizacion().get_Mapa().get_Celda(celda).get_Verificar_Habilidad(accion))
				return false;
			
			return personaje.get_Localizacion().get_Mapa().get_Celda(celda).get_Iniciar_Accion(personaje, accion, id, celda);
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
		personaje.get_Localizacion().get_Mapa().get_Celda(celda).get_Finalizar_Accion(personaje, accion);
	}

	public int get_Id()
	{
		return id;
	}

	public int get_Accion_id()
	{
		return 500;
	}
}
