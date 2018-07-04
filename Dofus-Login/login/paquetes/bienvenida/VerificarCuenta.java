package login.paquetes.bienvenida;

import login.LoginRespuesta;
import login.enums.ErroresLogin;
import login.enums.EstadosLogin;
import main.Main;

public class VerificarCuenta
{
	public void analizar(LoginRespuesta jugador, String paquete)
	{
		if(Main.get_Database().get_Cuentas().get_Existe_Campo_Cuenta("usuario", "usuario", paquete.toLowerCase()))
		{
			if(!Main.get_Database().get_Cuentas().get_Comprobar_Campo_Cuenta_Booleano("baneado", "usuario", paquete.toLowerCase()))
			{
				jugador.set_Cuenta_paquete(paquete.toLowerCase());
				jugador.set_Estado_login(EstadosLogin.PASSWORD_CUENTA);
			}
			else
			{
				jugador.enviar_Paquete(ErroresLogin.CUENTA_BANEADA.toString());
				jugador.cerrar_Conexion();
				return;
			}
		}
		else
		{
			jugador.enviar_Paquete(ErroresLogin.CUENTA_NO_VALIDA.toString());
			jugador.cerrar_Conexion();
			return;
		}
	}
}
