package objetos.items.tipos;

import java.util.ArrayList;

import main.util.Formulas;
import objetos.entidades.Experiencia;
import objetos.entidades.caracteristicas.Stats;
import objetos.entidades.hechizos.EfectoModelo;
import objetos.entidades.personajes.Items;
import objetos.items.ItemsModelo;

public class Objevivo extends Items
{
	private int experiencia, unido_objeto_id, experiencia_ganada, tipo;
	private byte apariencia, estado, nivel;
	private String fecha_ultima_comida;
	private Items objeto_base = null;
	private boolean esta_fusionado;

	public Objevivo(final int _id, final int _modelo, final int _cantidad, final byte _posicion, final Stats _stats, ArrayList<EfectoModelo> _efectos_normales)
	{
		super(_id, _modelo, _cantidad, _posicion, _stats, _efectos_normales);

		nivel = 1;
		experiencia = 0;
		tipo = item_modelo.get_Tipo_Objevivo();
		apariencia = 1;
		estado = 0;
		experiencia_ganada = 0;
		fecha_ultima_comida = "";
		set_Desequipar();
		objeto_base.get_Stats().set_Stat_Texto(805, Formulas.get_Nueva_Fecha("#"), true);
		es_objevivo = true;
	}
	
	public Objevivo(final Items objeto, final byte _apariencia, final byte _nivel, final int _experiencia, final byte _estado, final int _experiencia_ganada, final String _fecha_ultima_comida, final int _unido_objeto_id, final int _tipo)
	{
		//constructor padre
		super(objeto.get_Id(), objeto.get_Item_modelo().get_Id(), objeto.get_Cantidad(), objeto.get_Posicion_inventario(), objeto.get_Convertir_Stats_A_String());

		nivel = _nivel;
		experiencia = _experiencia;
		tipo = _tipo;
		apariencia = _apariencia;
		estado = _estado;
		experiencia_ganada = _experiencia_ganada;
		fecha_ultima_comida = _fecha_ultima_comida;
		unido_objeto_id = _unido_objeto_id;
		es_objevivo = true;
	}

	public void get_Stats(final String Stats) 
	{
		for (final String separador : Stats.split(",")) 
		{
			final String[] stats = separador.split("#");
			final short stat_id = Short.parseShort(stats[0], 16);
			
			switch (stat_id) 
			{
				case 808:
					fecha_ultima_comida = Formulas.get_Fecha_Stats(stats);
					final int comidas_perdidas = Formulas.get_Comidas_Perdidas(fecha_ultima_comida);
					if (comidas_perdidas > 0)
						agregar_experiencia_ganada(-comidas_perdidas);
				break;
				
				case 972:
					apariencia = Byte.parseByte(stats[3], 16);
				break;
				
				case 974:
					experiencia = Integer.parseInt(stats[3], 16);
				break;
				
				case 970:
					item_modelo = ItemsModelo.get_Items_Cargados(Integer.parseInt(stats[3], 16));
				break;
			}
		}
	}
	
	public boolean get_Puede_Alimentar_Objevivo() 
	{
		long minimo_tiempo_comida = 43200000;
		
		try 
		{
			String fecha = fecha_ultima_comida;
			if (fecha.contains("-"))
			{
				if (!Formulas.get_Comprar_fechas(fecha, minimo_tiempo_comida))
					return true;
			} 
			else 
			{
				fecha = Formulas.get_Fecha_Stats(("325#" + stats.get_Stat_Texto(805)).split("#"));
				if (!Formulas.get_Comprar_fechas(fecha, minimo_tiempo_comida))
					return true;
			}
		} 
		catch (final Exception e)
		{
			return false;
		}
		return false;
	}
	
	public void agregar_experiencia_ganada(final int numero) 
	{
		experiencia_ganada += numero;
	}
	
	public void subir_Nivel(final boolean agregar_experiencia) 
	{
		if (nivel > 20)
			return;
		
		nivel += 1;
		if (agregar_experiencia)
			experiencia = Experiencia.get_Experiencia_Objevivos(nivel);
	}
	
	public void agregar_Experiencia(long xp_ganada) 
	{
		if (xp_ganada < 1) 
			xp_ganada = 1;
		
		if (nivel >= 20) 
		{
			comer_Sin_Subir(xp_ganada);
			return;
		}
		
		experiencia = (int) (experiencia + xp_ganada);
		experiencia_ganada = (int) (experiencia_ganada + xp_ganada);
		
		if (experiencia_ganada > Math.round((Experiencia.get_Experiencia_Objevivos(nivel + 1) / 10))) 
		{
			experiencia_ganada = 0;
			if (estado == 0)
				aumentar_Estado();
		}
		
		while (experiencia >= Experiencia.get_Experiencia_Objevivos(nivel) && nivel < 20)
			subir_Nivel(false);
	}
	
	public void comer_Sin_Subir(long xp_ganada)
	{
		if (xp_ganada < 1)
			xp_ganada = 1;
		
		experiencia_ganada = (int) (experiencia_ganada + xp_ganada);
		
		if (experiencia_ganada > Math.round((Experiencia.get_Experiencia_Objevivos(nivel + 1) / 10)))
		{
			experiencia_ganada = 0;
			if (estado == 0)
				aumentar_Estado();
		}
	}
	
	public void aumentar_Estado() 
	{
		if (estado >= 2)
			estado = 2;
		estado += 1;
	}
	
	public void bajar_Estado() 
	{
		if (estado <= 0)
			estado = 0;
		estado -= 1;
	}
	
	public synchronized void set_Equipar(final Items objeto) 
	{
		unido_objeto_id = objeto.get_Id();
		objeto_base = objeto;
		esta_fusionado = true;
	}
	
	public synchronized void set_Desequipar()
	{
		unido_objeto_id = -1;
		esta_fusionado = false;
		objeto_base = null;
	}

	public static Objevivo get_Objevivo(final Items item) 
	{
		return (Objevivo) item;
	}

	public boolean get_Esta_fusionado()
	{
		return esta_fusionado;
	}

	public void set_Esta_fusionado(final boolean _esta_fusionado)
	{
		esta_fusionado = _esta_fusionado;
	}
	
	public int get_Tipo() 
	{
		return tipo;
	}
	
	public int get_Unido_objeto_id()
	{
		return unido_objeto_id;
	}

	public void set_Unido_objeto_id(int unido_objeto_id)
	{
		this.unido_objeto_id = unido_objeto_id;
	}

	public static Objevivo get_Crear_Objevivo(final int id, final int modelo, final int cantidad, final byte posicion, final Stats stats, final ArrayList<EfectoModelo> efectos) 
	{
		return new Objevivo(id, modelo, cantidad, posicion, stats, efectos);
	}

	public byte get_Apariencia()
	{
		return apariencia;
	}

	public void set_Apariencia(byte apariencia)
	{
		this.apariencia = apariencia;
	}
}
