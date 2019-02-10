package juego.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public enum TipoEfectoArea
{
	JUGADOR('P'),
    CIRCULO('C'),
    DIAGONALES('D'),
    LINEA('L'),
    CRUZ('X'),
    LINEA_PERPENDICULAR('T'),
    RECTANGULO('R'),
    ANILLO('O');
	
	final private char c;
	
	final static private Map<Character, TipoEfectoArea> tipo_efecto_area = new HashMap<Character, TipoEfectoArea>();

    static 
    {
        for (TipoEfectoArea type : values()) 
        {
        	tipo_efecto_area.put(type.c, type);
        }
    }

    private TipoEfectoArea(final char _c) 
    {
    	c = _c;
    }

    public char get_Char() 
    {
        return c;
    }
    
    static public TipoEfectoArea get_Tipo_Efecto_Area(char c) 
    {
        if (!tipo_efecto_area.containsKey(c)) 
        {
            throw new NoSuchElementException("Efecto de area no existe: " + c);
        }

        return tipo_efecto_area.get(c);
    }
}
