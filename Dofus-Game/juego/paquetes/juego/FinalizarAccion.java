package juego.paquetes.juego;

import java.util.regex.Pattern;

import juego.JuegoSocket;
import juego.acciones.JuegoAccion;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("GK")
public class FinalizarAccion implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		try	
		{
			boolean tiene_error = paquete.charAt(2) != 'K';
			String[] infos = paquete.substring(3).split(Pattern.quote("|"));

			JuegoAccion accion = socket.get_Personaje().get_Juego_Acciones();
			accion.get_Finalizar_Accion(accion.getAcciones().peek(), tiene_error, infos.length > 1 ? infos[1]: "");
		}
		catch(Exception e) 
		{
			socket.get_Personaje().get_Cuenta().get_Juego_socket().enviar_Paquete("BN");
		}
	}
}
