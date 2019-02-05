package objetos.mapas;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import main.consola.Consola;
import main.util.Compresor;
import objetos.entidades.Entidades;
import objetos.entidades.personajes.Personajes;

public class Mapas 
{
	private final short id, x, y, capacidades; // no superara el maximo permitido 32767 (2 bytes)
	private final byte anchura, altura;
	private final SubAreas sub_area;
	private final String fecha, data, key, celdas_pelea;
	private Celdas[] celdas;
	private ConcurrentMap<Integer, Entidades> entidades;
	private CopyOnWriteArrayList<Personajes> personajes;

	private static final ConcurrentHashMap<Short, Mapas> mapas_cargados = new ConcurrentHashMap<Short, Mapas>();

	public Mapas(final short _id, final String _fecha, final byte _anchura, final byte _altura, final String _celdas_pelea, final String _key, final String _data, final short _capacidades, final short _x, final short _y, final short _sub_area)
	{
		id = _id;
		fecha = _fecha;
		anchura = _anchura;
		altura = _altura;
		celdas_pelea = _celdas_pelea;
		key = _key;
		data = _data;
		capacidades = _capacidades;
		x = _x;
		y = _y;
		sub_area = SubAreas.get_Sub_Areas_Cargadas(_sub_area);
		try 
		{
			sub_area.get_Agregar_Mapa(this);
		} 
		catch (final Exception e)
		{
			Consola.println("Error al cargar el mapa " + id + ": subarea incorrecta");
			System.exit(1);
			return;
		}
		celdas = Compresor.get_Descomprimir_Celdas(data, this);
		mapas_cargados.put(id, this);
	}

	public short get_Id()
	{
		return id;
	}

	public String get_Fecha()
	{
		return fecha;
	}

	public byte get_Anchura()
	{
		return anchura;
	}

	public byte get_Altura() 
	{
		return altura;
	}

	public String get_Celdas_pelea() 
	{
		return celdas_pelea;
	}

	public String get_Key() 
	{
		return key;
	}

	public String get_Data() 
	{
		return data;
	}

	public short get_Capacidades() 
	{
		return capacidades;
	}

	public short get_X()
	{
		return x;
	}

	public short get_Y()
	{
		return y;
	}

	public SubAreas get_Sub_area()
	{
		return sub_area;
	}

	public Celdas[] get_Celdas() 
	{
		return celdas;
	}

	public Celdas get_Celda(final short id)
	{
		return celdas[id];
	}

	public synchronized void get_Acciones_Final_Movimiento(final Personajes personaje)
	{
		if (personaje.get_Mapa().id == id)
		{
			//Celdas celda_actual = personaje.get_Celda();
		}
	}

	public void get_Agregar_Jugador(Personajes jugador) 
	{
		if (personajes == null)
			personajes = new CopyOnWriteArrayList<Personajes>();
		if(!personajes.contains(jugador))
			personajes.add(jugador);
	}

	public void get_Eliminar_Personaje(Personajes jugador) 
	{
		if (personajes != null) 
		{
			if(personajes.contains(jugador))
				personajes.remove(jugador);
			if (personajes.isEmpty()) 
				personajes = null;
		}
	}

	public Collection<Personajes> get_Personajes() 
	{
		if (personajes == null)
			return new CopyOnWriteArrayList<Personajes>();
		return personajes.stream().filter(personaje -> personaje != null && personaje.get_Esta_Conectado()).collect(Collectors.toList());
	}

	public String get_Paquete_Gm_Jugadores()
	{
		StringBuilder paquete = new StringBuilder("GM");
		get_Personajes().forEach(jugador -> paquete.append("|+").append(jugador.get_Paquete_Gm()));
		return paquete.toString();
	}
	
	public void get_Agregar_Entidad(Entidades entidad) 
	{
		if (entidades == null)
			entidades = new ConcurrentHashMap<Integer, Entidades>();
		if(!entidades.containsKey(entidad.get_Id()))
			entidades.put(entidad.get_Id(), entidad);
	}
	
	public void get_Eliminar_Entidad(Entidades entidad) 
	{
		if (entidades != null) 
		{
			if(entidades.containsKey(entidad.get_Id()))
				entidades.remove(entidad.get_Id());
			if (entidades.isEmpty()) 
				entidades = null;
		}
	}
	
	public Collection<Entidades> get_Entidades() 
	{
		if (entidades == null)
			return new CopyOnWriteArrayList<Entidades>();
		return entidades.values().stream().filter(entidad -> entidad != null).collect(Collectors.toList());
	}
	
	public String get_Paquete_Gm_Entidades()
	{
		StringBuilder paquete = new StringBuilder("GM");
		get_Entidades().forEach(entidad -> paquete.append("|+").append(entidad.get_Paquete_Gm()));
		return paquete.toString();
	}

	/** capacidades de los mapas 
	 * https://github.com/Emudofus/Dofus/blob/1.29/dofus/managers/MapsServersManager.as#L141 */

	public boolean get_Se_Puede_Desafiar() 
	{
		return (capacidades & 1) == 0;
	}

	public boolean get_Se_Puede_Agredir()
	{
		return (capacidades >> 1 & 1) == 0;
	}

	public boolean get_Se_Puede_Volver_Punto_Guardado()
	{
		return (capacidades >> 2 & 1) == 0;
	}

	public boolean get_Se_Puede_Utilizar_Teleport()
	{
		return (capacidades >> 3 & 1) == 0;
	}

	public static Mapas get_Mapa_Coordenadas_SubArea(final int X, final int Y, final int super_area_id) 
	{
		for (final Mapas mapa : mapas_cargados.values())
		{
			if (mapa.get_X() == X && mapa.get_Y() == Y && mapa.get_Sub_area().get_Area().get_Super_area().get_Id() == super_area_id)
				return mapa;
		}
		return null;
	}

	public static ConcurrentHashMap<Short, Mapas> get_Mapas_Cargados()
	{
		return mapas_cargados;
	}

	public static Mapas get_Mapas_Cargados(final short mapa_id)
	{
		return mapas_cargados.get(mapa_id);
	}
}