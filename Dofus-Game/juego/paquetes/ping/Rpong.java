package juego.paquetes.ping;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("rp")
public class Rpong implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		try 
		{
			int i = Integer.parseInt(paquete.substring(5));
			if (i == socket.get_UltMillis())
				socket.set_ping(System.currentTimeMillis() - socket.get_LastMillis());
		}
		catch (Exception e) {}
	}
}
