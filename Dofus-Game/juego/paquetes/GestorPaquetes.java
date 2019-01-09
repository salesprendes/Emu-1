package juego.paquetes;

import juego.JuegoSocket;

public interface GestorPaquetes 
{
	public void analizar(JuegoSocket socket, String paquete);
	public String toString();
}