package main;

import database.ConexionPool;
import login.ServerSocketLogin;
import login.comunicador.ServerSocketComunicador;
import login.fila.ServerFilaLogin;
import main.consola.Consola;
import objetos.Comunidades;
import objetos.Servidores;

final public class Main 
{
	public static boolean modo_debug = false;
	public static Estados estado_emulador = Estados.APAGADO;

	/** THREADS **/
	public static ServerSocketLogin servidor_login;
	public static ServerSocketComunicador servidor_comunicador;
	public static ServerFilaLogin fila_espera_login;
	public static Consola comandos_consola;
	private static ConexionPool database = new ConexionPool();


	public static void main(String[] args)
	{
		Runtime.getRuntime().addShutdownHook(new Thread(() -> cerrar_Emulador()));
		Consola.print("Cargando la configuración: ");
		if(Configuracion.cargar_Configuracion())
			Consola.println("correcta");
		else
			Consola.println("incorrecta");

		Consola.print("Conectando a la base de datos: ");
		database.cargar_Configuracion();
		if(database.comprobar_conexion(database.get_Data_Source()))
		{
			database.iniciar_Database();
			Consola.println("correcta");
		}
		else
		{
			Consola.println("incorrecta");
		}

		estado_emulador = Estados.CARGANDO;
		cargar_Login();
		estado_emulador = Estados.ENCENDIDO;

		/** Threads **/
		servidor_login = new ServerSocketLogin();
		servidor_comunicador = new ServerSocketComunicador();
		fila_espera_login = new ServerFilaLogin();
		comandos_consola = new Consola();
	}

	public static void cargar_Login()
	{
		Consola.print("Cargando servidores: ");
		database.get_Servidores().cargar_Todos_Servidores();
		Consola.println(Servidores.servidores.size() + " servidores cargados");

		Consola.print("Cargando comunidades: ");
		database.get_Comunidades().cargar_Todas_Comunidades();
		Consola.println(Comunidades.get_Comunidades().size() + " comunidades cargadas");
	}

	public static void cerrar_Emulador()
	{
		Consola.println("> El servidor se esta cerrando");
		if (estado_emulador == Estados.ENCENDIDO)
		{
			estado_emulador = Estados.APAGADO;
			servidor_comunicador.detener_Server_Socket();
			servidor_login.detener_Server_Socket();
			fila_espera_login.detener_Fila();
			comandos_consola.interrupt();
		}
		Consola.println("> El emulador esta cerrado.");
		System.exit(1);
	}

	public static ConexionPool get_Database()
	{
		return database;
	}
}