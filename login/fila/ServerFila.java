package login.fila;

import login.Enum.EstadosLogin;
import main.Estados;
import main.Main;
import objetos.Cuentas;

public class ServerFila extends Thread implements Runnable
{
	private Fila fila;
	Cuentas cuenta;

	public ServerFila() 
	{
		setName("Fila-Login");
		start();
	}
	
	public void run()
	{
		fila = new Fila();
		while(Main.estado_emulador == Estados.ENCENDIDO && !isInterrupted())
		{
			cuenta = null;
			try
			{
				cuenta = fila.eliminar_Cuenta();
			}
			finally
			{
				
				fila.actualizar_Nuevas_Posiciones();
				cuenta.get_Login_respuesta().set_Estado_login(EstadosLogin.LISTA_SERVIDORES);
			}
		}
	}
	
	public void detener_Fila() 
	{
		if(isAlive())
		{
			interrupt();
			System.out.println("> ServerFila cerrada");
		}
	}
	
	public Fila get_Fila()
	{
		return fila;
	}
}