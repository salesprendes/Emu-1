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
	public static int SERVIDOR_ID = 601;
	
	/** Puertos **/
	public static int PUERTO_GAME = 5555;
	public static int PUERTO_COMUNICADOR = 489;
	
	/** ACCESO DATABASE LOGIN **/
	public static String DATABASE_IP_LOGIN = "127.0.0.1";
	public static int DATABASE_PUERTO_LOGIN = 3306;
	public static String DATABASE_USUARIO_LOGIN = "root";
	public static String DATABASE_PASSWORD_LOGIN = "";
	public static String DATABASE_NOMBRE_LOGIN = "dofus_global";
	
	public static boolean cargar_Configuracion()
	{
		try 
		{
			if(new File("conf-game.txt").exists())
			{
				propiedades = new Properties();
				propiedades.load(new FileInputStream("conf-game.txt"));
				
				//Puertos
				PUERTO_GAME		=	Integer.valueOf(propiedades.getProperty("PUERTO_GAME"));
				PUERTO_COMUNICADOR	=	Integer.valueOf(propiedades.getProperty("PUERTO_COMUNICADOR"));
				
				DATABASE_IP_LOGIN		=	propiedades.getProperty("DATABASE_IP_LOGIN");
				DATABASE_PUERTO_LOGIN	=	Integer.valueOf(propiedades.getProperty("DATABASE_PUERTO_LOGIN"));
				DATABASE_USUARIO_LOGIN	=	propiedades.getProperty("DATABASE_USUARIO_LOGIN");
				DATABASE_PASSWORD_LOGIN	=	propiedades.getProperty("DATABASE_PASSWORD_LOGIN");
				DATABASE_NOMBRE_LOGIN	=	propiedades.getProperty("DATABASE_NOMBRE_LOGIN");
				
				SERVIDOR_ID	=	Integer.valueOf(propiedades.getProperty("SERVIDOR_ID"));
				
				propiedades.clear();
				propiedades = null;
			}
			else
			{
				crear_Archivo_Configuracion();
			}
			return true;
		}
		catch (final IOException e) 
		{
			System.out.println("Error en la configuracion: " + e.getMessage());
		}
		System.exit(0);
		return false;
	}
	
	private static void crear_Archivo_Configuracion() throws IOException
	{
		propiedades = new Properties();
		
		propiedades.setProperty("PUERTO_GAME", Integer.toString(PUERTO_GAME));
		propiedades.setProperty("PUERTO_COMUNICADOR", Integer.toString(PUERTO_COMUNICADOR));
		
		propiedades.setProperty("DATABASE_IP_LOGIN", DATABASE_IP_LOGIN);
		propiedades.setProperty("DATABASE_PUERTO_LOGIN", Integer.toString(DATABASE_PUERTO_LOGIN));
		propiedades.setProperty("DATABASE_USUARIO_LOGIN", DATABASE_USUARIO_LOGIN);
		propiedades.setProperty("DATABASE_PASSWORD_LOGIN", DATABASE_PASSWORD_LOGIN);
		propiedades.setProperty("DATABASE_NOMBRE_LOGIN", DATABASE_NOMBRE_LOGIN);
		
		propiedades.setProperty("SERVIDOR_ID", Integer.toString(SERVIDOR_ID));
		
		propiedades.store(new FileOutputStream(new File("conf-game.txt")), "Archivo de configuración");
		propiedades.clear();
		propiedades = null;
	}
}
