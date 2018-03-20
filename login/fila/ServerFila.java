package login.fila;

import login.Enum.EstadosLogin;
import main.Estados;
import main.Main;
import objetos.Cuentas;
import objetos.Servidores;

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
		final StringBuilder paquete = new StringBuilder("Ad").append(cuenta.get_Apodo()).append((char)0);
		paquete.append("Ac").append(cuenta.get_Comunidad()).append((char)0);
		paquete.append(Servidores.get_Obtener_Servidores()).append((char)0);
		paquete.append("AlK").append(cuenta.get_Rango_cuenta() > 0 ? 1 : 0).append((char)0);
		paquete.append("AQ").append("Ninguna").append((char)0);;
		return paquete.toString();
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