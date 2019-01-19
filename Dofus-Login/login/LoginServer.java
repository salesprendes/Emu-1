package login;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import main.Configuracion;
import main.Estados;
import main.Main;
import main.consola.Consola;

final public class LoginServer extends Thread implements Runnable
{
	protected ServerSocket login_servidor;
	private ExecutorService threadPool = null;
	private static final Map<String, ConexionesCliente> conexiones_clientes = new TreeMap<String, ConexionesCliente>();
	
	public LoginServer()
	{
		threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		setName("Server-Login");
		start();
    }
	
	public void run()
	{
		get_Abrir_Server_Socket();
		
		while(Main.estado_emulador != Estados.APAGADO && !login_servidor.isClosed() && !isInterrupted())
		{
			try 
			{
				Socket socket = login_servidor.accept();
				String ip = socket.getInetAddress().getHostAddress();
				ConexionesCliente nueva_conexion = conexiones_clientes.get(ip);
				
				if (nueva_conexion != null && nueva_conexion.get_Tiempo_Ultima_Conexion() < 400)
				{
					Consola.println("possible ddos desde la ip: " + nueva_conexion.get_Ip_Cliente());
					nueva_conexion.refrescar_Tiempo_Ultima_Conexion();
					socket.close();
				}
				else
				{
					LoginSocket nuevo_cliente = new LoginSocket(socket, ip);
					if (!conexiones_clientes.containsKey(ip))
					{
						nueva_conexion = new ConexionesCliente(ip, nuevo_cliente);
						conexiones_clientes.put(ip, nueva_conexion);
					}
					else
					{
						conexiones_clientes.get(ip).agregar_Cliente(nuevo_cliente);
					}
					threadPool.execute(nuevo_cliente);
				}
			} 
			catch (Exception e)
			{
				detener_Server_Socket();
			}
		}
		
		detener_Server_Socket();
	}
	
	public synchronized void get_Abrir_Server_Socket()
	{
		try 
		{
			login_servidor = new ServerSocket(Configuracion.PUERTO_LOGIN);
			Consola.println(">> Login del servidor iniciado en el puerto: " + Configuracion.PUERTO_LOGIN);
		} 
		catch (final IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public synchronized void detener_Server_Socket()
	{
		if (login_servidor != null && !login_servidor.isClosed())
		{
			try 
	        {
				get_Expulsar_Todos_Clientes();
				if(!login_servidor.isClosed())
					login_servidor.close();
	            if(!isInterrupted())
	            	interrupt();
	            if(!threadPool.isShutdown())
	            	threadPool.shutdown();
	            Consola.println("ServerSocket login cerrado");
	        } 
	        catch (IOException e)
	        {
	        	throw new RuntimeException(e.getMessage());
	        }
		}
    }
	
	public static void get_Expulsar_Todos_Clientes()
	{
		ConexionesCliente.expulsar_Todos_Clientes();
	}
	
	public static void get_Eliminar_Cliente(LoginSocket _loginRespuesta)
	{
		String ip = _loginRespuesta.get_Ip();
		if (conexiones_clientes.containsKey(ip)) 
		{
			conexiones_clientes.get(ip).eliminar_Cliente(_loginRespuesta);
		}
	}
}
