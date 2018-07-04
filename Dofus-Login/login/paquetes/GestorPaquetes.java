package login.paquetes;

import login.LoginRespuesta;

public interface GestorPaquetes 
{
	public void parse(LoginRespuesta jugador, String paquete);
}