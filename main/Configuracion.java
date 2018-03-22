package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuracion 
{
	/** Puertos **/
	public static int LOGIN_PORT = 449;
	public static int INTERCAMBIO_PUERTO = 489;

	public static void configuracion()
	{
		try 
		{
			Properties configuracion = new Properties();
			configuracion.load(new FileInputStream("configuracion.txt"));

			//Puertos
			LOGIN_PORT			=	Integer.valueOf(configuracion.getProperty("PUERTO_LOGIN"));
			INTERCAMBIO_PUERTO	=	Integer.valueOf(configuracion.getProperty("PUERTO_INTERCAMBIO"));
		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
