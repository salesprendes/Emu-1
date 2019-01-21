package main.consola;

import java.util.Scanner;

final public class Consola extends Thread implements Runnable
{
	public Consola()
	{
		println("======================================================");
		println("Administración del login del emulador");
		println("Para visualizar los comandos introducir: ?");
		
		setName("Consola-Comandos");
		start();
	}
	
	public void run()
	{
		Scanner sc = new Scanner(System.in);
		while(!isInterrupted())
		{
			print("");
			String comando = sc.nextLine();
			Comandos.ejecutar(comando);
		}
		sc.close();
	}
	
	public static void print(final String mensaje)
	{
		synchronized(System.out) 
		{
			System.out.print(">> " + mensaje);
		}
	}
	
	public static void println(final String mensaje)
	{
		synchronized(System.out) 
		{
			System.out.println(mensaje);
		}
	}
}
