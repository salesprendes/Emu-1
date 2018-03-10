package main;

import database.DatabaseManager;
import login.ServerSocketLogin;
import login.fila.ServerFila;

public class Main 
{
	/** Estado login **/
	private static Estados estado_emulador = Estados.APAGADO;
	
	/** THREADS **/
	private static ServerSocketLogin servidor_login;
	private static ServerFila fila_espera;

	static 
	{
		Runtime.getRuntime().addShutdownHook(new Thread(() -> cerrar_Emulador()));
	}

	public static void main(String[] args)
	{
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
		fila_espera = new ServerFila();
		new Thread(new TimerWaiter().tiempo(() -> System.out.println(""), 30000, 30000)).start();
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

	public static ServerFila get_Fila_Espera()
	{
		return fila_espera;
	}
}