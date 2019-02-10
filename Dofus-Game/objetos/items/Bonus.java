package objetos.items;

import main.util.Formulas;

public class Bonus
{
	private short redondeado, numero, agregado;
	private final String formato;

	public Bonus(final String base, final short _redondeado, final short _numero, final short _agregado) 
	{
		formato = base.contains("+") ? base : base + "+0";
		redondeado = _redondeado;
		numero = _numero;
		agregado = _agregado;
	}

	public static Bonus get_Bonus(final String bonus_string) 
	{
		if (bonus_string.isEmpty())
			return new Bonus("0d+0", (short) 0, (short) 0, (short) 0);

		short a = (short) bonus_string.indexOf('d'), b = (short) bonus_string.indexOf('+');

		short redondeado = Short.parseShort(bonus_string.substring(0, a), 10);
		short numero = Short.parseShort(b >= 0 ? bonus_string.substring(a + 1, b) : bonus_string.substring(a + 1), 10);
		short agregado = b >= 0 ? Short.parseShort(bonus_string.substring(b + 1), 10) : 0;

		return new Bonus(bonus_string, redondeado, numero, agregado);
	}

	public short get_Medio() 
	{
		return (short) (((1 + numero) / 2 * redondeado) + agregado);
	}

	public short get_Minimo() 
	{
		return (short) (agregado == 1 ? agregado : agregado + 1);
	}

	public short get_Maximo()
	{
		return (short) ((redondeado * numero) + agregado);
	}

	public short get_Random() 
	{
		if (redondeado + numero + agregado == 0)
			return 0;
		
		int minimo = get_Minimo();
		int maximo = get_Maximo();
		return (short) Formulas.get_Random(minimo < maximo ? minimo : maximo, minimo > maximo ? minimo : maximo);
	}

	public String toString() 
	{
		return formato;
	}
}
