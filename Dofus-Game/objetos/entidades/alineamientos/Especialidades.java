package objetos.entidades.alineamientos;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Especialidades
{
	private final int id;
	private final byte orden, nivel;
	private final ArrayList<Dones> dones = new ArrayList<Dones>();

	private static Map<Integer, Especialidades> especialidades_cargadas = new TreeMap<Integer, Especialidades>();
	
	public Especialidades(final int _id, final byte _orden, final byte _nivel, final String _dones) 
	{
		id = _id;
		orden = _orden;
		nivel = _nivel;
		for (String s : _dones.split(Pattern.quote("|"))) 
		{
			if (!s.isEmpty())
			{
				try 
				{
					String[] args = s.split(",");
					byte don_id = Byte.parseByte(args[0]);
					short don_nivel = Short.parseShort(args[1]);
					short don_stat = Dones.get_Don_Stat(don_id);
					int valor = 0;
					if (args.length > 2)
						valor = Integer.parseInt(args[2]);

					dones.add(new Dones(don_id, don_nivel, don_stat, valor));
				} 
				catch (Exception e) {}
			}
		}
		especialidades_cargadas.put(id, this);
	}
	
	public int get_Id()
	{
		return id;
	}
	
	public int get_Orden()
	{
		return orden;
	}
	
	public int get_Nivel()
	{
		return nivel;
	}
	
	public ArrayList<Dones> get_Dones()
	{
		return dones;
	}
	
	public static Map<Integer, Especialidades> get_Especialidades()
	{
		return especialidades_cargadas;
	}

	public static Especialidades get_Especialidad(int especialidad) 
	{
		return especialidades_cargadas.get(especialidad);
	}
	
	public static Especialidades get_Especialidad(final int orden, final int nivel) 
	{
		Especialidades esp = null;
		for (Especialidades e : especialidades_cargadas.values()) 
		{
			if (e.get_Orden() == orden) 
			{
				if (esp == null || e.get_Nivel() <= nivel && e.get_Nivel() > esp.get_Nivel()) 
				{
					esp = e;
				}
			}
		}
		return esp;
	}
}
