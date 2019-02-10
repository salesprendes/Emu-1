package login.paquetes.salida;

final public class BienvenidaConexion 
{
	final private String hash_key;

    public BienvenidaConexion(String _hash_key) 
    {
    	hash_key = _hash_key;
    }

    public String toString()
    {
        return "HC" + hash_key;
    }
}
