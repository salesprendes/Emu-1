package login.comunicador;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import main.Configuracion;
import main.Estados;
import main.Main;
import main.consola.Consola;
import objetos.Servidores;

final public class ComunicadorServer extends Thread implements Runnable
{
	private ServerSocket server_socket;
	private final List<String> ips_servidores = new ArrayList<String>();

	public ComunicadorServer()
	{
		try
		{
			server_socket = new ServerSocket(Configuracion.PUERTO_COMUNICADOR);
			setName("Comunicador-Server");
			setDaemon(true);
			Servidores.get_Servidores().values().forEach(servidor -> ips_servidores.add(servidor.get_Ip()));
			start();
			Consola.println(">> Intercambio del servidor iniciado en el puerto: " + Configuracion.PUERTO_COMUNICADOR);
		}
		catch (final IOException | RuntimeException e) 
		{
			Consola.println(">> Error abriendo el puerto: " + e.getMessage());
			System.exit(0);
		}
	}

	public void run()
	{
		while (Main.estado_emulador != Estados.APAGADO && !server_socket.isClosed() && !isInterrupted())
		{
			try 
			{
				Socket cliente = server_socket.accept();
				if(ips_servidores.contains(cliente.getInetAddress().getHostAddress()))
				{
					new ComunicadorSocket(cliente);
				}
				else 
				{
					cliente.close();
				}
			}
			catch (IOException e)
			{
				detener_Server_Socket();
			}
		}
	}

	public synchronized void detener_Server_Socket()
	{
		if (server_socket != null && !server_socket.isClosed())
		{
			try 
			{
				server_socket.close();
				interrupt();
				Consola.println("ServerSocket comunicador cerrado");
			} 
			catch (IOException e)
			{
				throw new RuntimeException(e.getMessage());
			}
		}
	}
}
