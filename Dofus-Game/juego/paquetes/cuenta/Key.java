package juego.paquetes.cuenta;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("Ak")
public class Key implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete){}
}
