package login.fila;

import login.Enum.EstadosLogin;
import objetos.Cuentas;

public class ServerFila implements Runnable
{
	private Fila fila;
	private Thread thread;
	Cuentas cuenta;

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
			cuenta = null;
			try
			{
				cuenta = fila.eliminar_Cuenta();
			}
			finally
			{
				
				fila.agregar_nuevas_posiciones();
				cuenta.get_Login_respuesta().set_Estado_login(EstadosLogin.LISTA_SERVIDORES);
			}
		}
	}
	
	public Fila get_Fila()
	{
		return fila;
	}
}
