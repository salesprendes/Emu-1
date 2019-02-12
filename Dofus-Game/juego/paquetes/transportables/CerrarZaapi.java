package juego.paquetes.transportables;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("Wv")
public class CerrarZaapi implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		socket.enviar_Paquete("Wv");
	}
}
