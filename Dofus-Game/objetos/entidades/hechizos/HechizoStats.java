package objetos.entidades.hechizos;

import java.util.List;

public class HechizoStats
{
	final private int coste_pa, probabilidad_golpe_critico, probabilidad_fallo, tipo_hechizo, lanzamientos_por_turno, lanzamientos_por_objetivo, lanzamientos_delay, nivel_requerido;
    final private Rango rango;
    final private boolean es_lanzado_linea, necesita_linea_de_vision, necesita_celda_libre, es_rango_modificable, acaba_turno_fallo_critico;
    final private int[] estados_necesarios;
    final private int[] estados_prohibidos;
    final private List<EfectoModelo> efectos_normales;
    final private List<EfectoModelo> efectos_criticos;
    final private List<EfectoArea> areas_de_efecto;
    
    public HechizoStats(List<EfectoModelo> _efectos_normales, List<EfectoModelo> _efectos_criticos, final int _coste_pa, Rango _rango, int _probabilidad_golpe_critico, int criticalFailure, boolean _es_lanzado_linea, final boolean _necesita_linea_de_vision, final boolean _necesita_celda_libre, final boolean _es_rango_modificable, final int _tipo_hechizo, final int _lanzamientos_por_turno, final int _lanzamientos_por_objetivo, final int _lanzamientos_delay, List<EfectoArea> _areas_de_efecto, int[] _estados_necesarios, int[] _estados_prohibidos, final int _nivel_requerido, boolean _acaba_turno_fallo_critico) 
    {
    	efectos_normales = _efectos_normales;
    	efectos_criticos = _efectos_criticos;
    	coste_pa = _coste_pa;
        rango = _rango;
        probabilidad_golpe_critico = _probabilidad_golpe_critico;
        probabilidad_fallo = criticalFailure;
        es_lanzado_linea = _es_lanzado_linea;
        necesita_linea_de_vision = _necesita_linea_de_vision;
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
    
    public List<EfectoModelo> get_Efectos_normales()
	{
		return efectos_normales;
	}

	public List<EfectoModelo> get_Efectos_criticos()
	{
		return efectos_criticos;
	}

	public Rango get_Rango()
	{
		return rango;
	}

	public List<EfectoArea> get_Areas_de_efecto()
	{
		return areas_de_efecto;
	}
}
