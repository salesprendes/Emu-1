package login.comunicador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import login.enums.EstadosLogin;
import main.Estados;
import main.Main;
import main.consola.Consola;
import objetos.Cuentas;
import objetos.Servidores;
import objetos.Servidores.Estados_Servidor;

final public class ComunicadorRespuesta implements Runnable
{
	private Socket socket;
	private BufferedReader buffered_reader;
	private PrintWriter outputStream;
	private Servidores servidor_juego = null;
	
	public ComunicadorRespuesta(final Socket sock) 
	{
		try 
		{
			socket = sock;
			socket.setSendBufferSize(1);
			buffered_reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8), 1);//80 caracteres
			outputStream = new PrintWriter(socket.getOutputStream());
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
			while(paquete.append(buffered_reader.readLine().trim()).toString() != null && !paquete.toString().isEmpty() && Main.estado_emulador == Estados.ENCENDIDO && socket.isConnected())
			{
				controlador_Paquetes(paquete.toString()); 
				paquete.setLength(0); 
				if(Main.modo_debug) 
					Consola.println("Recibido-comunicador: " + paquete);
			}
		}
		catch (final IOException e)
		{
			cerrar_Conexion_Comunicador();
		}
		finally 
		{
			cerrar_Conexion_Comunicador();
			refrescar_Estado_Servidor();
		}
	}
	
	private void controlador_Paquetes(final String paquete)
	{
		switch(paquete.charAt(0))
		{
			case 'C':
				if(paquete.length() > 2)//C|id
				{
					servidor_juego = Servidores.get(Integer.valueOf(paquete.substring(2)));
					if(servidor_juego != null && servidor_juego.get_Comunicador_game() == null)
					{
						servidor_juego.set_Comunicador_game(this);
						servidor_juego.set_Estado(Estados_Servidor.CONECTADO);
						refrescar_Estado_Servidor();
						Consola.println("Servidor " + servidor_juego.get_Id() + " conectado");
					}
					else
					{
						enviar_Paquete("E|C");
						cerrar_Conexion_Comunicador();
						Consola.println("Se ha recibido el paquete " + paquete + " pero no existe el servidor");
					}
				}
			break;
			
			case 'E'://E|estado_letra
				if(servidor_juego != null)
				{
					switch (paquete.charAt(2))
					{
						case 'A'://abierto
							servidor_juego.set_Estado(Estados_Servidor.CONECTADO);
						break;
	
						case 'G'://guardando
							servidor_juego.set_Estado(Estados_Servidor.GUARDANDO);
						break;
	
						case 'C'://cerrado
							servidor_juego.set_Estado(Estados_Servidor.APAGADO);
						break;
					}
					refrescar_Estado_Servidor();
				}
			break;
		}
	}
	
	private void cerrar_Conexion_Comunicador()
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
			if (servidor_juego != null)
			{
				servidor_juego.set_Estado(Estados_Servidor.APAGADO);
				servidor_juego.set_Comunicador_game(null);
			}
		}
		catch (IOException e)
		{
			Consola.println("Error comunicador: " + e.getCause());
			return;
		}
	}
	
	private void refrescar_Estado_Servidor()
	{
		if(Cuentas.get_Cuentas_Cargadas().size() > 0)
		{
			String estado_servidor_modificado = Servidores.get_Obtener_Servidores();
			Cuentas.get_Cuentas_Cargadas().values().stream().filter(filtro -> filtro.get_Login_respuesta().get_Estado_login() == EstadosLogin.LISTA_SERVIDORES).forEach(cuenta -> 
			{
				cuenta.get_Login_respuesta().enviar_Paquete(estado_servidor_modificado);
			});
		}
	}
	
	public void enviar_Cuenta(final int id_cuenta) 
	{
		enviar_Paquete("C|N|" + id_cuenta);
	}
	
	private void enviar_Paquete(String paquete) 
	{
		if (socket.isClosed()) 
		{
			cerrar_Conexion_Comunicador();
			return;
		}
		if(outputStream != null && !paquete.isEmpty() && !paquete.equals("" + (char)0))
		{
			outputStream.println(paquete + (char)0x00);
			outputStream.flush();
			if(Main.modo_debug)
				Consola.println("Comunicador: Enviado >> " + paquete);
		}
	}
}
