package juego.paquetes.juego;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("GA")
public class NuevaAccion implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		try 
		{
			short accion = Short.parseShort(paquete.substring(2, 5));
			if(socket.get_Personaje() != null)
				socket.get_Personaje().get_Juego_Acciones().get_Crear_Accion(accion, paquete.substring(5));
		}
		catch(NumberFormatException e) 
		{
			socket.enviar_Paquete("BN");
			return;
		}
	}
}
