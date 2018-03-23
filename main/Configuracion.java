package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracion 
{
	private static Properties propiedades;
	
	/** Puertos **/
	public static int PUERTO_LOGIN = 449;
	public static int PUERTO_INTERCAMBIO = 489;
	
	/** ACCESO DATABASE **/
	public static String DATABASE_IP_LOGIN = "127.0.0.1";
	public static int DATABASE_PUERTO_LOGIN = 3306;
	public static String DATABASE_USUARIO_LOGIN = "root";
	public static String DATABASE_PASSWORD_LOGIN = "";
	public static String DATABASE_NOMBRE_LOGIN = "dofus_global";
	
	public static void cargar_Configuracion()
	{
		try 
		{
			if(new File("configuracion.txt").exists())
			{
				propiedades = new Properties();
				propiedades.load(new FileInputStream("configuracion.txt"));
				
				//Puertos
				PUERTO_LOGIN		=	Integer.valueOf(propiedades.getProperty("PUERTO_LOGIN"));
				PUERTO_INTERCAMBIO	=	Integer.valueOf(propiedades.getProperty("PUERTO_INTERCAMBIO"));
				
				DATABASE_IP_LOGIN		=	propiedades.getProperty("DATABASE_IP_LOGIN");
				DATABASE_PUERTO_LOGIN	=	Integer.valueOf(propiedades.getProperty("DATABASE_PUERTO_LOGIN"));
				DATABASE_USUARIO_LOGIN	=	propiedades.getProperty("DATABASE_USUARIO_LOGIN");
				DATABASE_PASSWORD_LOGIN	=	propiedades.getProperty("DATABASE_PASSWORD_LOGIN");
				DATABASE_NOMBRE_LOGIN	=	propiedades.getProperty("DATABASE_NOMBRE_LOGIN");
				
				propiedades.clear();
				propiedades = null;
			}
			else
			{
				crear_Archivo_Configuracion();
			}
		}
		catch (final IOException e) 
		{
			System.out.println("Error en la configuracion: " + e.getMessage());
			System.exit(1);
			return;
		}
	}
	
	private static void crear_Archivo_Configuracion() throws IOException
	{
		FileOutputStream nuevo_archivo = new FileOutputStream(new File("configuracion.txt"));
		propiedades = new Properties();
		
		propiedades.setProperty("PUERTO_LOGIN", Integer.toString(PUERTO_LOGIN));
		propiedades.setProperty("PUERTO_INTERCAMBIO", Integer.toString(PUERTO_INTERCAMBIO));
		
		propiedades.setProperty("DATABASE_IP_LOGIN", DATABASE_IP_LOGIN);
		propiedades.setProperty("DATABASE_PUERTO_LOGIN", Integer.toString(DATABASE_PUERTO_LOGIN));
		propiedades.setProperty("DATABASE_USUARIO_LOGIN", DATABASE_USUARIO_LOGIN);
		propiedades.setProperty("DATABASE_PASSWORD_LOGIN", DATABASE_PASSWORD_LOGIN);
		propiedades.setProperty("DATABASE_NOMBRE_LOGIN", DATABASE_NOMBRE_LOGIN);
		
		propiedades.store(nuevo_archivo, "Archivo de configuración");
		nuevo_archivo.close();
		propiedades.clear();
		propiedades = null;
	}
}
