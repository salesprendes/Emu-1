package objetos.entidades.hechizos;

import java.util.Collection;
import java.util.List;

public class HechizoStats
{
	final private int nivel_requerido;
	final private byte tipo_hechizo, coste_pa, lanzamientos_por_turno, lanzamientos_por_objetivo, lanzamientos_delay;
	final private short probabilidad_golpe_critico, probabilidad_fallo;
	final private HechizoRango rango;
    final private boolean es_lanzado_linea, necesita_linea_de_vget_ion, necesita_celda_libre, es_rango_modificable, acaba_turno_fallo_critico;
    final private int[] estados_necesarios, estados_prohibidos;
    final private Collection<EfectoModelo> efectos_normales;
    final private Collection<EfectoModelo> efectos_criticos;
    final private List<EfectoArea> areas_de_efecto;
    
    public HechizoStats(List<EfectoModelo> _efectos_normales, List<EfectoModelo> _efectos_criticos, final byte _coste_pa, HechizoRango _rango, short _probabilidad_golpe_critico, short _probabilidad_fallo, boolean _es_lanzado_linea, final boolean _necesita_linea_de_vget_ion, final boolean _necesita_celda_libre, final boolean _es_rango_modificable, final byte _tipo_hechizo, final byte _lanzamientos_por_turno, final byte _lanzamientos_por_objetivo, final byte _lanzamientos_delay, List<EfectoArea> _areas_de_efecto, int[] _estados_necesarios, int[] _estados_prohibidos, final int _nivel_requerido, boolean _acaba_turno_fallo_critico) 
    {
    	efectos_normales = _efectos_normales;
    	efectos_criticos = _efectos_criticos;
    	coste_pa = _coste_pa;
        rango = _rango;
        probabilidad_golpe_critico = _probabilidad_golpe_critico;
        probabilidad_fallo = _probabilidad_fallo;
        es_lanzado_linea = _es_lanzado_linea;
        necesita_linea_de_vget_ion = _necesita_linea_de_vget_ion;
        necesita_celda_libre = _necesita_celda_libre;
        es_rango_modificable = _es_rango_modificable;
        tipo_hechizo = _tipo_hechizo;
        lanzamientos_por_turno = _lanzamientos_por_turno;
        lanzamientos_por_objetivo = _lanzamientos_por_objetivo;
        lanzamientos_delay = _lanzamientos_delay;
        areas_de_efecto = _areas_de_efecto;
        estados_necesarios = _estados_necesarios;
        estados_prohibidos = _estados_prohibidos;
        nivel_requerido = _nivel_requerido;
        acaba_turno_fallo_critico = _acaba_turno_fallo_critico;
    }
    
    public Collection<EfectoModelo> get_Efectos_normales()
	{
		return efectos_normales;
	}

	public Collection<EfectoModelo> get_Efectos_criticos()
	{
		return efectos_criticos;
	}

	public HechizoRango get_Rango()
	{
		return rango;
	}

	public List<EfectoArea> get_Areas_de_efecto()
	{
		return areas_de_efecto;
	}

	public short get_Probabilidad_golpe_critico()
	{
		return probabilidad_golpe_critico;
	}

	public short get_Probabilidad_fallo()
	{
		return probabilidad_fallo;
	}

	public int get_Nivel_requerido()
	{
		return nivel_requerido;
	}

	public byte get_Lanzamientos_por_turno()
	{
		return lanzamientos_por_turno;
	}

	public byte get_Lanzamientos_por_objetivo()
	{
		return lanzamientos_por_objetivo;
	}

	public boolean get_Acaba_turno_fallo_critico()
	{
		return acaba_turno_fallo_critico;
	}

	public boolean get_Es_rango_modificable()
	{
		return es_rango_modificable;
	}

	public boolean get_Necesita_celda_libre()
	{
		return necesita_celda_libre;
	}

	public boolean get_Necesita_linea_de_vget_ion()
	{
		return necesita_linea_de_vget_ion;
	}

	public boolean get_Es_lanzado_linea()
	{
		return es_lanzado_linea;
	}

	public int[] get_Estados_prohibidos()
	{
		return estados_prohibidos;
	}

	public byte get_Lanzamientos_delay()
	{
		return lanzamientos_delay;
	}

	public int[] get_Estados_necesarios()
	{
		return estados_necesarios;
	}

	public byte get_Coste_pa()
	{
		return coste_pa;
	}

	public byte get_Tipo_hechizo()
	{
		return tipo_hechizo;
	}
}
