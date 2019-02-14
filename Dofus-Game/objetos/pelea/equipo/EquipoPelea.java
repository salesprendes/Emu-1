package objetos.pelea.equipo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import main.util.Compresor;
import objetos.entidades.personajes.Personajes;
import objetos.mapas.Celdas;
import objetos.mapas.Mapa;
import objetos.mapas.Mapas;
import objetos.pelea.Pelea;
import objetos.pelea.Peleador;
import objetos.pelea.global.ColorEquipo;

public abstract class EquipoPelea extends ArrayList<Peleador>
{
	protected final ColorEquipo equipo_color;
	private Pelea pelea;
	private Peleador lider_pelea, ultimo_muerto;
	private ArrayList<Celdas> celdas;
	
	//Estados preparacion_pelea
	private boolean bloqueado = false, permitir_espectadores = true, necesita_ayuda = false, solamente_grupo;
	
	public EquipoPelea(Peleador _lider, ColorEquipo _equipo_color) 
	{
		lider_pelea = _lider;
		equipo_color = _equipo_color;
    }
	
	public byte get_Id() 
	{
        return (byte) equipo_color.ordinal();
    }

	public abstract void agregar_Luchador(Peleador _luchador);
	public abstract void get_Actualizar_Mapa(Mapas mapa, Mapas mapa_pelea, Peleador _luchador);
	
	public void get_Actualizar_Mapa(Mapa _mapa, Mapas _mapa_pelea) 
	{
		celdas = Compresor.get_Celdas_Pelea(_mapa_pelea, _mapa.get_Celdas_pelea(), equipo_color);
        forEach(luchador -> get_Actualizar_Mapa(_mapa, _mapa_pelea, luchador));
    }
	
	public void enviar_Paquete(final String paquete) 
	{
        forEach(luchador -> luchador.get_Enviar_Paquete(paquete));
    }
	
	public void get_Ubicar_Luchador(final Peleador luchador) 
	{
		get_Ubicar_Luchador(luchador, get_Random_Celda());
    }
	
	public void get_Ubicar_Luchador(final Peleador luchador, final Celdas celda) 
	{
		luchador.set_Celda_Pelea(celda);
        luchador.set_Celda_inicio(luchador.get_Celda_Pelea());
    }
	
	public void get_Ubicar_Luchadores() 
	{
        forEach(this::get_Ubicar_Luchador);
    }
	
	public Celdas get_Random_Celda() 
	{
        List<Celdas> celdas_libres = celdas.stream().filter(Celdas::get_Esta_Libre).collect(Collectors.toList());
        return celdas_libres.isEmpty() ? null : celdas_libres.get(new Random().nextInt(celdas_libres.size()));
    }
	
	public boolean get_Contiene_celda(final short celda_id) 
	{
        return celdas.stream().anyMatch(celda -> celda.equals(celda_id));
    }
	
	public int get_Real_Tamano() 
	{
        return (int) stream().filter(luchador -> luchador.get_Lider_pelea() == null).count();
    }
	
	public int get_Real_Jugadores() 
	{
        return (int) stream().filter(luchador -> luchador instanceof Personajes).count();
    }
}
