package objetos.entidades.hechizos;

import java.util.Objects;

public class HechizoRango
{
	final private byte minimo, maximo;

    public HechizoRango(final byte _min, final byte _max) 
    {
        minimo = _min;
        maximo = _max;
    }

    public int get_Minimo()
    {
        return minimo;
    }

    public int get_Maximo()
    {
        return maximo;
    }
    
    public boolean get_Comprobar_Rango(final int rango) 
    {
        return rango >= minimo && rango <= maximo;
    }
    
    public HechizoRango get_Rango_Modificado(byte rango)
    {
        if (rango == 0 || (minimo == maximo && rango < 0))
            return this;

        final byte rango_nuevo = (byte) (maximo + rango);

        return new HechizoRango(minimo, rango_nuevo < minimo ? minimo : rango_nuevo);
    }
    
    public String toString() 
    {
        return "[" + minimo + ", " + maximo + "]";
    }
    
    public boolean equals(Object clase) 
    {
        if (!(clase instanceof HechizoRango))
            return false;

        HechizoRango otro = (HechizoRango) clase;

        return otro.minimo == minimo && otro.maximo == maximo;
    }
    
    public int hashCode() 
    {
        return Objects.hash(minimo, maximo);
    }
}
