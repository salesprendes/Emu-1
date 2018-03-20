package main;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import objetos.Cuentas;

final public class Mundo
{
	private final static Mundo mundo = new Mundo();//crea una nueva instancia
	public static final String caracteres_prohibidos[] = {"&", "é", "\"", "'", "(", "-", "è", "_", "ç", "à", ")", "=", "~", "#", "{", "[", "|", "`", "^", "@", "]", "}", "°", "+", "^", "$", "ù", "*", ",", ";", ":", "!", "<", ">", "¨", "£", "%", "µ", "?", ".", "/", "§", "\n"};
	private final ConcurrentHashMap<Integer, Cuentas> cuentas = new ConcurrentHashMap<Integer, Cuentas>();
	
	public static void cargar_Login()
	{
		System.out.print("> Cargando servidores: ");
		Main.get_Database().get_Servidores().cargar_Todos_Servidores();
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

	public static Mundo get_Mundo()
	{
		return mundo;
	}
}
