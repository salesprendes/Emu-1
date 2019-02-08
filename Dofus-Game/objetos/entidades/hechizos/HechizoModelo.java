package objetos.entidades.hechizos;

import java.util.HashMap;
import java.util.Map;

public class HechizoModelo
{
	final private int id, sprite;
	final private String sprite_valores;
	final private HechizoStats[] niveles;
	final private int[] afectados;
	
	private static Map<Integer, HechizoModelo> hechizos_cargados = new HashMap<Integer, HechizoModelo>();

	public HechizoModelo(int _id, final int _sprite, String _sprite_valores, HechizoStats[] _niveles, int[] _afectados) 
	{
		id = _id;
		sprite = _sprite;
		sprite_valores = _sprite_valores;
		niveles = _niveles;
		afectados = _afectados;
	}

	public int get_Id()
	{
		return id;
	}

	public int get_Sprite()
	{
		return sprite;
	}

	public String get_Sprite_valores()
	{
		return sprite_valores;
	}

	public HechizoStats[] get_Nivel()
	{
		return niveles;
	}

	public int[] get_Afectados()
	{
		return afectados;
	}
	
	public static Map<Integer, HechizoModelo> get_hechizos_Cargados()
	{
		return hechizos_cargados;
	}

	public static HechizoModelo get_Hechizo_Cargado(int personaje_id) 
	{
		return hechizos_cargados.get(personaje_id);
	}
}
