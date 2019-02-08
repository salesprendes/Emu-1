package database.objetos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.zaxxer.hikari.HikariDataSource;

import database.DatabaseManager;
import juego.enums.TipoEfectoArea;
import main.consola.Consola;
import main.util.Crypt;
import objetos.entidades.hechizos.EfectoArea;
import objetos.entidades.hechizos.EfectoModelo;
import objetos.entidades.hechizos.HechizoModelo;
import objetos.entidades.hechizos.HechizoRango;
import objetos.entidades.hechizos.HechizoStats;

public class Hechizos_DB extends DatabaseManager
{
	final static public byte EFECTOS_NORMALES = 0;
	final static public byte EFECTOS_CRITICOS = 1;
	final static public byte COSTE_PA = 2;
	final static public byte RANGO_MINIMO = 3;
	final static public byte RANGO_MAXIMO = 4;
	final static public byte PROBABILIDAD_CRITICO = 5;
	final static public byte PROBABILIDAD_FALLO = 6;
	final static public byte LANZADO_SOLAMENTE_LINEA = 7;
	final static public byte LINEA_DE_VISION = 8;
	final static public byte CELDA_LIBRE = 9;
	final static public byte RANGO_MODIFICABLE = 10;
	final static public byte TIPO_HECHIZO = 11;
	final static public byte LANZAMIENTOS_POR_TURNO = 12;
	final static public byte LANZAMIENTOS_POR_OBJETIVO = 13;
	final static public byte LANZAMIENTOS_DELAY = 14;
	final static public byte AREAS_DE_EFECTO = 15;
	final static public byte ESTADOS_NECESARIOS = 16;
	final static public byte ESTADOS_PROHIBIDOS = 17;
	final static public byte NIVEL_REQUERIDO = 18;
	final static public byte ACABA_TURNO_FALLO_CRITICO = 19;

	public Hechizos_DB(HikariDataSource database_conexion) 
	{
		super(database_conexion);
	}

	public void get_Cargar_Todos_Hechizos()
	{
		int hechizo_id = 0;
		try
		{
			final Ejecucion_Query query = ejecutar_Query_Select("SELECT * FROM hechizos;");
			while(query.get_Rs().next())
			{
				//id(1), nombre(2), sprite(3), sprite_infos(4), nivel_1(5), nivel_2(6), nivel_3(7), nivel_4(8), nivel_5(9), nivel_6(10), afectados(11), descripcion(12)
				hechizo_id = query.get_Rs().getInt(1);
				HechizoModelo.get_hechizos_Cargados().put(hechizo_id, new HechizoModelo(hechizo_id, query.get_Rs().getInt(3), query.get_Rs().getString(4), new HechizoStats[]{ deserializar_Hechizo(query.get_Rs().getString(5)), deserializar_Hechizo(query.get_Rs().getString(6)), deserializar_Hechizo(query.get_Rs().getString(7)), deserializar_Hechizo(query.get_Rs().getString(8)), deserializar_Hechizo(query.get_Rs().getString(9)), deserializar_Hechizo(query.get_Rs().getString(10)) }, Arrays.stream(query.get_Rs().getString(11).split(":")).mapToInt(Integer::parseInt).toArray()));
			}
			cerrar(query);
		}
		catch (final Exception e)
		{
			Consola.println("error al cargar el hechizo id: " + hechizo_id + " " + e);
		}
	}

	public HechizoStats deserializar_Hechizo(final String cadena) 
	{
		if (cadena.isEmpty() || cadena.equals("-1"))
			return null;

		String[] separador = cadena.split(",");

		try 
		{
			return verificar(new HechizoStats(efectos(separador[EFECTOS_NORMALES]), efectos(separador[EFECTOS_CRITICOS]), get_Byte(separador[COSTE_PA]), get_Rango_Hechizo(separador[RANGO_MINIMO], separador[RANGO_MAXIMO]), get_Short(separador[PROBABILIDAD_CRITICO]), get_Short(separador[PROBABILIDAD_FALLO]), get_Booleano(separador[LANZADO_SOLAMENTE_LINEA]), get_Booleano(separador[LINEA_DE_VISION]), get_Booleano(separador[CELDA_LIBRE]), get_Booleano(separador[RANGO_MODIFICABLE]), get_Byte(separador[TIPO_HECHIZO]), get_Byte(separador[LANZAMIENTOS_POR_TURNO]), get_Byte(separador[LANZAMIENTOS_POR_OBJETIVO]), get_Byte(separador[LANZAMIENTOS_DELAY]), get_Areas(separador[AREAS_DE_EFECTO]), get_Estados(separador[ESTADOS_NECESARIOS]), get_Estados(separador[ESTADOS_PROHIBIDOS]), get_Integro(separador[NIVEL_REQUERIDO]), get_Booleano(separador[ACABA_TURNO_FALLO_CRITICO])));
		} 
		catch (RuntimeException e) 
		{
			throw new IllegalArgumentException("error hechizo stats: " + e);
		}
	}

	private HechizoStats verificar(HechizoStats stats) 
	{
		if (stats.get_Efectos_criticos().size() + stats.get_Efectos_normales().size() != stats.get_Areas_de_efecto().size())
			throw new IllegalArgumentException("areas de efecto incorrectas");

		if (stats.get_Rango().get_Maximo() < stats.get_Rango().get_Minimo())
			throw new IllegalArgumentException("rango del hechizo incorrecto");

		return stats;
	}

	private List<EfectoModelo> efectos(final String efectos) 
	{
		return efectos.equals("-1") ? new ArrayList<EfectoModelo>() : Arrays.stream(efectos.split(Pattern.quote("|"))).map(this::efecto).collect(Collectors.toList());
	}

	private EfectoModelo efecto(final String efecto) 
	{
		String[] parametros = efecto.split(Pattern.quote(";"), 7);
		return new EfectoModelo(get_Integro(parametros[0]), get_Integro(parametros[1]), get_Integro(parametros[2]), get_Integro(parametros[3]), get_Integro(parametros[4]), get_Integro(parametros[5]), parametros.length == 7 ? parametros[6] : null);
	}

	private int get_Integro(final String valor) 
	{
		return valor.isEmpty() ? 0 : Integer.parseInt(valor);
	}
	
	private byte get_Byte(final String valor) 
	{
		return valor.isEmpty() ? 0 : Byte.parseByte(valor);
	}
	
	private short get_Short(final String valor) 
	{
		return valor.isEmpty() ? 0 : Short.parseShort(valor);
	}
	
	private boolean get_Booleano(final String valor) 
	{
		return valor.equals("true");
	}

	private int[] get_Estados(final String estados) 
	{
		return Arrays.stream(estados.split(";")).mapToInt(Integer::parseInt).toArray();
	}

	private List<EfectoArea> get_Areas(final String sAreas) 
	{
		List<EfectoArea> areas = new ArrayList<EfectoArea>(sAreas.length() / 2);

		for (int i = 0; i < sAreas.length(); i += 2)
			areas.add(deserializar_EfectoArea(sAreas.substring(i, i + 2)));

		return areas;
	}

	private HechizoRango get_Rango_Hechizo(final String minimo, final String maximo) 
	{
		return new HechizoRango(get_Byte(minimo), get_Byte(maximo));
	}

	public String serializar_EfectoArea(final EfectoArea valor) 
	{
		return valor == null ? null : new String(new char[] {valor.get_Tipo().get_Char(), Crypt.get_Hash_Desde_Index(valor.get_Tamano())});
	}

	public EfectoArea deserializar_EfectoArea(final String cadena) 
	{
		return cadena == null ? null : new EfectoArea(TipoEfectoArea.get_Tipo_Efecto_Area(cadena.charAt(0)), Crypt.get_ordinal(cadena.charAt(1)));
	}
}
