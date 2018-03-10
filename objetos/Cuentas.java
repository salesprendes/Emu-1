package objetos;

import java.util.Date;

import login.LoginRespuesta;

public class Cuentas 
{
	protected int id;
	private String usuario, password, apodo;
	private Date fecha_abono;
	private LoginRespuesta login_respuesta = null;
	protected boolean cuenta_baneada = false, esta_fila_espera = false;
	
	public Cuentas(int _id, String _usuario, String _password, String _apodo, Date _fecha_abono, boolean _baneado)
	{
		id = _id;
		usuario = _usuario;
		password = _password;
		apodo = _apodo;
		fecha_abono = _fecha_abono;
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
	
	public Date get_Fecha_abono()
	{
		return fecha_abono;
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

	public void set_Fecha_abono(Date _fecha_abono)
	{
		fecha_abono = _fecha_abono;
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
		return fecha_abono.getTime() >= new Date().getTime();
	}
	
	public boolean get_Fila_Espera()
	{
		return esta_fila_espera;
	}
	
	public void set_Fila_Espera(boolean _esta_fila_espera) 
	{
		esta_fila_espera = _esta_fila_espera;
	}
}
