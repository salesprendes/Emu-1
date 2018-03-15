package main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import database.Servidores_DB;
import objetos.Cuentas;
import objetos.Servidores;

final public class Mundo
{
	private final static Mundo mundo = new Mundo();//crea una nueva instancia
	private final ConcurrentHashMap<Integer, Cuentas> cuentas = new ConcurrentHashMap<Integer, Cuentas>();
	private final ConcurrentMap<Integer, Servidores> servidores = new ConcurrentHashMap<Integer, Servidores>();
	
	public static void cargar_Login()
	{
		System.out.print("> Cargando servidores: ");
		Servidores_DB.cargar_Todos_Servidores();
		System.out.println("ok");
	}
	
	public Map<Integer, Cuentas> get_Cuentas()
	{
		return cuentas;
	}
	
	public void agregar_Cuenta(final Cuentas _cuenta)
	{
		if (!cuentas.containsKey(_cuenta.get_Id()))
		{
			cuentas.put(_cuenta.get_Id(), _cuenta);
		}
	}
	
	public void eliminar_Cuenta(final Cuentas _cuenta)
	{
		if (cuentas.containsKey(_cuenta.get_Id()))
		{
			cuentas.remove(_cuenta.get_Id());
		}
	}
	
	public Map<Integer, Servidores> get_Servidores()
	{
		return servidores;
	}
	
	public void agregar_Servidor(final Servidores _servidor)
	{
		if (!servidores.containsKey(_servidor.get_Id()))
		{
			servidores.put(_servidor.get_Id(), _servidor);
		}
	}

	public static Mundo get_Mundo()
	{
		return mundo;
	}
}
