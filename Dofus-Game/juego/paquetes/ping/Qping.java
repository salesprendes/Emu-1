package juego.paquetes.ping;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("qp")
public class Qping implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		if (paquete.equals("qping"))
			socket.enviar_Paquete("BN");
	}
}
