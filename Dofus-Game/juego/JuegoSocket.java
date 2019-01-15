package juego;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import juego.enums.EstadosJuego;
import juego.fila.FilaSocket;
import juego.paquetes.GestorPaquetes;
import main.Configuracion;
import main.Estados;
import main.Main;
import main.consola.Consola;
import objetos.cuentas.Cuentas;
import objetos.entidades.personajes.Personajes;

public class JuegoSocket implements Runnable
{
	private Cuentas cuenta;
	private Personajes personaje;
	private ExecutorService ejecutor;
	private BufferedReader buffered_reader;
	private PrintWriter outputStream;
	private Socket socket;
	private FilaSocket fila;
	private EstadosJuego estado_juego = EstadosJuego.NINGUNO;//para evitar el envio de paquetes cuando no toca

	/** Buffer paquetes **/
	private Map<Integer, String> buffer_paquetes = new TreeMap<Integer, String>();
	private String ultimo_paquete = "";
	private long tiempo_ultimo_paquete = 0;


	public JuegoSocket(final Socket _socket)
	{
		try
		{
			socket = _socket;
			buffered_reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8), 1);
			outputStream = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			ejecutor = Executors.newCachedThreadPool();
			ejecutor.submit(this);

			if(Configuracion.ACTIVAR_FILA_DE_ESPERA)
				fila = Main.fila_espera.get_Fila();
		}
		catch (final IOException e) 
		{
			cerrar_Conexion();
		}
	}

	public void run() 
	{
		final StringBuilder paquete = new StringBuilder();

		try
		{
			enviar_Paquete("HG");//envia la bienvenida al papasito

			while (paquete.append(buffered_reader.readLine().trim()).toString() != null && !paquete.toString().isEmpty() && (Main.estado_emulador == Estados.ENCENDIDO || Main.estado_emulador == Estados.VINCULANDO) && !ejecutor.isShutdown() && socket.isConnected())
			{
				Consola.println("Recibido-Game: " + paquete.toString());
				controlador_Paquetes(paquete.toString());
				paquete.setLength(0);
			}
		}
		catch (final IOException e)
		{
			cerrar_Conexion();
		}
		finally
		{
			cerrar_Conexion();
		}
	}

	private void controlador_Paquetes(final String paquete)
	{
		if(paquete.length() >= 2)
		{
			if (paquete.charAt(0) != 'A' && personaje == null) 
			{
				enviar_Paquete("BN");
				return;
			}

			GestorPaquetes buscar_paquete = manejar_paquete(paquete);
			if(manejar_paquete(paquete) != null)
			{
				buscar_paquete.analizar(this, paquete);
			}
			else
			{
				Consola.println("paquete desconocido: " + paquete);
				return;
			}
		}
		else
		{
			enviar_Paquete("ATE");
			cerrar_Conexion();
			return;
		}
	}

	public void get_Iniciar_Buffering()
	{
		if(!buffer_paquetes.containsKey(outputStream.hashCode()))
		{
			buffer_paquetes.put(outputStream.hashCode(), "");
		}
	}

	public void get_Detener_Buffering()
	{
		if(buffer_paquetes.containsKey(outputStream.hashCode()))
		{
			enviar_Paquete(buffer_paquetes.remove(outputStream.hashCode()));
		}
	}

	public void enviar_Paquete(final String paquete)
	{
		synchronized (this) 
		{
			if (!socket.isClosed()) 
			{
				if (outputStream != null && !paquete.isEmpty() && !paquete.equals("" + (char)0x00))
				{
					if(buffer_paquetes.containsKey(outputStream.hashCode()))
					{
						final String paquete_bufferizado = buffer_paquetes.get(outputStream.hashCode()) + (char)0x00;
						buffer_paquetes.put(outputStream.hashCode(), paquete_bufferizado + paquete);
					}
					else if(!(paquete.equalsIgnoreCase(ultimo_paquete) && (System.currentTimeMillis() - tiempo_ultimo_paquete) < 500))
					{
						outputStream.print(paquete + (char)0x00);
						outputStream.flush();
						ultimo_paquete = paquete;
						tiempo_ultimo_paquete = System.currentTimeMillis();
						Consola.println("Enviado >> " + paquete);
					}
				}
			}
			else
			{
				cerrar_Conexion();
				return;
			}
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
				if (socket != null && socket.isClosed())
				{
					if(cuenta != null)
					{
						if(cuenta.get_Juego_socket() == this)
							cuenta.set_Juego_socket(null);
						if(Configuracion.ACTIVAR_FILA_DE_ESPERA)
						{
							if(cuenta.get_Fila_espera() && cuenta.get_Nodo_fila() != null)
								fila.set_eliminar_Cuenta(cuenta.get_Nodo_fila());
						}
					}
					socket.close();
				}
				JuegoServer.get_Eliminar_Cliente(this);
				JuegoServer.get_Agregar_Cuenta_Esperando(cuenta);
				cuenta = null;
				personaje = null;
				ejecutor.shutdown();
				ejecutor = null;
			}
			catch (final IOException e)
			{
				Consola.println("Error el kickear a la cuenta (apodo): " + cuenta.get_Apodo() + " causa: " + e.getMessage());
				return;
			}
		}
	}

	private GestorPaquetes manejar_paquete(final String paquete_string)
	{
		GestorPaquetes paquete_buscado = Configuracion.get_Paquetes_Emulador().get(paquete_string);
		int tamano_paquete = paquete_string.length() >= 3 ? 3 : 2;

		if(paquete_buscado == null)
			paquete_buscado = Configuracion.get_Paquetes_Emulador().get(paquete_string);
		if(paquete_buscado == null && tamano_paquete != 2)
			paquete_buscado = Configuracion.get_Paquetes_Emulador().get(paquete_string.substring(0, tamano_paquete - 1));

		return paquete_buscado;
	}

	public Cuentas get_Cuenta()
	{
		return cuenta;
	}

	public void set_Cuenta(final Cuentas _cuenta) 
	{
		cuenta = _cuenta;
	}

	public Personajes get_Personaje() 
	{
		return personaje;
	}

	public void set_Personaje(final Personajes _personaje)
	{
		personaje = _personaje;
	}

	public Socket get_Socket()
	{
		return socket;
	}

	public FilaSocket get_Fila() 
	{
		return fila;
	}

	public EstadosJuego get_Estado_Juego() 
	{
		return estado_juego;
	}

	public void set_Estado_Juego(EstadosJuego _estado_login)
	{
		estado_juego = _estado_login;
	}
}
