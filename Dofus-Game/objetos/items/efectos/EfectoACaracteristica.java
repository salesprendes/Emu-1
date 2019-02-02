package objetos.items.efectos;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import juego.enums.Efectos;
import juego.enums.TipoCaracteristica;
import objetos.items.CaracteristicaEfecto;
import objetos.items.ItemsModeloEfecto;

public class EfectoACaracteristica implements EfectosMap<CaracteristicaEfecto>
{
	static private class CaracteristicaMapeada 
	{
		final private int multiplicador;
		final private TipoCaracteristica caracteristica;

		public CaracteristicaMapeada(int _multiplicador, TipoCaracteristica _caracteristica) 
		{
			multiplicador = _multiplicador;
			caracteristica = _caracteristica;
		}
	}

	final private Map<Efectos, CaracteristicaMapeada> map = new EnumMap<Efectos, CaracteristicaMapeada>(Efectos.class);
	final private Random random = new Random();

	public EfectoACaracteristica() 
	{
		set(Efectos.AGREGAR_REENVIA_DANOS,			+1,	TipoCaracteristica.REENVIA_DANOS);
		set(Efectos.AGREGAR_PA,						+1,	TipoCaracteristica.PUNTOS_ACCION);
		set(Efectos.AGREGAR_DANOS,					+1,	TipoCaracteristica.DANOS_FIJOS);
		set(Efectos.AGREGAR_DANOS2,					+1, TipoCaracteristica.DANOS_FIJOS);
		set(Efectos.AGREGAR_GOLPES_CRITICOS,		+1, TipoCaracteristica.GOLPES_CRITICOS);
		set(Efectos.RETIRAR_ALCANCE,     			-1, TipoCaracteristica.ALCANZE);
		set(Efectos.AGREGAR_ALCANCE,     			+1, TipoCaracteristica.ALCANZE);
		set(Efectos.AGREGAR_FUERZA,         		+1, TipoCaracteristica.FUERZA);
		set(Efectos.AGREGAR_AGILIDAD,          		+1, TipoCaracteristica.AGILIDAD);
		set(Efectos.AGREGAR_FALLOS_CRITICOS, 		+1, TipoCaracteristica.FALLOS_CRITICOS);
		set(Efectos.AGREGAR_SUERTE,           		+1, TipoCaracteristica.SUERTE);
		set(Efectos.AGREGAR_SABIDURIA,    			+1, TipoCaracteristica.SABIDURIA);
		set(Efectos.AGREGAR_VITALIDAD,    			+1, TipoCaracteristica.VITALIDAD);
		set(Efectos.AGREGAR_INTELIGENCIA,     		+1, TipoCaracteristica.INTELIGENCIA);
		set(Efectos.AGREGAR_PM,  					+1, TipoCaracteristica.PUNTOS_MOVIMIENTO);
		set(Efectos.AGREGAR_PORC_DANOS,   			+1, TipoCaracteristica.PORC_DANOS);
		set(Efectos.RETIRA_FUERZA,         			-1, TipoCaracteristica.FUERZA);
		set(Efectos.RETIRA_AGILIDAD,          		-1, TipoCaracteristica.AGILIDAD);
		set(Efectos.RETIRA_SUERTE,           		-1, TipoCaracteristica.SUERTE);
		set(Efectos.RETIRA_SABIDURIA,           	-1, TipoCaracteristica.SABIDURIA);
		set(Efectos.RETIRA_VITALIDAD,         		-1, TipoCaracteristica.VITALIDAD);
		set(Efectos.RETIRA_INTELIGENCIA,     		-1, TipoCaracteristica.INTELIGENCIA);
		set(Efectos.AGREGAR_ESQUIVA_PERD_PA,    	+1, TipoCaracteristica.ESQUIVA_PERD_PA);
		set(Efectos.AGREGAR_ESQUIVA_PERD_PM,    	+1, TipoCaracteristica.ESQUIVA_PERD_PM);
		set(Efectos.RETIRA_ESQUIVA_PERD_PA,     	-1, TipoCaracteristica.ESQUIVA_PERD_PA);
		set(Efectos.RETIRA_ESQUIVA_PERD_PM,     	-1, TipoCaracteristica.ESQUIVA_PERD_PM);
		set(Efectos.REDUCIR_DANOS_REDUCIDOS,    	-1, TipoCaracteristica.DANOS_FIJOS);
		set(Efectos.RETIRAR_PA_FIJO_NO_ESQUIVABLE,	-1, TipoCaracteristica.PUNTOS_ACCION);
		set(Efectos.RETIRAR_PM_FIJO,				-1, TipoCaracteristica.PUNTOS_MOVIMIENTO);
		set(Efectos.SUB_CRITICAL_HIT,     			-1, TipoCaracteristica.GOLPES_CRITICOS);
		set(Efectos.AGREGAR_CURAS,      			+1, TipoCaracteristica.CURACIONES);
		set(Efectos.RETIRAR_CURAS,      			-1, TipoCaracteristica.CURACIONES);
		set(Efectos.AGREGAR_CRIATURAS_INVOCABLES,	+1, TipoCaracteristica.CRIATURAS_INVOCABLES);

		set(Efectos.AGREGAR_RES_PORC_TIERRA,	+1, TipoCaracteristica.RES_PORC_TIERRA);
		set(Efectos.AGREGAR_RES_PORC_AGUA,		+1, TipoCaracteristica.RES_PORC_AGUA);
		set(Efectos.AGREGAR_RES_PORC_AIRE,		+1, TipoCaracteristica.RES_PORC_AIRE);
		set(Efectos.AGREGAR_RES_PORC_FUEGO,		+1, TipoCaracteristica.RES_PORC_FUEGO);
		set(Efectos.AGREGAR_RES_PORC_NEUTRAL,	+1, TipoCaracteristica.RES_PORC_NEUTRAL);
		set(Efectos.RETIRAR_RES_PORC_TIERRA,	-1, TipoCaracteristica.RES_PORC_TIERRA);
		set(Efectos.RETIRAR_RES_PORC_AGUA,		-1, TipoCaracteristica.RES_PORC_AGUA);
		set(Efectos.RETIRAR_RES_PORC_AIRE,		-1, TipoCaracteristica.RES_PORC_AIRE);
		set(Efectos.RETIRAR_RES_PORC_FUEGO,		-1, TipoCaracteristica.RES_PORC_FUEGO);
		set(Efectos.RETIRAR_RES_PORC_NEUTRAL,	-1, TipoCaracteristica.RES_PORC_NEUTRAL);

		set(Efectos.AGREGAR_RES_FIJA_TIERRA,	+1, TipoCaracteristica.RES_FIJA_TIERRA);
		set(Efectos.AGREGAR_RES_FIJA_AGUA,		+1, TipoCaracteristica.RES_FIJA_AGUA);
		set(Efectos.AGREGAR_RES_FIJA_AIRE,		+1, TipoCaracteristica.RES_FIJA_AIRE);
		set(Efectos.AGREGAR_RES_FIJA_FUEGO,		+1, TipoCaracteristica.RES_FIJA_FUEGO);
		set(Efectos.AGREGAR_RES_FIJA_NEUTRAL,	+1, TipoCaracteristica.RES_FIJA_NEUTRAL);
		set(Efectos.RETIRA_RES_FIJA_TIERRA,		-1, TipoCaracteristica.RES_FIJA_TIERRA);
		set(Efectos.RETIRA_RES_FIJA_AGUA,		-1, TipoCaracteristica.RES_FIJA_AGUA);
		set(Efectos.RETIRA_RES_FIJA_AGILIDAD,	-1, TipoCaracteristica.RES_FIJA_AIRE);
		set(Efectos.RETIRA_RES_FIJA_FUEGO,		-1, TipoCaracteristica.RES_FIJA_FUEGO);
		set(Efectos.RETIRA_RES_FIJA_NEUTRAL,	-1, TipoCaracteristica.RES_FIJA_NEUTRAL);

		set(Efectos.AGREGAR_RES_PORC_PVP_TIERRA,	+1, TipoCaracteristica.RES_PORC_PVP_TIERRA);
		set(Efectos.AGREGAR_RES_PORC_PVP_AGUA,		+1, TipoCaracteristica.RES_PORC_PVP_AGUA);
		set(Efectos.AGREGAR_RES_PORC_PVP_AIRE,		+1, TipoCaracteristica.RES_PORC_PVP_AIRE);
		set(Efectos.AGREGAR_RES_PORC_PVP_FUEGO,		+1, TipoCaracteristica.RES_PORC_PVP_FUEGO);
		set(Efectos.AGREGAR_RES_PORC_PVP_NEUTRAL,	+1, TipoCaracteristica.RES_PORC_PVP_NEUTRAL);
		set(Efectos.RETIRA_RES_PORC_PVP_TIERRA,		-1, TipoCaracteristica.RES_PORC_PVP_TIERRA);
		set(Efectos.RETIRA_RES_PORC_PVP_AGUA,		-1, TipoCaracteristica.RES_PORC_PVP_AGUA);
		set(Efectos.RETIRA_RES_PORC_PVP_AIRE,		-1, TipoCaracteristica.RES_PORC_PVP_AIRE);
		set(Efectos.RETIRA_RES_PORC_PVP_FUEGO,		-1, TipoCaracteristica.RES_PORC_PVP_FUEGO);
		set(Efectos.RETIRA_RES_PORC_PVP_NEUTRAL,	-1, TipoCaracteristica.RES_PORC_PVP_NEUTRAL);

		set(Efectos.AGREGAR_RES_FIJA_PVP_TIERRA,	+1, TipoCaracteristica.RES_FIJA_PVP_TIERRA);
		set(Efectos.AGREGAR_RES_FIJA_PVP_AGUA,		+1, TipoCaracteristica.RES_FIJA_PVP_AGUA);
		set(Efectos.AGREGAR_RES_FIJA_PVP_AIRE,		+1, TipoCaracteristica.RES_FIJA_PVP_AIRE);
		set(Efectos.AGREGAR_RES_FIJA_PVP_FUEGO,		+1, TipoCaracteristica.RES_FIJA_PVP_FUEGO);
		set(Efectos.AGREGAR_RES_FIJA_PVP_NEUTRAL,	+1, TipoCaracteristica.RES_FIJA_PVP_NEUTRAL);

		set(Efectos.AGREGAR_DANOS_TRAMPA,		+1, TipoCaracteristica.DANOS_TRAMPA);
		set(Efectos.AGREGAR_PORC_DANOS_TRAMPA,	+1, TipoCaracteristica.PORC_DANOS_TRAMPA);
	}

	public CaracteristicaEfecto get_crear(final ItemsModeloEfecto efectos, boolean maximizado)
	{
		return maximizado ? get_Crear_Maximo(efectos) : get_Crear_Random(efectos);
	}

	public List<CaracteristicaEfecto> get_crear(List<ItemsModeloEfecto> efectos, boolean maximizado)
	{
		return efectos.stream().filter(efecto -> efecto.get_Efecto().get_Tipo() == Efectos.Tipo.CARACTERISTICAS).map(efecto -> get_crear(efecto, maximizado)).collect(Collectors.toList());
	}

	public Class<CaracteristicaEfecto> tipo()
	{
		return CaracteristicaEfecto.class;
	}
	
	public boolean get_Es_Negativo(Efectos efecto) 
	{
        return get(efecto).multiplicador < 0;
    }
	
	public CaracteristicaEfecto get_Crear(final Efectos efectos, int valor) 
	{
		CaracteristicaMapeada mapeado = get(efectos);

        return new CaracteristicaEfecto(efectos, valor, mapeado.multiplicador, mapeado.caracteristica);
    }
	
	public CaracteristicaEfecto get_Crear_Random(ItemsModeloEfecto efecto) 
	{
		int value = efecto.get_Minimo();

		if (efecto.get_Maximo() > value)
			value = random.nextInt(efecto.get_Maximo() - value + 1) + value;

		return get_Crear(efecto.get_Efecto(), value);
	}

	public CaracteristicaEfecto get_Crear_Maximo(ItemsModeloEfecto efecto) 
	{
		int valor;

		if (get_Es_Negativo(efecto.get_Efecto()))
			valor = efecto.get_Minimo();
		 else
			 valor = Math.max(efecto.get_Minimo(), efecto.get_Maximo());

		return get_Crear(efecto.get_Efecto(), valor);
	}
	
	private void set(final Efectos efecto, final int multiplicador, final TipoCaracteristica caracteristica) 
	{
		map.put(efecto, new CaracteristicaMapeada(multiplicador, caracteristica));
	}

	private CaracteristicaMapeada get(final Efectos efecto) 
	{
		return map.get(efecto);
	}
}
