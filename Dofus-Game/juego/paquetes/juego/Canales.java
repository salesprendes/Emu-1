package juego.paquetes.juego;

import juego.JuegoSocket;
import juego.enums.TipoCanales;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;
import main.consola.Consola;

@Paquete("cC")
public class Canales implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		try 
		{
			final String[] canales = paquete.substring(3).split("");
			
			switch(paquete.charAt(2)) //el for sirve para el canal privado que contiene los caracteres "#$p"
			{
				case '+':
					for (final String canal : canales)
					{
						if(!canal.isEmpty())
							socket.get_Personaje().get_Agregar_Canal(TipoCanales.get_Canal().get(canal));
					}
				break;
				
				case '-':
					for (final String canal : canales)
					{
						if(!canal.isEmpty())
							socket.get_Personaje().get_Eliminar_Canal(TipoCanales.get_Canal().get(canal));
					}
				break;
				
				default:
					socket.cerrar_Conexion();
					Consola.println("Paquete desconocido: " + paquete + " cuenta desconectada");
				break;
			}
		}
		catch (final Exception e) {}
	}
}
