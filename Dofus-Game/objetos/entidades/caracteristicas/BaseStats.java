package objetos.entidades.caracteristicas;

public class BaseStats 
{
	private final Stats base, equipo, dones, boost;

	public BaseStats(final Stats _base, final Stats _equipo, final Stats _dones, final Stats _boost) 
	{
		base = _base;
		equipo = _equipo;
		dones = _dones;
		boost = _boost;
	}

	public void clear() 
	{
		if (base != null)
			base.clear();
		
		if (equipo != null)
			equipo.clear();
		
		if (dones != null)
			dones.clear();
		
		if (boost != null)
			boost.clear();
	}

	public boolean get_Tiene_Stat_Id(final int statID)
	{
		boolean b = false;
		
		if (base != null) 
			b |= base.get_Stat_Id(statID);
		
		if (equipo != null)
			b |= equipo.get_Stat_Id(statID);
		
		if (dones != null)
			b |= dones.get_Stat_Id(statID);

		if (boost != null)
			b |= boost.get_Stat_Id(statID);
		return b;
	}

	public int get_Stat_Mostrar_Complemento(final int statID)
	{
		int valor = 0;
		if (base != null)
			valor += base.get_Mostrar_Stat_Complemento(statID);
		
		if (equipo != null)
			valor += equipo.get_Mostrar_Stat_Complemento(statID);
		
		if (dones != null)
			valor += dones.get_Mostrar_Stat_Complemento(statID);
		
		return valor;
	}

	public int get_Stat_Para_Mostrar(final int statID)
	{
		int valor = 0;
		
		if (base != null)
			valor += base.get_Mostrar_Stat(statID);
		
		if (equipo != null)
			valor += equipo.get_Mostrar_Stat(statID);
		
		if (dones != null)
			valor += dones.get_Mostrar_Stat(statID);

		if (boost != null)
			valor += boost.get_Mostrar_Stat(statID);
		
		return valor;
	}

	public void clear_Boost_Stat() 
	{
		if (boost != null) 
			boost.clear();
	}

	public Stats get_Base()
	{
		return base;
	}

	public Stats get_Equipo()
	{
		return equipo;
	}
	
	public Stats get_Dones()
	{
		return dones;
	}

	public Stats get_Boost()
	{
		return boost;
	}
}
