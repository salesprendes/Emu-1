package objetos.personajes;

final public class Derechos
{
	private boolean PUEDE_AGREDIR;
	private boolean PUEDE_DESAFIAR;
	private boolean PUEDE_INTERCAMBIAR;
	private boolean PUEDE_ATACAR;
	private boolean PUEDE_HABLAR_CON_TODOS;
	private boolean PUEDE_SER_MERCANTE;
	private boolean PUEDE_UTILIZAR_OBJETOS;
	private boolean PUEDE_INTERACTUAR_CON_RECAUDADORES;
	private boolean PUEDE_UTILIZAR_OBJETOS_INTERACTIVOS;
	private boolean PUEDE_HABLAR_NPC;
	private boolean PUEDE_ATACAR_MOBS_EN_DUNG_SIENDO_MUTANTE;
	private boolean PUEDE_MOVERSE_TODAS_DIRECCIONES;
	private boolean PUEDE_ATACAR_MONSTRUOS_CUALQUIER_LUGAR_SIENDO_MUTANTE;
	private boolean PUEDE_INTERACTUAR_PRISMA;

	public Derechos(short derecho) 
	{
		PUEDE_AGREDIR = (derecho & 1) != 1;
		PUEDE_DESAFIAR = (derecho & 2) != 2;
		PUEDE_INTERCAMBIAR = (derecho & 4) != 4;
		PUEDE_ATACAR = (derecho & 8) == 8;
		PUEDE_HABLAR_CON_TODOS = (derecho & 16) != 16;
		PUEDE_SER_MERCANTE = (derecho & 32) != 32;
		PUEDE_UTILIZAR_OBJETOS = (derecho & 64) != 64;
		PUEDE_INTERACTUAR_CON_RECAUDADORES = (derecho & 128) != 128;
		PUEDE_UTILIZAR_OBJETOS_INTERACTIVOS = (derecho & 256) != 256;
		PUEDE_HABLAR_NPC = (derecho & 512) != 512;
		PUEDE_ATACAR_MOBS_EN_DUNG_SIENDO_MUTANTE = (derecho & 4096) == 4096;
		PUEDE_MOVERSE_TODAS_DIRECCIONES = (derecho & 8192) == 8192;
		PUEDE_ATACAR_MONSTRUOS_CUALQUIER_LUGAR_SIENDO_MUTANTE = (derecho & 16384) == 16384;
		PUEDE_INTERACTUAR_PRISMA = (derecho & 32768) != 32768;
	}

	public final int get_Derechos() 
	{
		int derechos = 0;

		if (!PUEDE_AGREDIR)
			derechos++;
		if (!PUEDE_DESAFIAR)
			derechos += 2;
		if (!PUEDE_INTERCAMBIAR)
			derechos += 4;
		if (PUEDE_ATACAR)
			derechos += 8;
		if (!PUEDE_HABLAR_CON_TODOS)
			derechos += 16;
		if (!PUEDE_SER_MERCANTE)
			derechos += 32;
		if (!PUEDE_UTILIZAR_OBJETOS)
			derechos += 64;
		if (!PUEDE_INTERACTUAR_CON_RECAUDADORES)
			derechos += 128;
		if (!PUEDE_UTILIZAR_OBJETOS_INTERACTIVOS)
			derechos += 256;
		if (PUEDE_HABLAR_NPC)
			derechos += 512;
		if (PUEDE_ATACAR_MOBS_EN_DUNG_SIENDO_MUTANTE)
			derechos += 4096;
		if (PUEDE_MOVERSE_TODAS_DIRECCIONES)
			derechos += 8192;
		if (PUEDE_ATACAR_MONSTRUOS_CUALQUIER_LUGAR_SIENDO_MUTANTE)
			derechos += 16384;
		if (!PUEDE_INTERACTUAR_PRISMA)
			derechos += 32768;

		return derechos;
	}

	public final String get_Convertir_Base36() 
	{
		String convertido = "";
		try 
		{
			convertido = Integer.toString(get_Derechos(), 36);
		} 
		catch (final Throwable ex) 
		{
			convertido = "6bk";
		}
		return convertido;
	}

	public void get_Modificar_Todo(boolean valor) 
	{
		PUEDE_AGREDIR = valor;
		PUEDE_DESAFIAR = valor;
		PUEDE_INTERCAMBIAR = valor;
		PUEDE_ATACAR = valor;
		PUEDE_HABLAR_CON_TODOS = valor;
		PUEDE_SER_MERCANTE = valor;
		PUEDE_UTILIZAR_OBJETOS = valor;
		PUEDE_INTERACTUAR_CON_RECAUDADORES = valor;
		PUEDE_UTILIZAR_OBJETOS_INTERACTIVOS = valor;
		PUEDE_HABLAR_NPC = valor;
		PUEDE_ATACAR_MOBS_EN_DUNG_SIENDO_MUTANTE = valor;
		PUEDE_MOVERSE_TODAS_DIRECCIONES = valor;
		PUEDE_ATACAR_MONSTRUOS_CUALQUIER_LUGAR_SIENDO_MUTANTE = valor;
		PUEDE_INTERACTUAR_PRISMA = valor;
	}

	public final boolean get_Puede_Agredir()
	{
		return PUEDE_AGREDIR;
	}

	public final void set_Puede_Agredir(final boolean puede_agredir)
	{
		PUEDE_AGREDIR = puede_agredir;
	}

	public final boolean get_Puede_Desafiar()
	{
		return PUEDE_DESAFIAR;
	}

	public final void set_Puede_Desafiar(final boolean puede_desafiar)
	{
		PUEDE_DESAFIAR = puede_desafiar;
	}

	public final boolean get_Puede_Intercambiar()
	{
		return PUEDE_INTERCAMBIAR;
	}

	public final void set_Puede_Intercambiar(final boolean puede_intercambiar)
	{
		PUEDE_INTERCAMBIAR = puede_intercambiar;
	}

	public final boolean get_Puede_Atacar()
	{
		return PUEDE_ATACAR;
	}

	public final void set_Puede_Atacar(final boolean puede_atacar)
	{
		PUEDE_ATACAR = puede_atacar;
	}

	public final boolean get_Puede_Hablar_Con_Todos()
	{
		return PUEDE_HABLAR_CON_TODOS;
	}

	public final void set_Puede_Hablar_Con_Todos(final boolean puede_hablar_con_todos)
	{
		PUEDE_HABLAR_CON_TODOS = puede_hablar_con_todos;
	}

	public final boolean get_Puede_Ser_Mercante()
	{
		return PUEDE_SER_MERCANTE;
	}

	public final void set_Puede_Ser_Mercante(final boolean puede_ser_mercante)
	{
		PUEDE_SER_MERCANTE = puede_ser_mercante;
	}

	public final boolean get_Puede_Utilizar_Objetos()
	{
		return PUEDE_UTILIZAR_OBJETOS;
	}

	public final void set_Puede_Utilizar_Objetos(final boolean puede_utilizar_objetos)
	{
		PUEDE_UTILIZAR_OBJETOS = puede_utilizar_objetos;
	}

	public final boolean get_Puede_Interactuar_Con_Recaudadores()
	{
		return PUEDE_INTERACTUAR_CON_RECAUDADORES;
	}

	public final void set_Puede_Interactuar_Con_Recaudadores(final boolean puede_interactuar_con_recaudadores)
	{
		PUEDE_INTERACTUAR_CON_RECAUDADORES = puede_interactuar_con_recaudadores;
	}

	public final boolean get_Puede_Utilizar_Objetos_Interactivos()
	{
		return PUEDE_UTILIZAR_OBJETOS_INTERACTIVOS;
	}

	public final void set_Puede_Utilizar_Objetos_Interactivos(final boolean puede_utilizar_objetos_interactivos)
	{
		PUEDE_UTILIZAR_OBJETOS_INTERACTIVOS = puede_utilizar_objetos_interactivos;
	}

	public final boolean get_Puede_Hablar_Npc()
	{
		return PUEDE_HABLAR_NPC;
	}

	public final void set_Puede_Hablar_Npc(final boolean puede_hablar_npc)
	{
		PUEDE_HABLAR_NPC = puede_hablar_npc;
	}

	public final boolean get_Puede_Atacar_Mobs_En_Dung_Siendo_Mutante()
	{
		return PUEDE_ATACAR_MOBS_EN_DUNG_SIENDO_MUTANTE;
	}

	public final void set_Puede_Atacar_Mobs_En_Dung_Siendo_Mutante(final boolean puede_stacar_mobs_en_dung_siendo_mutante)
	{
		PUEDE_ATACAR_MOBS_EN_DUNG_SIENDO_MUTANTE = puede_stacar_mobs_en_dung_siendo_mutante;
	}

	public final boolean get_Puede_Moverse_Todas_Direcciones()
	{
		return PUEDE_MOVERSE_TODAS_DIRECCIONES;
	}

	public final void set_Puede_Moverse_Todas_Direcciones(final boolean puede_moverse_todas_direcciones)
	{
		PUEDE_MOVERSE_TODAS_DIRECCIONES = puede_moverse_todas_direcciones;
	}

	public final boolean get_Puede_Atacar_Monstruos_Cualquier_Lugar_Siendo_Mutante()
	{
		return PUEDE_ATACAR_MONSTRUOS_CUALQUIER_LUGAR_SIENDO_MUTANTE;
	}

	public final void set_Puede_Atacar_Monstruos_Cualquier_Lugar_Siendo_Mutante(final boolean puede_atacar_monstruos_cualquier_lugar_diendo_mutante)
	{
		PUEDE_ATACAR_MONSTRUOS_CUALQUIER_LUGAR_SIENDO_MUTANTE = puede_atacar_monstruos_cualquier_lugar_diendo_mutante;
	}

	public final boolean get_Puede_Interactuar_Prisma()
	{
		return PUEDE_INTERACTUAR_PRISMA;
	}

	public final void set_Puede_Interactuar_Prisma(final boolean puede_interactuar_prisma)
	{
		PUEDE_INTERACTUAR_PRISMA = puede_interactuar_prisma;
	}
}
