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

	@Override
	public int compareTo(NodoFila comparacion)
	{
		if(cuenta.es_Cuenta_Abonada() && !comparacion.get_Cuenta().es_Cuenta_Abonada())
		{
			return -1;
		}
		else if(!cuenta.es_Cuenta_Abonada() && comparacion.get_Cuenta().es_Cuenta_Abonada())
		{
			return 1;
		}
		return 0;
	}
}