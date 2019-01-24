package objetos.entidades.monstruos;

import java.util.HashMap;
import java.util.Map;

public class MonstruosSuperRaza
{
	final byte id;
	
	private static final Map<Byte, MonstruosSuperRaza> monstruos_super_raza_cargados = new HashMap<Byte, MonstruosSuperRaza>();
	
	public MonstruosSuperRaza(final byte _id)
	{
		id = _id;
		
		monstruos_super_raza_cargados.put(id, this);
	}
	
	public static Map<Byte, MonstruosSuperRaza> get_Super_Razas_Cargadas()
	{
		return monstruos_super_raza_cargados;
	}
	
	public static MonstruosSuperRaza get_Super_Razas_Cargadas(final byte super_raza_id)
	{
		return monstruos_super_raza_cargados.get(super_raza_id);
	}
}
