package login.fila;

import login.enums.EstadosLogin;
import main.Estados;
import main.Main;
import objetos.Cuentas;
import objetos.Servidores;

final public class ServerFilaLogin extends Thread implements Runnable
{
	private Fila fila;
	private Cuentas cuenta = null;

	public ServerFilaLogin() 
	{
		setName("Fila-Login");
		fila = new Fila(-1, (byte) 100);
		start();
	}

	public void run()
	{
		while(Main.estado_emulador == Estados.ENCENDIDO && !isInterrupted())
		{
			cuenta = fila.eliminar_Cuenta_Fila_Espera();
			if(cuenta != null)
			{
				cuenta.get_Login_respuesta().enviar_paquete(paquete_salida_fila(cuenta));
				cuenta.get_Login_respuesta().set_Estado_login(EstadosLogin.LISTA_SERVIDORES);
				cuenta.set_Fila_espera(false);
			}
			fila.actualizar_A_Nuevas_Posiciones();
		}
	}

	private String paquete_salida_fila(Cuentas _cuenta)
	{
		final StringBuilder paquete = new StringBuilder("Ad").append(_cuenta.get_Apodo()).append((char)0);
		paquete.append("Ac").append(_cuenta.get_Comunidad()).append((char)0);
		paquete.append(Servidores.get_Obtener_Servidores()).append((char)0);
		paquete.append("AlK").append(_cuenta.get_Rango_cuenta() > 0 ? 1 : 0).append((char)0);
		paquete.append("AQ").append("Ninguna").append((char)0);
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