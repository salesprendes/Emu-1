package objetos.pelea;

import objetos.entidades.Entidades;
import objetos.entidades.Localizacion;
import objetos.entidades.caracteristicas.Stats;
import objetos.pelea.global.ColorEquipo;

public abstract class Peleador
{
	private Localizacion anterior_localizacion;
	private Pelea pelea;
	private ColorEquipo equipo_color;
	
	
	
	
	
	public abstract int get_Id();
	public abstract String get_Nombre();
	public abstract short get_Nivel();
	public abstract Entidades get_Entidad();
	public abstract Stats get_Stats();
	public abstract void enviar_Paquete(final String paquete);
	public abstract String get_Pelea_Gm();
}
