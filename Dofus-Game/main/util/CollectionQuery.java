package main.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class CollectionQuery<T> implements Iterable<T>
{
	private Iterable<T> iterable;

	private CollectionQuery(Iterable<T> _iterable) 
	{
		iterable = _iterable;
	}

	public static <T> CollectionQuery<T> from(Iterable<T> from) 
	{
		return new CollectionQuery<T>(from);
	}

	public CollectionQuery<T> filter(Predicate<T> predicate) 
	{
		return from(StreamSupport.stream(iterable.spliterator(), false).filter(predicate).collect(Collectors.toList()));
	}

	public <E> CollectionQuery<E> transform(Function<T, E> funcion) 
	{
		return from(StreamSupport.stream(iterable.spliterator(), false).map(funcion).collect(Collectors.toList()));
	}

	public CollectionQuery<T> orderBy(Comparator<T> comparador) 
	{
		return from(Ordering.from(comparador).sortedCopy(iterable));
	}

	private <C extends Collection<T>> C addTo(C coleccion) 
	{
		iterable.forEach(coleccion::add);
		return coleccion;
	}

	public List<T> reverse() 
	{
		return Lists.reverse(computeList(new ArrayList<T>()));
	}

	public <A extends List<T>> A computeList(A collection) 
	{
		return addTo(collection);
	}

	@Override
	public Iterator<T> iterator() 
	{
		return iterable.iterator();
	}

	public Iterable<T> get_Iterable()
	{
		return iterable;
	}

	public void set_Iterable(Iterable<T> _iterable)
	{
		iterable = _iterable;
	}
}
