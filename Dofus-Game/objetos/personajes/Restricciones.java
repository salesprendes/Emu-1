package objetos.personajes;

final public class Restricciones 
{
	private boolean PUEDE_SER_AGREDIDO;
	private boolean PUEDE_SER_DESAFIADO;
	private boolean PUEDE_HACER_INTERCAMBIO;
	private boolean PUEDE_SER_ATACADO;
	private boolean PUEDE_CORRER;
	private boolean ES_LENTO;
	private boolean PUEDE_CAMBIAR_MODO_CRIATURA;
	private boolean ES_TUMBA;

	public Restricciones(int restriccion)
	{
		PUEDE_SER_AGREDIDO = (restriccion & 1) != 1;
		PUEDE_SER_DESAFIADO = (restriccion & 2) != 2;
		PUEDE_HACER_INTERCAMBIO = (restriccion & 4) != 4;
		PUEDE_SER_ATACADO = (restriccion & 8) != 8;
		PUEDE_CORRER = (restriccion & 16) == 16;
		ES_LENTO = (restriccion & 32) == 32;
		PUEDE_CAMBIAR_MODO_CRIATURA = (restriccion & 64) != 64;
		ES_TUMBA = (restriccion & 128) == 128;
	}

	public int get_Restricciones() 
	{
		int restricciones = 0;

		if (!PUEDE_SER_AGREDIDO)
			restricciones ++;
		if (!PUEDE_SER_DESAFIADO)
			restricciones += 2;
		if (!PUEDE_HACER_INTERCAMBIO)
			restricciones += 4;
		if (!PUEDE_SER_ATACADO)
			restricciones += 8;
		if (PUEDE_CORRER)
			restricciones += 16;
		if (ES_LENTO)
			restricciones += 32;
		if (!PUEDE_CAMBIAR_MODO_CRIATURA)
			restricciones += 64;
		if (ES_TUMBA)
			restricciones += 128;

		return restricciones;
	}
	
	public final String get_Convertir_Base36() 
	{
		String convertido = "";
		try 
		{
			convertido = Integer.toString(get_Restricciones(), 36);
		} 
		catch (final Throwable ex) 
		{
			convertido = "6bk";
		}
		return convertido;
	}
	
	public void get_Modificar_Todo(boolean valor) 
	{
		PUEDE_SER_AGREDIDO = valor;
		PUEDE_SER_DESAFIADO = valor;
		PUEDE_HACER_INTERCAMBIO = valor;
		PUEDE_SER_ATACADO = valor;
		PUEDE_CORRER = valor;
		ES_LENTO = valor;
		PUEDE_CAMBIAR_MODO_CRIATURA = valor;
		ES_TUMBA = valor;
	}

	public final boolean get_Puede_Ser_Agredido()
	{
		return PUEDE_SER_AGREDIDO;
	}

	public final void set_Puede_Ser_Agredido(final boolean puede_ser_agredido)
	{
		PUEDE_SER_AGREDIDO = puede_ser_agredido;
	}

	public final boolean get_Puede_Ser_Desafiado()
	{
		return PUEDE_SER_DESAFIADO;
	}

	public final void set_Puede_Ser_Desafiado(final boolean puede_ser_desafiado)
	{
		PUEDE_SER_DESAFIADO = puede_ser_desafiado;
	}

	public final boolean get_Puede_Hacer_Intercambio()
	{
		return PUEDE_HACER_INTERCAMBIO;
	}

	public final void set_Puede_Hacer_Intercambio(final boolean puede_hacer_intercambio)
	{
		PUEDE_HACER_INTERCAMBIO = puede_hacer_intercambio;
	}

	public final boolean get_Puede_Ser_Atacado()
	{
		return PUEDE_SER_ATACADO;
	}

	public final void set_Puede_Ser_Atacado(final boolean puede_ser_atacado)
	{
		PUEDE_SER_ATACADO = puede_ser_atacado;
	}

	public final boolean get_Puede_Correr()
	{
		return PUEDE_CORRER;
	}

	public final void set_Puede_Correr(final boolean puede_correr)
	{
		PUEDE_CORRER = puede_correr;
	}

	public final boolean get_Es_Lento()
	{
		return ES_LENTO;
	}

	public final void set_Es_Lento(final boolean es_lento)
	{
		ES_LENTO = es_lento;
	}

	public final boolean get_Puede_Cambiar_Modo_Criatura()
	{
		return PUEDE_CAMBIAR_MODO_CRIATURA;
	}

	public final void set_Puede_Cambiar_Modo_Criatura(final boolean puede_cambiar_modo_criatura)
	{
		PUEDE_CAMBIAR_MODO_CRIATURA = puede_cambiar_modo_criatura;
	}

	public final boolean get_Es_Tumba()
	{
		return ES_TUMBA;
	}

	public final void set_Es_Tumba(final boolean es_tumba)
	{
		ES_TUMBA = es_tumba;
	}
}
