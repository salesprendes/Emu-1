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
			fila = new PriorityQueue<NodoFila>(50);
		}
	}

	public void agregar_Cuenta(Cuentas cuenta)
	{
		synchronized(fila)
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
			if(fila.isEmpty())
				fila.notify();
			fila.add(new NodoFila(cuenta, posicion));
			actualizar_Posiciones();
		}
	}

	public void set_eliminar_Cuenta(NodoFila nodo_cuenta)
	{
		synchronized(fila)
		{
			fila.remove(nodo_cuenta);
			actualizar_Nuevas_Posiciones();
		}
	}

	public NodoFila seleccion_Eliminar_Cuenta()
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
				nodo = fila.peek();
				cuenta = nodo.get_Cuenta();
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
		return nodo;
	}

	private String get_Paquete_Fila_Espera(int posicion, boolean esta_abonado)
	{
		final StringBuilder paquete = new StringBuilder("Af");
		paquete.append(posicion);
		if(esta_abonado)
		{
			paquete.append("|").append(fila.size());
		}
		return paquete.append("|").append(esta_abonado ? total_no_abonados : total_abonados).append("|").append(esta_abonado ? 1 : 0).append("|").append(-1).toString();
	}

	private void actualizar_Posiciones()
	{
		fila.forEach(f -> 
		{
			f.get_Cuenta().get_Login_respuesta().enviar_paquete(get_Paquete_Fila_Espera(f.get_Posicion(), f.get_Cuenta().es_Cuenta_Abonada()));
		});
	}

	public void actualizar_Nuevas_Posiciones()
	{
		fila.forEach(f ->
		{
			f.set_Posicion(f.get_Posicion() - 1);
			f.get_Cuenta().get_Login_respuesta().enviar_paquete(get_Paquete_Fila_Espera(f.get_Posicion(), f.get_Cuenta().es_Cuenta_Abonada()));
		});
	}
	
	public PriorityQueue<NodoFila> get_Fila()
	{
		return fila;
	}
}
