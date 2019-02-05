package juego.paquetes.ambiente;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;
import objetos.entidades.personajes.Personajes;

@Paquete("eU")
public class Emoticonos implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete)
	{
		byte emote = -1;
		try
		{
			emote = Byte.parseByte(paquete.substring(2));
		}
		catch(final Exception e)
		{
			socket.enviar_Paquete("BN");
			return;
		};
		Personajes personaje_cuenta = socket.get_Personaje();
		
		if (emote < 0 || !personaje_cuenta.get_Tiene_Emote(emote)) 
		{
			socket.enviar_Paquete("eUE");
			return;
		}
		
		switch (emote) 
		{
			case 1://sentarse
			case 19://acostarse
			case 20://sentarse en el taburete
				if (personaje_cuenta.get_Esta_sentado())
					emote = 0;
				personaje_cuenta.setSentado(!personaje_cuenta.get_Esta_sentado());
			break;
			
			case 21: //Campeon
				if(personaje_cuenta.get_Emote_Activo() == 0)
				{
					personaje_cuenta.get_Mapa().get_Personajes().stream().filter(filtro -> filtro.get_Id() != personaje_cuenta.get_Id()).forEach(personaje -> 
					{
						personaje.set_Emote_Activo((byte) 3);
						personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("eUK" + personaje.get_Id() + '|' + personaje.get_Emote_Activo());
					});
				}
			break;
		}
		
		if(personaje_cuenta.get_Emote_Activo() == emote)
			personaje_cuenta.set_Emote_Activo((byte) 0);
		else 
			personaje_cuenta.set_Emote_Activo(emote);
		
		personaje_cuenta.get_Mapa().get_Personajes().forEach(personaje -> personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("eUK" + personaje_cuenta.get_Id() + '|' + personaje_cuenta.get_Emote_Activo()));
	}
}
