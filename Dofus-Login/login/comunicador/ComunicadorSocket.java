package login.comunicador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import login.enums.EstadosLogin;
import main.Estados;
import main.Main;
import main.consola.Consola;
import objetos.Cuentas;
import objetos.Servidores;
import objetos.Servidores.Estados_Servidor;

final public class ComunicadorSocket extends Thread implements Runnable
{
	private Socket socket;
	private BufferedReader buffered_reader;
	private PrintWriter outputStream;
	private Servidores servidor_juego = null;
	
	public ComunicadorSocket(final Socket sock) 
	{
		try 
		{
			socket = sock;
			buffered_reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			outputStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			setDaemon(true);
			start();
		}
		catch (final IOException e)
		{
			System.out.println("Error en ComunicadorSocket: " + e);
			cerrar_Conexion_Comunicador();
		}
	}

	public void run()
	{
		try
		{
			final StringBuilder paquete = new StringBuilder();
			
			while(paquete.append(buffered_reader.readLine().trim()).toString() != null && !paquete.toString().isEmpty() && Main.estado_emulador != Estados.APAGADO && socket.isConnected())
			{
				controlador_Paquetes(paquete.toString());
				paquete.setLength(0);
				if(Main.modo_debug)
					Consola.println("Recibido comunicador: " + paquete);
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
			case 'C'://Cuenta
				if(paquete.length() > 2)
				{
					switch(paquete.charAt(2))
					{
						case 'M'://C|M| --> Migracion
							switch(paquete.charAt(4))
							{
								case 'P'://C|M|P|ID
									int id = Integer.parseInt(paquete.substring(6));
									Map<Servidores, ArrayList<Integer>> map = Main.get_Database().get_Cuentas().get_Cargar_Todos_Personajes_Cuenta_Id(id, servidor_juego.get_Id());
									StringBuilder personajes = new StringBuilder();
									
									for(Entry<Servidores, ArrayList<Integer>> entry : map.entrySet()) 
									{
										entry.getValue().forEach(personaje -> personajes.append(personajes.length() < 1 ? String.valueOf(personaje) : ',' + String.valueOf(personaje)));
										entry.getKey().get_Comunicador_game().enviar_Paquete("MG" + id + '|' + servidor_juego.get_Id() + '|' + personajes.toString());
									}
								break;
								
								case 'T'://C|M|T|id_cuenta|servidor_id
									String[] separador = paquete.substring(6).split("\\|");
									String cuenta = separador[0], lista_personajes = paquete.substring(paquete.indexOf('|' , paquete.indexOf('|') + 1) + 1);
									Servidores servidor = Servidores.get_Servidores().get(Integer.parseInt(separador[1]));
									
									if(servidor != null)
										servidor.get_Comunicador_game().enviar_Paquete("MF" + cuenta + "|" + servidor_juego.get_Id() + "|" + lista_personajes);	
								break;
							}
						break;
						
						default:
							System.out.println("El paquete " + paquete + " no existe");
						break;
					}
				}
			break;
			
			case 'S'://Servidor
				switch(paquete.charAt(2))
				{
					case 'C': //Conectar
						if(paquete.length() > 4)//S|C|id
						{
							servidor_juego = Servidores.get(Integer.valueOf(paquete.substring(4)));
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
							}
						}
					break;
					
					case 'E'://Estado|letra
						if(servidor_juego != null)
						{
							switch (paquete.charAt(4))
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
					
					case 'P'://plazas libres|numero_plazas_libres_restantes
						final short plazas_libres = Short.valueOf(paquete.substring(4));
						servidor_juego.set_Poblacion(plazas_libres);
					break;
					
					default:
						System.out.println("El paquete " + paquete + " no existe");
					break;
				}
			break;
			
			default:
				System.out.println("El paquete " + paquete + " no existe");
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
			if (socket != null && socket.isClosed())
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
			Cuentas.get_Cuentas_Cargadas().values().stream().filter(filtro -> filtro.get_Login_respuesta() != null && filtro.get_Login_respuesta().get_Estado_login() == EstadosLogin.LISTA_SERVIDORES).forEach(cuenta -> 
			{
				cuenta.get_Login_respuesta().enviar_Paquete(Servidores.get_Obtener_Servidores(cuenta));
			});
		}
	}
	
	public void get_Nueva_Conexion_Cuenta(final int id_cuenta, final String ip) 
	{
		enviar_Paquete("C|N|" + id_cuenta + ';' + ip);
	}
	
	public void get_Desconectar_Cuenta(final int id_cuenta) 
	{
		enviar_Paquete("C|D|" + id_cuenta);
	}
	
	private void enviar_Paquete(String paquete)
	{
		if (!socket.isClosed()) 
		{
			if(outputStream != null && socket.isConnected() && !paquete.isEmpty() && !paquete.equals("" + (char)0))
			{
				outputStream.println(paquete + (char)0x00);
				outputStream.flush();
				if(Main.modo_debug)
					Consola.println("Comunicador: Enviado >> " + paquete);
			}
		}
		else
		{
			cerrar_Conexion_Comunicador();
			return;
		}
	}
}
