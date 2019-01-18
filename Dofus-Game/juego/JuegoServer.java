package juego;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import main.Configuracion;
import main.Estados;
import main.Main;
import main.consola.Consola;
import objetos.cuentas.Cuentas;

public class JuegoServer extends Thread implements Runnable
{
	protected ServerSocket juego_servidor;
	private ExecutorService threadPool;
	private static final CopyOnWriteArrayList<JuegoSocket> clientes = new CopyOnWriteArrayList<JuegoSocket>();
	private static final ConcurrentHashMap<Integer, Cuentas> cuentas_esperando = new ConcurrentHashMap<Integer, Cuentas>();
	private static final CopyOnWriteArrayList<String> ip_esperando = new CopyOnWriteArrayList<String>();
	
	public JuegoServer()
	{
		try
		{
			setName("Server-Juego");
			juego_servidor = new ServerSocket(Configuracion.PUERTO_GAME);
			threadPool = Executors.newFixedThreadPool(Configuracion.PLAZAS_SERVIDOR);
			start();
			Consola.println(">> Juego del servidor iniciado en el puerto: " + Configuracion.PUERTO_GAME);
		} 
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}
    }
	
	public void run()
	{
		while(Main.estado_emulador != Estados.APAGADO && !juego_servidor.isClosed() && !isInterrupted())
		{
			try
			{
				JuegoSocket cliente = new JuegoSocket(juego_servidor.accept());
				clientes.add(cliente);
				threadPool.execute(cliente);
			}
			catch (Exception e)
			{
				detener_Server_Socket();
				throw new RuntimeException(e.getMessage());
			}
		}
		threadPool.shutdown();
	}
	
	public synchronized void detener_Server_Socket()
	{
		if (juego_servidor != null && juego_servidor.isClosed())
		{
			try 
	        {
				juego_servidor.close();
	            interrupt();
	            Consola.println("ServerSocket login cerrado");
	        } 
	        catch (IOException e)
	        {
	        	throw new RuntimeException(e.getMessage());
	        }
		}
    }
	
	/** Metodos Clientes **/
	public CopyOnWriteArrayList<JuegoSocket> get_Clientes()
	{
		return clientes;
	}
	
	public static void get_Agregar_Cliente(final JuegoSocket _socket)
	{
		if (!clientes.contains(_socket) && _socket != null)
		{
			clientes.add(_socket);
		}
	}
	
	public static void get_Eliminar_Cliente(final JuegoSocket _socket)
	{
		if (clientes.contains(_socket) && _socket != null)
		{
			clientes.remove(_socket);
		}
	}
	
	/** Metodos cuentas esperando **/
	public static Cuentas get_Cuenta_Esperando(int id_cuenta) 
	{
		return cuentas_esperando.get(id_cuenta);
	}
	
	public static void get_Agregar_Cuenta_Esperando(final Cuentas cuenta) 
	{
		if (!cuentas_esperando.contains(cuenta) && cuenta != null)
		{
			cuentas_esperando.put(cuenta.get_Id(), cuenta);
		}
	}
	
	public static void get_Eliminar_Cuenta_Esperando(final Cuentas cuenta) 
	{
		if (cuentas_esperando.contains(cuenta) && cuenta != null)
		{
			cuentas_esperando.remove(cuenta.get_Id());
		}
	}
	
	public static boolean get_Borrar_Ip_Esperando(final String ip) 
	{
		return ip_esperando.remove(ip);
	}
	
	public static void get_Agregar_Ip_Esperando(final String ip)
	{
		ip_esperando.add(ip);
	}
}
