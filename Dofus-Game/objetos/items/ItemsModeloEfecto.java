package objetos.items;

import juego.enums.Efectos;

final public class ItemsModeloEfecto
{
	final private Efectos efecto;
    final private int minimo, maximo, especial;
    final private String texto;
    
    public ItemsModeloEfecto(final Efectos _efecto, final int _minimo, final int _maximo, final int _especial, final String _texto) 
    {
    	efecto = _efecto;
    	minimo = _minimo;
    	maximo = _maximo;
    	especial = _especial;
    	texto = _texto;
    }
    
    public Efectos get_Efecto() 
    {
        return efecto;
    }
    
    public int get_Minimo()
    {
        return minimo;
    }
    
    public int get_Maximo() 
    {
        return maximo;
    }
    
    public int get_Especial() 
    {
        return especial;
    }
    
    public String get_Texto() 
    {
        return texto;
    }
}
