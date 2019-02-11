package objetos.entidades.npcs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import objetos.items.ItemsModelo;

final public class NpcsModelo
{
	private final short id;
	private final byte sexo;
	private short gfx, foto, X, Y;
	private int color_1, color_2, color_3, pregunta;
	private String accesorios = "";
	private final ItemsModelo[] objetos_vendidos;

	final public static Map<Short, NpcsModelo> npcs_cargados = new HashMap<Short, NpcsModelo>();
	
	public NpcsModelo(final short _id, final short _gfx, final short _X, final short _Y, final byte _sexo, final int color_1, final int color_2, final int color_3, final String _accesorios, final short _foto, final int _pregunta, final String _ventas)
	{
		id = _id;
		gfx = _gfx;
		X = _X;
		Y = _Y;
		sexo = _sexo;
		set_Colores(color_1, color_2, color_3);
		
		final String[] separador_accesorios = _accesorios.split(Pattern.quote(","), 5);
		if (separador_accesorios.length == 5)
		{
			accesorios = Arrays.stream(separador_accesorios).map(accesorio -> Integer.toHexString(Short.parseShort(accesorio))).collect(Collectors.joining(","));
		}
		
		foto = _foto;
		pregunta = _pregunta;

		if (!_ventas.isEmpty()) 
		{
			ItemsModelo item_modelo;
			String[] objetos = _ventas.split(Pattern.quote(","));
			objetos_vendidos = new ItemsModelo[objetos.length];
			
			for(int i = 0; i < objetos_vendidos.length; ++i)
			{
				try 
				{
					item_modelo = ItemsModelo.get_Items_Cargados(Short.parseShort(objetos[i]));
					if (item_modelo != null)
						objetos_vendidos[i] = item_modelo;
				}
				catch (final NumberFormatException e) {}
			}
		}
		else
			objetos_vendidos = new ItemsModelo[0];
		
		npcs_cargados.put(id, this);
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

	public int get_Pregunta()
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

	public ItemsModelo[] get_Objetos_vendidos()
	{
		return objetos_vendidos;
	}

	public final short get_Id()
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
        return Arrays.stream(objetos_vendidos).filter(objeto -> objeto.get_Id() == id).findFirst().isPresent();
    }
	
	public String[] get_Array_Colores() 
	{
		return Arrays.stream(new int[] {color_1, color_2, color_3}).mapToObj(valor -> valor == -1 ? "-1" : Integer.toHexString(valor)).toArray(String[]::new);
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
	
	public static Map<Short, NpcsModelo> get_Npcs_Cargados()
	{
		return npcs_cargados;
	}
	
	public static NpcsModelo get_Npcs_Cargados(final short npc_modelo_id)
	{
		return npcs_cargados.get(npc_modelo_id);
	}
}
