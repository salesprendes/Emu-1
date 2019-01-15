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

	public static Map<Integer, Especialidades> ESPECIALIDADES = new TreeMap<Integer, Especialidades>();
	
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
					int donID = Integer.parseInt(args[0]);
					int donNivel = Integer.parseInt(args[1]);
					//int donStat = Mundo.getDonStat(donID);
					int valor = 0;
					if (args.length > 2)
						valor = Integer.parseInt(args[2]);

					//dones.add(new Dones(donID, donNivel, donStat, valor));
				} 
				catch (Exception e) {}
			}
		}
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
}
