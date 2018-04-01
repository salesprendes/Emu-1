package login;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

import main.Configuracion;
import main.Estados;
import main.Main;
import main.consola.Consola;

final public class ServerSocketLogin extends Thread implements Runnable
{
	protected ServerSocket server_socket;
	private final Map<String, ConexionesCliente> conexiones_clientes = new TreeMap<String, ConexionesCliente>();
	
	public ServerSocketLogin()
	{
		try
		{
			setName("Server-Login");
			server_socket = new ServerSocket(Configuracion.PUERTO_LOGIN, 10);
			start();
			Consola.println(">> Login del servidor iniciado en el puerto: " + Configuracion.PUERTO_LOGIN);
		} 
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}
    }
	
	public void run()
	{
		while(Main.estado_emulador == Estados.ENCENDIDO && !server_socket.isClosed() && !isInterrupted())
		{
			try 
			{
				Socket socket = server_socket.accept();
				String ip = socket.getInetAddress().getHostAddress();
				ConexionesCliente nueva_conexion = conexiones_clientes.get(ip);
				
				if (nueva_conexion != null && nueva_conexion.get_Tiempo_Ultima_Conexion() < 500)
				{
					Consola.println("possible ddos desde la ip: " + nueva_conexion.get_Ip_Cliente());
					nueva_conexion.refrescar_Tiempo_Ultima_Conexion();
					socket.close();
					return;
				}
				
				LoginRespuesta nuevo_cliente = new LoginRespuesta(socket, ip);
				if (!conexiones_clientes.containsKey(ip))
				{
					nueva_conexion = new ConexionesCliente(ip , nuevo_cliente);
					conexiones_clientes.put(ip, nueva_conexion);
				}
				else
				{
					conexiones_clientes.get(ip).agregar_Cliente(nuevo_cliente);
				}
			} 
			catch (Exception e)
			{
				System.out.println(e.getMessage());
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
				expulsar_Todos_Clientes();
	            server_socket.close();
	            interrupt();
	            Consola.println("ServerSocket login cerrado");
	        } 
	        catch (IOException e)
	        {
	        	throw new RuntimeException(e.getMessage());
	        }
		}
    }
	
	public void expulsar_Todos_Clientes()
	{
		conexiones_clientes.values().forEach(clientes -> clientes.expulsar_Todos_Clientes());
	}
	
	public void eliminar_Cliente(LoginRespuesta _loginRespuesta)
	{
		String ip = _loginRespuesta.get_Ip();
		if (conexiones_clientes.containsKey(ip)) 
		{
			conexiones_clientes.get(ip).eliminar_Cliente(_loginRespuesta);
		}
		System.gc();
	}
}
