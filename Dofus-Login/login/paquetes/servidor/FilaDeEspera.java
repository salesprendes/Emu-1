package login.paquetes.servidor;

import login.LoginSocket;
import login.enums.EstadosLogin;
import login.paquetes.GestorPaquetes;
import login.paquetes.Paquete;
import login.paquetes.salida.ErroresLogin;
import objetos.Cuentas;

@Paquete("Af")
public class FilaDeEspera implements GestorPaquetes
{
	public void analizar(LoginSocket jugador, String paquete)
	{
		Cuentas cuenta = jugador.get_Cuenta();
		
		if(cuenta != null)
		{
			if(jugador.get_Estado_login() == EstadosLogin.FILA_ESPERA)
			{
				if(cuenta.get_Apodo().isEmpty() && !cuenta.esta_Creando_apodo() && !cuenta.get_Fila_espera())
				{
					jugador.enviar_Paquete(new ErroresLogin(ErroresLogin.CUENTA_SIN_APODO).toString());
					cuenta.set_Creando_apodo(true);
					jugador.set_Estado_login(EstadosLogin.CREACION_APODO);
				}
				else if(cuenta.get_Fila_espera())
				{
					jugador.get_Fila().actualizar_Posiciones();
				}
				else
				{
					cuenta.set_Fila_espera(true);
					jugador.get_Fila().agregar_Cuenta(cuenta);
				}
			}
			else
			{
				jugador.enviar_Paquete("Af0|0|0|" + (cuenta.es_Cuenta_Abonada() ? 1 : 0) +"|-1");
				System.out.println("la cuenta " + cuenta.get_Usuario() + " ha enviado af sin tener que haber entrado en la fila de espera");
			}
		}
		else
		{
			jugador.enviar_Paquete(new ErroresLogin(ErroresLogin.CUENTA_NO_VALIDA).toString());
			jugador.cerrar_Conexion();
		}
	}
}