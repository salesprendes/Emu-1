package login;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import login.enums.ErroresLogin;
import main.Configuracion;
import main.Estados;
import main.Main;
import main.consola.Consola;

final public class ServerSocketLogin extends Thread implements Runnable
{
	protected ServerSocket server_socket;
	private final List<LoginRespuesta> clientes_login = new ArrayList<LoginRespuesta>();
	
	public ServerSocketLogin()
	{
		try
		{
			setName("Server-Login");
			server_socket = new ServerSocket(Configuracion.PUERTO_LOGIN);
			start();
			Consola.println("Login del servidor iniciado en el puerto: " + Configuracion.PUERTO_LOGIN);
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
				clientes_login.add(new LoginRespuesta(server_socket.accept()));
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
	
	public List<LoginRespuesta> get_Clientes() 
	{
		return clientes_login;
	}
	
	public void expulsar_Todos_Clientes()
	{
		for(LoginRespuesta jugador : clientes_login)
		{
			jugador.enviar_paquete(ErroresLogin.SERVIDOR_EN_MANTENIMIENTO.toString());
			jugador.cerrar_Conexion();
		}
	}
	
	public void eliminar_Cliente(LoginRespuesta _loginRespuesta)
	{
		int id = clientes_login.indexOf(_loginRespuesta);
		clientes_login.get(id).cerrar_Conexion();
		clientes_login.remove(id);
		System.gc();
	}
}
