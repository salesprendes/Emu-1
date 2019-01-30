package objetos.entidades.personajes;

final public class Derechos
{
	public static final int PUEDE_AGREDIR = 1;
	public static final int PUEDE_DESAFIAR = 2;
	public static final int PUEDE_INTERCAMBIAR = 4;
	public static final int PUEDE_ATACAR = 8;
	public static final int PUEDE_HABLAR_CON_TODOS = 16;
	public static final int PUEDE_SER_MERCANTE = 32;
	public static final int PUEDE_UTILIZAR_OBJETOS = 64;
	public static final int PUEDE_INTERACTUAR_CON_RECAUDADORES = 128;
	public static final int PUEDE_UTILIZAR_OBJETOS_INTERACTIVOS = 256;
	public static final int PUEDE_HABLAR_NPC = 512;
	public static final int PUEDE_ATACAR_MOBS_EN_DUNG_SIENDO_MUTANTE = 4096;
	public static final int PUEDE_MOVERSE_TODAS_DIRECCIONES = 8192;
	public static final int PUEDE_ATACAR_MONSTRUOS_CUALQUIER_LUGAR_SIENDO_MUTANTE = 16384;
	public static final int PUEDE_INTERACTUAR_PRISMA = 32768;
	private int valor_derechos;
	
	public Derechos(int derecho) 
	{
		valor_derechos = derecho;
	}
	
	public int get_Valor_Derechos()
	{
		return valor_derechos;
	}

	public boolean get_Derechos(int condicion)
	{
		return (valor_derechos & condicion) == condicion;
	}
	
	public void set_Derechos(final int restriccion, final int modificado)
	{
		valor_derechos = (valor_derechos | restriccion) ^ (restriccion ^ modificado);
	}

	public final String get_Convertir_Base36() 
	{
		String convertido = "";
		try 
		{
			convertido = Integer.toString(valor_derechos, 36);
		} 
		catch (final Throwable ex) 
		{
			convertido = "6bk";
		}
		return convertido;
	}
}
