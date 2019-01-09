package objetos;

import java.util.concurrent.ConcurrentHashMap;

import login.LoginSocket;
import login.fila.NodoFila;

final public class Cuentas 
{
	private final int id;
	private final String usuario, password;
	private String apodo;
	private long tiempo_abono;
	private LoginSocket login_respuesta = null;
	private boolean cuenta_baneada = false, fila_espera = false, creando_apodo = false;
	private final byte rango_cuenta;
	private final Comunidades comunidad;
	private NodoFila nodo_fila;
	
	private static final ConcurrentHashMap<Integer, Cuentas> cuentas_cargadas = new ConcurrentHashMap<Integer, Cuentas>();
	
	public Cuentas(final int _id, final String _usuario, final String _password, String _apodo, final byte _rango_cuenta, long _tiempo_abono, final Comunidades _comunidad, boolean _baneado)
	{
		id = _id;
		usuario = _usuario;
		password = _password;
		apodo = _apodo;
		rango_cuenta = _rango_cuenta;
		tiempo_abono = _tiempo_abono;
		comunidad = _comunidad;
		cuenta_baneada = _baneado;
		
		cuentas_cargadas.put(id, this);
	}
	
	public int get_Id()
	{
		return id;
	}

	public String get_Usuario()
	{
		return usuario;
	}
	
	public String get_Password()
	{
		return password;
	}
	
	public long get_Tiempo_Abono()
	{
		return tiempo_abono <= System.currentTimeMillis() ? 0 : tiempo_abono - System.currentTimeMillis();
	}
	
	public boolean es_Cuenta_Abonada()
	{
		return tiempo_abono >= System.currentTimeMillis();
	}
	
	public byte get_Rango_cuenta() 
	{
		return rango_cuenta;
	}
	
	public Comunidades get_Comunidad() 
	{
		return comunidad;
	}

	public boolean get_Fila_espera() 
	{
		return fila_espera;
	}
	
	public LoginSocket get_Login_respuesta() 
	{
		return login_respuesta;
	}

	public String get_Apodo() 
	{
		return apodo;
	}

	public void set_Apodo(String _apodo) 
	{
		apodo = _apodo;
	}

	public void set_Tiempo_Abono(long _tiempo_abono)
	{
		tiempo_abono = _tiempo_abono;
	}
	
	public void set_Login_respuesta(LoginSocket _login_respuesta)
	{
		login_respuesta = _login_respuesta;
	}

	public boolean es_Cuenta_baneada()
	{
		return cuenta_baneada;
	}

	public void set_Cuenta_baneada(boolean _cuenta_baneada) 
	{
		cuenta_baneada = _cuenta_baneada;
	}
	
	public void set_Fila_espera(boolean _fila_espera) 
	{
		fila_espera = _fila_espera;
	}

	public NodoFila get_Nodo_fila() 
	{
		return nodo_fila;
	}

	public void set_Nodo_fila(NodoFila _nodo_fila)
	{
		nodo_fila = _nodo_fila;
	}

	public boolean esta_Creando_apodo()
	{
		return creando_apodo;
	}

	public void set_Creando_apodo(boolean _creando_apodo) 
	{
		creando_apodo = _creando_apodo;
	}
	
	public static ConcurrentHashMap<Integer, Cuentas> get_Cuentas_Cargadas()
	{
		return cuentas_cargadas;
	}
}
