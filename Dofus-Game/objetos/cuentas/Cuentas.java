package objetos.cuentas;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import juego.JuegoSocket;
import juego.fila.Nodo;
import main.Main;
import objetos.entidades.personajes.Personajes;
import objetos.entidades.personajes.Razas;

final public class Cuentas 
{
	private final int id;
	private String apodo, ip, uid, idioma_cliente;
	private final byte rango_cuenta, comunidad;
	private JuegoSocket juego_socket;
	private boolean esta_baneado, fila_espera = false;
	private Personajes personaje_jugando;
	private Map<Integer, Personajes> personajes;
	private Nodo nodo_fila;
	
	final static SimpleDateFormat formato_fecha = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
	private static final ConcurrentHashMap<Integer, Cuentas> cuentas_cargadas = new ConcurrentHashMap<Integer, Cuentas>();

	public Cuentas(final int _id, final String _apodo, final String _uid, final String _ip, final byte _rango_cuenta, final byte _comunidad, boolean _baneado)
	{
		id = _id;
		apodo = _apodo;
		uid = _uid;
		ip = _ip;
		rango_cuenta = _rango_cuenta;
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
		long tiempo_abono = Main.get_Database().get_Cuentas().get_Abono(id);
		return tiempo_abono <= System.currentTimeMillis() ? 0 : tiempo_abono - System.currentTimeMillis();
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

	public void set_Juego_socket(final JuegoSocket _juego_socket) 
	{
		juego_socket = _juego_socket;
	}

	public String get_Uid() 
	{
		return uid;
	}

	public void set_Uid(final String _uid) 
	{
		uid = _uid;
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
		if(personajes == null)
			personajes = new TreeMap<Integer, Personajes>();
		
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
	
	public String get_Idioma_cliente()
	{
		return idioma_cliente;
	}

	public void set_Idioma_cliente(String _idioma_cliente)
	{
		idioma_cliente = _idioma_cliente;
	}
	
	public byte get_Max_Creacion_Personajes_Total() 
	{
		return Main.get_Database().get_Cuentas().get_Max_Pjs_Creacion(id);
	}

	public void agregar_Personaje(final Personajes personaje)
	{
		if(personajes == null)
			personajes = new TreeMap<Integer, Personajes>();
		if (!personajes.containsKey(personaje.get_Id()))
		{
			personajes.put(personaje.get_Id(), personaje);
		}
	}
	
	public void eliminar_Personaje(final int id_personaje)
	{
		if(personajes == null)
			personajes = new TreeMap<Integer, Personajes>();
		if (personajes.containsKey(id_personaje))
		{
			personajes.remove(id_personaje);
		}
	}
	
	public Personajes crearPersonaje(final String nombre, final byte clase, byte sexo, int color_1, int color_2, int color_3) 
	{
		color_1 = Math.min(16777215, Math.max(-1, color_1));
		color_2 = Math.min(16777215, Math.max(-1, color_2));
		color_3 = Math.min(16777215, Math.max(-1, color_3));
		Razas raza = Razas.get_Razas_Cargadas(clase);
		if (raza == null)
			raza = Razas.get_Razas_Cargadas((byte) 1);
		
		final Personajes personaje = Personajes.crear_Personaje(id, nombre, color_1, color_2, color_3, raza, (byte) (sexo != 0 ? 1 : 0));
		
		if (personaje != null)
			agregar_Personaje(personaje);
		return personaje;
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
