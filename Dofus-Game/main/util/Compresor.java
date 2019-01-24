package main.util;

import static main.util.Crypt.get_Hash_Desde_Index;
import static main.util.Crypt.get_Index_Desde_Hash;

import java.util.Arrays;

import main.consola.Consola;
import objetos.mapas.Celdas;
import objetos.mapas.Mapas;

final public class Compresor
{
	public static String get_Comprimir_Celda_Id(final int cellId) 
	{
		return Integer.toString(get_Hash_Desde_Index((cellId & 0xFC0) >> 6) + get_Hash_Desde_Index(cellId & 0x3F));
	}
	
	public static int get_Descomprimir_Celda_Id(final String cellId) 
	{
		return (get_Index_Desde_Hash(cellId.charAt(0)) << 6) + get_Index_Desde_Hash(cellId.charAt(1));
	}
	
	public static String get_Celdas_Descomprimidas(final Celdas[] celdas) 
	{
		return Arrays.stream(celdas).map(Compresor::get_Comprimir_Celdas).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
	}
	
	public static Celdas[] get_Descomprimir_Celdas(final String d, final Mapas mapa) 
	{
		int[] data = new int[d.length()];
		
		try 
		{
			for (int i = 0; i < data.length; i++)
				data[i] = get_Index_Desde_Hash(d.charAt(i));
		}
		catch (final Exception e) 
		{
			Consola.println("El mapa " + mapa.get_Id() + " esta con error mapData: " + mapa.get_Data());
			e.printStackTrace();
		}
		
		final boolean activa = (data[0] & 32) >> 5 == 1;
		Celdas[] celdas = new Celdas[data.length / 10];
		
		for (short i = 0; i < data.length / 10; i++)
			celdas[i] = get_Descomprimir_Celda(i, data, activa, mapa);

		return celdas;
	}
	
	public static String get_Comprimir_Celdas(final Celdas celda) 
	{
		int[] data = new int[10];
		data[0] = (celda.get_Esta_Activa() ? 1 : 0) << 5;
		data[0] = data[0] | (celda.get_Es_Linea_De_Vista() ? 1 : 0);
		data[0] = data[0] | (celda.get_Layer_Ground_Num() & 1536) >> 6;
		data[0] = data[0] | (celda.get_Layer_Objeto_1_Num() & 8192) >> 11;
		data[0] = data[0] | (celda.get_layer_Objeto_2_Num() & 8192) >> 12;
		data[1] = (celda.get_Layer_Ground_Rot() & 3) << 4;
		data[1] = data[1] | celda.get_Ground_nivel() & 15;
		data[2] = (celda.get_Tipo_Movimiento() & 7) << 3;
		data[2] = data[2] | celda.get_Layer_Ground_Num() >> 6 & 7;
		data[3] = celda.get_Layer_Ground_Num() & 63;
		data[4] = (celda.get_Ground_Slope() & 15) << 2;
		data[4] = data[4] | (celda.get_Es_Layer_Ground_Flip() ? 1 : 0) << 1;
		data[4] = data[4] | celda.get_Layer_Objeto_1_Num() >> 12 & 1;
		data[5] = celda.get_Layer_Objeto_1_Num() >> 6 & 63;
		data[6] = celda.get_Layer_Objeto_1_Num() & 63;
		data[7] = (celda.get_Layer_objeto_1_Rot() & 3) << 4;
		data[7] = data[7] | (celda.get_Es_Layer_objeto_1_Flip() ? 1 : 0) << 3;
		data[7] = data[7] | (celda.get_Es_Layer_objeto_2_Flip() ? 1 : 0) << 2;
		data[7] = data[7] | (celda.get_Es_Layer_objeto_2_Interactivo() ? 1 : 0) << 1;
		data[7] = data[7] | celda.get_layer_Objeto_2_Num() >> 12 & 1;
		data[8] = celda.get_layer_Objeto_2_Num() >> 6 & 63;
		data[9] = celda.get_layer_Objeto_2_Num() & 63;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++)
			sb.append(Crypt.get_Hash_Desde_Index(data[i]));
		return sb.toString();
	}
	
	private static Celdas get_Descomprimir_Celda(final short i, final int[] data, final boolean activa, final Mapas mapa) 
	{
		final short id = i;
		final int index = i * 10;
		final boolean linea_de_vision = (data[index] & 1) == 1;
		final byte layer_ground_rot = (byte) ((data[index + 1] & 48) >> 4);
		final byte ground_nivel = (byte) (data[index + 1] & 15);
		final byte tipo_movimiento = (byte) ((data[index + 2] & 56) >> 3);
		final byte layer_ground_num = (byte) (((data[index] & 24) << 6) + ((data[index + 2] & 7) << 6) + data[index + 3]);
		final byte ground_slope = (byte) ((data[index + 4] & 60) >> 2);
		final boolean layer_ground_flip = (data[index + 4] & 2) >> 1 == 1;
		final short layer_objeto_1_num = (short) (((data[index] & 4) << 11) + ((data[index + 4] & 1) << 12) + (data[index + 5] << 6) + data[index + 6]);
		final byte layer_objeto_1_rot = (byte) ((data[index + 7] & 48) >> 4);
		final boolean layer_objeto_1_flip = (data[index + 7] & 8) >> 3 == 1;
		final boolean layer_objeto_2_flip = (data[index + 7] & 4) >> 2 == 1;
		final boolean layer_objeto_2_interactivo = (data[index + 7] & 2) >> 1 == 1;
		final short layer_objeto_2_num = (short) (((data[index] & 2) << 12) + ((data[index + 7] & 1) << 12) + (data[index + 8] << 6) + data[index + 9]);
		
		return new Celdas(id, mapa, activa, linea_de_vision, layer_ground_num, ground_slope, layer_objeto_1_num, layer_objeto_2_num, layer_ground_rot, ground_nivel, tipo_movimiento, layer_ground_flip, layer_objeto_1_rot, layer_objeto_1_flip, layer_objeto_2_flip, layer_objeto_2_interactivo);
	}
}
