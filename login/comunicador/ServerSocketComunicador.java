package login.comunicador;

import java.io.IOException;
import java.net.ServerSocket;

import main.Estados;
import main.Main;

final public class ServerSocketComunicador extends Thread implements Runnable
{
	private ServerSocket server_socket;
	
	public ServerSocketComunicador(int puerto)
	{
		try
		{
			server_socket = new ServerSocket(puerto);
			new Thread(this);
			start();
			System.out.println("> Intercambio del servidor iniciado en el puerto: " + 489);
		}
		catch (final IOException | RuntimeException e) 
		{
			System.out.println("> Error abriendo el puerto: " + e.getMessage());
			System.exit(0);
		}
	}
	
	public void run()
	{
		while (Main.estado_emulador == Estados.ENCENDIDO && !server_socket.isClosed() && !isInterrupted())
		{
			try 
			{
				server_socket.accept();
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
	            System.out.println("> ServerSocket comunicador cerrado");
	        } 
	        catch (IOException e)
	        {
	        	throw new RuntimeException(e.getMessage());
	        }
		}
    }
}
