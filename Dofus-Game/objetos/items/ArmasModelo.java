package objetos.items;

public class ArmasModelo
{
	final private byte coste_pa, bonus_golpe_critico, alcanze_minimo, alcanze_maximo;
	final private short porcentaje_fallo_critico, porcentaje_golpe_critico;
	final private boolean lanzado_en_linea, necesita_linea_vision, es_dos_manos;
	
	public ArmasModelo(final byte _bonus_golpe_critico, final byte _coste_pa, final byte _alcanze_minimo, final byte _alcanze_maximo, final short _porcentaje_golpe_critico, final short _porcentaje_fallo_critico, final boolean _lanzado_en_linea, final boolean _necesita_linea_vision, final boolean _es_dos_manos)
	{
		bonus_golpe_critico = _bonus_golpe_critico;
		coste_pa = _coste_pa;
		alcanze_minimo = _alcanze_minimo;
		alcanze_maximo = _alcanze_maximo;
		porcentaje_golpe_critico = _porcentaje_golpe_critico;
		porcentaje_fallo_critico = _porcentaje_fallo_critico;
		lanzado_en_linea = _lanzado_en_linea;
		necesita_linea_vision = _necesita_linea_vision;
		es_dos_manos = _es_dos_manos;
	}

	public byte get_Coste_pa()
	{
		return coste_pa;
	}

	public byte get_Bonus_golpe_critico()
	{
		return bonus_golpe_critico;
	}

	public byte get_Alcanze_minimo()
	{
		return alcanze_minimo;
	}

	public byte get_Alcanze_maximo()
	{
		return alcanze_maximo;
	}

	public short get_Porcentaje_fallo_critico()
	{
		return porcentaje_fallo_critico;
	}

	public short get_Porcentaje_golpe_critico()
	{
		return porcentaje_golpe_critico;
	}

	public boolean get_Lanzado_en_linea()
	{
		return lanzado_en_linea;
	}

	public boolean get_Necesita_Linea_Vision()
	{
		return necesita_linea_vision;
	}

	public boolean get_Es_dos_manos()
	{
		return es_dos_manos;
	}
}
