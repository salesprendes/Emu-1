package objetos;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import login.LoginRespuesta;

final public class Cuentas 
{
	private final int id;
	private final String usuario, password;
	private String apodo;
	private long tiempo_abono;
	private LoginRespuesta login_respuesta = null;
	private boolean cuenta_baneada = false, fila_espera = false, creando_apodo = false;
	private final byte rango_cuenta;
	private final Comunidades comunidad;
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
	
	public long get_Fecha_abono()
	{
		if(tiempo_abono <= System.currentTimeMillis())
		{
			return 0;
		}
		return tiempo_abono - System.currentTimeMillis();
	}
	
	public boolean es_Cuenta_Abonada()
	{
		if(tiempo_abono <= System.currentTimeMillis())
		{
			return false;
		}
		return true;
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
	
	public LoginRespuesta get_Login_respuesta() 
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
	
	public void set_Login_respuesta(LoginRespuesta _login_respuesta)
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

	public boolean esta_Creando_apodo()
	{
		return creando_apodo;
	}

	public void set_Creando_apodo(boolean _creando_apodo) 
	{
		creando_apodo = _creando_apodo;
	}
	
	public static Map<Integer, Cuentas> get_Cuentas_Cargadas()
	{
		return cuentas_cargadas;
	}
	
	public static void agregar_Cuenta_Cargada(final Cuentas _cuenta)
	{
		if (!cuentas_cargadas.containsKey(_cuenta.get_Id()))
		{
			cuentas_cargadas.put(_cuenta.get_Id(), _cuenta);
		}
	}
	
	public static void eliminar_Cuenta_Cargada(final int id_cuenta)
	{
		if (cuentas_cargadas.containsKey(id_cuenta))
		{
			cuentas_cargadas.remove(id_cuenta);
		}
	}
}
