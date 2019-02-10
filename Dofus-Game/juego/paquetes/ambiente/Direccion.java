package juego.paquetes.ambiente;

import juego.JuegoSocket;
import juego.enums.TipoDirecciones;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("eD")
public class Direccion implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		try	
		{
			byte direccion_numero = (byte) (paquete.substring(2).charAt(0) - '0');
			if (direccion_numero <= TipoDirecciones.values().length)
			{
				socket.get_Personaje().get_Localizacion().set_Orientacion(TipoDirecciones.values()[direccion_numero]);
				socket.get_Personaje().get_Localizacion().get_Mapa().get_Personajes().stream().filter(personaje -> personaje.get_Esta_Conectado()).forEach(personaje -> personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("eD" + socket.get_Personaje().get_Id() + '|' + direccion_numero));
			}
		}
		catch(NumberFormatException e) {}
	}
}
