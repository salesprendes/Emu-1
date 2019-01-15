package juego.paquetes.juego;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("GC")
public class CrearJuego implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete) 
	{
		if(socket.get_Cuenta() != null && socket.get_Personaje() != null)
			socket.get_Personaje().get_Crear_Juego();
	}
}
