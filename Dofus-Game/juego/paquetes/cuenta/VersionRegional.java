package juego.paquetes.cuenta;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("AV")
public class VersionRegional implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete) 
	{
		if (socket.get_Personaje() == null)
			socket.enviar_Paquete("AV0");
	}
}
