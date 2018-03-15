package objetos;

import login.LoginRespuesta;

final public class Cuentas 
{
	final private int id;
	protected String usuario, password, apodo;
	protected long tiempo_abono;
	protected LoginRespuesta login_respuesta = null;
	private boolean cuenta_baneada = false, fila_espera;
	private byte comunidad;
	
	public Cuentas(int _id, String _usuario, String _password, String _apodo, long _tiempo_abono, final byte _comunidad, boolean _baneado)
	{
		id = _id;
		usuario = _usuario;
		password = _password;
		apodo = _apodo;
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
		return tiempo_abono;
	}
	
	public void set_Usuario(String _usuario)
	{
		usuario = _usuario;
	}

	public void set_Password(String _password)
	{
		password = _password;
	}

	public void set_Apodo(String _apodo)
	{
		apodo = _apodo;
	}

	public void set_Tiempo_Abono(long _tiempo_abono)
	{
		tiempo_abono = _tiempo_abono;
	}

	public LoginRespuesta get_Login_respuesta() 
	{
		return login_respuesta;
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
	
	public boolean es_Cuenta_Abonada()
	{
		return tiempo_abono >= System.currentTimeMillis();
	}

	public byte get_Comunidad() 
	{
		return comunidad;
	}

	public boolean get_Fila_espera() 
	{
		return fila_espera;
	}

	public void set_Fila_espera(boolean _fila_espera) 
	{
		fila_espera = _fila_espera;
	}
}
