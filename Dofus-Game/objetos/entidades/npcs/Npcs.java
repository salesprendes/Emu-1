package objetos.entidades.npcs;

import java.util.StringJoiner;

import juego.enums.TipoDirecciones;
import objetos.entidades.Entidades;

public class Npcs implements Entidades
{
	private final NpcsModelo modelo;
	private short celda_id;
	private final int id;
	private TipoDirecciones orientacion;
	
	public Npcs(final NpcsModelo _modelo, final int _id, final short _celda_id, final TipoDirecciones _orientacion)
	{
		modelo = _modelo;
		id = _id;
		celda_id = _celda_id;
		orientacion = _orientacion;
	}
	
	public String get_Paquete_Gm()
	{
		final StringJoiner npc = new StringJoiner(";");
		
		npc.add(Short.toString(celda_id));
		npc.add(Byte.toString((byte) orientacion.ordinal()));
		npc.add("0");
		npc.add(Integer.toString(id));
		
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

	public TipoDirecciones get_Orientacion()
	{
		return orientacion;
	}

	public short get_Mapa_Id()
	{
		return -1;
	}

	public short get_Celda_Id()
	{
		return celda_id;
	}
}
