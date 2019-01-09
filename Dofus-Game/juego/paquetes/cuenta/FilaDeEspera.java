package juego.paquetes.cuenta;

import juego.JuegoSocket;
import juego.enums.EstadosJuego;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;
import main.Configuracion;

@Paquete("Af")
public class FilaDeEspera implements GestorPaquetes
{
	public void analizar(JuegoSocket jugador, String paquete) 
	{
		if(jugador.get_Cuenta() != null && jugador.get_Estado_Juego() == EstadosJuego.FILA_ESPERA)
		{
			if(Configuracion.ACTIVAR_FILA_DE_ESPERA)
			{
				if(jugador.get_Cuenta().get_Fila_espera())
				{
					jugador.get_Fila().actualizar_Posiciones();
				}
				else
				{
					jugador.get_Cuenta().set_Fila_espera(true);
					jugador.get_Fila().agregar_Cuenta(jugador.get_Cuenta());
				}
			}
			else
			{
				jugador.enviar_Paquete("BN");
			}
		}
		else
		{
			jugador.enviar_Paquete("AlEn");
			jugador.cerrar_Conexion();
		}
	}
}
