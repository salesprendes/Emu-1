package juego.paquetes.ambiente;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

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
		
		if (emote < 0 || !socket.get_Personaje().get_Tiene_Emote(emote)) 
		{
			socket.enviar_Paquete("eUE");
			return;
		}
		
		switch (emote) 
		{
			case 1://sentarse
			case 19://acostarse
			case 20://sentarse en el taburete
				//_player.setSitted(!_player.isSitted());
			break;
			
			case 21: //Campeon
				socket.get_Personaje().get_Mapa().get_Personajes().stream().filter(personaje -> personaje.get_Id() != socket.get_Personaje().get_Id()).forEach(personaje -> 
				{
					personaje.set_Emote_Activo((byte) 3);
					personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("eUK" + personaje.get_Id() + '|' + socket.get_Personaje().get_Emote_Activo());
				});
			break;
		}
		
		if(socket.get_Personaje().get_Emote_Activo() == emote)
			socket.get_Personaje().set_Emote_Activo((byte) 0);
		else 
			socket.get_Personaje().set_Emote_Activo(emote);
		
		socket.get_Personaje().get_Mapa().get_Personajes().forEach(personaje -> personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("eUK" + socket.get_Personaje().get_Id() + '|' + socket.get_Personaje().get_Emote_Activo()));
	}
}
