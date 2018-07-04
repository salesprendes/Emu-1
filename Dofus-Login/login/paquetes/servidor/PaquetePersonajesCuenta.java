package login.paquetes.servidor;

import login.LoginRespuesta;
import login.paquetes.GestorPaquetes;
import login.paquetes.Paquete;
import main.Main;

@Paquete("Ax")
public class PaquetePersonajesCuenta implements GestorPaquetes
{
	public void analizar(LoginRespuesta jugador, String paquete)
	{
		jugador.enviar_Paquete("AxK" + jugador.get_Cuenta().get_Fecha_abono() + Main.get_Database().get_Cuentas().get_Contar_Personajes_Servidor(jugador.get_Cuenta()));
	}
}
