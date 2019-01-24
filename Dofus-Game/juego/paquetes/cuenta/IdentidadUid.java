package juego.paquetes.cuenta;

import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;
import main.Main;
import objetos.cuentas.Cuentas;

@Paquete("Ai")
public class IdentidadUid implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete) 
	{
		Cuentas cuenta = socket.get_Cuenta();
		String uid_actual = paquete.substring(2);
		
		if(cuenta != null && !uid_actual.isEmpty())
		{
			if(cuenta.get_Uid().isEmpty() || cuenta.get_Uid() != uid_actual)
			{
				socket.get_Cuenta().set_Uid(uid_actual);
				Main.get_Database().get_Cuentas().get_Actualizar_Campo("uid", uid_actual, cuenta.get_Id());
			}
		}
		else
		{
			socket.enviar_Paquete("AlEn");
			socket.cerrar_Conexion();
		}
	}
}
