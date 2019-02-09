package main.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Formulas
{
	public static int get_Comidas_Perdidas(final String ExDate) 
	{
		final Calendar actual = GregorianCalendar.getInstance();
		final long tiempo_actual = actual.getTimeInMillis();
		actual.clear();
		final String[] separador = ExDate.split("-");
		actual.set(Integer.parseInt(separador[0]), Integer.parseInt(separador[1]), Integer.parseInt(separador[2]), Integer.parseInt(separador[3]), Integer.parseInt(separador[4]));
		final int diferencia_tiempo = (int) ((tiempo_actual - actual.getTimeInMillis()) / Long.parseLong("259200000"));

		return diferencia_tiempo;
	}
	
	public static String get_Fecha_Stats(final String[] stats) 
	{
		final StringBuilder str = new StringBuilder("");
		str.append(Integer.parseInt(stats[1], 16)).append('-');

		final String fecha = get_Fecha_Correcta(Integer.toString(Integer.parseInt(stats[2], 16)));
		try 
		{
			str.append(Integer.parseInt(fecha.replaceAll(fecha.substring(2), "")) + 1).append('-');
		} 
		catch (final Exception e) 
		{
			str.append("00").append('-');
		}
		
		try 
		{
			str.append(fecha.substring(2)).append('-');
		} 
		catch (final Exception e) 
		{
			str.append("00").append('-');
		}

		final String tiempo = get_Fecha_Correcta("" + Integer.parseInt(stats[3], 16));
		try 
		{
			str.append(tiempo.replaceAll(tiempo.substring(2), "")).append('-');
		} 
		catch (final Exception e) 
		{
			str.append("00").append('-');
		}
		
		try 
		{
			str.append(tiempo.substring(2));
		} 
		catch (final Exception e) 
		{
			str.append("00");
		}
		
		return str.toString();
	}
	
	public static String get_Fecha_Correcta(final String str) 
	{
		switch (str.length()) 
		{
			case 0:
			return "0000";
			
			case 1:
			return "000" + str;
			
			case 2:
			return "00" + str;
			
			case 3:
			return "0" + str;
			
			case 4:
			return str;
		}
		return null;
	}
	
	public static boolean get_Comprar_fechas(final String fecha, final long tiempo) 
	{
		final Calendar hoy = GregorianCalendar.getInstance();
		final long hoy_milisegundos = hoy.getTimeInMillis();

		hoy.clear();
		final String[] separador_fecha = fecha.split("-");
		hoy.set(Integer.parseInt(separador_fecha[0]), Integer.parseInt(separador_fecha[1]), Integer.parseInt(separador_fecha[2]), Integer.parseInt(separador_fecha[3]), Integer.parseInt(separador_fecha[4]));

		return hoy.getTimeInMillis() + tiempo >= hoy_milisegundos;
	}
	
	public static String get_Nueva_Fecha(final String separador) 
	{
		final Calendar hoy = Calendar.getInstance();
		int ano = hoy.get(Calendar.YEAR);
		int dia = hoy.get(Calendar.DAY_OF_MONTH);
		int mes = (hoy.get(Calendar.MONTH) + 1);
		int hora = hoy.get(Calendar.HOUR_OF_DAY);
		int minutos = hoy.get(Calendar.MINUTE);

		return Integer.toHexString(ano) + separador + Integer.toHexString(Integer.parseInt(new StringBuilder(mes).append(dia).toString())) + separador + Integer.toHexString(Integer.parseInt(new StringBuilder(hora).append(minutos).toString()));
	}
}
