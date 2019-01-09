package objetos.cuentas;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import juego.JuegoSocket;
import juego.fila.Nodo;
import objetos.personajes.Personajes;

final public class Cuentas 
{
	private final int id;
	private String apodo, ip, key;
	private long tiempo_abono;
	private final byte rango_cuenta, comunidad;
	private JuegoSocket juego_socket;
	private boolean esta_baneado, fila_espera = false;
	private Personajes personaje_jugando;
	private final Map<Integer, Personajes> personajes = new TreeMap<Integer, Personajes>();
	private Nodo nodo_fila;
	
	private static final ConcurrentHashMap<Integer, Cuentas> cuentas_cargadas = new ConcurrentHashMap<Integer, Cuentas>();

	public Cuentas(final int _id, final String _apodo, final String _ip, final byte _rango_cuenta, long _tiempo_abono, final byte _comunidad, boolean _baneado)
	{
		id = _id;
		apodo = _apodo;
		ip = _ip;
		rango_cuenta = _rango_cuenta;
		tiempo_abono = _tiempo_abono;
		comunidad = _comunidad;
		esta_baneado = _baneado;
		
		cuentas_cargadas.put(id, this);
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
	
	public boolean get_Esta_Baneado() 
	{
		return esta_baneado;
	}

	public void set_Esta_baneado(final boolean _esta_baneado)
	{
		esta_baneado = _esta_baneado;
	}

	public byte get_Rango_cuenta() 
	{
		return rango_cuenta;
	}
	
	public byte get_Comunidad() 
	{
		return comunidad;
	}

	public String get_Apodo() 
	{
		return apodo;
	}

	public void set_Apodo(final String _apodo) 
	{
		apodo = _apodo;
	}

	public void set_Tiempo_Abono(final long _tiempo_abono)
	{
		tiempo_abono = _tiempo_abono;
	}
	
	public String get_Ip() 
	{
		return ip;
	}

	public void set_Ip(final String _ip)
	{
		ip = _ip;
	}

	public JuegoSocket get_Juego_socket() 
	{
		return juego_socket;
	}

	public void set_Juego_socket(JuegoSocket _juego_socket) 
	{
		juego_socket = _juego_socket;
	}

	public String get_Key() 
	{
		return key;
	}

	public void set_Key(String _key) 
	{
		key = _key;
	}
	
	public boolean get_Fila_espera() 
	{
		return fila_espera;
	}
	
	public void set_Fila_espera(boolean _fila_espera) 
	{
		fila_espera = _fila_espera;
	}
	
	public Nodo get_Nodo_fila() 
	{
		return nodo_fila;
	}

	public void set_Nodo_fila(final Nodo _nodo_fila)
	{
		nodo_fila = _nodo_fila;
	}
	
	public Collection<Personajes> get_Personajes() 
	{
		return personajes.values();
	}
	
	public Personajes get_Personajes(final int id)
	{
		return personajes.get(id);
	}
	
	public Personajes get_Personaje_jugando() 
	{
		return personaje_jugando;
	}

	public void set_Personaje_jugando(final Personajes _personaje_jugando) 
	{
		personaje_jugando = _personaje_jugando;
	}

	public void agregar_Personaje(final Personajes personaje)
	{
		if (!personajes.containsKey(personaje.get_Id()))
		{
			personajes.put(personaje.get_Id(), personaje);
		}
	}
	
	public void eliminar_Personaje(final int id_personaje)
	{
		if (personajes.containsKey(id_personaje))
		{
			personajes.remove(id_personaje);
		}
	}
	
	public final static ConcurrentHashMap<Integer, Cuentas> get_Cuentas_Cargadas() 
	{
		return cuentas_cargadas;
	}

	public final static Cuentas get_Cuenta_Cargada(final int cuenta_id) 
	{
		return cuentas_cargadas.get(cuenta_id);
	}
}
