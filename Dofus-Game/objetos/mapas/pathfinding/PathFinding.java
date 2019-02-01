package objetos.mapas.pathfinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import objetos.mapas.Celdas;

public class PathFinding implements Collection<Camino>
{
	final private Descifrador decodificador;
    final private List<Camino> pasos;
    private int tiempo_recorrido;
    
    public PathFinding(Descifrador _decodificador, List<Camino> _pasos) 
    {
    	decodificador = _decodificador;
        pasos = _pasos;
    }
    
    public PathFinding(Descifrador _decodificador) 
    {
        this(_decodificador, new ArrayList<Camino>());
    }
    
    public String get_Codificar(final boolean esta_con_montura) 
    {
        return decodificador.get_Codificado(this, esta_con_montura);
    }
    
    public PathFinding get_Mantener_Mientras_Cumpla_Condicion(Predicate<Camino> condicion) 
    {
    	PathFinding nuevo_camino = new PathFinding(decodificador, new ArrayList<Camino>(size()));

        for (Camino step : pasos) 
        {
            if (condicion.test(step)) 
            {
            	nuevo_camino.add(step);
            }
        }
        return nuevo_camino;
    }

	public int get_Tiempo_recorrido()
	{
		return tiempo_recorrido;
	}

	public void set_Tiempo_recorrido(int _tiempo_recorrido)
	{
		tiempo_recorrido = _tiempo_recorrido;
	}

	public boolean add(Camino paso)
	{
		return pasos.add(paso);
	}
	
	public Camino get(int paso) 
	{
        return pasos.get(paso);
    }

	public boolean addAll(Collection<? extends Camino> celdas)
	{
		return pasos.addAll(celdas);
	}
	
	public Camino get_Anterior() 
	{
        return pasos.get(pasos.size() - 1);
    }
	
	public Celdas celda_objetivo() 
	{
        return get_Anterior().get_Celda();
    }

	public void clear()
	{
		pasos.clear();
	}

	public boolean contains(Object o)
	{
		return pasos.contains(o);
	}

	public boolean containsAll(Collection<?> c)
	{
		return pasos.containsAll(c);
	}

	public boolean isEmpty()
	{
		return pasos.isEmpty();
	}

	public Iterator<Camino> iterator()
	{
		return pasos.iterator();
	}

	public boolean remove(Object o)
	{
		return pasos.remove(o);
	}

	public boolean removeAll(Collection<?> c)
	{
		return pasos.removeAll(c);
	}

	public boolean retainAll(Collection<?> c)
	{
		return pasos.retainAll(c);
	}

	public int size()
	{
		return pasos.size();
	}

	public Object[] toArray()
	{
		return pasos.toArray();
	}

	public <T> T[] toArray(T[] a)
	{
		 return pasos.toArray(a);
	}
}
