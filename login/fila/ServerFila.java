package login.fila;

public class ServerFila implements Runnable
{
	private Fila fila;
	private Thread thread;

	public ServerFila() 
	{
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}
	
	public void run()
	{
		fila = new Fila();
		while(true)
		{
			try
			{
				fila.eliminar_Cuenta();
			}
			finally
			{
				
				fila.agregar_nuevas_posiciones();
			}
		}
	}
	
	public Fila queue()
	{
		return fila;
	}
}
