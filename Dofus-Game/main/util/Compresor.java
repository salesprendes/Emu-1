package main.util;

import static main.util.Crypt.get_Index_Desde_Hash;

import main.consola.Consola;
import objetos.mapas.Celdas;
import objetos.mapas.Mapas;

final public class Compresor
{
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
	
	public static String get_Comprimir_Celdas(final boolean[] permisos, final int[] valores)
	{
		int i = 0;
		int finalPermiso = 0;
		for (boolean b : permisos) 
		{
			if (b)
				finalPermiso += (1 << i);
			i++;
		}
		
		String fP = Integer.toHexString(finalPermiso);
		int[] preData = new int[10];
		preData[0] = ((valores[13] == 1) ? (1) : (0)) << 5;
		preData[0] = preData[0] | (valores[12] == 1 ? (1) : (0));
		preData[0] = preData[0] | (valores[8] & 1536) >> 6;
		preData[0] = preData[0] | (valores[5] & 8192) >> 11;
		preData[0] = preData[0] | (valores[2] & 8192) >> 12;
		preData[1] = (valores[3] & 3) << 4;
		preData[1] = preData[1] | valores[10] & 15;
		preData[2] = (valores[11] & 7) << 3;
		preData[2] = preData[2] | valores[8] >> 6 & 7;
		preData[3] = valores[8] & 63;
		preData[4] = (valores[9] & 15) << 2;
		preData[4] = preData[4] | ((valores[7] == 1) ? (1) : (0)) << 1;
		preData[4] = preData[4] | valores[5] >> 12 & 1;
		preData[5] = valores[5] >> 6 & 63;
		preData[6] = valores[5] & 63;
		preData[7] = (valores[3] & 3) << 4;
		preData[7] = preData[7] | ((valores[4] == 1) ? (1) : (0)) << 3;
		preData[7] = preData[7] | ((valores[1] == 1) ? (1) : (0)) << 2;
		preData[7] = preData[7] | ((valores[0] == 1) ? (1) : (0)) << 1;
		preData[7] = preData[7] | valores[2] >> 12 & 1;
		preData[8] = valores[2] >> 6 & 63;
		preData[9] = valores[2] & 63;
		
		StringBuilder sb = new StringBuilder();
		for (int d : preData)
		{
			sb.append(Crypt.get_Hash_Desde_Index(d));
		}
		return sb.toString() + fP;
	}
	
	private static Celdas get_Descomprimir_Celda(final short i, final int[] data, final boolean activa, final Mapas mapa) 
	{
		final short id = i;
		final int index = i * 10;
		final boolean linea_de_vision = (data[index] & 1) == 1;
		final boolean tiene_objeto_interactivo = (data[index + 7] & 2) >> 1 == 1;
		final byte tipo_movimiento = (byte) ((data[index + 2] & 56) >> 3);
		final byte ground_nivel = (byte) (data[index + 1] & 15);
		final byte ground_slope = (byte) ((data[index + 4] & 60) >> 2);
		final short layer_objeto_2_num = (short) (((data[index] & 2) << 12) + ((data[index + 7] & 1) << 12) + (data[index + 8] << 6) + data[index + 9]);
		
		return new Celdas(id, mapa, activa, tipo_movimiento, ground_nivel, ground_slope, linea_de_vision, tiene_objeto_interactivo ? layer_objeto_2_num : -1);
	}
}
