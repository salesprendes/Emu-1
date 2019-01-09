package juego.paquetes.cuenta;

import juego.JuegoServer;
import juego.JuegoSocket;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;
import main.Main;
import objetos.cuentas.Cuentas;

@Paquete("AT")
final public class Tiquet implements GestorPaquetes
{
	public void analizar(final JuegoSocket socket, final String paquete) 
	{
		socket.set_Cuenta(JuegoServer.get_Cuenta_Esperando(Integer.parseInt(paquete.substring(2))));
		Cuentas cuenta = socket.get_Cuenta();
		
		if(cuenta != null)
		{
			JuegoServer.get_Eliminar_Cuenta_Esperando(cuenta);
			cuenta.set_Juego_socket(socket);
			cuenta.set_Ip(socket.get_Socket().getInetAddress().getHostAddress());
			Main.get_Database().get_Cuentas().get_Actualizar_Campo("ip", cuenta.get_Ip(), cuenta.get_Id());
			socket.enviar_Paquete("ATK0");
		}
		else
		{
			socket.enviar_Paquete("ATE");
			socket.cerrar_Conexion();
			return;
		}
	}
}
