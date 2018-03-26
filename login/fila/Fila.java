package login.fila;

import java.util.PriorityQueue;

import objetos.Cuentas;

final public class Fila
{
	private PriorityQueue<NodoFila> fila;
	private int total_abonados = 0, total_no_abonados = 0, servidor_id;

	public Fila(int _servidor_id, byte _maximos_logins)
	{
		if(fila == null) 
		{
			fila = new PriorityQueue<NodoFila>(_maximos_logins);
			servidor_id = _servidor_id;
		}
	}

	public void agregar_Cuenta(Cuentas cuenta)
	{
		synchronized(fila)
		{
			int posicion = fila.size() + 1;
			if(fila.isEmpty())
				fila.notify();
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

	public Cuentas eliminar_Cuenta_Fila_Espera()
	{
		Cuentas cuenta = null;
		synchronized(fila)
		{
			try
			{
				if(fila.isEmpty())
				{
					fila.wait();
					
				}
				cuenta = fila.remove().get_Cuenta();
				if(cuenta.es_Cuenta_Abonada())
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
		return cuenta;
	}

	private String get_Paquete_Fila_Espera(int posicion, boolean esta_abonado)
	{
		final StringBuilder paquete = new StringBuilder("Af");
		synchronized(fila)
		{
			paquete.append(posicion);
			if(esta_abonado)
			{
				paquete.append("|").append(fila.size());
			}
		}
		return paquete.append("|").append(esta_abonado ? total_no_abonados : total_abonados).append("|").append(esta_abonado ? 1 : 0).append("|").append(servidor_id).toString();
	}

	private void actualizar_Posiciones()
	{
		synchronized(fila)
		{
			fila.forEach(f -> 
			{
				f.get_Cuenta().get_Login_respuesta().enviar_paquete(get_Paquete_Fila_Espera(f.get_Posicion(), f.get_Cuenta().es_Cuenta_Abonada()));
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
				f.get_Cuenta().get_Login_respuesta().enviar_paquete(get_Paquete_Fila_Espera(f.get_Posicion(), f.get_Cuenta().es_Cuenta_Abonada()));
			});
		}
	}
	
}
