package login.fila;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import objetos.Cuentas;

public class Fila
{
	private LinkedList<NodoFila> fila;
	private final Lock bloqueo = new ReentrantLock();
	private final Condition condicion = bloqueo.newCondition();
	private int total_abonados = 0, total_no_abonados = 0;
	protected NodoFila nodo;

	public Fila()
	{
		if(fila == null) 
		{
			fila = new LinkedList<NodoFila>();
		}
	}
	
	public void agregar_Cuenta(Cuentas account)
	{
		bloqueo.lock();
		int posicion = 0;
		try
		{
			posicion = size() + 1;
			nodo = new NodoFila(account, posicion);
			if(account.es_Cuenta_Abonada())
			{
				fila.addFirst(nodo);
				total_abonados++;
			}
			else
			{
				fila.addLast(nodo);
				total_no_abonados++;
			}
			account.get_Login_respuesta().enviar_paquete(account.get_Login_respuesta().get_OutputStream(), new StringBuilder("Af").append(posicion).append("|").append(size()).append("|").append(total_no_abonados).append("|1|").append(-1).toString());
			condicion.await(5000, TimeUnit.MILLISECONDS);
			condicion.signal();
		} 
		catch (InterruptedException e) 
		{
			System.out.println("Error fila interrumpida: " + e.getMessage());
			e.printStackTrace();
		}
		finally 
		{
			bloqueo.unlock();
		}
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
			cuenta = fila.removeFirst().get_Cuenta();
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
			System.out.println("Error queue: " + e.getMessage());
			e.printStackTrace();
		} 
		finally 
		{
			bloqueo.unlock();
		}
		return cuenta;
	}

	public int size() 
	{
		bloqueo.lock();
		int contador = fila.size() - 1;
		bloqueo.unlock();
		return contador;
	}

	public int get_Total_Abonados()
	{
		return total_abonados;
	}

	public int get_Total_No_Abonados() 
	{
		return total_no_abonados;
	}
	
	public void agregar_nuevas_posiciones()
	{
		bloqueo.lock();
		for(NodoFila cuenta_esperando : fila) 
		{
			cuenta_esperando.set_Posicion(cuenta_esperando.get_Posicion() - 1);
			cuenta_esperando.get_Cuenta().get_Login_respuesta().enviar_paquete(cuenta_esperando.get_Cuenta().get_Login_respuesta().get_OutputStream(), new StringBuilder("Af").append(cuenta_esperando.get_Posicion()).append("|").append(size()).append("|").append(total_no_abonados).append("|1|").append(-1).toString());
			try 
			{
				condicion.await(1500, TimeUnit.MILLISECONDS);
			} 
			catch (InterruptedException e) 
			{
				System.out.println("Error queue: " + e.getMessage());
				e.printStackTrace();
			}
		}
		bloqueo.unlock();
	}
}
