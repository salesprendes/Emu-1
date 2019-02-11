package objetos.cuentas;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.TreeMap;

final public class Migracion
{
	private final int cuenta_id;
	private final ArrayList<Integer> servidores = new ArrayList<Integer>();//almacena los servidores de cada personaje
	private final Map<Integer, ArrayList<Integer>> personajes = new TreeMap<Integer, ArrayList<Integer>>();
	private StringBuilder lista_personajes;
	final private static Map<Integer, Migracion> migraciones = new TreeMap<Integer, Migracion>();

	public Migracion(int _cuenta_id, String _servidores)
	{
		cuenta_id = _cuenta_id;
		
		for(final String servidor : _servidores.split(Pattern.quote(",")))
		{
			if(!servidores.contains(Integer.parseInt(servidor)))
				servidores.add(Integer.parseInt(servidor));
		}
		
		migraciones.put(cuenta_id, this);
	}
	
	public void get_Agregar(int servidor, String paquete) 
	{
		if(lista_personajes == null) 
			lista_personajes = new StringBuilder();

		lista_personajes.append(paquete);
		servidores.remove((Integer)servidor);

		if(servidores.isEmpty()) 
		{
			Cuentas account = Cuentas.get_Cuenta_Cargada(cuenta_id);
			StringBuilder paquete_am = new StringBuilder("AM");
			get_Paquete_AM();

			paquete_am.append(lista_personajes.toString());
			account.get_Juego_socket().enviar_Paquete(paquete_am.toString());
			lista_personajes = null;
		}
	}
	
	private void get_Paquete_AM() 
	{
		String[] personajes_paquete = lista_personajes.toString().substring(1).split(Pattern.quote("|"));

		for(final String personaje : personajes_paquete)
		{
			if(personaje.contains(";"))
			{
				String[] data = personaje.split(";");
				int servidor = Integer.parseInt(data[9]);
				ArrayList<Integer> array = personajes.get(servidor);
				if(array != null) 
				{
					personajes.get(servidor).add(Integer.parseInt(data[0]));
				} 
				else 
				{
					array = new ArrayList<>();
					array.add(Integer.parseInt(data[0]));
					personajes.put(servidor, array);
				}
			}
		}
	}
	
	public int get_Buscar_Servidor_personaje(final int personaje_id)
	{
		for(final Entry<Integer, ArrayList<Integer>> servidor : personajes.entrySet())
		{
			for(final int personaje_buscado : servidor.getValue())
			{
				if(personaje_buscado == personaje_id)
					return servidor.getKey();
			}
		}
		return -1;
	}
	
	public Map<Integer, ArrayList<Integer>> get_Personajes() 
	{
		return personajes;
	}
	
	public static final Migracion get_Migracion(int migracion_id)
	{
		return migraciones.get(migracion_id);
	}
}
