package juego.Acciones;

import java.util.Arrays;
import java.util.List;

import main.util.Crypt;
import objetos.entidades.personajes.Personajes;
import objetos.mapas.pathfinding.Camino;
import objetos.mapas.pathfinding.Descifrador;
import objetos.mapas.pathfinding.PathFinding;

public class Desplazamiento implements JuegoAcciones
{
	private final int id;
	private final Personajes personaje;
	private String args;
	private int step;

	public Desplazamiento(final int _id, final Personajes _personaje, final String _args) 
	{
		id = _id;
		personaje = _personaje;
		args = _args;
	}

	public boolean get_Esta_Iniciado()
	{
		if(personaje.get_Juego_Acciones().get_Estado() != JuegoAccionEstado.ESPERANDO)
		{
			personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GA;0");
            return false;
        }
		final short celda_destino = Crypt.get_Hash_A_Celda_Id(args.substring(args.length() - 2));
		List<Camino> camino = Arrays.asList(new Camino(personaje.get_Celda(), personaje.get_Celda().get_Direccion(personaje.get_Mapa().get_Celda(celda_destino))), new Camino(personaje.get_Mapa().get_Celda(celda_destino), personaje.get_Mapa().get_Celda(celda_destino).get_Direccion(personaje.get_Mapa().get_Celda(celda_destino))));
		PathFinding pathfinding = new PathFinding(new Descifrador(personaje.get_Mapa()), camino);
		
		
		personaje.get_Mapa().get_Personajes().stream().filter(personaje -> personaje != null && personaje.get_Esta_Conectado()).forEach(personaje -> personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GA" + id + ';' + personaje.get_Id() + ";a" + pathfinding.get_Codificar()));
		personaje.get_Juego_Acciones().set_Estado(JuegoAccionEstado.DESPLAZANDO);
		return false;
	}
	
	public void get_Cancelar()
	{
		// TODO Auto-generated method stub

	}
	
	public void get_Fallo(String args)
	{
		int nueva_celda_id = Integer.parseInt(args);
		
		personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("BN");
	}
	
	public void get_Correcto(String args)
	{
		// TODO Auto-generated method stub

	}
	
	public int get_Id()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int get_Accion_id()
	{
		// TODO Auto-generated method stub
		return 0;
	}
}
