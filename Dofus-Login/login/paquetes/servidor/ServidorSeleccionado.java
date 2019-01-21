package login.paquetes.servidor;

import login.LoginSocket;
import login.enums.ErroresServidor;
import login.enums.EstadosLogin;
import login.paquetes.GestorPaquetes;
import login.paquetes.Paquete;
import objetos.Cuentas;
import objetos.Servidores;
import objetos.Servidores.Estados_Servidor;

@Paquete("AX")
public class ServidorSeleccionado implements GestorPaquetes
{
	public void analizar(LoginSocket socket, String paquete) 
	{
		if(socket.get_Estado_login() == EstadosLogin.LISTA_SERVIDORES)
		{
			Servidores servidor = Servidores.get(Integer.parseInt(paquete.substring(2)));
			Cuentas cuenta = socket.get_Cuenta();

			if(servidor.get_Comunicador_game() != null && cuenta != null)
			{
				if(servidor.get_Estado() == Estados_Servidor.CONECTADO)
				{
					if(servidor.es_Servidor_Vip() && !(cuenta.get_Tiempo_Abono() > 0))
					{
						socket.enviar_Paquete(Servidores.get_Servidores_Disponibles());
						return;
					}
					if(cuenta.get_Comunidad().get_Id() != servidor.get_Comunidad().get_Id())
					{
						socket.enviar_Paquete("M019");
						socket.cerrar_Conexion();
						return;
					}
					servidor.get_Comunicador_game().get_Nueva_Conexion_Cuenta(cuenta.get_Id(), socket.get_Socket().getInetAddress().getHostAddress());
					socket.enviar_Paquete("AYK" + servidor.get_Ip() + ':' + servidor.get_Puerto() + ';' + cuenta.get_Id());
				}
				else
				{
					socket.enviar_Paquete(ErroresServidor.SERVIDOR_NO_DISPONIBLE.toString());
					socket.cerrar_Conexion();
				}
			}
			else
			{
				socket.enviar_Paquete(ErroresServidor.SERVIDOR_NO_EXISTENTE.toString());
			}
		}
		else
		{
			socket.cerrar_Conexion();
		}
	}
}
