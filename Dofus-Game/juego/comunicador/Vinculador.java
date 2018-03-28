package juego.comunicador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import main.Estados;
import main.Main;

final public class Vinculador extends Thread implements Runnable
{
	private Socket socket;
	private BufferedReader inputStreamReader;
	private PrintWriter outputStream;

	public Vinculador() 
	{
		try 
		{
			socket = new Socket("localhost", 489);
			socket.setReceiveBufferSize(1048);
			inputStreamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = new PrintWriter(socket.getOutputStream());
			setName("Socket-Vinculador");
			start();
			Main.esta_vinculado = true;
			System.out.println("LoginServer y GameServer vinculados.");

		}
		catch (final Exception e) 
		{
			System.out.println("Error: No se puede vincular el LoginServer y GameServer.");
			Main.esta_vinculado = false;
			Main.volver_Vincular_Login();
		}
	}

	public void run() 
	{
		try 
		{
			enviar_Paquete("C|Eratz");
			StringBuilder paquete = new StringBuilder();
			Main.esta_vinculado = true;
			final char[] buffer = new char[1];
			while(inputStreamReader.read(buffer, 0, 1) != -1 && Main.estado_emulador == Estados.ENCENDIDO && !isInterrupted() && !socket.isClosed())
			{
				if (buffer[0] != (char)0 && buffer[0] != '\n' && buffer[0] != '\r')
		    	{
					paquete.append(buffer[0]);
		    	}
				else if (!paquete.toString().isEmpty())
				{
					paquete.append(new String(paquete.toString().getBytes("UTF-8")));
					if(Main.modo_debug)
						System.out.println("Comunicador: Recibido >> " + paquete);
					controlador_Paquetes(paquete.toString());
					paquete.setLength(0);
				}
			}
		}
		catch (final IOException e) 
		{
			Main.esta_vinculado = false;
			Main.volver_Vincular_Login();
		}
		finally
		{
			cerrar_Conexion();
		}
	}
	
	private void controlador_Paquetes(String paquete)
	{
		
	}
	
	private void enviar_Paquete(String paquete) 
	{
		if(outputStream != null && !socket.isClosed() && !paquete.isEmpty() && !paquete.equals("" + (char)0))
		{
			outputStream.print(paquete + (char)0x00);
			outputStream.flush();
			if(Main.modo_debug)
				System.out.println("Comunicador: Enviado >> " + paquete);
		}
	}
	
	public void cerrar_Conexion()
	{
		try 
		{
			if(inputStreamReader != null)
			{
				inputStreamReader.close();
			}
			if(outputStream != null)
			{
				outputStream.close();
			}
			if (socket != null && !socket.isClosed())
			{
				socket.close();
			}
			interrupt();
			System.out.println("> Socket comunicador cerrado");
		} 
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}
}
