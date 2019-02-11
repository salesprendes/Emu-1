package objetos;

import java.io.Serializable;
import java.util.ArrayList;

import objetos.entidades.personajes.Items;

public abstract class Intercambios extends ArrayList<Items> implements Serializable
{
	private static final long serialVersionUID = 1;
	private long kamas;
	
	protected Intercambios(long _kamas) 
	{
		kamas = _kamas;
    }
	
	public String get_Objetos() 
	{
        StringBuilder cadena = new StringBuilder();
        
        forEach(item -> cadena.append(item.get_Id()).append(';'));
        return cadena.length() == 0 ? cadena.toString() : cadena.substring(0, cadena.length() - 1);
    }

	public void agregar_Objeto(Items objeto) 
	{
        add(objeto);
    }
	
	public Items get_Objeto(int objeto_id) 
	{
        return stream().filter(item -> item.get_Id() == objeto_id).findAny().orElse(null);
    }
	
	public void set_Eliminar_Objeto(Items objeto) 
	{
        remove(objeto);
    }
	
	public void set_Kamas(final long _kamas)
	{
        kamas = _kamas;
    }

    public long get_Kamas() 
    {
        return kamas;
    }
}
