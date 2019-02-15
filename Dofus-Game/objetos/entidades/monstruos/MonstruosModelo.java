package objetos.entidades.monstruos;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import main.consola.Consola;
import objetos.entidades.alineamientos.AlineamientosModelo;

public class MonstruosModelo
{
	private final int id, gfx;
	private final MonstruosRaza raza;
	private final AlineamientosModelo alineamiento;
	private final MonstruoGradoModelo[] grados;
	private final String colores;

	public static Map<Integer, MonstruosModelo> monstruos_modelo_cargados = new HashMap<Integer, MonstruosModelo>();

	public MonstruosModelo(final int _id, final int _gfx, final byte _raza, final byte _alineamiento, final Map<Byte, String> _grados, final String experiencias, final String _colores)
	{
		id = _id;
		gfx = _gfx;
		raza = MonstruosRaza.get_Razas_Cargadas(_raza);

		if(AlineamientosModelo.get_Alineamientos_Cargados(_alineamiento) != null)
			alineamiento = AlineamientosModelo.get_Alineamientos_Cargados(_alineamiento);
		else
			alineamiento = AlineamientosModelo.get_Alineamientos_Cargados((byte) -1);

		byte contador_grado = 0;
		grados = new MonstruoGradoModelo[_grados.size()];
		final String[] separador_experiencia = experiencias.split(Pattern.quote("|"));
		int experiencia;
		
		for(String stats_grado : _grados.values())
		{
			try 
			{
				experiencia = Integer.parseInt(separador_experiencia[contador_grado]);
			}
			catch (final Exception e) 
			{
				Consola.println("monstruo: " + id + " tiene error en la experiencia grado: " + (contador_grado + 1));
				experiencia = 0;
			}
			grados[contador_grado] = new MonstruoGradoModelo(contador_grado, stats_grado, experiencia, this);
			contador_grado++;
		}
		colores = _colores;
		
		monstruos_modelo_cargados.put(id, this);
	}

	public int get_Id()
	{
		return id;
	}

	public int get_Gfx()
	{
		return gfx;
	}

	public MonstruosRaza get_Raza()
	{
		return raza;
	}

	public AlineamientosModelo get_Alineamiento()
	{
		return alineamiento;
	}

	public String get_Colores()
	{
		return colores;
	}

	public static Map<Integer, MonstruosModelo> get_Monstruos_Modelo_Cargados()
	{
		return monstruos_modelo_cargados;
	}

	public static MonstruosModelo get_Monstruos_Modelo_Cargados(final int monstro_modelo_id)
	{
		return monstruos_modelo_cargados.get(monstro_modelo_id);
	}
}
