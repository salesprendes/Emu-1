package login.paquetes;

import login.LoginSocket;

public interface GestorPaquetes 
{
	public void analizar(LoginSocket jugador, String paquete);
	public String toString();
}