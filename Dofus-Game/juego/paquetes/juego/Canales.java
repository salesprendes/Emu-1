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
			TipoCanales tipo_canal;
			switch(paquete.charAt(2)) 
			{
				case '+'://el for sirve para el canal privado que contiene los caracteres "#$p"
					for (final String canal : canales)
					{
						tipo_canal = TipoCanales.get_Canal().get(canal);
						if(!canal.isEmpty() && TipoCanales.get_Canal().containsValue(tipo_canal))
						{
							if(tipo_canal != TipoCanales.ADMINISTRADOR || tipo_canal != TipoCanales.MEETIC)
								socket.get_Personaje().get_Agregar_Canal(tipo_canal);
						}
						
					}
				break;
				
				case '-'://el for sirve para el canal privado que contiene los caracteres "#$p"
					for (final String canal : canales)
					{
						tipo_canal = TipoCanales.get_Canal().get(canal);
						if(!canal.isEmpty() && TipoCanales.get_Canal().containsValue(tipo_canal))
						{
							if(tipo_canal != TipoCanales.ADMINISTRADOR || tipo_canal != TipoCanales.MEETIC)
								socket.get_Personaje().get_Eliminar_Canal(tipo_canal);
						}
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
