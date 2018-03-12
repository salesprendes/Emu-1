package main;

import database.DatabaseManager;
import login.ServerSocketLogin;
import login.fila.ServerFila;
import objetos.Cuentas;

public class Main 
{
	/** Estado login **/
	private static Estados estado_emulador = Estados.APAGADO;
	
	/** THREADS **/
	private static ServerSocketLogin servidor_login;
	private static ServerFila fila_espera_login;
	private static ServerFila fila_espera_juego;

	public static void main(String[] args)
	{
		Runtime.getRuntime().addShutdownHook(new Thread(() -> cerrar_Emulador()));
		System.out.print("> Conectando a la base de datos: ");
		if(DatabaseManager.ejecutar_Conexion())
		{
			System.out.println("correcta");
		}
		else
		{
			System.out.println("incorrecta");
			System.exit(1);
			return;
		}
		estado_emulador = Estados.CARGANDO;
		Mundo.cargar_Login();
		estado_emulador = Estados.ENCENDIDO;
		servidor_login = new ServerSocketLogin(443);
		
		/** Filas de espera **/
		fila_espera_login = new ServerFila();
		fila_espera_juego = new ServerFila();
		
		new Thread(new TimerWaiter().tiempo(() -> 
		{
			for(Cuentas cuenta : Mundo.get_Mundo().get_Cuentas().values())
			{
				System.out.println("expulsando al jugador: " + cuenta.get_Apodo());
				cuenta.get_Login_respuesta().enviar_paquete("M01|");
				cuenta.get_Login_respuesta().enviar_paquete("ATE");
				cuenta.get_Login_respuesta().cerrar_Conexion();
			}
		}
		, 0, 1200000)).start();
	}

	public static Estados get_Estado_emulador() 
	{
		return estado_emulador;
	}

	public static void cerrar_Emulador()
	{
		System.out.println("> El servidor se esta cerrando");
		if (estado_emulador == Estados.ENCENDIDO)
		{
			estado_emulador = Estados.APAGADO;
			servidor_login.detener_Server_Socket();
		}
		System.out.println("> El emulador esta cerrado.");
	}

	public static ServerSocketLogin server_Socket_Login()
	{
		return servidor_login;
	}

	public static ServerFila get_Fila_Espera_Login()
	{
		return fila_espera_login;
	}
	
	public static ServerFila get_Fila_Espera_Juego()
	{
		return fila_espera_juego;
	}
}