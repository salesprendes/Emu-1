package juego.paquetes.cuenta;

import juego.JuegoSocket;
import juego.enums.EstadosJuego;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;
import main.Configuracion;
import objetos.cuentas.Cuentas;

@Paquete("Af")
public class FilaDeEspera implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete) 
	{
		Cuentas cuenta = socket.get_Cuenta();
		
		if(cuenta != null && socket.get_Estado_Juego() == EstadosJuego.FILA_ESPERA)
		{
			if(Configuracion.ACTIVAR_FILA_DE_ESPERA)
			{
				if(cuenta.get_Fila_espera())
				{
					socket.get_Fila().actualizar_Posiciones();
				}
				else
				{
					cuenta.set_Fila_espera(true);
					socket.get_Fila().agregar_Cuenta(cuenta);
				}
			}
			else
			{
				socket.enviar_Paquete("BN");
			}
		}
		else
		{
			socket.enviar_Paquete("AlEn");
			socket.cerrar_Conexion();
		}
	}
}
