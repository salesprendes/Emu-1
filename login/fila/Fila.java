package login.fila;

import java.util.PriorityQueue;

import objetos.Cuentas;

final public class Fila
{
	private PriorityQueue<NodoFila> fila;
	private int total_abonados = 0, total_no_abonados = 0;
	private NodoFila nodo;
	
	public Fila()
	{
		if(fila == null) 
		{
			fila = new PriorityQueue<NodoFila>();
		}
	}
	
	public void agregar_Cuenta(Cuentas cuenta)
	{
		synchronized(fila)
		{
			try
			{
				int posicion = fila.size() + 1;
				if(cuenta.es_Cuenta_Abonada())
				{
					total_abonados++;
				}
				else
				{
					total_no_abonados++;
				}
				nodo = new NodoFila(cuenta, posicion);
				fila.add(nodo);
				actualizar_Posiciones();
				fila.wait(5000);
				fila.notify();
			}
			catch (InterruptedException e) 
			{
				fila.remove(nodo);
				cuenta.get_Login_respuesta().cerrar_Conexion();
			}
		}
	}
	
	public void set_eliminar_Cuenta(Cuentas cuenta)
	{
		synchronized(fila)
		{
			fila.forEach(f -> 
			{
				if(f.get_Cuenta() == cuenta)
				{
					fila.remove(f);
				}
			});
			actualizar_Nuevas_Posiciones();
		}
	}
	
	public Cuentas eliminar_Cuenta()
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
				nodo = fila.remove();
				cuenta = nodo.get_Cuenta();
				if(cuenta.es_Cuenta_Abonada())
				{
					total_abonados--;
				}
				else
				{
					total_no_abonados--;
				}
				fila.wait(5000);
			}
			catch (InterruptedException e) 
			{
				fila.remove(nodo);
				cuenta.get_Login_respuesta().cerrar_Conexion();
			}
		}
		return cuenta;
	}

	public String get_Paquete_Fila_Espera(int posicion, boolean esta_abonado)
	{
		final StringBuilder paquete = new StringBuilder("Af");
		paquete.append(posicion);
		synchronized(fila)
		{
			if(esta_abonado)
			{
				paquete.append("|").append(fila.size());
			}
		}
		return paquete.append("|").append(esta_abonado ? total_no_abonados : total_abonados).append("|").append(esta_abonado ? 1 : 0).append("|").append(-1).toString();
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
	
	public void actualizar_Nuevas_Posiciones()
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
