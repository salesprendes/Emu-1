package objetos.pelea.equipo;

import java.util.ArrayList;

import main.util.Compresor;
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
	private ArrayList<Short> celdas;
	
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
		celdas = Compresor.get_Celdas_Pelea(_mapa.get_Celdas_pelea(), equipo_color);
        forEach(luchador -> get_Actualizar_Mapa(_mapa, _mapa_pelea, luchador));
    }
	
	public void enviar_Paquete(String data) 
	{
        forEach(luchador -> luchador.enviar_Paquete(data));
    }
}
