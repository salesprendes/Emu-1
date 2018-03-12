package main;

import database.DatabaseManager;
import login.ServerSocketLogin;
import login.fila.ServerFila;
import main.Consola.Consola;

public class Main 
{
	/** Estado login **/
	private static Estados estado_emulador = Estados.APAGADO;
	
	/** THREADS **/
	private static ServerSocketLogin servidor_login;
	private static ServerFila fila_espera_login;
	private static Consola comandos_consola;

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
		
		comandos_consola = new Consola();
		comandos_consola.start();
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
			fila_espera_login.detener_Fila();
			comandos_consola.interrupt();
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
	
	public static Consola get_Comandos_consola() 
	{
		return comandos_consola;
	}
}