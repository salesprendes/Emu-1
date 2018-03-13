package main.Consola;

import java.util.Scanner;

final public class Consola extends Thread implements Runnable
{
	public Consola()
	{
		setName("Consola-Comandos");
		System.out.println("======================================================");
		System.out.println("Administración del login del emulador");
		System.out.println("Para visualizar los comandos introducir: ?");
		start();
	}
	
	public void run()
	{
		Scanner sc = new Scanner(System.in);
		while(!isInterrupted())
		{
			System.out.print(">> ");
			String command = sc.nextLine();
			Comandos.ejecutar(command);
		}
		sc.close();
	}
}
