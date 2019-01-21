package juego.Acciones;

import objetos.entidades.personajes.Personajes;

public class Desplazamiento implements JuegoAcciones
{
	private final int id;
	private final Personajes personaje;
	private String args;
	private String path_final;
	private String path_inicial;
	private int step;

	public Desplazamiento(final int _id, final Personajes _personaje, final String _args) 
	{
		id = _id;
		personaje = _personaje;
		args = _args;
	}

	public boolean get_Esta_Iniciado()
	{
		path_final = path_inicial = args;
		if(personaje.get_Juego_Acciones().get_Estado() != JuegoAccionEstado.ESPERANDO)
		{
			personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GA;0");
            return false;
        }
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
