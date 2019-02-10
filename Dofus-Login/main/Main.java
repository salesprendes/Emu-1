package main;

import database.ConexionPool;
import login.LoginServer;
import login.comunicador.ComunicadorServer;
import login.fila.FilaServer;
import main.consola.Consola;
import objetos.Comunidades;
import objetos.Cuentas;
import objetos.Servidores;

final public class Main 
{
	public static boolean modo_debug = false;
	public static Estados estado_emulador = Estados.APAGADO;

	/** THREADS **/
	public static LoginServer servidor_login;
	public static ComunicadorServer servidor_comunicador;
	public static FilaServer fila_espera_login;
	public static Consola comandos_consola;
	private static ConexionPool database = new ConexionPool();
	
	public static void main(String[] args)
	{
		Runtime.getRuntime().addShutdownHook(new Thread(() -> cerrar_Emulador()));
		
		Consola.print("Cargando la configuración: ");
		if(Configuracion.cargar_Configuracion())
			Consola.println("correcta");

		/** Conexion a la database **/
		database = new ConexionPool();

		estado_emulador = Estados.CARGANDO;
		get_Cargar_Login();
		Configuracion.cargar_Paquetes();
		
		/** Threads **/
		servidor_login = new LoginServer();
		servidor_comunicador = new ComunicadorServer();
		fila_espera_login = new FilaServer();
		comandos_consola = new Consola();
		estado_emulador = Estados.ENCENDIDO;
	}

	public static void get_Cargar_Login()
	{
		Consola.print("Cargando comunidades: ");
		database.get_Comunidades().cargar_Todas_Comunidades();
		Consola.println(Comunidades.get_Comunidades().size() + " comunidades cargadas");
		
		Consola.print("Cargando servidores: ");
		database.get_Servidores().cargar_Todos_Servidores();
		Consola.println(Servidores.get_Servidores().size() + " servidores cargados");

		Consola.print("Cargando cuentas: ");
		database.get_Cuentas().get_Cargar_Todas_Cuentas();
		Consola.println(Cuentas.get_Cuentas_Cargadas().size() + " cuentas cargadas");
	}

	public static void cerrar_Emulador()
	{
		Consola.println("> El servidor se esta cerrando");
		if (estado_emulador == Estados.ENCENDIDO)
		{
			if(servidor_comunicador != null)
				servidor_comunicador.detener_Server_Socket();
			if(servidor_login != null)
				servidor_login.detener_Server_Socket();
			if(fila_espera_login != null)
				fila_espera_login.detener_Fila();
			if(comandos_consola != null)
				comandos_consola.interrupt();
			estado_emulador = Estados.APAGADO;
		}
		Consola.println("> El emulador esta cerrado.");
		System.exit(1);
	}

	public static ConexionPool get_Database()
	{
		return database;
	}
}