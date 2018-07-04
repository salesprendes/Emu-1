package login.paquetes.entrada;

import login.LoginRespuesta;
import login.enums.ErroresLogin;
import login.enums.EstadosLogin;
import login.paquetes.GestorPaquetes;
import login.paquetes.Paquete;

@Paquete("1.29.1")
public class VerificarVersion implements GestorPaquetes
{
	public void analizar(LoginRespuesta jugador, String paquete)
	{
		if (paquete.equalsIgnoreCase("1.29.1"))
		{
			jugador.set_Estado_login(EstadosLogin.NOMBRE_CUENTA);
		}
		else
		{
			jugador.enviar_Paquete(ErroresLogin.VERSION_INCORRECTA.toString());
			jugador.cerrar_Conexion();
			return;
		}
	}
}
