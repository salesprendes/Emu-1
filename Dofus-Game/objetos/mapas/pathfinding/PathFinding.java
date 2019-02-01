package objetos.mapas.pathfinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import juego.enums.TipoDirecciones;
import objetos.mapas.Celdas;

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
        this(_decodificador, new ArrayList<Camino>());
    }
    
    public String get_Codificar() 
    {
        return decodificador.get_Codificado(this);
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
    
    public int get_Path_Tiempo(final boolean esta_con_montura) 
    {
    	boolean esta_caminando = pasos.size() < 6;
    	int tiempo = 0;
    	Celdas celda_anterior = pasos.get(0).get_Celda();
    	
    	byte lastGroundLevel = celda_anterior.get_Ground_nivel();
		byte lastGroundSlope = celda_anterior.get_Ground_Slope();
		
		for (int i = 1; i < pasos.size(); i++) 
		{
			Celdas celda_siguiente = pasos.get(i).get_Celda();
			TipoDirecciones direccion = celda_anterior.get_Direccion(celda_siguiente);
			
			tiempo += 25 / (esta_con_montura ? direccion.get_Velocidad_Montura() : esta_caminando ? direccion.get_Velocidad_Caminando() : direccion.get_Velocidad_Corriendo());
		
			if (lastGroundLevel < celda_siguiente.get_Ground_nivel())
				tiempo += 100;
			else if (celda_siguiente.get_Ground_nivel() > lastGroundLevel)
				tiempo -= 100;
			else if (lastGroundSlope != celda_siguiente.get_Ground_Slope())
			{
				if (lastGroundSlope == 1)
					tiempo += 100;
				else if (celda_siguiente.get_Ground_Slope() == 1)
					tiempo -= 100;
			}
			
			lastGroundLevel = celda_siguiente.get_Ground_nivel();
			lastGroundSlope = celda_siguiente.get_Ground_Slope();
			celda_anterior = celda_siguiente;
		}
    	return tiempo;
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
