package objetos.entidades.hechizos;

import java.util.Objects;

public class Rango
{
	final private int minimo, maximo;

    public Rango(final int _min, final int _max) 
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
    
    public Rango get_Rango_Modificado(int rango)
    {
        if (rango == 0 || (minimo == maximo && rango < 0))
            return this;

        final int rango_nuevo = maximo + rango;

        return new Rango(minimo, rango_nuevo < minimo ? minimo : rango_nuevo);
    }
    
    public String toString() 
    {
        return "[" + minimo + ", " + maximo + "]";
    }
    
    public boolean equals(Object clase) 
    {
        if (!(clase instanceof Rango))
            return false;

        Rango otro = (Rango) clase;

        return otro.minimo == minimo && otro.maximo == maximo;
    }
    
    public int hashCode() 
    {
        return Objects.hash(minimo, maximo);
    }
}
