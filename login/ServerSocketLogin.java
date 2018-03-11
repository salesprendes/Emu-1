package login;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import main.Estados;
import main.Main;

public class ServerSocketLogin extends Thread implements Runnable
{
	protected ServerSocket server_socket;
	protected Socket cliente;
	protected Thread thread;
	private final static List<LoginRespuesta> clientes = new ArrayList<LoginRespuesta>();
	
	public ServerSocketLogin(int _puerto)
	{
		try
		{
			server_socket = new ServerSocket(_puerto);
			thread = new Thread(this);
			thread.setDaemon(true);
			thread.start();
		} 
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}
    }
	
	public void run() 
	{
		while(Main.get_Estado_emulador() == Estados.ENCENDIDO && !server_socket.isClosed() && !thread.isInterrupted())
		{
			try 
			{
				clientes.add(new LoginRespuesta(server_socket.accept()));
			} 
			catch (Exception e)
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
	            thread.interrupt();
	            System.out.println("> ServerSocket login cerrado");
	        } 
	        catch (IOException e)
	        {
	        	throw new RuntimeException(e.getMessage());
	        }
		}
    }
	
	public List<LoginRespuesta> get_Clientes() 
	{
		return clientes;
	}
	
	public void expulsar_Todos_Clientes()
	{
		for(LoginRespuesta jugador : clientes)
		{
			jugador.cerrar_Conexion();
		}
	}
	
	public void eliminar_Cliente(LoginRespuesta _loginRespuesta)
	{
		int id = clientes.indexOf(_loginRespuesta);
		clientes.get(id).cerrar_Conexion();
		clientes.remove(id);
	}
}
