package objetos;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import login.comunicador.ComunicadorRespuesta;

final public class Servidores 
{
	final private int id;
	final private Comunidades comunidad;
	private byte estado;
	final private String nombre, ip, puerto, ip_database, usuario_database, password_database;
	private static final ConcurrentMap<Integer, Servidores> servidores = new ConcurrentHashMap<Integer, Servidores>();
	private ComunicadorRespuesta comunicador_game = null;
	
	public Servidores(int _id, String _nombre, Comunidades _comunidad, byte _estado, String _ip, String _puerto, String _ip_database, String _usuario_database, String _password_database)
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
		servidores.put(id, this);
	}
	
	public int get_Id() 
	{
		return id;
	}
	
	public Comunidades get_Comunidad()
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
	
	public static String get_Obtener_Servidores()
	{
        final StringBuilder paquete_servidores = new StringBuilder(servidores.size() * 10).append("AH");
        servidores.values().forEach(servidor ->
        {
        	paquete_servidores.append(paquete_servidores.length() > 2 ? "|" : "");
        	paquete_servidores.append(servidor.get_Id()).append(";").append(servidor.get_Estado()).append(";110;1");
        });
        return paquete_servidores.toString();
    }
	
	public static ConcurrentMap<Integer, Servidores> get_Servidores()
	{
		return servidores;
	}

	public ComunicadorRespuesta get_Comunicador_game()
	{
		return comunicador_game;
	}

	public byte get_Estado() 
	{
		return estado;
	}

	public void set_Estado(byte _estado) 
	{
		estado = _estado;
	}

	public void set_Comunicador_game(ComunicadorRespuesta _comunicador_game)
	{
		comunicador_game = _comunicador_game;
	}
}
