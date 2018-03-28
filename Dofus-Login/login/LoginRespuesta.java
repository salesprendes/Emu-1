package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import login.enums.ErroresLogin;
import login.enums.ErroresServidor;
import login.enums.EstadosLogin;
import login.fila.Fila;
import main.Estados;
import main.Formulas;
import main.Main;
import main.consola.Consola;
import objetos.Cuentas;
import objetos.Servidores;
import objetos.Servidores.Estados_Servidor;

final public class LoginRespuesta implements Runnable
{
	private Socket socket;
	private BufferedReader inputStreamReader;
	private PrintWriter outputStream;
	private Cuentas cuenta;
	private String hash_key, cuenta_paquete;
	private ExecutorService ejecutor;
	private EstadosLogin estado_login = EstadosLogin.VERSION;
	private Fila fila = Main.fila_espera_login.get_Fila();
	
	public LoginRespuesta(final Socket _socket)
	{
		try 
		{
			socket = _socket;
			inputStreamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = new PrintWriter(socket.getOutputStream());
			ejecutor = Executors.newCachedThreadPool();
			ejecutor.submit(this);
		}
		catch (final IOException e) 
		{
			cerrar_Conexion();
		}
		finally
		{
			if(cuenta != null)
			{
				Main.servidor_login.get_Clientes().remove(this);
				cuenta.set_Login_respuesta(null);
				Cuentas.get_Cuentas_Cargadas().remove(cuenta.get_Id());
			}
		}
	}

	public void run()
	{
		final char[] buffer = new char[1];
		final StringBuilder paquete = new StringBuilder();

		hash_key = Formulas.generar_Key();
		enviar_Paquete(paquete.append("HC").append(hash_key).toString());
		paquete.setLength(0);
		try
		{
			while (inputStreamReader.read(buffer, 0, 1) != -1 && Main.estado_emulador == Estados.ENCENDIDO && !ejecutor.isShutdown() && socket.isConnected())
			{
				if (buffer[0] != (char)0 && buffer[0] != '\n' && buffer[0] != '\r')
				{
					paquete.append(buffer[0]);
				}
				else if (!paquete.toString().isEmpty())
				{
					if(Main.modo_debug)
					{
						Consola.println("Recibido-login: " + paquete);
					}
					controlador_Paquetes(paquete.toString());
					paquete.setLength(0);
				}
			}
		}
		catch (IOException e)
		{
			cerrar_Conexion();
		}
		finally
		{
			cerrar_Conexion();
		}
	}

	private void controlador_Paquetes(String paquete)
	{
		switch(estado_login)
		{
			case VERSION:
				if (paquete.equalsIgnoreCase("1.29.1"))
				{
					estado_login = EstadosLogin.NOMBRE_CUENTA;
				}
				else
				{
					enviar_Paquete(ErroresLogin.VERSION_INCORRECTA.toString());
					cerrar_Conexion();
					return;
				}
			break;
			
			case CREACION_APODO:
				if(cuenta.get_Apodo().isEmpty() && cuenta.esta_Creando_apodo())
				{
					if(!paquete.toLowerCase().equals(cuenta.get_Usuario().toLowerCase()))
					{
						if(paquete.matches("[A-Za-z0-9.@.-]+") && !Main.get_Database().get_Cuentas().get_Existe_Campo_Cuenta("apodo", "apodo", paquete))
						{
							cuenta.set_Apodo(paquete);
							cuenta.set_Creando_apodo(false);
							cuenta.set_Fila_espera(true);
							fila.agregar_Cuenta(cuenta);
							estado_login = EstadosLogin.FILA_ESPERA;
						}
						else
						{
							enviar_Paquete(ErroresLogin.CUENTA_APODO_ERROR.toString());
							return;
						}
					}
					else
					{
						enviar_Paquete(ErroresLogin.CUENTA_SIN_APODO.toString());
						return;
					}
				}
				else
				{
					enviar_Paquete(ErroresLogin.CONEXION_NO_TERMINADA.toString());
					cerrar_Conexion();
				}
			break;

			case NOMBRE_CUENTA:
				if(paquete.length() >= 2)
				{
					if(Main.get_Database().get_Cuentas().get_Existe_Campo_Cuenta("usuario", "usuario", paquete.toLowerCase()))
					{
						if(!Main.get_Database().get_Cuentas().get_Comprobar_Campo_Cuenta_Booleano("baneado", "usuario", paquete.toLowerCase()))
						{
							cuenta_paquete = paquete.toLowerCase();
							estado_login = EstadosLogin.PASSWORD_CUENTA;
						}
						else
						{
							enviar_Paquete(ErroresLogin.CUENTA_BANEADA.toString());
							cerrar_Conexion();
							return;
						}
					}
					else
					{
						enviar_Paquete(ErroresLogin.CUENTA_NO_VALIDA.toString());
						cerrar_Conexion();
						return;
					}
				}
				else
				{
					enviar_Paquete(ErroresLogin.CUENTA_CONECTADA.toString());
					cerrar_Conexion();
					return;
				}
			break;

			case PASSWORD_CUENTA:
				if(paquete.substring(0, 2).equalsIgnoreCase("#1"))
				{
					if(paquete.equals(Formulas.desencriptar_Password(hash_key, Main.get_Database().get_Cuentas().get_Obtener_Cuenta_Campo_String("password", cuenta_paquete))))
					{
						cuenta = Main.get_Database().get_Cuentas().cargar_Cuenta(cuenta_paquete);
						
						/** puntero que extrae la dirección de memoria del hashmap **/
						Cuentas _cuenta = Cuentas.get_Cuentas_Cargadas().get(cuenta.get_Id());

						if(_cuenta == null)//Si el puntero es nulo no esta conectado
						{
							
							Cuentas.agregar_Cuenta_Cargada(cuenta);
							cuenta.set_Login_respuesta(this);
							estado_login = EstadosLogin.FILA_ESPERA;
						}
						else
						{
							_cuenta.get_Login_respuesta().enviar_Paquete("ATE");
							enviar_Paquete(ErroresLogin.CUENTA_YA_CONECTADA.toString());
							cerrar_Conexion();
							_cuenta.get_Login_respuesta().cerrar_Conexion();
							return;
						}
					}
					else
					{
						enviar_Paquete(ErroresLogin.CUENTA_PASSWORD_INCORRECTA.toString());
						cerrar_Conexion();
						return;
					}
				}
				else
				{
					enviar_Paquete(ErroresLogin.CUENTA_CONECTADA.toString());
					cerrar_Conexion();
					Consola.println("paquete incorrecto password");
					return;
				}
			break;

			case FILA_ESPERA:
				if(cuenta.get_Apodo().isEmpty() && !cuenta.esta_Creando_apodo() && !cuenta.get_Fila_espera())
				{
					enviar_Paquete(ErroresLogin.CUENTA_SIN_APODO.toString());
					cuenta.set_Creando_apodo(true);
					estado_login = EstadosLogin.CREACION_APODO;
				}
				else if(!cuenta.get_Fila_espera())
				{
					cuenta.set_Fila_espera(true);
					fila.agregar_Cuenta(cuenta);
				}
			break;

			case LISTA_SERVIDORES:
				switch(paquete.charAt(1))
				{
					case 'x':
						enviar_Paquete("AxK" + cuenta.get_Fecha_abono() + Main.get_Database().get_Cuentas().get_Contar_Personajes_Servidor(cuenta));
					break;
					
					case 'X'://Seleccion del servidor
						if(estado_login == EstadosLogin.LISTA_SERVIDORES)
						{
							Servidores servidor = Servidores.get(Integer.parseInt(paquete.substring(2)));
							if(servidor.get_Comunicador_game() != null)
							{
								if(servidor.get_Estado() != Estados_Servidor.CONECTADO)
								{
									enviar_Paquete(ErroresServidor.SERVIDOR_NO_DISPONIBLE.toString());
									return;
								}
								if(servidor.es_Servidor_Vip() && !cuenta.es_Cuenta_Abonada())
								{
									enviar_Paquete(Servidores.get_Obtener_Servidores_Disponibles());
									return;
								}
								servidor.get_Comunicador_game().enviar_Cuenta(cuenta);
							}
							else
							{
								enviar_Paquete(ErroresServidor.SERVIDOR_NO_EXISTENTE.toString());
								return;
							}
						}
					break;
					
					case 'F':
						enviar_Paquete("AF" + Main.get_Database().get_Cuentas().get_Paquete_Buscar_Servidores(paquete.substring(2)));
					break;
					
					default:
						enviar_Paquete(ErroresLogin.CONEXION_NO_TERMINADA.toString());
						cerrar_Conexion();
					break;
				}
			break;
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
			Main.servidor_login.get_Clientes().remove(this);
			if(cuenta != null)
			{
				Cuentas.eliminar_Cuenta_Cargada(cuenta.get_Id());
			}
			hash_key = null;
			estado_login = null;
			cuenta = null;
			ejecutor.shutdown();
		}
		catch (final IOException e)
		{
			Consola.println("Error el kickear a la cuenta: " + cuenta.get_Usuario() + " causa: " + e.getMessage());
			return;
		}
	}
	
	public void enviar_Paquete(String paquete)
	{
		if (outputStream != null && !socket.isClosed() && !paquete.isEmpty() && !paquete.equals("" + (char)0))
		{
			try 
			{
				paquete = new String(paquete.getBytes("UTF-8"));
				outputStream.print(paquete + (char)0);
				outputStream.flush();
				if(Main.modo_debug)
					Consola.println("Enviado >> " + paquete);
			} 
			catch (final UnsupportedEncodingException e) 
			{
				Consola.println("Error al convertir el paquete a UTF-8: " + paquete);
				return;
			}
		}
	}
	
	public EstadosLogin get_Estado_login()
	{
		return estado_login;
	}
	
	public void set_Estado_login(EstadosLogin _estado_login)
	{
		estado_login = _estado_login;
	}
}
