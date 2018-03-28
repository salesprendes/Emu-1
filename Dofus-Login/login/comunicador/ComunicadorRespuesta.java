package login.comunicador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import login.enums.EstadosLogin;
import main.Estados;
import main.Main;
import objetos.Cuentas;
import objetos.Servidores;

final public class ComunicadorRespuesta implements Runnable
{
	private Socket socket;
	private BufferedReader inputStreamReader;
	private PrintWriter outputStream;
	private ExecutorService ejecutor;
	private Servidores servidor_juego = null;
	
	public ComunicadorRespuesta(final Socket sock) 
	{
		try 
		{
			socket = sock;
			socket.setSendBufferSize(1048);
			inputStreamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = new PrintWriter(socket.getOutputStream());
			ejecutor = Executors.newCachedThreadPool();
			ejecutor.submit(this);
		}
		catch (final IOException e)
		{
			cerrar_Conexion_Comunicador();
		}
	}

	public void run()
	{
		try
		{
			StringBuilder paquete = new StringBuilder();
			final char[] buffer = new char[1];
			
			while(inputStreamReader.read(buffer, 0, 1) != -1 && Main.estado_emulador == Estados.ENCENDIDO && !ejecutor.isShutdown() && socket.isConnected())
			{
				if (buffer[0] != (char)0 && buffer[0] != '\n' && buffer[0] != '\r')
				{
					paquete.append(buffer[0]);
				}
				else if (!paquete.toString().isEmpty())
				{
					if(Main.modo_debug)
					{
						System.out.println("> Recibido-comunicador: " + paquete);
					}
					controlador_Paquetes(paquete.toString());
					paquete.setLength(0);
				}
			}
		}
		catch (final IOException e)
		{
			cerrar_Conexion_Comunicador();
		}
		finally 
		{
			cerrar_Conexion_Comunicador();
		}
	}
	
	private void controlador_Paquetes(final String paquete)
	{
		switch(paquete.charAt(0))
		{
			case 'C':
				if(paquete.length() > 2)//C|Nombre_servidor
				{
					final String id_servidor_recibido = paquete.substring(2);
					Servidores.get_Servidores().values().forEach(S ->
					{
						if(id_servidor_recibido.equals(S.get_Nombre()))
							servidor_juego = S;
					});
					if(servidor_juego != null)
					{
						servidor_juego.set_Comunicador_game(this);
						servidor_juego.set_Estado((byte) 1);
						System.out.println("> Servidor " + servidor_juego.get_Nombre() + " conectado");
					}
					else
					{
						System.out.println("> Se ha recibido el paquete " + paquete + " pero no existe el servidor");
					}
				}
			break;
			
			case 'E'://E|estado_letra
				if(servidor_juego != null)
				{
					switch (paquete.charAt(2))
					{
						case 'A'://abierto
							servidor_juego.set_Estado((byte) 1);
						break;
	
						case 'G'://guardando
							servidor_juego.set_Estado((byte) 2);
						break;
	
						case 'C'://cerrado
							servidor_juego.set_Estado((byte) 0);
						break;
					}
				}
			break;
		}
		
		if(Cuentas.get_Cuentas_Cargadas().size() > 0)
		{
			Cuentas.get_Cuentas_Cargadas().values().stream().filter(filtro -> filtro.get_Login_respuesta().get_Estado_login() == EstadosLogin.LISTA_SERVIDORES).forEach(cuenta -> 
			{
				cuenta.get_Login_respuesta().enviar_paquete(Servidores.get_Obtener_Servidores());
			});
		}
	}
	
	private void cerrar_Conexion_Comunicador()
	{
		try 
		{
			if (socket != null && !socket.isClosed())
			{
				socket.close();
			}
			inputStreamReader.close();
			outputStream.close();
			if (servidor_juego != null)
			{
				servidor_juego.set_Estado((byte) 0);
				Cuentas.get_Cuentas_Cargadas().values().forEach(cuenta ->
				{
					cuenta.get_Login_respuesta().enviar_paquete(Servidores.get_Obtener_Servidores());
				});
				servidor_juego.set_Comunicador_game(null);
			}
			ejecutor.shutdown();
		}
		catch (IOException e)
		{
			System.out.println("Error comunicador: " + e.getCause());
			return;
		}
	}
}