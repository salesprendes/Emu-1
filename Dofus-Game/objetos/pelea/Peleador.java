package objetos.pelea;

import java.util.Comparator;

import objetos.entidades.Entidades;
import objetos.entidades.Localizacion;
import objetos.entidades.caracteristicas.BaseStats;
import objetos.mapas.Celdas;
import objetos.pelea.global.ColorEquipo;
import objetos.pelea.turnos.Turno;

public abstract class Peleador
{
	private Localizacion anterior_localizacion;
	private Pelea pelea;
	private ColorEquipo equipo_color;
	private Celdas celda_inicio;
	private Peleador lider_pelea;
	private Turno turno;
	
	
	
	
	public abstract int get_Id();
	public abstract String get_Nombre(final boolean mostrar_admin);
	public abstract short get_Nivel();
	public abstract Entidades get_Entidad();
	public abstract BaseStats get_Stats();
	public abstract void get_Enviar_Paquete(final String paquete);
	public abstract String get_Paquete_Gm_Pelea();
	public abstract int get_Iniciativa();
	
	public Peleador get_Lider_pelea()
	{
		return lider_pelea;
	}
	
	public void set_Lider_pelea(final Peleador _lider_pelea)
	{
		lider_pelea = _lider_pelea;
	}
	
	public Turno get_Turno()
	{
		return turno;
	}
	
	public void set_Turno(final Turno _turno)
	{
		turno = _turno;
	}
	
	public Celdas get_Celda_inicio()
	{
		return celda_inicio;
	}
	
	public void set_Celda_inicio(final Celdas _celda_inicio)
	{
		celda_inicio = _celda_inicio;
	}
	
	public Celdas get_Celda_Pelea()
	{
        return get_Entidad().get_Localizacion().get_Celda();
    }

    public void set_Celda_Pelea(final Celdas celda) 
    {
        if (get_Celda_Pelea() != null)
        	get_Celda_Pelea().get_Eliminar_Entidad(get_Entidad(), false);
        if (celda != null) 
        {
        	celda.get_Agregar_Entidad(get_Entidad(), false);
        	get_Entidad().get_Localizacion().set_Celda(celda);
        }
    }
    
    public static Comparator<Peleador> get_Comparador_Iniciativa() 
    {
        return Comparator.comparingInt(Peleador::get_Iniciativa);
    }
    
    public boolean equals(final Peleador luchador) 
	{
        return get_Id() == luchador.get_Id();
    }
	
	@Override
    public int hashCode() 
	{
        return get_Entidad().get_Id();
    }
}
