package objetos;

public class Servidores 
{
	private int id;
	private byte comunidad;
	private String nombre, ip, puerto, ip_database, usuario_database, password_database;
	
	public Servidores(int _id, String _nombre, byte _comunidad, String _ip, String _puerto, String _ip_database, String _usuario_database, String _password_database)
	{
		id = _id;
		nombre = _nombre;
		comunidad = _comunidad;
		ip = _ip;
		puerto = _puerto;
		ip_database = _ip_database;
		usuario_database = _usuario_database;
		password_database = _password_database;
		if (password_database == null)
			password_database = "";
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
	
	public void set_Id(int _id) 
	{
		id = _id;
	}
	
	public void set_Comunidad(byte _comunidad) 
	{
		comunidad = _comunidad;
	}
	
	public void set_Nombre(String _nombre)
	{
		nombre = _nombre;
	}

	public void set_Ip(String _ip)
	{
		ip = _ip;
	}

	public void set_Puerto(String _puerto)
	{
		puerto = _puerto;
	}

	public void set_Ip_database(String _ip_database) 
	{
		ip_database = _ip_database;
	}

	public void set_Usuario_database(String _usuario_database) 
	{
		usuario_database = _usuario_database;
	}

	public void set_Password_database(String _password_database) 
	{
		password_database = _password_database;
	}
}
