package objetos;

final public class Servidores 
{
	final private int id;
	final private byte comunidad, estado;
	final private String nombre, ip, puerto, ip_database, usuario_database, password_database;
	
	public Servidores(int _id, String _nombre, byte _comunidad, byte _estado, String _ip, String _puerto, String _ip_database, String _usuario_database, String _password_database)
	{
		id = _id;
		nombre = _nombre;
		comunidad = _comunidad;
		estado = _estado;
		ip = _ip;
		puerto = _puerto;
		ip_database = _ip_database;
		usuario_database = _usuario_database;
		password_database = _password_database;
	}
	
	public int get_Id() 
	{
		return id;
	}
	
	public byte get_Comunidad()
	{
		return comunidad;
	}
	
	public String get_Nombre() 
	{
		return nombre;
	}
	
	public String get_Ip() 
	{
		return ip;
	}
	
	public String get_Puerto()
	{
		return puerto;
	}
	
	public String get_Ip_database()
	{
		return ip_database;
	}
	
	public String get_Usuario_database()
	{
		return usuario_database;
	}
	
	public String get_Password_database() 
	{
		return password_database;
	}

	public byte get_Estado() 
	{
		return estado;
	}
}
