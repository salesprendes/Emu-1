package main.Consola;

import java.util.Scanner;

final public class Consola extends Thread
{
	private static Scanner sc = new Scanner(System.in);
	
	public Consola()
	{
		System.out.println("======================================================");
		System.out.println("Administración del login del emulador");
		System.out.println("Para visualizar los comandos introducir: ?");
	}
	
	public void run()
	{
		while(!isInterrupted())
		{
			System.out.print(">> ");
			String command = sc.nextLine();
			Comandos.ejecutar(command);
		}
	}
}
