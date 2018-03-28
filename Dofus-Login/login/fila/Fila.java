package login.fila;

import java.util.PriorityQueue;

import main.Configuracion;
import objetos.Cuentas;

final public class Fila
{
	private PriorityQueue<NodoFila> fila;
	private int total_abonados = 0, total_no_abonados = 0, servidor_id;

	public Fila(int _servidor_id)
	{
		if(fila == null) 
		{
			fila = new PriorityQueue<NodoFila>();
			servidor_id = _servidor_id;
		}
	}

	public void agregar_Cuenta(Cuentas cuenta)
	{
		synchronized(fila)
		{
			if(Configuracion.MAXIMOS_LOGINS_FILA_ESPERA >= fila.size())
			{
				int posicion = fila.size() + 1;
				fila.add(new NodoFila(cuenta, posicion));
				if(cuenta.es_Cuenta_Abonada())
				{
					total_abonados++;
				}
				else
				{
					total_no_abonados++;
				}
				actualizar_Posiciones();
				if(fila.size() < 2)
					fila.notify();
			}
			else
			{
				cuenta.get_Login_respuesta().enviar_Paquete("M116");
				cuenta.get_Login_respuesta().enviar_Paquete("ATE");
				cuenta.get_Login_respuesta().cerrar_Conexion();
			}
		}
	}

	public void set_eliminar_Cuenta(NodoFila nodo_cuenta)
	{
		synchronized(fila)
		{
			fila.remove(nodo_cuenta);
			actualizar_A_Nuevas_Posiciones();
		}
	}

	public NodoFila eliminar_Cuenta_Fila_Espera()
	{
		NodoFila nodo_cuenta = null;
		synchronized(fila)
		{
			try
			{
				if(fila.isEmpty())
				{
					fila.wait();
				}
				nodo_cuenta = fila.peek();
				if(nodo_cuenta.get_Cuenta().es_Cuenta_Abonada())
				{
					total_abonados--;
				}
				else
				{
					total_no_abonados--;
				}
			}
			catch (InterruptedException e){}
		}
		return nodo_cuenta;
	}

	private String get_Paquete_Fila_Espera(int posicion, boolean esta_abonado)
	{
		synchronized(fila)
		{
			final StringBuilder paquete = new StringBuilder("Af");
			paquete.append(posicion);
			if(esta_abonado)
			{
				paquete.append("|").append(fila.size());
			}
			return paquete.append("|").append(esta_abonado ? total_no_abonados : total_abonados).append("|").append(esta_abonado ? 1 : 0).append("|").append(servidor_id).toString();
		}
	}

	private void actualizar_Posiciones()
	{
		synchronized(fila)
		{
			fila.forEach(f -> 
			{
				f.get_Cuenta().get_Login_respuesta().enviar_Paquete(get_Paquete_Fila_Espera(f.get_Posicion(), f.get_Cuenta().es_Cuenta_Abonada()));
			});
		}
	}

	public void actualizar_A_Nuevas_Posiciones()
	{
		synchronized(fila)
		{
			fila.forEach(f ->
			{
				f.set_Posicion(f.get_Posicion() - 1);
				f.get_Cuenta().get_Login_respuesta().enviar_Paquete(get_Paquete_Fila_Espera(f.get_Posicion(), f.get_Cuenta().es_Cuenta_Abonada()));
			});
		}
	}
	
	public PriorityQueue<NodoFila> get_Fila()
	{
		return fila;
	}
}
