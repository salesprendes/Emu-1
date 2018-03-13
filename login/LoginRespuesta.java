package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import database.Cuentas_DB;
import login.Enum.EstadosLogin;
import login.fila.Fila;
import main.Estados;
import main.Main;
import main.Mundo;
import objetos.Cuentas;

final public class LoginRespuesta implements Runnable
{
	protected Socket socket;
	protected BufferedReader inputStreamReader;
	protected PrintWriter outputStream;
	protected Cuentas cuenta;
	protected GenerarKey hash_key;
	private String cuenta_paquete;
	private ExecutorService ejecutor;
	private EstadosLogin estado_login = EstadosLogin.VERSION;
	private Fila fila = Main.fila_espera_login.get_Fila();

	public LoginRespuesta(final Socket _socket)
	{
		try 
		{
			socket = _socket;
			inputStreamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()), 1);
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
			if(cuenta != null)//si la cuenta no esta nula
			{
				cuenta.set_Login_respuesta(null);
			}
		}
	}

	public void run()
	{
		final char charCur[] = new char[1];
		final StringBuilder paquete = new StringBuilder();

		hash_key = new GenerarKey();
		enviar_paquete(paquete.append("HC").append(hash_key.get_Hash_key()).toString());
		paquete.setLength(0);
		try
		{
			while (inputStreamReader.read(charCur, 0, 1) != -1 && Main.estado_emulador == Estados.ENCENDIDO && socket.isConnected())
			{
				if (charCur[0] != 0 && charCur[0] != '\n' && charCur[0] != '\r') 
				{
					paquete.append(charCur[0]);
				}
				else if (!paquete.toString().isEmpty())
				{
					controlador_Paquetes(paquete.toString());
					if(Main.modo_debug)
					{
						System.out.println("> Recibido: " + paquete);
					}
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
					enviar_paquete("AlEv");
					cerrar_Conexion();
					System.out.println("> version incorrecta de la ip: " + socket.getInetAddress().getHostAddress());
					return;
				}
			break;

			case NOMBRE_CUENTA:
				if(paquete.length() >= 2)
				{
					if(Cuentas_DB.get_Existe_Cuenta(paquete.toLowerCase()))
					{
						if(!Cuentas_DB.get_Cuenta_Baneada(paquete.toLowerCase()))
						{
							cuenta_paquete = paquete.toLowerCase();
							estado_login = EstadosLogin.PASSWORD_CUENTA;
						}
						else
						{
							enviar_paquete("AlEb");
							cerrar_Conexion();
							return;
						}
					}
					else
					{
						enviar_paquete("AlEp");
						cerrar_Conexion();
						return;
					}
				}
				else
				{
					enviar_paquete("AlEa");
					cerrar_Conexion();
					return;
				}
			break;

			case PASSWORD_CUENTA:
				if (paquete.substring(0, 2).equalsIgnoreCase("#1"))
				{
					if(Cuentas_DB.get_Obtener_Cuenta_Campo_String("password", cuenta_paquete).equals(hash_key.desencriptar_Password(paquete.substring(2))))
					{
						cuenta = Cuentas_DB.get_Cuenta(cuenta_paquete);
						
						if(Mundo.get_Mundo().get_Cuentas().get(cuenta.get_Id()) == null)
						{
							Mundo.get_Mundo().agregar_Cuenta(cuenta);
							cuenta.set_Login_respuesta(this);
							estado_login = EstadosLogin.FILA_ESPERA;
						}
						else
						{
							enviar_paquete("AlEc");
							cerrar_Conexion();
							return;
						}
					}
					else
					{
						enviar_paquete("AlEf");
						cerrar_Conexion();
						return;
					}
				}
				else
				{
					enviar_paquete("AlEa");
					cerrar_Conexion();
					System.out.println("Formato incorrecto del hash");
					return;
				}
			break;

			case FILA_ESPERA:
				if(!cuenta.get_Fila_Espera())
				{
					cuenta.set_Fila_Espera(true);
					fila.agregar_Cuenta(cuenta);
				}
			break;

			case LISTA_SERVIDORES:
				switch(paquete.charAt(1))
				{
					case 'x':
						enviar_paquete("AxK31536000000|601,80");
					break;
					
					case 'X'://Seleccion del servidor
						
					break;
					
					default:
						enviar_paquete("AlEn");
						cerrar_Conexion();
					break;
				}
			break;
		}
	}

	public synchronized void cerrar_Conexion()
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
			}
			inputStreamReader.close();
			outputStream.close();
			ejecutor.shutdown();
			hash_key = null;
			estado_login = EstadosLogin.VERSION;
			cuenta = null;
		}
		catch (final IOException e)
		{
			System.out.println("Error el kickear a la cuenta: " + cuenta.get_Usuario() + " causa:" + e.getCause());
			e.printStackTrace();
		}
	}
	
	public void enviar_paquete(String paquete)
	{
		if (outputStream != null && !paquete.isEmpty() && !paquete.equals(""+(char)0))
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

	public BufferedReader get_InputStreamReader()
	{
		return inputStreamReader;
	}

	public PrintWriter get_OutputStream()
	{
		return outputStream;
	}
	
	public void set_Estado_login(EstadosLogin _estado_login)
	{
		estado_login = _estado_login;
	}
}
