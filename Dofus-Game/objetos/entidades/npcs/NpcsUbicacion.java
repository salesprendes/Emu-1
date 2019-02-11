package objetos.entidades.npcs;

import java.util.StringJoiner;

import juego.enums.TipoDirecciones;
import objetos.entidades.Entidades;
import objetos.entidades.Localizacion;
import objetos.mapas.Celdas;

public class NpcsUbicacion implements Entidades
{
	private final NpcsModelo modelo;
	final private Localizacion posicion = new Localizacion(TipoDirecciones.ABAJO_DERECHA);
	private final int id;
	
	public NpcsUbicacion(final NpcsModelo _modelo, final int _id, final Celdas celda, final byte orientacion)
	{
		modelo = _modelo;
		id = _id;
		posicion.set_Celda(celda);
		posicion.set_Orientacion(TipoDirecciones.values()[orientacion]);
	}
	
	public String get_Paquete_Gm()
	{
		final StringJoiner npc = new StringJoiner(";");
		
		npc.add(Short.toString(posicion.get_Celda().get_Id()));
		npc.add(Integer.toString(posicion.get_Orientacion().ordinal()));
		npc.add("0");
		npc.add(Integer.toString(id));
		npc.add(Short.toString(modelo.get_Id()));
		npc.add(Integer.toString(get_Tipo().get_Id()));
		npc.add(modelo.get_Gfx() + "^" + modelo.get_X() + "x" + modelo.get_Y());
		npc.add(Byte.toString(modelo.get_Sexo()));
		npc.add(String.join(";", modelo.get_Array_Colores()));
		npc.add(modelo.get_Accesorios());
		npc.add("-4");//Signo de admiracion
		npc.add(Short.toString((modelo.get_Foto())));
		
		return npc.toString();
	}

	public int get_Id()
	{
		return id;
	}
	
	public NpcsModelo get_Modelo() 
	{
		return modelo;
	}

	public TipoEntidades get_Tipo()
	{
		return TipoEntidades.NPC;
	}

	public Localizacion get_Localizacion()
	{
		return posicion;
	}
}
