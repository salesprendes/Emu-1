package juego.paquetes.cuenta;

import juego.JuegoSocket;
import juego.enums.EstadosJuego;
import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;
import main.Configuracion;
import main.consola.Consola;
import objetos.cuentas.Cuentas;

@Paquete("AS")
public class SeleccionPersonaje implements GestorPaquetes
{
	public void analizar(JuegoSocket socket, String paquete) 
	{
		final int id_personaje = Integer.parseInt(paquete.substring(2));
		Cuentas cuenta = socket.get_Cuenta();

		if(cuenta.get_Personajes(id_personaje) != null)
		{
			socket.set_Personaje(cuenta.get_Personajes(id_personaje));
			if(!Configuracion.ACTIVAR_FILA_DE_ESPERA)
				socket.get_Personaje().get_Conexion_juego();
			else if(socket.get_Estado_Juego() == EstadosJuego.SELECCION_PERSONAJE)
				socket.set_Estado_Juego(EstadosJuego.FILA_ESPERA);
			else//WPE?
			{
				socket.enviar_Paquete("AlEn");
				socket.cerrar_Conexion();
			}
		}
		else
		{
			Consola.println("El personaje id " + id_personaje + " es nulo, para la cuenta " + cuenta.get_Id());
			socket.enviar_Paquete("ASE");
		}
	}
}
