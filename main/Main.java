package main;

import database.ConexionPool;
import login.ServerSocketLogin;
import login.comunicador.ServerSocketComunicador;
import login.fila.ServerFilaLogin;
import main.consola.Consola;
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
		System.out.print("> Cargando la configuración: ");
		if(Configuracion.cargar_Configuracion())
			System.out.println("correcta");
		else
			System.out.println("incorrecta");
		
		System.out.print("> Conectando a la base de datos: ");
		database.cargar_Configuracion();
		if(database.comprobar_conexion(database.get_Data_Source()))
		{
			database.iniciar_Database();
			System.out.println("correcta");
		}
		else
		{
			System.out.println("incorrecta");
			System.exit(1);
		}
		
		estado_emulador = Estados.CARGANDO;
		cargar_Login();
		estado_emulador = Estados.ENCENDIDO;
		
		/** Threads **/
		servidor_login = new ServerSocketLogin(443);
		servidor_comunicador = new ServerSocketComunicador(489);
		fila_espera_login = new ServerFilaLogin();
		comandos_consola = new Consola();
	}
	
	public static void cargar_Login()
	{
		System.out.print("> Cargando servidores: ");
		database.get_Servidores().cargar_Todos_Servidores();
		System.out.println(Servidores.get_Servidores().size() + " servidores cargados");
	}
	
	public static void cerrar_Emulador()
	{
		System.out.println("> El servidor se esta cerrando");
		if (estado_emulador == Estados.ENCENDIDO)
		{
			estado_emulador = Estados.APAGADO;
			servidor_comunicador.detener_Server_Socket();
			servidor_login.detener_Server_Socket();
			fila_espera_login.detener_Fila();
			comandos_consola.interrupt();
		}
		System.out.println("> El emulador esta cerrado.");
	}
	
	public static ConexionPool get_Database()
	{
		return database;
	}
}