package juego.comunicador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

final public class Vinculador implements Runnable
{
	private Socket socket;
	private BufferedReader inputStreamReader;
	private PrintWriter outputStream;
	private ExecutorService ejecutor;

	public Vinculador() 
	{
		try 
		{
			socket = new Socket("localhost", 489);
			socket.setReceiveBufferSize(1048);
			inputStreamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputStream = new PrintWriter(socket.getOutputStream());
			ejecutor = Executors.newCachedThreadPool();
			ejecutor.submit(this);
		}
		catch (final Exception e) 
		{
			System.out.println("Error: No se puede vincular el LoginServer y GameServer.");
		}
	}

	public void run() 
	{
	}
}
