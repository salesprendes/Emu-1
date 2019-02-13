package juego.acciones;

import juego.enums.TipoEstadoAcciones;
import objetos.entidades.personajes.Personajes;
import objetos.mapas.Mapas;

public class MapaAccion implements JuegoAcciones
{
	private final Personajes personaje;
	private final String args;
	private short celda, accion;

	public MapaAccion(final Personajes _personaje, final String _args) 
	{
		personaje = _personaje;
		args = _args;
	}

	public synchronized boolean get_Puede_Hacer_Accion()
	{
		try 
		{
			celda = Short.parseShort(args.split(";")[0]);
			accion = Short.parseShort(args.split(";")[1]);
			Mapas mapa = personaje.get_Localizacion().get_Mapa();
			
			if(!mapa.get_Celda(celda).get_Verificar_Habilidad(accion))
				return false;
			
			if(mapa.get_Celda(celda).get_Iniciar_Accion(personaje, accion, celda))
			{
				personaje.get_Juego_Acciones().set_Estado(TipoEstadoAcciones.INTERACCION);
				return true;
			}
			return false;
		} 
		catch(Exception e) 
		{
			return false;
		}
	}

	public synchronized void get_Accion_Fallida(String args)
	{
		personaje.get_Juego_Acciones().set_Estado(TipoEstadoAcciones.ESPERANDO);
	}

	public synchronized void get_Accion_Correcta(String args)
	{
		personaje.get_Localizacion().get_Mapa().get_Celda(celda).get_Finalizar_Accion(personaje, accion);
		personaje.get_Juego_Acciones().set_Estado(TipoEstadoAcciones.ESPERANDO);
	}
	
	public short get_Tipo_Accion()
	{
		return 500;
	}
}
