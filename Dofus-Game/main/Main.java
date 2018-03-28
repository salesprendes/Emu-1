package main;

import juego.comunicador.Vinculador;
import main.consola.Consola;

public class Main 
{
	public static boolean modo_debug = false;
	public static boolean esta_vinculado = false;
	public static Estados estado_emulador = Estados.APAGADO;
	
	/** THREADS **/
	public static Vinculador socket_vinculador;
	public static Consola comandos_consola;
	
	public static void main(String[] args) 
	{
		Runtime.getRuntime().addShutdownHook(new Thread(() -> cerrar_Emulador()));
		
		estado_emulador = Estados.CARGANDO;
		System.out.print("> Cargando la configuración: ");
		if(Configuracion.cargar_Configuracion())
			System.out.println("correcta");
		else
			System.out.println("incorrecta");
		
		estado_emulador = Estados.VINCULANDO;
		Vincular_Login();
		
		comandos_consola = new Consola();
		estado_emulador = Estados.ENCENDIDO;
	}
	
	public static void Vincular_Login()
	{
		if(!esta_vinculado)
		{
			System.out.println("Intentando vincular con LoginServer");
			while(!esta_vinculado && estado_emulador == Estados.VINCULANDO)
			{
				socket_vinculador = new Vinculador();
			}
			if(esta_vinculado)
				System.out.println("LoginServer y GameServer vinculados.");
		}
	}
	
	public static void cerrar_Emulador()
	{
		System.out.println("> El servidor se esta cerrando");
		if (estado_emulador == Estados.ENCENDIDO)
		{
			socket_vinculador.cerrar_Conexion();
			estado_emulador = Estados.APAGADO;
		}
		System.out.println("> El emulador esta cerrado.");
		System.exit(1);
	}
}
