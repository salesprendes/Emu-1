package juego.paquetes.cuenta;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("Ai")
public class Identidad implements GestorPaquetes
{
	public void analizar(JuegoSocket jugador, String paquete) 
	{
		jugador.get_Cuenta().set_Key(paquete.substring(2));
	}
}
