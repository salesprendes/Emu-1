package objetos.pelea;

import java.util.LinkedList;
import java.util.List;

import objetos.mapas.Mapas;
import objetos.pelea.equipo.EquipoPelea;

public abstract class Pelea
{
	private long tiempo_combate;
	private int id;
	private Mapas mapa, mapa_pelea;
	private EquipoPelea primer_equipo, segundo_equipo;
	private List<String> datos_espada = new LinkedList<String>();
	protected byte fase = 1;
}
