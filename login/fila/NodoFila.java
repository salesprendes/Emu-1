package login.fila;

import objetos.Cuentas;

public class NodoFila 
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
}