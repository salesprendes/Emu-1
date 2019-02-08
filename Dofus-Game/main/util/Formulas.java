package main.util;

import java.util.Calendar;

public class Formulas
{
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
