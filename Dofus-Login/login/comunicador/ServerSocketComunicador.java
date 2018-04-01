package login.comunicador;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import main.Configuracion;
import main.Estados;
import main.Main;
import main.consola.Consola;
import objetos.Servidores;

final public class ServerSocketComunicador extends Thread implements Runnable
{
	private ServerSocket server_socket;
	private final List<String> ip_servidores = new ArrayList<String>();

	public ServerSocketComunicador()
	{
		try
		{
			setName("Server-Comunicador");
			server_socket = new ServerSocket();
			server_socket.bind(new InetSocketAddress("localhost", Configuracion.PUERTO_COMUNICADOR));
			new Thread(this);
			setDaemon(true);
			start();
			Servidores.get_Servidores().values().forEach(servidor -> ip_servidores.add(servidor.get_Ip()));
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
		while (Main.estado_emulador == Estados.ENCENDIDO && !server_socket.isClosed() && !isInterrupted())
		{
			try 
			{
				Socket cliente = server_socket.accept();
				if(ip_servidores.contains(cliente.getInetAddress().getHostAddress()))
				{
					new ComunicadorRespuesta(cliente);
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
