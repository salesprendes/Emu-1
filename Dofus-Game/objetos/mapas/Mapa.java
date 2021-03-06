package objetos.mapas;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import main.consola.Consola;
import main.util.Compresor;
import main.util.Formulas;
import objetos.entidades.Entidades;
import objetos.entidades.npcs.NpcsUbicacion;
import objetos.entidades.personajes.Personajes;
import objetos.pelea.Pelea;
import objetos.pelea.PeleaConstructor;

public class Mapa implements Mapas
{
	private final short id, x, y, capacidades; // no superara el maximo permitido 32767 (2 bytes)
	private final byte anchura, altura;
	private final SubAreas sub_area;
	private final String fecha, data, key, celdas_pelea;
	private final Celdas[] celdas;
	private CopyOnWriteArrayList<Entidades> entidades;
	private final AtomicInteger generar_id = new AtomicInteger(-100);
	private final PeleaConstructor metodos_pelea;

	private static final ConcurrentHashMap<Short, Mapa> mapas_cargados = new ConcurrentHashMap<Short, Mapa>();

	public Mapa(final short _id, final String _fecha, final byte _anchura, final byte _altura, final String _celdas_pelea, final String _key, final String _data, final short _capacidades, final short _x, final short _y, final short _sub_area)
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
		celdas = Compresor.get_Descomprimir_Celdas(data, this);
		metodos_pelea = new PeleaConstructor(this);
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

	public synchronized void get_Acciones_Final_Movimiento(final Personajes personaje)
	{
		if (personaje.get_Localizacion().get_Mapa().equals(this))
		{
			//Celdas celda_actual = personaje.get_Celda();
		}
	}

	public Collection<Personajes> get_Personajes()
	{
		if (entidades == null)
			return new CopyOnWriteArrayList<Personajes>();
		return entidades.stream().filter(personaje -> personaje != null && personaje instanceof Personajes).map(personaje -> (Personajes) personaje).collect(Collectors.toList());
	}
	
	public void get_Enviar_Personajes_Mapa(final String paquete) 
	{
		get_Personajes().stream().filter(personaje -> personaje.get_Esta_Conectado()).forEach(personaje -> personaje.get_Cuenta().get_Juego_socket().enviar_Paquete(paquete));
    }
	
	public Collection<NpcsUbicacion> get_Npcs()
	{
		if (entidades == null)
			return new CopyOnWriteArrayList<NpcsUbicacion>();
		return entidades.stream().filter(npc -> npc != null && npc instanceof NpcsUbicacion).map(npc -> (NpcsUbicacion) npc).collect(Collectors.toList());
	}

	public String get_Paquete_Gm_Jugadores()
	{
		if(get_Personajes().isEmpty())
			return "";
		
		StringBuilder paquete = new StringBuilder(30 * get_Personajes().size());
		paquete.append("GM");
		get_Personajes().stream().filter(personaje -> personaje.get_Esta_Conectado()).forEach(jugador -> paquete.append("|+").append(jugador.get_Paquete_Gm()));
		return paquete.toString();
	}
	
	public String get_Paquete_Gm_Npcs()
	{
		if(get_Npcs().isEmpty())
			return "";
			
		StringBuilder paquete = new StringBuilder(30 * get_Npcs().size());
		paquete.append("GM");
		get_Npcs().forEach(npc -> paquete.append("|+").append(npc.get_Paquete_Gm()));
		return paquete.toString();
	}
	
	public int get_Siguiente_Id_Entidad() 
	{
        return generar_id.incrementAndGet();
    }
	
	public void get_Agregar_Entidad(Entidades entidad) 
	{
		if (entidades == null)
			entidades = new CopyOnWriteArrayList<Entidades>();
		if(!entidades.contains(entidad))
			entidades.add(entidad);
	}
	
	public void get_Eliminar_Entidad(Entidades entidad) 
	{
		if (entidades != null) 
		{
			if(entidades.contains(entidad))
				entidades.remove(entidad);
			if (entidades.isEmpty()) 
				entidades = null;
		}
	}

	public PeleaConstructor get_Metodos_pelea()
	{
		return metodos_pelea;
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

	public static Mapa get_Mapa_Coordenadas_SubArea(final int X, final int Y, final int super_area_id) 
	{
		for (final Mapa mapa : mapas_cargados.values())
		{
			if (mapa.get_X() == X && mapa.get_Y() == Y && mapa.get_Sub_area().get_Area().get_Super_area().get_Id() == super_area_id)
				return mapa;
		}
		return null;
	}
	
	public MapaPelea crear_Mapa_Pelea(Pelea pelea)
	{
        return new MapaPelea(this, celdas, pelea);
    }
	
	public Celdas get_Random_Celda() 
	{
        return Formulas.get_Random_Lista(Arrays.asList(celdas).stream().filter(cell -> cell.get_Es_Caminable() && cell.get_Entidades().isEmpty()).collect(Collectors.toList()));
    }
	
	public boolean equals(Mapa mapa) 
    {
    	return mapa != null? id == mapa.id : false;
    }

	public static ConcurrentHashMap<Short, Mapa> get_Mapas_Cargados()
	{
		return mapas_cargados;
	}

	public static Mapa get_Mapas_Cargados(final short mapa_id)
	{
		return mapas_cargados.get(mapa_id);
	}
}
