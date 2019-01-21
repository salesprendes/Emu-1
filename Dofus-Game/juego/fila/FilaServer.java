package juego.fila;

import juego.JuegoSocket;
import juego.enums.EstadosJuego;
import main.Configuracion;
import main.EstadosEmulador;
import main.Main;
import main.consola.Consola;
import objetos.cuentas.Cuentas;
import objetos.cuentas.Migracion;

final public class FilaServer extends Thread implements Runnable
{
	private FilaSocket fila;
	private Nodo nodo_fila = null;

	public FilaServer() 
	{
		setName("Server-Fila");
		fila = new FilaSocket(Configuracion.SERVIDOR_ID);
		start();
	}

	public void run()
	{
		synchronized(fila)
		{
			while(Main.estado_emulador != EstadosEmulador.APAGADO && !isInterrupted())
			{
				nodo_fila = fila.eliminar_Cuenta_Fila_Espera();
				try
				{
					fila.wait(1100 * fila.get_Fila().size());//1 segundo * tamaño fila
				}
				catch (InterruptedException e) {}
				finally 
				{
					if(nodo_fila != null)
					{
						fila.set_eliminar_Cuenta(nodo_fila);
						JuegoSocket socket = nodo_fila.get_Cuenta().get_Juego_socket();
						if(socket != null)
						{
							if(socket.get_Personaje() != null)
							{
								socket.get_Personaje().get_Conexion_juego();
								socket.set_Estado_Juego(EstadosJuego.CONECTADO);
							}
							else
							{
								if(Main.get_Database().get_Cuentas().get_Puede_Migrar(nodo_fila.get_Cuenta().get_Id())) 
								{
									String servidores = Main.get_Database().get_Cuentas().get_Lista_Servidores_Otros_Personajes(nodo_fila.get_Cuenta().get_Id());
									if(!servidores.isEmpty())
									{
										new Migracion(nodo_fila.get_Cuenta().get_Id(), servidores);
										Main.socket_vinculador.enviar_Paquete("C|M|P|" + nodo_fila.get_Cuenta().get_Id());
										socket.set_Estado_Juego(EstadosJuego.MIGRACION);
									}
								}
								else
								{
									socket.enviar_Paquete(get_Paquete_Lista_Personajes(nodo_fila.get_Cuenta()));
									socket.set_Estado_Juego(EstadosJuego.SELECCION_PERSONAJE);
								}
							}
						}
						nodo_fila.get_Cuenta().set_Fila_espera(false);
					}
					fila.actualizar_A_Nuevas_Posiciones();
				}
			}
		}
	}
	
	private String get_Paquete_Lista_Personajes(Cuentas cuenta)
	{
		final StringBuilder paquete = new StringBuilder();
		paquete.append("ALK" + cuenta.get_Fecha_abono(cuenta.get_Id()) + "|").append(cuenta.get_Personajes().size());
		cuenta.get_Personajes().forEach(personaje -> paquete.append(personaje.get_Paquete_Alk()));
		
		return paquete.toString();
	}

	public void detener_Fila() 
	{
		if(isAlive())
		{
			interrupt();
			Consola.println(">> ServerFila cerrada");
		}
	}

	public FilaSocket get_Fila()
	{
		return fila;
	}
}