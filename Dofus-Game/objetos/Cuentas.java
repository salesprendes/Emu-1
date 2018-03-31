package objetos;

final public class Cuentas 
{
	private final int id;
	private String apodo;
	private long tiempo_abono;
	private boolean fila_espera = false, creando_apodo = false;
	private final byte rango_cuenta;
	private final byte comunidad;

	public Cuentas(final int _id, String _apodo, final byte _rango_cuenta, long _tiempo_abono, final byte _comunidad, boolean _baneado)
	{
		id = _id;
		apodo = _apodo;
		rango_cuenta = _rango_cuenta;
		tiempo_abono = _tiempo_abono;
		comunidad = _comunidad;
	}
	
	public int get_Id()
	{
		return id;
	}
	
	public long get_Fecha_abono()
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
	
	public byte get_Comunidad() 
	{
		return comunidad;
	}

	public boolean get_Fila_espera() 
	{
		return fila_espera;
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
}
