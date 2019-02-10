package juego.paquetes.basicos;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;
import objetos.mapas.Mapas;

@Paquete("Ba")
public class ComandosRapidos implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		if (socket.get_Cuenta().get_Rango_cuenta() > 0) 
		{
			try 
			{
				final String[] separador = paquete.substring(3).split(",");
				final int X = Integer.parseInt(separador[0]);
				final int Y = Integer.parseInt(separador[1]);
				
				final Mapas mapa = Mapas.get_Mapa_Coordenadas_SubArea(X, Y, socket.get_Personaje().get_Localizacion().get_Mapa().get_Sub_area().get_Area().get_Super_area().get_Id());
		
				socket.get_Personaje().get_Teleport(mapa.get_Id(), mapa.get_Random_Celda().get_Id());
			
			}
			catch (final Exception e) 
			{
				socket.enviar_Paquete("Im116;Teleport~El mapa no existe");
			}
		}
	}
}
