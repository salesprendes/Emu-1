package objetos.items.tipos;

import java.util.ArrayList;

import main.util.Formulas;
import objetos.entidades.caracteristicas.Stats;
import objetos.entidades.hechizos.EfectoModelo;
import objetos.entidades.personajes.Items;

public class Objevivo extends Items
{
	private int experiencia, unido_objeto_id, experiencia_ganada, tipo;
	private byte apariencia, estado, nivel;
	private String ultima_comida;
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
		ultima_comida = "";
		set_Desequipar();
		objeto_base.get_Stats().set_Stat_Texto(805, Formulas.get_Nueva_Fecha("#"), true);
		es_objevivo = true;
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
}
