package juego.paquetes.ping;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("pi")
public class Ping implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		if (paquete.equals("ping"))
			socket.enviar_Paquete("pong");
	}
}
