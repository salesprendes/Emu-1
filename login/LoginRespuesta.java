package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import login.Enum.ErroresLogin;
import login.Enum.EstadosLogin;
import login.fila.Fila;
import main.Estados;
import main.Formulas;
import main.Main;
import main.Mundo;
import objetos.Cuentas;

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
				cuenta.set_Login_respuesta(null);
			}
		}
	}

	public void run()
	{
		final char charCur[] = new char[1];
		final StringBuilder paquete = new StringBuilder();

		hash_key = Formulas.generar_Key();
		enviar_paquete(paquete.append("HC").append(hash_key).toString());
		paquete.setLength(0);
		try
		{
			while (inputStreamReader.read(charCur, 0, 1) != -1 && Main.estado_emulador == Estados.ENCENDIDO && !ejecutor.isShutdown() && socket.isConnected())
			{
				if (charCur[0] != 0 && charCur[0] != '\n' && charCur[0] != '\r') 
				{
					paquete.append(charCur[0]);
				}
				else if (!paquete.toString().isEmpty())
				{
					if(Main.modo_debug)
					{
						System.out.println("> Recibido: " + paquete);
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
			if(cuenta != null)
			{
				cerrar_Conexion();
			}
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
						Cuentas _cuenta = Mundo.get_Mundo().get_Cuentas().get(cuenta.get_Id());

						if(_cuenta == null)//Si el puntero es nulo no esta conectado
						{
							Mundo.get_Mundo().agregar_Cuenta(cuenta);
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
				if(!cuenta.get_Fila_espera())
				{
					if(cuenta.get_Apodo().isEmpty())
					{
						enviar_paquete(ErroresLogin.CUENTA_SIN_APODO.toString());
						cuenta.set_Creando_apodo(true);
						estado_login = EstadosLogin.CREACION_APODO;
					}
					else
					{
						cuenta.set_Fila_espera(true);
						fila.agregar_Cuenta(cuenta);
					}
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
							estado_login = EstadosLogin.FILA_ESPERA;
							fila.agregar_Cuenta(cuenta);
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
				Mundo.get_Mundo().get_Cuentas().remove(cuenta.get_Id());
				if(cuenta.get_Fila_espera())
				{
					fila.set_eliminar_Cuenta(cuenta);
				}
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
			System.out.println("Error el kickear a la cuenta: " + cuenta.get_Usuario() + " causa:" + e.getCause());
			return;
		}
	}
	
	public void enviar_paquete(String paquete)
	{
		if (outputStream != null && socket != null && !paquete.isEmpty() && !paquete.equals(""+(char)0))
		{
			try 
			{
				paquete = new String(paquete.getBytes("UTF8"));
				outputStream.print(paquete + (char)0);
				outputStream.flush();
				if(Main.modo_debug)
				{
					System.out.println("Enviado >> " + paquete);
				}
			} 
			catch (Exception e) 
			{
				System.out.println("> Error al convertir el paquete: " + paquete);
				return;
			}
		}
	}
	
	public void set_Estado_login(EstadosLogin _estado_login)
	{
		estado_login = _estado_login;
	}
}
