package juego.paquetes.cuenta;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("Ag")
public class IdiomaRegalos implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete) 
	{
		socket.get_Cuenta().set_Idioma_cliente(paquete.substring(2));
	}
}
