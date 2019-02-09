package objetos.entidades.npcs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import objetos.items.ItemsModelo;

public class NpcsModelo
{
	private final int id;
	private final byte sexo;
	private short gfx, foto, X, Y, pregunta;
	private int color_1, color_2, color_3;
	private String accesorios;
	private List<ItemsModelo> objetos_vendidos;

	public NpcsModelo(final int _id, final short _gfx, final short _X, final short _Y, final byte _sexo, final int color_1, final int color_2, final int color_3, final String _accesorios, final short _foto, final short _pregunta, final String _ventas)
	{
		id = _id;
		gfx = _gfx;
		X = _X;
		Y = _Y;
		sexo = _sexo;
		set_Colores(color_1, color_2, color_3);
		accesorios = _accesorios;
		foto = _foto;
		pregunta = _pregunta;

		if (!_ventas.isEmpty()) 
		{
			objetos_vendidos = new ArrayList<ItemsModelo>();
			ItemsModelo item_modelo;
			for (final String obj : _ventas.split(Pattern.quote(",")))
			{
				try 
				{
					item_modelo = ItemsModelo.get_Items_Cargados(Short.parseShort(obj));
					if (item_modelo != null)
						objetos_vendidos.add(item_modelo);
				}
				catch (final NumberFormatException e) {}
			}
		}
	}

	public short get_Gfx()
	{
		return gfx;
	}

	public void set_Gfx(final short _gfx)
	{
		gfx = _gfx;
	}

	public short get_Foto()
	{
		return foto;
	}

	public void set_Foto(final short _foto)
	{
		foto = _foto;
	}

	public short get_X()
	{
		return X;
	}

	public void set_X(final short x)
	{
		X = x;
	}

	public short get_Y()
	{
		return Y;
	}

	public void set_Y(final short y)
	{
		Y = y;
	}

	public short get_Pregunta()
	{
		return pregunta;
	}

	public void set_Pregunta(final short _pregunta)
	{
		pregunta = _pregunta;
	}

	public int get_Color_1()
	{
		return color_1;
	}

	public void set_Color_1(final int _color_1)
	{
		color_1 = _color_1;
	}

	public int get_Color_2()
	{
		return color_2;
	}

	public void set_Color_2(final int color_2)
	{
		this.color_2 = color_2;
	}

	public int get_Color_3()
	{
		return color_3;
	}

	public void set_Color_3(final int color_3)
	{
		this.color_3 = color_3;
	}

	public List<ItemsModelo> get_Objetos_vendidos()
	{
		return objetos_vendidos;
	}

	public final int get_Id()
	{
		return id;
	}

	public byte get_Sexo()
	{
		return sexo;
	}

	public String get_Accesorios()
	{
		return accesorios;
	}
	
	public boolean get_Tiene_Objeto(int id) 
	{
        return objetos_vendidos.stream().filter(objeto -> objeto.get_Id() == id).findFirst().isPresent();
    }

	public final void set_Colores(int _color_1, int _color_2, int _color_3) 
	{
		if (_color_1 < -1)
			_color_1 = -1;
		else if (_color_1 > 16777215)
			_color_1 = 16777215;

		if (_color_2 < -1)
			_color_2 = -1;
		else if (_color_2 > 16777215)
			_color_2 = 16777215;

		if (_color_3 < -1)
			_color_3 = -1;
		else if (_color_3 > 16777215)
			_color_3 = 16777215;

		color_1 = _color_1;
		color_2 = _color_2;
		color_3 = _color_3;
	}
}
