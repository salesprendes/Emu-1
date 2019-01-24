package objetos.entidades.personajes;

import juego.enums.TipoAlineamientos;
import objetos.entidades.alineamientos.AlineamientosModelo;

public class Alineamientos
{
	private AlineamientosModelo alineamiento;
	private int orden, orden_nivel, honor, deshonor;
	private boolean activado;

	public Alineamientos(final byte _alineamiento, final int _orden, final int _orden_nivel, final int _honor, final int _deshonor, final boolean _activado)
	{
		if(AlineamientosModelo.get_Alineamientos_Cargados(_alineamiento) != null)
			alineamiento = AlineamientosModelo.get_Alineamientos_Cargados(_alineamiento);
		else
			alineamiento = AlineamientosModelo.get_Alineamientos_Cargados((byte) 0);
		
		if (_orden == 0)
		{
			orden_nivel = 0;
			switch (alineamiento.get_Id())
			{
				case TipoAlineamientos.BONTA:
					orden = 1;
				break;
				
				case TipoAlineamientos.BRAKMAR:
					orden = 5;
				break;
				
				case TipoAlineamientos.MERCENARIO:
					orden = 9;
				break;
				
				default:
					orden = 0;
				break;
			}
		}
		else
		{
			orden_nivel = _orden_nivel;
			orden = _orden;
		}
		if(alineamiento.get_Id() != 0)
		{
			honor = _honor;
			deshonor = _deshonor;
		}
		activado = _activado;
	}

	public AlineamientosModelo get_Alineamiento()
	{
		return alineamiento;
	}
	
	public void set_Alineamiento(final AlineamientosModelo _alineamiento)
	{
		alineamiento = _alineamiento;
	}

	public int get_Orden()
	{
		return orden;
	}

	public void set_Orden(final int _orden)
	{
		orden = _orden;
	}

	public int get_Orden_nivel()
	{
		return orden_nivel;
	}

	public void set_Orden_nivel(final int _orden_nivel)
	{
		orden_nivel = _orden_nivel;
	}

	public int get_Honor()
	{
		return honor;
	}

	public void set_Honor(final int _honor)
	{
		honor = _honor;
	}

	public int get_Deshonor()
	{
		return deshonor;
	}
	
	public void set_Deshonor(final int _deshonor)
	{
		deshonor = _deshonor;
	}

	public boolean get_Esta_Activado()
	{
		return alineamiento.get_Id() == 0 ? false : activado;
	}

	public void set_Esta_Activado(final boolean _activado)
	{
		activado = _activado;
	}
}
