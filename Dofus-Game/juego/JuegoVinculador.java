package juego;

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
import main.consola.Consola;
import objetos.cuentas.Cuentas;
import objetos.cuentas.Migracion;
import objetos.entidades.personajes.Personajes;

final public class JuegoVinculador extends Thread implements Runnable
{
	private Socket socket;
	private BufferedReader buffered_reader;
	private PrintWriter outputStream;
	private ConexionPool database = Main.get_Database();

	public JuegoVinculador() 
	{
		try 
		{
			socket = new Socket("localhost", Configuracion.PUERTO_COMUNICADOR);
			buffered_reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			outputStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			setName("Vinculador-Socket");
			start();
			Main.esta_vinculado = true;
		}
		catch(final UnknownHostException e)
		{
			Consola.println("Error: No se encuentra el host para vincular con LoginServer");
			System.exit(0);
		}
		catch (final IOException e) 
		{
			Consola.println("Error: No se puede vincular el LoginServer y GameServer.");
			cerrar_Conexion();
		}
	}

	public void run() 
	{
		if(Main.esta_vinculado)
		{
			try 
			{
				enviar_Paquete("S|C|" + Configuracion.SERVIDOR_ID);
				final StringBuilder paquete = new StringBuilder();
				
				while(paquete.append(buffered_reader.readLine().trim()).toString() != null && !paquete.toString().isEmpty() && Main.estado_emulador != Estados.APAGADO && !isInterrupted() && socket.isConnected())
				{
					controlador_Paquetes(paquete.toString());

						Consola.println("Comunicador: Recibido >> " + paquete);
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
			case 'E': //E|estado_letra
				switch (paquete.charAt(2))
				{
					case 'C'://cierra el socket
						cerrar_Conexion();
					break;
				}
			break;
			
			case 'C': //C|
				int id_cuenta = Integer.parseInt(paquete.substring(4).split(";")[0]);
				Cuentas cuenta = Cuentas.get_Cuenta_Cargada(id_cuenta);
				
				switch (paquete.charAt(2))
				{
					case 'N'://C|N|idcuenta ---> nueva conexion cargar cuenta
						if(cuenta == null)//nueva cuenta
						{
							database.get_Cuentas().get_Cargar_Cuenta_Id(id_cuenta);
							cuenta = Cuentas.get_Cuenta_Cargada(id_cuenta);
						}
						
						if(cuenta != null)
						{
							JuegoServer.get_Agregar_Cuenta_Esperando(cuenta);
							JuegoServer.get_Agregar_Ip_Esperando(paquete.substring(4).split(";")[1]);
							enviar_Paquete("S|P|"  + (Configuracion.PLAZAS_SERVIDOR - Main.juego_servidor.get_Clientes().size()));
						}
					break;
					
					case 'D'://C|D|IdCuenta ---> desconectar cuenta
						if(cuenta != null)
						{
							if(cuenta.get_Juego_socket() != null)
								cuenta.get_Juego_socket().cerrar_Conexion();
						}
					break;
				}
			break;
			
			case 'M'://Migracion
				switch(paquete.charAt(1)) 
				{
					case 'G'://Obtener
						String[] split_migracion = paquete.substring(2).split("\\|");
						StringBuilder alks = new StringBuilder("C|M|T|" + split_migracion[0] + "|" + split_migracion[1]);
						
						for(String id : split_migracion[2].split("\\,")) 
						{
							alks.append(Personajes.get_Personaje_Cargado(Integer.parseInt(id)).get_Paquete_Alk());
						}
						enviar_Paquete(alks.toString());
					break;
					
					case 'F':
						split_migracion = paquete.substring(2).split("\\|");
						int id_migracion = Integer.parseInt(split_migracion[0]);
						int servidor = Integer.parseInt(split_migracion[1]);
						String personajes = paquete.substring(paquete.indexOf('|' , paquete.indexOf('|') + 1) + 3);
						
						Migracion.get_Migracion(id_migracion).get_Agregar(servidor, '|' + personajes);
					break;
				}
			break;
		}
	}
	
	public void enviar_Paquete(String paquete) 
	{
		if (!socket.isClosed()) 
		{
			if(outputStream != null && !paquete.isEmpty() && !paquete.equals("" + (char)0))
			{
				outputStream.println(paquete + (char)0);
				outputStream.flush();
				if(Main.modo_debug)
					Consola.println("Comunicador: Enviado >> " + paquete);
			}
		}
		else
		{
			cerrar_Conexion();
			return;
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
			if (socket != null && socket.isClosed())
			{
				socket.close();
			}
			interrupt();
			if(Main.modo_debug)
				Consola.println("> Socket comunicador cerrado");
		} 
		catch (final IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}
}
