package objetos.entidades.monstruos;

import java.util.HashMap;
import java.util.Map;

public class MonstruosRaza
{
	final private byte id;
	final private MonstruosSuperRaza super_raza;
	
	private static final Map<Byte, MonstruosRaza> monstruos_raza_cargados = new HashMap<Byte, MonstruosRaza>();
	
	public MonstruosRaza(final byte _id, final byte _super_raza)
	{
		id = _id;
		super_raza = MonstruosSuperRaza.get_Super_Razas_Cargadas(_super_raza);
		
		monstruos_raza_cargados.put(id, this);
	}
	
	public byte get_Id()
	{
		return id;
	}

	public MonstruosSuperRaza get_Super_Raza()
	{
		return super_raza;
	}

	public static Map<Byte, MonstruosRaza> get_Razas_Cargadas()
	{
		return monstruos_raza_cargados;
	}
	
	public static MonstruosRaza get_Razas_Cargadas(final byte raza_id)
	{
		return monstruos_raza_cargados.get(raza_id);
	}
}
