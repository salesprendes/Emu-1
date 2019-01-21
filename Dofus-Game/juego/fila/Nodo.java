package juego.fila;

import objetos.cuentas.Cuentas;

final public class Nodo implements Comparable<Nodo>
{
	private Cuentas cuenta;
	private int posicion;

	public Nodo(Cuentas _cuenta, int _posicion)
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

	public int compareTo(Nodo comparacion)
	{
		Cuentas cuenta_comparacion = comparacion.get_Cuenta();
		
		if(cuenta.es_Cuenta_Abonada(cuenta.get_Id()) && !cuenta_comparacion.es_Cuenta_Abonada(cuenta_comparacion.get_Id()))
		{
			return -1;
		}
		else if(!cuenta.es_Cuenta_Abonada(cuenta.get_Id()) && cuenta_comparacion.es_Cuenta_Abonada(cuenta_comparacion.get_Id()))
		{
			return 1;
		}
		return 0;
	}
}