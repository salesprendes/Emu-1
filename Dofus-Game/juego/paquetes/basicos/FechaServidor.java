package juego.paquetes.basicos;

import java.util.Calendar;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("BD")
public class FechaServidor implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1370);
		
		socket.enviar_Paquete(String.format("BD%04d|%02d|%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));
		socket.enviar_Paquete("BT" + (calendar.getTimeInMillis() + calendar.get(Calendar.ZONE_OFFSET)));
	}
}
