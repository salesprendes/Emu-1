package login.fila;

import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import objetos.Cuentas;

final public class Fila
{
	private PriorityQueue<NodoFila> fila;
	private final Lock bloqueo = new ReentrantLock();
	private final Condition condicion = bloqueo.newCondition();
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
		bloqueo.lock();
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
			condicion.await(5000, TimeUnit.MILLISECONDS);
			condicion.signal();
		} 
		catch (InterruptedException e) 
		{
			fila.remove(nodo);
			cuenta.get_Login_respuesta().cerrar_Conexion();
		}
		finally 
		{
			bloqueo.unlock();
		}
	}
	
	public void set_eliminar_Cuenta(Cuentas cuenta)
	{
		bloqueo.lock();
		fila.forEach(f -> 
		{
			if(f.get_Cuenta() == cuenta)
			{
				fila.remove(f);
			}
		});
		actualizar_Nuevas_Posiciones();
		bloqueo.unlock();
	}
	
	public Cuentas eliminar_Cuenta()
	{
		bloqueo.lock();
		Cuentas cuenta = null;
		try
		{
			if(fila.isEmpty())
			{
				condicion.await();
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
			condicion.await(3000, TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException e) 
		{
			fila.remove(nodo);
			cuenta.get_Login_respuesta().cerrar_Conexion();
		} 
		finally 
		{
			bloqueo.unlock();
		}
		return cuenta;
	}

	public String get_Paquete_Fila_Espera(int posicion, boolean esta_abonado)
	{
		bloqueo.lock();
		StringBuilder paquete = new StringBuilder("Af").append(posicion);
		if(esta_abonado)
		{
			paquete.append("|").append(fila.size());
		}
		bloqueo.unlock();
		return paquete.append("|").append(esta_abonado ? total_no_abonados : total_abonados).append("|").append(esta_abonado ? 1 : 0).append("|").append(-1).toString();
	}
	
	private void actualizar_Posiciones()
	{
		bloqueo.lock();
		fila.forEach(f -> 
		{
			f.get_Cuenta().get_Login_respuesta().enviar_paquete(get_Paquete_Fila_Espera(f.get_Posicion(), f.get_Cuenta().es_Cuenta_Abonada()));
		});
		bloqueo.unlock();
	}
	
	public void actualizar_Nuevas_Posiciones()
	{
		bloqueo.lock();
		fila.forEach(f ->
		{
			f.set_Posicion(f.get_Posicion() - 1);
			f.get_Cuenta().get_Login_respuesta().enviar_paquete(get_Paquete_Fila_Espera(f.get_Posicion(), f.get_Cuenta().es_Cuenta_Abonada()));
		});
		bloqueo.unlock();
	}
}
