package login.fila;

import login.Enum.EstadosLogin;
import main.Estados;
import main.Main;
import objetos.Cuentas;
import objetos.Servidores;

final public class ServerFila extends Thread implements Runnable
{
	private Fila fila;
	private NodoFila nodo_cuenta = null;

	public ServerFila() 
	{
		setName("Fila-Login");
		fila = new Fila();
		start();
	}
	
	public void run()
	{
		while(Main.estado_emulador == Estados.ENCENDIDO && !isInterrupted())
		{
			synchronized(fila)
			{
				try 
				{
					nodo_cuenta = fila.seleccion_Eliminar_Cuenta();
					fila.wait(5000);
					fila.get_Fila().remove(nodo_cuenta);
					nodo_cuenta.get_Cuenta().get_Login_respuesta().enviar_paquete(paquete_salida_fila(nodo_cuenta.get_Cuenta()));
					nodo_cuenta.get_Cuenta().get_Login_respuesta().set_Estado_login(EstadosLogin.LISTA_SERVIDORES);
					nodo_cuenta.get_Cuenta().set_Fila_espera(false);
					fila.actualizar_Nuevas_Posiciones();
				} 
				catch (InterruptedException e) 
				{
					fila.set_eliminar_Cuenta(nodo_cuenta);
				}
			}
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