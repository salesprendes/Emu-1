package juego.acciones;

import juego.enums.TipoDirecciones;
import juego.enums.TipoEstadoAcciones;
import objetos.entidades.personajes.Derechos;
import objetos.entidades.personajes.Personajes;
import objetos.mapas.Celdas;
import objetos.mapas.pathfinding.Camino;
import objetos.mapas.pathfinding.PathFinding;

public class Desplazamiento implements JuegoAcciones
{
	private final int id;
	private final long tiempo_inicio;
	private final Personajes personaje;
	private PathFinding pathfinding;

	public Desplazamiento(final int _id, final Personajes _personaje, final PathFinding _pathfinding) 
	{
		id = _id;
		personaje = _personaje;
		pathfinding = _pathfinding;
		tiempo_inicio = System.currentTimeMillis();
	}

	public synchronized boolean get_Esta_Iniciado()
	{
		if(pathfinding == null || personaje.get_Juego_Acciones().get_Estado() != TipoEstadoAcciones.ESPERANDO)
		{
			personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GA;0");
            return false;
        }
		
		if (pathfinding.celda_objetivo().equals(personaje.get_Localizacion().get_Celda()))
		{
			personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GA;0");
            return false;
        }
		
		//verifica si en el camino hay celdas caminables si no hay devuelve hasta X casilla caminable
		pathfinding = pathfinding.get_Mantener_Mientras_Cumpla_Condicion(celdas -> celdas.get_Celda().get_Es_Caminable());
		
		//si no puede moverse en todas las direcciones y hay direcciones en diagonal 
		if(!personaje.get_Derechos().get_Derechos(Derechos.PUEDE_MOVERSE_TODAS_DIRECCIONES) && pathfinding.stream().map(Camino::get_Direccion).allMatch(TipoDirecciones::get_Restringido))
		{
			personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GA;0");
			return false;
		}
		
		personaje.get_Localizacion().get_Mapa().get_Personajes().stream().filter(personaje -> personaje.get_Esta_Conectado()).forEach(pj -> pj.get_Cuenta().get_Juego_socket().enviar_Paquete("GA" + id + ';' + get_Accion_id() + ';' + personaje.get_Id() + ';' + pathfinding.get_Codificar(false)));
		personaje.get_Juego_Acciones().set_Estado(TipoEstadoAcciones.DESPLAZANDO);
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
					personaje.get_Localizacion().set_Orientacion(camino.get_Direccion());
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
		long tiempo_local = (System.currentTimeMillis() - tiempo_inicio) + personaje.get_Cuenta().get_Juego_socket().get_ping();

		if(pathfinding.get_Tiempo_Recorrido_Sin_Pelea() <= tiempo_local)
		{
			Celdas celda_destino = pathfinding.celda_objetivo();
			personaje.set_Celda(celda_destino != null ? celda_destino : personaje.get_Localizacion().get_Celda());
			personaje.get_Localizacion().set_Orientacion(pathfinding.get_Anterior().get_Direccion());
			personaje.get_Localizacion().get_Mapa().get_Acciones_Final_Movimiento(personaje);
		}
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
