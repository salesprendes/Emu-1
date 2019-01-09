package login.paquetes.entrada;

import login.LoginSocket;
import login.enums.EstadosLogin;
import login.paquetes.salida.ErroresLogin;
import main.Main;

public class VerificarCreacionApodo
{
	public void analizar(LoginSocket jugador, String paquete)
	{
		if(jugador.get_Cuenta().get_Apodo().isEmpty() && jugador.get_Cuenta().esta_Creando_apodo())
		{
			if(!paquete.toLowerCase().equals(jugador.get_Cuenta().get_Usuario().toLowerCase()))
			{
				if(paquete.matches("[A-Za-z0-9.@.-]+") && !Main.get_Database().get_Cuentas().get_Existe_Campo_Cuenta("apodo", "apodo", paquete))
				{
					jugador.get_Cuenta().set_Apodo(paquete);
					Main.get_Database().get_Cuentas().actualizar_Apodo(jugador.get_Cuenta());
					jugador.get_Cuenta().set_Creando_apodo(false);
					jugador.get_Cuenta().set_Fila_espera(true);
					jugador.get_Fila().agregar_Cuenta(jugador.get_Cuenta());
					jugador.set_Estado_login(EstadosLogin.FILA_ESPERA);
				}
				else
				{
					jugador.enviar_Paquete(new ErroresLogin(ErroresLogin.CUENTA_APODO_ERROR).toString());
					return;
				}
			}
			else
			{
				jugador.enviar_Paquete(new ErroresLogin(ErroresLogin.CUENTA_SIN_APODO).toString());
				return;
			}
		}
		else
		{
			jugador.enviar_Paquete(new ErroresLogin(ErroresLogin.CONEXION_NO_TERMINADA).toString());
			jugador.cerrar_Conexion();
		}
	}
}
