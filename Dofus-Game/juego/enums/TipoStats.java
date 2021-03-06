package juego.enums;

final public class TipoStats
{
	public static final short ROBA_PM = 77;
	public static final short AGREGAR_PM2 = 78;
	public static final short RETIRAR_PA = 101;
	public static final short AGREGAR_DANOS_DEVUELTOS = 107;
	public static final short AGREGAR_VIDA = 110;
	public static final short AGREGAR_PA = 111;
	public static final short AGREGAR_DANOS = 112;
	public static final short AGREGAR_GOLPES_CRITICOS = 115;
	public static final short RETIRAR_ALCANCE = 116;
	public static final short AGREGAR_ALCANCE = 117;
	public static final short AGREGAR_FUERZA = 118;
	public static final short AGREGAR_AGILIDAD = 119;
	public static final short AGREGAR_PA2 = 120;
	public static final short AGREGAR_DANOS2 = 121;
	public static final short AGREGAR_FALLOS_CRITICOS = 122;
	public static final short AGREGAR_SUERTE = 123;
	public static final short AGREGAR_SABIDURIA = 124;
	public static final short AGREGAR_VITALIDAD = 125;
	public static final short AGREGAR_INTELIGENCIA = 126;
	public static final short RETIRAR_PM = 127;
	public static final short AGREGAR_PM = 128;
	public static final short AGREGAR_PORC_DANOS = 138;
	public static final short AGREGAR_DANO_FISICO = 142;
	public static final short RETIRAR_VITALIDAD = 153;
	public static final short RETIRAR_INTELIGENCIA = 155;
	public static final short RETIRAR_SABIDURIA = 156;
	public static final short AGREGAR_PODS = 158;
	public static final short RETIRAR_PODS = 159;
	public static final short AGREGAR_ESQUIVA_PERD_PA = 160;
	public static final short AGREGAR_ESQUIVA_PERD_PM = 161;
	public static final short AGREGAR_DOMINIO_ARMAS = 165;
	public static final short RETIRAR_PA_FIJO_NO_ESQUIVABLE = 168;
	public static final short RETIRAR_PM_FIJO = 169;
	public static final short AGREGAR_INICIATIVA = 174;
	public static final short RETIRAR_INICIATIVA = 175;
	public static final short AGREGAR_PROSPECCION = 176;
	public static final short RETIRAR_PROSPECCION = 177;
	public static final short AGREGAR_CURAS = 178;
	public static final short RETIRAR_CURAS = 179;
	public static final short AGREGAR_CRIATURAS_INVOCABLES = 182;
	public static final short AGREGAR_RES_PORC_TIERRA = 210;
	public static final short AGREGAR_RES_PORC_AGUA = 211;
	public static final short AGREGAR_RES_PORC_AIRE = 212;
	public static final short AGREGAR_RES_PORC_FUEGO = 213;
	public static final short AGREGAR_RES_PORC_NEUTRAL = 214;
	public static final short AGREGAR_REENVIA_DANOS = 220;
	public static final short AGREGAR_DANOS_TRAMPA = 225;
	public static final short AGREGAR_PORC_DANOS_TRAMPA = 226;
	public static final short AGREGAR_RES_FIJA_TIERRA = 240;
	public static final short AGREGAR_RES_FIJA_AGUA = 241;
	public static final short AGREGAR_RES_FIJA_AIRE = 242;
	public static final short AGREGAR_RES_FIJA_FUEGO = 243;
	public static final short AGREGAR_RES_FIJA_NEUTRAL = 244;
	public static final short AGREGAR_RES_PORC_PVP_TIERRA = 250;
	public static final short AGREGAR_RES_PORC_PVP_AGUA = 251;
	public static final short AGREGAR_RES_PORC_PVP_AIRE = 252;
	public static final short AGREGAR_RES_PORC_PVP_FUEGO = 253;
	public static final short AGREGAR_RES_PORC_PVP_NEUTRAL = 254;
	public static final short AGREGAR_RES_FIJA_PVP_TIERRA = 260;
	public static final short AGREGAR_RES_FIJA_PVP_AGUA = 261;
	public static final short AGREGAR_RES_FIJA_PVP_AIRE = 262;
	public static final short AGREGAR_RES_FIJA_PVP_FUEGO = 263;
	public static final short AGREGAR_RES_FIJA_PVP_NEUTRAL = 264;
	public static final short RETIRAR_PORC_PDV_TEMPORAL = 424;
	public static final short AGREGA_STAT_SABIDURIA = 606;
	public static final short AGREGA_STAT_VITALIDAD = 610;
	public static final short AGREGA_STAT_INTELIGENCIA = 611;
	public static final short AURA = 850;
	
	public static final short get_Stat_Opuesto(final short stat_id) 
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