package objetos.pelea;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import objetos.entidades.personajes.Personajes;
import objetos.mapas.Mapas;

public class PeleaConstructor
{
	private final AtomicInteger id_generador = new AtomicInteger(0);
    private final ScheduledExecutorService ejecutor = new ScheduledThreadPoolExecutor(0);
    private final Mapas mapa;
    private final Map<Integer, Pelea> peleas = new ConcurrentHashMap<Integer, Pelea>();
    
    public PeleaConstructor(Mapas _mapa) 
    {
    	mapa = _mapa;
    }
    
    private void agregar_Pelea(Pelea pelea) 
    {
    	peleas.put(pelea.get_Id(), pelea);
    }
    
    public void removeFight(final Pelea pelea) 
    {
    	peleas.remove(pelea.get_Id());
    }
    
    public byte get_Contar_Peleas() 
    {
        return (byte) peleas.values().stream().filter(fight -> fight.get_Fase() == 3).count();
    }
    
    public Pelea get_Pelea(int id_pelea) 
    {
        return peleas.get(id_pelea);
    }
    
    public Personajes get_Jugador_Desconectado(final int id_jugador) 
    {
        Optional<Pelea> resultado = peleas.values().stream().filter(pelea -> pelea.get_Jugadores_desconectados().get(id_jugador) != null).findAny();
        return resultado.isPresent() ? resultado.get().get_Jugadores_desconectados().get(id_jugador).getKey() : null;
    }
}
