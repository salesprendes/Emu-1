package juego.paquetes.juego;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;

@Paquete("Ir")
public class Resolucion implements GestorPaquetes
{
	/** 
	 * https://github.com/Emudofus/Dofus/blob/1.29/dofus/aks/Infos.as#L43 */
	
	public void analizar(JuegoSocket socket, String paquete)
	{
		if(socket.get_Cuenta() == null && socket.get_Personaje() == null)
		{
			socket.cerrar_Conexion();
			socket.enviar_Paquete("AlEn");
		}
	}
}
