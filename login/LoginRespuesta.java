package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import login.Enum.ErroresLogin;
import login.Enum.EstadosLogin;
import login.fila.Fila;
import main.Estados;
import main.Formulas;
import main.Main;
import objetos.Cuentas;
import objetos.Servidores;

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
		enviar_paquete(paquete.append("HC").append(hash_key).toString());
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
						System.out.println("> Recibido-login: " + paquete);
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
					enviar_paquete(ErroresLogin.VERSION_INCORRECTA.toString());
					cerrar_Conexion();
					System.out.println("> version incorrecta de la ip: " + socket.getInetAddress().getHostAddress());
					return;
				}
			break;

			case NOMBRE_CUENTA:
				if(paquete.length() >= 2)
				{
					if(Main.get_Database().get_Cuentas().get_Existe_Cuenta(paquete.toLowerCase()))
					{
						if(!Main.get_Database().get_Cuentas().get_Cuenta_Baneada(paquete.toLowerCase()))
						{
							cuenta_paquete = paquete.toLowerCase();
							estado_login = EstadosLogin.PASSWORD_CUENTA;
						}
						else
						{
							enviar_paquete(ErroresLogin.CUENTA_BANEADA.toString());
							cerrar_Conexion();
							return;
						}
					}
					else
					{
						enviar_paquete(ErroresLogin.CUENTA_NO_VALIDA.toString());
						cerrar_Conexion();
						return;
					}
				}
				else
				{
					enviar_paquete(ErroresLogin.CUENTA_CONECTADA.toString());
					cerrar_Conexion();
					return;
				}
			break;

			case PASSWORD_CUENTA:
				if(paquete.substring(0, 2).equalsIgnoreCase("#1"))
				{
					if(paquete.equals(Formulas.desencriptar_Password(hash_key, Main.get_Database().get_Cuentas().get_Obtener_Cuenta_Campo_String("password", cuenta_paquete))))
					{
						cuenta = Main.get_Database().get_Cuentas().get_Cuenta(cuenta_paquete);
						
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
							_cuenta.get_Login_respuesta().enviar_paquete("ATE");
							enviar_paquete(ErroresLogin.CUENTA_YA_CONECTADA.toString());
							cerrar_Conexion();
							_cuenta.get_Login_respuesta().cerrar_Conexion();
							return;
						}
					}
					else
					{
						enviar_paquete(ErroresLogin.CUENTA_PASSWORD_INCORRECTA.toString());
						cerrar_Conexion();
						return;
					}
				}
				else
				{
					enviar_paquete(ErroresLogin.CUENTA_CONECTADA.toString());
					cerrar_Conexion();
					System.out.println("paquete incorrecto password");
					return;
				}
			break;

			case FILA_ESPERA:
				if(cuenta.get_Apodo().isEmpty() && !cuenta.esta_Creando_apodo() && !cuenta.get_Fila_espera())
				{
					enviar_paquete(ErroresLogin.CUENTA_SIN_APODO.toString());
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
						enviar_paquete("AxK" + cuenta.get_Fecha_abono());
					break;
					
					case 'X'://Seleccion del servidor
						
					break;
					
					case 'F':
						enviar_paquete("AF" + Main.get_Database().get_Cuentas().get_Personaje_Servidores(paquete.substring(2)));
					break;
					
					default:
						enviar_paquete(ErroresLogin.CONEXION_NO_TERMINADA.toString());
						cerrar_Conexion();
					break;
				}
			break;
			
			case CREACION_APODO:
				if(cuenta.get_Apodo().isEmpty() && cuenta.esta_Creando_apodo())
				{
					if(!paquete.toLowerCase().equals(cuenta.get_Usuario().toLowerCase()))
					{
						if(paquete.matches("[A-Za-z0-9.@.-]+") && !Main.get_Database().get_Cuentas().get_Apodo_Existe(paquete))
						{
							cuenta.set_Apodo(paquete);
							cuenta.set_Creando_apodo(false);
							cuenta.set_Fila_espera(true);
							fila.agregar_Cuenta(cuenta);
							estado_login = EstadosLogin.FILA_ESPERA;
						}
						else
						{
							enviar_paquete(ErroresLogin.CUENTA_APODO_ERROR.toString());
							return;
						}
					}
					else
					{
						enviar_paquete(ErroresLogin.CUENTA_SIN_APODO.toString());
						return;
					}
				}
				else
				{
					enviar_paquete(ErroresLogin.CONEXION_NO_TERMINADA.toString());
					cerrar_Conexion();
				}
			break;
		}
	}

	public void cerrar_Conexion()
	{
		try
		{
			if (socket != null && !socket.isClosed())
			{
				socket.close();
			}
			Main.servidor_login.get_Clientes().remove(this);
			if(cuenta != null)
			{
				Cuentas.eliminar_Cuenta_Cargada(cuenta.get_Id());
			}
			inputStreamReader.close();
			outputStream.close();
			hash_key = null;
			estado_login = null;
			cuenta = null;
			ejecutor.shutdown();
		}
		catch (final IOException e)
		{
			System.out.println("Error el kickear a la cuenta: " + cuenta.get_Usuario() + " causa: " + e.getMessage());
			return;
		}
	}
	
	public void enviar_paquete(String paquete)
	{
		if (outputStream != null && !socket.isClosed() && !paquete.isEmpty() && !paquete.equals("" + (char)0))
		{
			try 
			{
				paquete = new String(paquete.getBytes("UTF-8"));
				outputStream.print(paquete + (char)0);
				outputStream.flush();
				if(Main.modo_debug)
				{
					System.out.println("Enviado >> " + paquete);
				}
			} 
			catch (final UnsupportedEncodingException e) 
			{
				System.out.println("> Error al convertir el paquete a UTF-8: " + paquete);
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
	
	public void refrescar_servidores()
	{
		enviar_paquete(Servidores.get_Obtener_Servidores());
	}
}
