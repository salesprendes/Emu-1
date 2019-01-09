package objetos.personajes;

import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

import juego.JuegoSocket;
import juego.enums.EstadosJuego;
import main.consola.Consola;
import objetos.Entidad;
import objetos.cuentas.Cuentas;
import objetos.mapas.Celdas;
import objetos.mapas.Mapas;

public class Personajes implements Entidad
{
	private int id, color_1, color_2, color_3; // superara 32767 (4 bytes)
	private short nivel, servidor, gfx, tamano; // no superara el maximo permitido 32767 (2 bytes)
	private byte titulo; //No superara los 258 (1 byte)
	private String nombre; //maximo 20 caracteres restringido por database
	private Razas raza;
	private final Cuentas cuenta; //referencia a la cuenta del personaje
	private boolean es_mercante = false; //si es mercante
	private Mapas mapa;
	private Celdas celda;
	private Derechos derechos;
	private Restricciones restricciones;
	
	/** Caches **/
	private String cache_gm = "";
	
	/** Condiciones **/
	

	private static final ConcurrentHashMap<Integer, Personajes> personajes_cargados = new ConcurrentHashMap<Integer, Personajes>();

	public Personajes(final int _id, final String _nombre, final int _color_1, final int _color_2, final int _color_3, final short _nivel, final short _gfx, final short _tamano, final short mapa_id, final short _celda_id, final byte _raza_id, final int cuenta_id, final short derecho_id, final short restriccion_id, final short servidor_id)
	{
		id = _id;
		nombre = _nombre;
		color_1 = _color_1;
		color_2 = _color_2;
		color_3 = _color_3;
		nivel = _nivel;
		gfx = _gfx;
		tamano = _tamano;
		raza = Razas.get_Razas_Cargadas(_raza_id);
		cuenta = Cuentas.get_Cuenta_Cargada(cuenta_id);
		try 
		{
			mapa = Mapas.get_Mapas_Cargados(mapa_id);
			set_Celda(mapa.get_Celda(_celda_id));
		}
		catch (Exception e)
		{
			mapa = Mapas.get_Mapas_Cargados(raza.get_Mapa_id_comienzo());
			set_Celda(mapa.get_Celda(raza.get_Celda_id_comienzo()));
		};
		derechos = new Derechos(derecho_id);
		restricciones = new Restricciones(restriccion_id);
		servidor = servidor_id;
		
		if(cuenta != null)
			cuenta.agregar_Personaje(this);
		
		personajes_cargados.put(id, this);
	}

	public int get_Id() 
	{
		return id;
	}

	public void set_Id(int _id) 
	{
		id = _id;
	}

	public String get_Nombre() 
	{
		return nombre;
	}

	public void set_Nombre(String _nombre) 
	{
		nombre = _nombre;
	}

	public int get_Nivel() 
	{
		return nivel;
	}

	public void set_Nivel(short _nivel) 
	{
		nivel = _nivel;
	}

	public short get_Gfx() 
	{
		return gfx;
	}

	public void set_Gfx(final short _gfx)
	{
		gfx = _gfx;
		get_Limpiar_Cache_Gm();
	}
	
	public synchronized void set_Celda(Celdas _celda)
	{
		boolean mapa_diferente = _celda == null || celda == null ? true : (_celda.get_Mapa() != celda.get_Mapa());
		
		if (celda != null) 
		{
			celda.get_Eliminar_Personaje(this, mapa_diferente || get_Esta_Conectado());
		}
		if (_celda != null) 
		{
			celda = _celda;
			celda.get_Agregar_Jugador(this, mapa_diferente && get_Esta_Conectado());
		}
	}

	public int get_Color_1() 
	{
		return color_1;
	}

	public int get_Color_2() 
	{
		return color_2;
	}

	public int get_Color_3() 
	{
		return color_3;
	}

	public void setColores(int _color_1, int _color_2, int _color_3) 
	{
		if (_color_1 < -1)
			_color_1 = -1;
		else if (_color_1 > 16777215)
			_color_1 = 16777215;
		if (_color_1 < -1)
			_color_1 = -1;
		else if (_color_1 > 16777215)
			_color_1 = 16777215;
		if (_color_2 < -1)
			_color_2 = -1;
		else if (_color_2 > 16777215)
			_color_2 = 16777215;
		if (_color_3 < -1)
			_color_3 = -1;
		else if (_color_3 > 16777215)
			_color_3 = 16777215;
		color_1 = _color_1;
		color_2 = _color_2;
		color_3 = _color_3;
		get_Limpiar_Cache_Gm();
	}

	public Cuentas get_Cuenta()
	{
		return cuenta;
	}

	public boolean get_Es_mercante()
	{
		return es_mercante;
	}

	public void set_Es_mercante(boolean _es_mercante)
	{
		es_mercante = _es_mercante;
	}

	public Razas get_Raza() 
	{
		return raza;
	}

	public int get_Servidor()
	{
		return servidor;
	}

	public void set_Servidor(short _servidor)
	{
		servidor = _servidor;
	}
	
	public boolean get_Esta_Conectado()
	{
		JuegoSocket socket = cuenta.get_Juego_socket();
		
		return socket == null ? false : socket.get_Estado_Juego() == EstadosJuego.CONECTADO;
	}

	public int get_Tipo() 
	{
		return Tipo_Entidad.PERSONAJE.get_Tipo_Entidad();
	}

	public byte get_Orientacion()
	{
		return 0;
	}
	
	public Mapas get_Mapa()
	{
		return mapa;
	}
	
	public short get_Mapa_Id()
	{
		return mapa.get_Id();
	}
	
	public Celdas get_Celda()
	{
		return celda;
	}

	public short get_Celda_Id() 
	{
		return celda.get_Id();
	}
	
	public void get_Limpiar_Cache_Gm()
	{
		cache_gm = "";
	}
	
	public void get_Conexion_juego()
	{
		if (cuenta != null || cuenta.get_Juego_socket() != null)
		{
			cuenta.get_Juego_socket().get_Iniciar_Buffering();
			
			if (es_mercante)
			{
				es_mercante = false;
				mapa.get_Personajes().stream().filter(personaje -> personaje != null && personaje.get_Esta_Conectado()).forEach(personaje -> personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GM|-" + id));
			}
			
			cuenta.set_Personaje_jugando(this);
			cuenta.get_Juego_socket().enviar_Paquete(get_Paquete_ASK());
			cuenta.get_Juego_socket().enviar_Paquete("Im189");
			cuenta.get_Juego_socket().enviar_Paquete("Im0153;" + cuenta.get_Ip());
			cuenta.get_Juego_socket().enviar_Paquete("AR" + derechos.get_Derechos());
			
			cuenta.get_Juego_socket().get_Detener_Buffering();
		}
		else
		{
			Consola.println("El personaje " + nombre + " tiene como entrada personaje NULL");
			return;
		}
	}
	
	public void get_Crear_Juego()
	{
		cuenta.get_Juego_socket().enviar_Paquete("GCK|1|" + nombre);
		cuenta.get_Juego_socket().enviar_Paquete("GDM|" + mapa.get_Id() + '|' + mapa.get_Fecha() + '|' + mapa.get_Key());
		mapa.get_Personajes().stream().filter(personaje -> personaje != null && personaje.get_Esta_Conectado()).forEach(personaje -> personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GM|+" + get_Paquete_Gm()));
		celda.get_Agregar_Jugador(this, true);
	}
	
	public String get_Paquete_ASK()
	{
		final StringJoiner paquete = new StringJoiner("|").add("ASK");
		
		paquete.add(Integer.toString(id));
		paquete.add(nombre);
		paquete.add(Integer.toString(nivel));
		paquete.add(Integer.toString(raza.get_Id()));
		paquete.add(Integer.toString(0));//Sexo
		paquete.add(Integer.toString(gfx));
		paquete.add((color_1 == -1 ? "-1" : Integer.toHexString(color_1)));
		paquete.add((color_2 == -1 ? "-1" : Integer.toHexString(color_2)));
		paquete.add((color_3 == -1 ? "-1" : Integer.toHexString(color_3)));
		//paquete.append(perso.parseItemToASK());
		
		return paquete.toString();
	}
	
	public String get_Paquete_Alk()
	{
		final StringBuilder personaje = new StringBuilder(20);
		
		personaje.append('|');
		personaje.append(id).append(';');
		personaje.append(nombre).append(';');
		personaje.append(nivel).append(';');
		personaje.append(gfx).append(';');
		personaje.append((color_1 != -1 ? Integer.toHexString(color_1) : "-1")).append(';');
		personaje.append((color_2 != -1 ? Integer.toHexString(color_1) : "-1")).append(';');
		personaje.append((color_3 != -1 ? Integer.toHexString(color_1) : "-1")).append(';');
		personaje.append("").append(';');//objetos
		personaje.append(es_mercante ? 1 : 0).append(';');//mercante
		personaje.append(servidor).append(';');
		personaje.append(';');//DeathCount
		personaje.append(';');//LevelMax
		
		return personaje.toString();
	}
	
	public String get_Paquete_Gm()
	{
		if(cache_gm.isEmpty())
		{
			StringBuilder personaje = new StringBuilder(30);
			
			personaje.append(get_Tipo()).append(';');
			personaje.append(id).append(';').append(nombre).append(';').append(raza.get_Id());
			personaje.append((titulo > 0 ? ("," + titulo + ";") : (';')));
			personaje.append(gfx).append('^').append(tamano).append(';');
			personaje.append(0).append(';').append(0).append(',');
			personaje.append(0).append(",").append("0").append(',');
			personaje.append(nivel + id).append(',');
			personaje.append(0).append(';');
			personaje.append((color_1 == -1 ? "-1" : Integer.toHexString(color_1))).append(';');
			personaje.append((color_2 == -1 ? "-1" : Integer.toHexString(color_2))).append(';');
			personaje.append((color_3 == -1 ? "-1" : Integer.toHexString(color_3))).append(';');
			
			cache_gm = personaje.toString();
		}
		return new StringBuilder().append(celda.get_Id()).append(';').append(1).append(';').append(cache_gm).toString();
	}

	public static ConcurrentHashMap<Integer, Personajes> get_Personajes_Cargados()
	{
		return personajes_cargados;
	}

	public static Personajes get_Personaje_Cargado(int personaje_id) 
	{
		return personajes_cargados.get(personaje_id);
	}

	public static void eliminar_Personaje_Cargado(final int personaje_id)
	{
		if (personajes_cargados.containsKey(personaje_id))
		{
			personajes_cargados.remove(personaje_id);
		}
	}
}
