package login.fila;

import login.Enum.EstadosLogin;
import main.Estados;
import main.Main;
import main.Mundo;
import objetos.Cuentas;

final public class ServerFila extends Thread implements Runnable
{
	private Fila fila;
	private Cuentas cuenta;

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
				cuenta.get_Login_respuesta().enviar_paquete(paquete_salida_fila());
				cuenta.get_Login_respuesta().set_Estado_login(EstadosLogin.LISTA_SERVIDORES);
				cuenta.set_Fila_espera(false);
				fila.actualizar_Nuevas_Posiciones();
			}
		}
	}
	
	private String paquete_salida_fila()
	{
		final StringBuilder paquete_salida = new StringBuilder("Ad").append(cuenta.get_Apodo()).append((char)0);
		paquete_salida.append("Ac").append(cuenta.get_Comunidad()).append("AH");
		Mundo.get_Mundo().get_Servidores().values().forEach(S ->
		{
			paquete_salida.append(S.get_Id()).append(';').append(0).append(";110;1");
		});
		paquete_salida.append((char)0);
		paquete_salida.append("AlK").append(1).append((char)0);
		paquete_salida.append("AQ").append("Ninguna");
		
		return paquete_salida.toString();
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