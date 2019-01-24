package database.objetos.transformer;

public interface Transformer<T>
{
	public String serializar(T valor);
	public T deserializar(String serializador);
}
