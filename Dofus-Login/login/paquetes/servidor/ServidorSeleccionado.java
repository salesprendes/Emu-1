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
	public void analizar(LoginSocket jugador, String paquete) 
	{
		if(jugador.get_Estado_login() == EstadosLogin.LISTA_SERVIDORES)
		{
			Servidores servidor = Servidores.get(Integer.parseInt(paquete.substring(2)));
			Cuentas cuenta = jugador.get_Cuenta();
			
			if(servidor.get_Comunicador_game() != null && cuenta != null)
			{
				if(servidor.get_Estado() != Estados_Servidor.CONECTADO)
				{
					jugador.enviar_Paquete(ErroresServidor.SERVIDOR_NO_DISPONIBLE.toString());
					return;
				}
				if(servidor.es_Servidor_Vip() && !cuenta.es_Cuenta_Abonada())
				{
					jugador.enviar_Paquete(Servidores.get_Obtener_Servidores_Disponibles());
					return;
				}
				servidor.get_Comunicador_game().get_Nueva_Conexion_Cuenta(cuenta.get_Id());
				jugador.enviar_Paquete("AYK" + servidor.get_Ip() + ':' + servidor.get_Puerto() + ';' + cuenta.get_Id());
			}
			else
			{
				jugador.enviar_Paquete(ErroresServidor.SERVIDOR_NO_EXISTENTE.toString());
				return;
			}
		}
		else
		{
			jugador.cerrar_Conexion();
			return;
		}
	}
}
