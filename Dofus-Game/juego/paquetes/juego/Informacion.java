package juego.paquetes.juego;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("GI")
public class Informacion implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete) 
	{
		socket.get_Iniciar_Buffering();
		
		socket.enviar_Paquete("GDK");
		socket.enviar_Paquete(socket.get_Personaje().get_Localizacion().get_Mapa().get_Paquete_Gm_Jugadores());
		socket.enviar_Paquete(socket.get_Personaje().get_Localizacion().get_Mapa().get_Paquete_Gm_Npcs());
		socket.get_Detener_Buffering();
		socket.get_Registrar_Ping();
	}
}
