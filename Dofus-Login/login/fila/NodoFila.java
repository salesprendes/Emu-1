package login.fila;

import objetos.Cuentas;

final public class NodoFila implements Comparable<NodoFila>
{
	private Cuentas cuenta;
	private int posicion;

	public NodoFila(Cuentas _cuenta, int _posicion)
	{
		cuenta = _cuenta;
		posicion = _posicion;
	}

	public Cuentas get_Cuenta() 
	{
		return cuenta;
	}

	public void set_Cuenta(Cuentas _cuenta) 
	{
		cuenta = _cuenta;
	}

	public int get_Posicion() 
	{
		return posicion;
	}

	public void set_Posicion(int _posicion)
	{
		posicion = _posicion;
	}

	public int compareTo(NodoFila comparacion)
	{
		if(cuenta.get_Tiempo_Abono() > 0 && !(comparacion.get_Cuenta().get_Tiempo_Abono() > 0))
		{
			return -1;
		}
		else if(!(cuenta.get_Tiempo_Abono() > 0) && comparacion.get_Cuenta().get_Tiempo_Abono() > 0)
		{
			return 1;
		}
		return 0;
	}
}