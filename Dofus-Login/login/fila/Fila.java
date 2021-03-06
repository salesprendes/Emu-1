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
				NodoFila nodo = new NodoFila(cuenta, posicion);
				fila.add(nodo);
				cuenta.set_Nodo_fila(nodo);
				if(cuenta.get_Tiempo_Abono() > 0)
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
		NodoFila nodo = null;
		synchronized(fila)
		{
			try
			{
				if(fila.isEmpty())
				{
					fila.wait();
				}
				nodo = fila.peek();
				Cuentas cuenta = nodo.get_Cuenta();
				if(cuenta.get_Tiempo_Abono() > 0)
					total_abonados--;
				else
					total_no_abonados--;
				nodo.get_Cuenta().set_Nodo_fila(null);
			}
			catch (InterruptedException e){}
		}
		return nodo;
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

	public void actualizar_Posiciones()
	{
		synchronized(fila)
		{
			fila.forEach(f -> 
			{
				f.get_Cuenta().get_Login_respuesta().enviar_Paquete(get_Paquete_Fila_Espera(f.get_Posicion(), f.get_Cuenta().get_Tiempo_Abono() > 0));
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
				f.get_Cuenta().get_Login_respuesta().enviar_Paquete(get_Paquete_Fila_Espera(f.get_Posicion(), f.get_Cuenta().get_Tiempo_Abono() > 0));
			});
		}
	}
	
	public PriorityQueue<NodoFila> get_Fila()
	{
		return fila;
	}
}
