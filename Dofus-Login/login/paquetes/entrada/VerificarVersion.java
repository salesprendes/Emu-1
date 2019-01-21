package login.paquetes.entrada;

import login.LoginSocket;
import login.enums.EstadosLogin;
import login.paquetes.GestorPaquetes;
import login.paquetes.Paquete;
import login.paquetes.salida.ErroresLogin;

@Paquete("1.29.1")
public class VerificarVersion implements GestorPaquetes
{
	public void analizar(LoginSocket jugador, String paquete)
	{
		System.out.println("asasaas");
		if (paquete.equalsIgnoreCase("1.29.1"))
		{
			jugador.set_Estado_login(EstadosLogin.NOMBRE_CUENTA);
		}
		else
		{
			jugador.enviar_Paquete(ErroresLogin.VERSION_INCORRECTA.toString());
			jugador.cerrar_Conexion();
		}
	}
}
