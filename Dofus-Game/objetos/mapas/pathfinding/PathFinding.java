package objetos.mapas.pathfinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PathFinding implements Collection<Camino>
{
	final private Descifrador decodificador;
    final private List<Camino> pasos;
    
    public PathFinding(Descifrador _decodificador, List<Camino> _pasos) 
    {
    	decodificador = _decodificador;
        pasos = _pasos;
    }
    
    public PathFinding(Descifrador _decodificador) 
    {
        this(_decodificador, new ArrayList<>());
    }

	public boolean add(Camino e)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addAll(Collection<? extends Camino> c)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void clear()
	{
		// TODO Auto-generated method stub
		
	}

	public boolean contains(Object o)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsAll(Collection<?> c)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public Iterator<Camino> iterator()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean remove(Object o)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeAll(Collection<?> c)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean retainAll(Collection<?> c)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public int size()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public Object[] toArray()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T[] toArray(T[] a)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
