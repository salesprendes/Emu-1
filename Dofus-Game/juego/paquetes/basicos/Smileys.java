package juego.paquetes.basicos;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("BS")
public class Smileys implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		try 
		{
			byte smiley_id = Byte.parseByte(paquete.substring(2));
			socket.get_Personaje().get_Localizacion().get_Mapa().get_Personajes().stream().filter(personaje -> personaje.get_Esta_Conectado()).forEach(personaje -> personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("cS" + socket.get_Personaje().get_Id() + '|' + smiley_id));
		} 
		catch (NumberFormatException ignored) {}
	}
}
