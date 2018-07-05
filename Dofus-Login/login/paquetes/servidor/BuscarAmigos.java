package login.paquetes.servidor;

import login.LoginRespuesta;
import login.enums.EstadosLogin;
import login.paquetes.GestorPaquetes;
import login.paquetes.Paquete;
import main.Main;

@Paquete("AF")
public class BuscarAmigos implements GestorPaquetes
{
	public void analizar(LoginRespuesta jugador, String paquete) 
	{
		if (jugador.get_Estado_login() == EstadosLogin.LISTA_SERVIDORES)
		{
			jugador.enviar_Paquete("AF" + Main.get_Database().get_Cuentas().get_Paquete_Buscar_Servidores(paquete.substring(2)));
		}
		else
		{
			jugador.cerrar_Conexion();
			return;
		}
	}
	
	public String toString()
	{
		return "[BuscarAmigos]";
	}
}
