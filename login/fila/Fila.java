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
			posicion = get_size_fila() + 1;
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
			account.get_Login_respuesta().enviar_paquete(account.get_Login_respuesta().get_OutputStream(), get_Paquete_Fila_Espera(posicion, account.es_Cuenta_Abonada()));
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
			System.out.println("Error fila interrumpida: " + e.getMessage());
			e.printStackTrace();
		} 
		finally 
		{
			bloqueo.unlock();
		}
		return cuenta;
	}

	public int get_size_fila() 
	{
		bloqueo.lock();
		int contador = fila.size() - 1;
		bloqueo.unlock();
		return contador;
	}
	
	private String get_Paquete_Fila_Espera(int posicion, boolean esta_abonado)
	{
		bloqueo.lock();
		StringBuilder paquete = new StringBuilder("Af").append(posicion);
		if(esta_abonado)
		{
			paquete.append("|").append(get_size_fila());
		}
		bloqueo.unlock();
		return paquete.append("|").append(esta_abonado ? total_no_abonados : total_abonados).append("|").append(esta_abonado ? 1 : 0).append("|").append(-1).toString();
	}
	
	public void agregar_nuevas_posiciones()
	{
		bloqueo.lock();
		for(NodoFila cuenta_esperando : fila) 
		{
			cuenta_esperando.set_Posicion(cuenta_esperando.get_Posicion() - 1);
			cuenta_esperando.get_Cuenta().get_Login_respuesta().enviar_paquete(cuenta_esperando.get_Cuenta().get_Login_respuesta().get_OutputStream(), get_Paquete_Fila_Espera(cuenta_esperando.get_Posicion(), cuenta_esperando.get_Cuenta().es_Cuenta_Abonada()));
			try 
			{
				condicion.await(1500, TimeUnit.MILLISECONDS);
			} 
			catch (InterruptedException e) 
			{
				System.out.println("Error fila interrumpida: " + e.getMessage());
				e.printStackTrace();
			}
		}
		bloqueo.unlock();
	}
}
