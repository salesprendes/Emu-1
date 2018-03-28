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
		
		estado_emulador = Estados.VINCULANDO;
		socket_vinculador = new Vinculador();
		esta_vinculado = true;
		
		comandos_consola = new Consola();
		estado_emulador = Estados.ENCENDIDO;
	}
	
	public static void volver_Vincular_Login()
	{
		if(!esta_vinculado)
		{
			System.out.println("Intentando vincular con LoginServer");
			while(!esta_vinculado) 
			{
				socket_vinculador = new Vinculador();
			}
			System.out.println("GameServer vinculado con LoginServer");
		}
	}
	
	public static void cerrar_Emulador()
	{
		System.out.println("> El servidor se esta cerrando");
		if (estado_emulador == Estados.ENCENDIDO)
		{
			estado_emulador = Estados.APAGADO;
		}
		System.out.println("> El emulador esta cerrado.");
	}
}
