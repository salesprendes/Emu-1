package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import database.Cuentas_DB;
import login.Enum.EstadosLogin;
import login.fila.Fila;
import main.Estados;
import main.Formulas;
import main.Main;
import main.Mundo;
import objetos.Cuentas;

public class LoginRespuesta implements Runnable 
{
	protected Socket socket;
	protected Thread thread;
	protected BufferedReader inputStreamReader;
	protected PrintWriter outputStream;
	protected Cuentas cuenta;
	protected String hash_key, cuenta_paquete;
	private EstadosLogin estado_login = EstadosLogin.VERSION;
	private Fila fila = Main.get_Fila_Espera_Login().get_Fila();

	public LoginRespuesta(final Socket _socket)
	{
		socket = _socket;
		try 
		{
			inputStreamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = new PrintWriter(socket.getOutputStream());
			thread = new Thread(this);
			thread.setDaemon(true);
			thread.start();
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

		hash_key = generar_Key();
		enviar_paquete(outputStream, paquete.append("HC").append(hash_key).toString());
		paquete.setLength(0);
		try
		{
			while (inputStreamReader.read(charCur, 0, 1) != -1 && Main.get_Estado_emulador() == Estados.ENCENDIDO && !thread.isInterrupted() && socket.isConnected())
			{
				if (charCur[0] != 0 && charCur[0] != '\n' && charCur[0] != '\r') 
				{
					paquete.append(charCur[0]);
				}
				else if (!paquete.toString().isEmpty())
				{
					System.out.println("> Recibido: " + paquete);
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
					enviar_paquete(outputStream, "AlEv");
					cerrar_Conexion();
					System.out.println("> version incorrecta de la ip: " + socket.getInetAddress().getHostAddress());
					return;
				}
			break;

			case NOMBRE_CUENTA:
				if(paquete.length() >= 6)
				{
					enviar_paquete(outputStream, "AlEa");
					cerrar_Conexion();
					return;
				}
				else if(Cuentas_DB.get_Existe_Cuenta(paquete.toLowerCase()))
				{
					if(!Cuentas_DB.get_Cuenta_Baneada(paquete.toLowerCase()))
					{
						cuenta_paquete = paquete.toLowerCase();
						estado_login = EstadosLogin.PASSWORD_CUENTA;
					}
					else
					{
						enviar_paquete(outputStream, "AlEb");
						cerrar_Conexion();
						return;
					}
				}
				else
				{
					enviar_paquete(outputStream, "AlEp");
					cerrar_Conexion();
					return;
				}
			break;

			case PASSWORD_CUENTA:
				if (paquete.substring(0, 2).equalsIgnoreCase("#1"))
				{
					if(paquete.equals(Formulas.desencriptar_Password(hash_key, Cuentas_DB.get_Obtener_Cuenta_Campo_String("password", cuenta_paquete))))
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
							enviar_paquete(outputStream, "AlEc");
							cerrar_Conexion();
							return;
						}
					}
					else
					{
						enviar_paquete(outputStream, "AlEf");
						cerrar_Conexion();
						return;
					}
				}
				else
				{
					enviar_paquete(outputStream, "AlEa");
					cerrar_Conexion();
					return;
				}
			break;

			case FILA_ESPERA:
				if(!cuenta.get_Fila_Espera())
				{
					fila.agregar_Cuenta(cuenta);
					cuenta.set_Fila_Espera(true);
				}
			break;

			case LISTA_SERVIDORES:
				switch(paquete.charAt(1))
				{
					case 'x':
						enviar_paquete(outputStream, "AxK31536000000|601,80");
					break;
					
					case 'X'://Seleccion del servidor
						
					break;
					
					default:
						enviar_paquete(outputStream, "AlEn");
						cerrar_Conexion();
					break;
				}
			break;
		}
	}

	private String generar_Key()
	{
		Random random = new Random();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder hashKey = new StringBuilder();

		for (int i = 0; i < 32; i++)
			hashKey.append(alphabet.charAt(random.nextInt(alphabet.length())));
		return hashKey.toString();
	}

	public synchronized void cerrar_Conexion()
	{
		try
		{
			Main.server_Socket_Login().get_Clientes().remove(this);
			if (socket != null && !socket.isClosed())
			{
				socket.close();
			}
			if(cuenta != null)
			{
				Mundo.get_Mundo().get_Cuentas().remove(cuenta.get_Id());
			}
			inputStreamReader.close();
			outputStream.close();
			thread.interrupt();
			hash_key = "";
			cuenta = null;
			estado_login = EstadosLogin.VERSION;
		}
		catch (final IOException e)
		{
			System.out.println("Error el kickear a la cuenta: " + cuenta.get_Usuario() + " causa:" + e.getCause());
			e.printStackTrace();
		}
	}

	public void enviar_paquete(PrintWriter _outputStream, String paquete)
	{
		if (_outputStream != null && !paquete.isEmpty() && !paquete.equals(""+(char)0x00))
		{
			try 
			{
				paquete = new String(paquete.getBytes("UTF8"));
				_outputStream.print(paquete + (char)0);
				_outputStream.flush();
				System.out.println("Enviado >> " + paquete);
			} 
			catch (Exception e) 
			{
				System.out.println(">> Error al convertir el paquete: " + paquete);
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
