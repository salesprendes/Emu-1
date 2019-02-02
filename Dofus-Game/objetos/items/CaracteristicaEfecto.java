package objetos.items;

import juego.enums.Efectos;
import juego.enums.TipoCaracteristica;

final public class CaracteristicaEfecto implements ItemEfecto
{
	final private Efectos efecto;
	final private int valor;
	final private int multiplicador;
	final private TipoCaracteristica caracteristica;

	public CaracteristicaEfecto(final Efectos _efecto, final int _valor, final int _multiplicador, final TipoCaracteristica _caracteristica) 
	{
		efecto = _efecto;
		valor = _valor;
		multiplicador = _multiplicador;
		caracteristica = _caracteristica;
	}

	public Efectos get_Efecto()
	{
		return efecto;
	}

	public ItemsModeloEfecto get_Efecto_Modelo()
	{
		return new ItemsModeloEfecto(efecto, valor, 0, 0, "0d0+"+valor);
	}

	public int get_Valor() 
	{
		return valor;
	}

	public TipoCaracteristica get_Caracteristica() 
	{
		return caracteristica;
	}

	public int boost() 
	{
		return multiplicador * valor;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (getClass() != o.getClass())
			return false;

		CaracteristicaEfecto that = (CaracteristicaEfecto) o;

		return valor == that.get_Valor() && efecto == that.get_Efecto();
	}

	public int hashCode() 
	{
		int result = efecto.hashCode();
		result = 31 * result + valor;

		return result;
	}

	public String toString() 
	{
		return efecto + " : " + valor;
	}
}
