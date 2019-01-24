package juego.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoCanales
{
	INFORMACIONES("i"),
	GENERAL("*"),
	PRIVADO("p"),
	EQUIPO_PELEA("#"),
	GRUPO("$"),
	GREMIO("%"),
	ALINEACION("!"),
	RECLUTAMIENTO("?"),
	COMERCIO(":"),
	INCARNAM("^"),
	ADMINISTRADOR("@"),
	MEETIC("¤");
	
	final static private Map<String, TipoCanales> canales = new HashMap<String, TipoCanales>();
	final private String identificador;

	static
	{
		for (TipoCanales tipo : values()) 
		{
			canales.put(tipo.identificador, tipo);
		}
	}
	
	private TipoCanales(String _identificador) 
	{
		identificador = _identificador;
	}
	
	public String get_Identificador() 
	{
        return identificador;
    }
	
	public static Map<String, TipoCanales> get_Canal()
	{
        return canales;
    }
}
