package login.paquetes.servidor;

import login.LoginRespuesta;
import login.enums.ErroresLogin;
import login.enums.EstadosLogin;
import login.paquetes.GestorPaquetes;
import login.paquetes.Paquete;

@Paquete("Af")
public class PaqueteFilaDeEspera implements GestorPaquetes
{
	public void analizar(LoginRespuesta jugador, String paquete)
	{
		if(jugador.get_Cuenta().get_Apodo().isEmpty() && !jugador.get_Cuenta().esta_Creando_apodo() && !jugador.get_Cuenta().get_Fila_espera())
		{
			jugador.enviar_Paquete(ErroresLogin.CUENTA_SIN_APODO.toString());
			jugador.get_Cuenta().set_Creando_apodo(true);
			jugador.set_Estado_login(EstadosLogin.CREACION_APODO);
		}
		else if(!jugador.get_Cuenta().get_Fila_espera())
		{
			jugador.get_Cuenta().set_Fila_espera(true);
			jugador.get_Fila().agregar_Cuenta(jugador.get_Cuenta());
		}
	}
}