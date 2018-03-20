package objetos;

import login.LoginRespuesta;

final public class Cuentas 
{
	final private int id;
	private final String usuario, password, apodo;
	private long tiempo_abono;
	private LoginRespuesta login_respuesta = null;
	private boolean cuenta_baneada = false, fila_espera;
	private final byte comunidad, rango_cuenta;
	
	public Cuentas(int _id, String _usuario, String _password, String _apodo, byte _rango_cuenta, long _tiempo_abono, final byte _comunidad, boolean _baneado)
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
	
	public String get_Apodo()
	{
		return apodo;
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
	
	public byte get_Comunidad() 
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
}
