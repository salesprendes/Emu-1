package login.paquetes.servidor;

import login.LoginSocket;
import login.paquetes.GestorPaquetes;
import login.paquetes.Paquete;
import login.paquetes.salida.ListaServidores;
import main.Main;

@Paquete("Ax")
final public class PersonajesCuenta implements GestorPaquetes
{
	public void analizar(LoginSocket jugador, String paquete)
	{
		jugador.enviar_Paquete(new ListaServidores(jugador.get_Cuenta().get_Tiempo_Abono(), Main.get_Database().get_Cuentas().get_Contar_Personajes_Servidor(jugador.get_Cuenta())).toString());
	}
}
