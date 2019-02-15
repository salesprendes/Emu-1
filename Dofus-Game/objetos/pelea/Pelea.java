package objetos.pelea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.util.Pair;
import objetos.entidades.personajes.Personajes;
import objetos.mapas.Mapa;
import objetos.mapas.MapaPelea;
import objetos.mapas.Mapas;
import objetos.pelea.equipo.EquipoPelea;
import objetos.pelea.turnos.TurnoLista;

public abstract class Pelea
{
	private long tiempo_combate;
	private int id;
	private Mapa mapa;
	private MapaPelea mapa_pelea;
	private EquipoPelea primer_equipo, segundo_equipo;
	private List<String> datos_espada = new LinkedList<String>();
	protected byte fase = 1;
	protected TurnoLista turnos_lista;
	private ScheduledExecutorService ejecutor;
	private AtomicInteger generador_id = new AtomicInteger(-100);
	private int tiempo_espera = 0;
	private List<Personajes> espectadores = new ArrayList<Personajes>();
	private final boolean permitir_espada;
	private final Map<Integer, Pair<Personajes, Byte>> jugadores_desconectados = new ConcurrentHashMap<Integer, Pair<Personajes, Byte>>();
	
	protected Pelea(final ScheduledExecutorService _ejecutor, final int _id, final EquipoPelea _primer_equipo, final EquipoPelea _segundo_equipo, Mapa _mapa, final boolean _permitir_espada)
	{
		ejecutor = _ejecutor;
		id = _id;
		mapa = _mapa;
		mapa_pelea = mapa.crear_Mapa_Pelea(this);

		primer_equipo = _primer_equipo;
		segundo_equipo = _segundo_equipo;

		inicializar_Combate();

		if (permitir_espada = _permitir_espada)
			get_Generar_Bandera();

		if (get_Tiempo_scheduled() > 0)
			schedule(this::iniciar_Pelea, get_Tiempo_scheduled() * 1000);
	}
	
	private void inicializar_Combate() 
	{
        fase = 2;
        List<EquipoPelea> equipos = Arrays.asList(primer_equipo, segundo_equipo);
    }
	
	public void get_Generar_Bandera() 
	{
        if (fase == 2) 
        {
        }
    }
	
	public long get_Tiempo_combate()
	{
		return tiempo_combate;
	}
	
	public int get_Id()
	{
		return id;
	}
	
	public Mapas get_Mapa()
	{
		return mapa;
	}
	
	public Mapas get_Mapa_pelea()
	{
		return mapa_pelea;
	}
	
	public EquipoPelea get_Primer_equipo()
	{
		return primer_equipo;
	}
	
	public EquipoPelea get_Segundo_equipo()
	{
		return segundo_equipo;
	}
	
	public List<String> get_Datos_espada()
	{
		return datos_espada;
	}
	
	public byte get_Fase()
	{
		return fase;
	}
	
	public void set_Fase(final byte _fase)
	{
		fase = _fase;
	}

	public Map<Integer, Pair<Personajes, Byte>> get_Jugadores_desconectados()
	{
		return jugadores_desconectados;
	}
	
	public ScheduledFuture<?> schedule(final Runnable runnable, final long tiempo) 
	{
        return ejecutor.schedule(runnable, tiempo, TimeUnit.MILLISECONDS);
    }
	
	private void iniciar_Pelea() 
	{
        if (fase == 3)
            return;

        get_Iniciar_Pelea();

        fase = 4;
        turnos_lista = new TurnoLista(this);
    }
	
	protected abstract byte get_Tiempo_scheduled();
    protected abstract boolean get_Puede_eliminar();
    public abstract boolean get_Permitir_Desconexion();
    protected abstract byte get_Tipo_Pelea();
    protected abstract String get_Espada_Mensaje();
    protected abstract void get_Iniciar_Pelea();
}
