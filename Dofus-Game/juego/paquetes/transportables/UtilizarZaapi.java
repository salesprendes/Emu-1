package juego.paquetes.transportables;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("Wu")
public class UtilizarZaapi implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		if(socket.get_Personaje().get_Alineamiento_Deshonor() >= 2) 
		{
			socket.enviar_Paquete("Im183");
			return;
		}
	}
}
