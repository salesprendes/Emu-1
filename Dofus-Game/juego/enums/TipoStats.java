package juego.enums;

final public class TipoStats
{
	public static final int RETIRAR_PA = 101;
	public static final int AGREGAR_VIDA = 110;
	public static final int AGREGAR_PA = 111;
	public static final int AGREGAR_PA2 = 120;
	public static final int AGREGAR_VITALIDAD = 125;
	public static final int RETIRAR_PM = 127;
	public static final int AGREGAR_PM = 128;
	public static final int AGREGAR_PODS = 158;
	public static final int RETIRAR_PODS = 159;
	public static final int RETIRAR_PA_FIJO_NO_ESQUIVABLE = 168;
	public static final int AGREGAR_INICIATIVA = 174;
	public static final int RETIRAR_INICIATIVA = 175;
	public static final int AGREGAR_PROSPECCION = 176;
	public static final int RETIRAR_PROSPECCION = 177;
	public static final int AGREGAR_CRIATURAS_INVOCABLES = 182;
	
	public static final int get_Stat_Opuesto(final int stat_id) 
	{
		switch(stat_id)
		{
			case RETIRAR_PA:
				return AGREGAR_PA;
			
			case AGREGAR_PA:
				return RETIRAR_PA;
				
			case RETIRAR_PM:
				return AGREGAR_PM;
				
			case AGREGAR_PM:
				return RETIRAR_PM;
				
			case AGREGAR_PODS:
				return RETIRAR_PODS;
				
			case RETIRAR_PODS:
				return AGREGAR_PODS;
				
			case AGREGAR_INICIATIVA:
				return RETIRAR_INICIATIVA;
				
			case RETIRAR_INICIATIVA:
				return AGREGAR_INICIATIVA;
				
			case AGREGAR_PROSPECCION:
				return RETIRAR_PROSPECCION;
				
			case RETIRAR_PROSPECCION:
				return AGREGAR_PROSPECCION;
		}
		return stat_id;
	}
}
