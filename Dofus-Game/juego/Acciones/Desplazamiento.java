package juego.acciones;

import juego.enums.TipoDirecciones;
import objetos.entidades.personajes.Derechos;
import objetos.entidades.personajes.Personajes;
import objetos.mapas.pathfinding.Camino;
import objetos.mapas.pathfinding.PathFinding;

public class Desplazamiento implements JuegoAcciones
{
	private final int id;
	private final Personajes personaje;
	private PathFinding pathfinding;

	public Desplazamiento(final int _id, final Personajes _personaje, final PathFinding _pathfinding) 
	{
		id = _id;
		personaje = _personaje;
		pathfinding = _pathfinding;
	}

	public synchronized boolean get_Esta_Iniciado()
	{
		if(personaje.get_Juego_Acciones().get_Estado() != JuegoAccionEstado.ESPERANDO || pathfinding == null)
		{
			personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GA;0");
            return false;
        }
		
		if (pathfinding.celda_objetivo().get_Id() == personaje.get_Celda_Id())
		{
			personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GA;0");
            return false;
        }
		
		//verifica si en el camino hay celdas caminables si no hay devuelve hasta X casilla caminable
		pathfinding = pathfinding.get_Mantener_Mientras_Cumpla_Condicion(celdas -> celdas.get_Celda().get_Es_Caminable());
		
		//si no puede moverse en todas las direcciones y hay direcciones en diagonal 
		if(!personaje.get_Derechos().get_Derechos(Derechos.PUEDE_MOVERSE_TODAS_DIRECCIONES) && pathfinding.stream().skip(1).map(Camino::get_Direccion).allMatch(TipoDirecciones::get_Restringido))
		{
			personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GA;0");
			return false;
		}
		
		personaje.get_Mapa().get_Personajes().stream().filter(personaje -> personaje != null && personaje.get_Esta_Conectado()).forEach(personaje -> personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GA" + id + ';' + get_Accion_id() + ';' + personaje.get_Id() + ';' + pathfinding.get_Codificar()));
		personaje.get_Juego_Acciones().set_Estado(JuegoAccionEstado.DESPLAZANDO);
		return true;
	}
	
	public synchronized void get_Cancelar(String args)
	{
		if (!args.isEmpty()) 
		{
			try 
			{
				short celda_id = Short.parseShort(args);
				
				pathfinding.stream().filter(camino -> camino.get_Celda().get_Id() == celda_id).forEach(camino -> 
				{
					personaje.set_Celda(camino.get_Celda());
					personaje.set_Orientacion(camino.get_Direccion());
				});
				
				personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("BN");
			}
			catch(NumberFormatException e) 
			{
				personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("BN");
				return;
			}
        }
	}
	
	public synchronized void get_Correcto(String args)
	{
		personaje.set_Celda(pathfinding.celda_objetivo());
		personaje.set_Orientacion(pathfinding.get_Anterior().get_Direccion());
		personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("BN");
		personaje.get_Juego_Acciones().set_Estado(JuegoAccionEstado.ESPERANDO);
	}
	
	public int get_Id()
	{
		return id;
	}
	
	public int get_Accion_id()
	{
		return 1;
	}
}
