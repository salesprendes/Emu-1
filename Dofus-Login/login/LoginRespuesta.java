package login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import login.enums.EstadosLogin;
import login.fila.Fila;
import login.paquetes.GestorPaquetes;
import login.paquetes.entrada.VerificarCreacionApodo;
import login.paquetes.entrada.VerificarCuenta;
import login.paquetes.entrada.VerificarPassword;
import main.Configuracion;
import main.EstadosEmuLogin;
import main.Formulas;
import main.Main;
import main.consola.Consola;
import objetos.Cuentas;

final public class LoginRespuesta implements Runnable
{
	private Socket socket;
	private BufferedReader buffered_reader;
	private PrintWriter outputStream;
	private Cuentas cuenta;
	private String hash_key, cuenta_paquete, ip;
	private EstadosLogin estado_login = EstadosLogin.VERSION;
	private ExecutorService ejecutor;
	private Fila fila = Main.fila_espera_login.get_Fila();

	public LoginRespuesta(final Socket _socket, final String _ip)
	{
		if(!_socket.isClosed() && _socket.isBound())
		{
			try
			{
				socket = _socket;
				buffered_reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8), 1);
				outputStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
				ejecutor = Executors.newCachedThreadPool();
				ejecutor.submit(this);
				ip = _ip;
			}
			catch (final IOException e) 
			{
				cerrar_Conexion();
			}
			finally
			{
				if(cuenta != null)
				{
					Main.servidor_login.eliminar_Cliente(this);
					cuenta.set_Login_respuesta(null);
					Cuentas.get_Cuentas_Cargadas().remove(cuenta.get_Id());
				}
			}
		}
	}
	
	public void run()
	{
		try
		{
			final StringBuilder paquete = new StringBuilder();
			hash_key = Formulas.generar_Key();
			enviar_Paquete("HC" + hash_key);

			while (paquete.append(buffered_reader.readLine().trim()).toString() != null && !paquete.toString().isEmpty() && Main.estado_emulador == EstadosEmuLogin.ENCENDIDO && !ejecutor.isShutdown() && socket.isConnected())
			{
				if(Main.modo_debug)
					Consola.println("Recibido-login: " + paquete.toString());
				controlador_Paquetes(paquete.toString());
				paquete.setLength(0);
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
		if(paquete.length() >= 2)
		{
			GestorPaquetes buscar_paquete = Configuracion.get_Paquetes_Emulador().get(paquete);
			
			if(buscar_paquete != null)
			{
				buscar_paquete.analizar(this, paquete);
			}
			else
			{
				switch(estado_login)
				{
					case NOMBRE_CUENTA:
						new VerificarCuenta().analizar(this, paquete);
					break;
					
					case PASSWORD_CUENTA:
						new VerificarPassword().analizar(this, paquete);
					break;
					
					case CREACION_APODO:
						new VerificarCreacionApodo().analizar(this, paquete);
					break;
					
					case VERSION:
					default:
						Consola.println("Paquete desconocido: " + paquete);
						cerrar_Conexion();
					break;
				}
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
		synchronized(this) 
		{
			try
			{
				if(outputStream != null)
					outputStream.close();
				if(buffered_reader != null)
					buffered_reader.close();
				if (socket != null && !socket.isClosed())
					socket.close();
				Main.servidor_login.eliminar_Cliente(this);
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
	}
	
	public void enviar_Paquete(String paquete)
	{
		synchronized (this) 
		{
			if (socket.isClosed()) 
			{
				cerrar_Conexion();
				return;
			}
			if (outputStream != null && !paquete.isEmpty() && !paquete.equals("" + (char)0))
			{
				outputStream.print(paquete + (char)0);
				outputStream.flush();
				if(Main.modo_debug)
					Consola.println("Enviado >> " + paquete);
			}
		}
	}

	public Socket get_Socket() 
	{
		return socket;
	}

	public void set_Socket(Socket _socket) 
	{
		socket = _socket;
	}

	public BufferedReader get_Buffered_reader()
	{
		return buffered_reader;
	}

	public void set_Buffered_reader(BufferedReader _buffered_reader) 
	{
		buffered_reader = _buffered_reader;
	}

	public PrintWriter get_OutputStream() 
	{
		return outputStream;
	}

	public void set_OutputStream(PrintWriter _outputStream)
	{
		outputStream = _outputStream;
	}

	public Cuentas get_Cuenta()
	{
		return cuenta;
	}

	public void set_Cuenta(Cuentas _cuenta)
	{
		cuenta = _cuenta;
	}

	public String get_Hash_key() 
	{
		return hash_key;
	}

	public void set_Hash_key(String _hash_key)
	{
		hash_key = _hash_key;
	}

	public String get_Cuenta_paquete()
	{
		return cuenta_paquete;
	}

	public void set_Cuenta_paquete(String _cuenta_paquete) 
	{
		cuenta_paquete = _cuenta_paquete;
	}

	public EstadosLogin get_Estado_login() 
	{
		return estado_login;
	}

	public void set_Estado_login(EstadosLogin _estado_login) 
	{
		estado_login = _estado_login;
	}

	public String get_Ip()
	{
		return ip;
	}

	public void set_Ip(String _ip)
	{
		ip = _ip;
	}

	public Fila get_Fila() 
	{
		return fila;
	}

	public void set_Fila(Fila _fila)
	{
		fila = _fila;
	}
}
