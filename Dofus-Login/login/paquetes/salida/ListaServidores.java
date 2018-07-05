package login.paquetes.salida;

final public class ListaServidores 
{
	final private long tiempo_abono;
	final private String personajes;
	
	public ListaServidores(long _tiempo_abono, String query_personajes)
	{
		tiempo_abono = _tiempo_abono;
		personajes = query_personajes;
    }
	
    public String toString() 
	{
        StringBuilder paquete_final = new StringBuilder(64);
        return paquete_final.append("AxK").append(tiempo_abono).append(personajes).toString();
    }
}
