package login.paquetes;

import login.LoginRespuesta;

public interface GestorPaquetes 
{
	public void analizar(LoginRespuesta jugador, String paquete);
	public String toString();
}