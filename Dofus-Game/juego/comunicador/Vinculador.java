package juego.comunicador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import database.ConexionPool;
import main.Configuracion;
import main.Estados;
import main.Main;
import objetos.Cuentas;

final public class Vinculador extends Thread implements Runnable
{
	private Socket socket;
	private BufferedReader buffered_reader;
	private PrintWriter outputStream;
	private ConexionPool database = Main.get_Database();

	public Vinculador() 
	{
		try 
		{
			socket = new Socket("localhost", Configuracion.PUERTO_COMUNICADOR);
			socket.setReceiveBufferSize(1);
			buffered_reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			setName("Socket-Vinculador");
			start();
			Main.esta_vinculado = true;
		}
		catch(final UnknownHostException e)
		{
			System.out.println("Error: No se encuentra el host para vincular con LoginServer");
			System.exit(0);
		}
		catch (final IOException e) 
		{
			System.out.println("Error: No se puede vincular el LoginServer y GameServer.");
		}
	}

	public void run() 
	{
		if(Main.esta_vinculado)
		{
			try 
			{
				enviar_Paquete("C|" + Configuracion.SERVIDOR_ID);
				StringBuilder paquete = new StringBuilder();
				
				while(paquete.append(buffered_reader.readLine().trim()).toString() != null && !paquete.toString().isEmpty() && Main.estado_emulador != Estados.APAGADO && !isInterrupted() && socket.isConnected())
				{
					controlador_Paquetes(paquete.toString());
					if(Main.modo_debug)
						System.out.println("Comunicador: Recibido >> " + paquete);
					paquete.setLength(0);
				}
			}
			catch (final IOException e) 
			{
				Main.esta_vinculado = false;
				Main.Vincular_Login();
			}
			finally
			{
				Main.estado_emulador = Estados.VINCULANDO;
				Main.esta_vinculado = false;
				Main.Vincular_Login();
			}
		}
	}
	
	private void controlador_Paquetes(String paquete)
	{
		switch(paquete.charAt(0))
		{
			case 'E'://E|estado_letra
				switch (paquete.charAt(2))
				{
					case 'C'://cierra el socket
						cerrar_Conexion();
					break;
				}
			break;
			
			case 'C'://C|
				switch (paquete.charAt(2))
				{
					case 'N'://C|N|integro ---> nueva conexion cargar cuenta
						Cuentas cuenta = database.get_Cuentas().cargar_Cuenta(paquete.charAt(4));
						if(cuenta != null)
						{
							
						}
					break;
				}
			break;
		}
		
	}
	
	private void enviar_Paquete(String paquete) 
	{
		if(outputStream != null && !socket.isClosed() && !paquete.isEmpty() && !paquete.equals("" + (char)0))
		{
			outputStream.println(paquete + (char)0x00);
			outputStream.flush();
			if(Main.modo_debug)
				System.out.println("Comunicador: Enviado >> " + paquete);
		}
	}
	
	public void cerrar_Conexion()
	{
		try 
		{
			if(buffered_reader != null)
			{
				buffered_reader.close();
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
