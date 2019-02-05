package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.reflections.Reflections;

import juego.paquetes.GestorPaquetes;
import juego.paquetes.Paquete;
import main.consola.Consola;

public class Configuracion 
{
	private static Properties propiedades;
	private final static Map<String, GestorPaquetes> paquetes_emulador = new HashMap<String, GestorPaquetes>();
	private final static String nombre_archivo = "conf-game.txt";
	private static File archivo_configuracion = new File(nombre_archivo);

	/** Puertos **/
	public static int PUERTO_GAME = 5555, PUERTO_COMUNICADOR = 489;
	
	/** ACCESO SERVIDOR **/
	public static boolean ACTIVAR_FILA_DE_ESPERA = true;
	public static int MAXIMOS_FILA_ESPERA = 100;
	public static short PLAZAS_SERVIDOR = 1000, SERVIDOR_ID = 601;;
	public static byte TIPO_COMUNIDAD = 4;
	
	/** ACCESO DATABASE LOGIN **/
	public static String DATABASE_IP_LOGIN = "127.0.0.1";
	public static int DATABASE_PUERTO_LOGIN = 3306;
	public static String DATABASE_USUARIO_LOGIN = "root";
	public static String DATABASE_PASSWORD_LOGIN = "";
	public static String DATABASE_NOMBRE_LOGIN = "dofus_global";
	
	/** ACCESO DATABASE LOGIN **/
	public static String DATABASE_IP_GAME = "127.0.0.1";
	public static int DATABASE_PUERTO_GAME = 3306;
	public static String DATABASE_USUARIO_GAME = "root";
	public static String DATABASE_PASSWORD_GAME = "";
	public static String DATABASE_NOMBRE_GAME = "dofus_servidor";
	
	public static boolean cargar_Configuracion()
	{
		try 
		{
			propiedades = new Properties();
			
			try (FileInputStream fileInputStream = new FileInputStream(nombre_archivo))
			{
				propiedades.load(fileInputStream);

				PUERTO_GAME		=	Integer.valueOf(propiedades.getProperty("PUERTO_GAME"));
				PUERTO_COMUNICADOR	=	Integer.valueOf(propiedades.getProperty("PUERTO_COMUNICADOR"));
				
				DATABASE_IP_LOGIN		=	propiedades.getProperty("DATABASE_IP_LOGIN");
				DATABASE_PUERTO_LOGIN	=	Integer.valueOf(propiedades.getProperty("DATABASE_PUERTO_LOGIN"));
				DATABASE_USUARIO_LOGIN	=	propiedades.getProperty("DATABASE_USUARIO_LOGIN");
				DATABASE_PASSWORD_LOGIN	=	propiedades.getProperty("DATABASE_PASSWORD_LOGIN");
				DATABASE_NOMBRE_LOGIN	=	propiedades.getProperty("DATABASE_NOMBRE_LOGIN");
				
				SERVIDOR_ID	=	Short.valueOf(propiedades.getProperty("SERVIDOR_ID"));
				
				DATABASE_IP_GAME		=	propiedades.getProperty("DATABASE_IP_GAME");
				DATABASE_PUERTO_GAME	=	Integer.valueOf(propiedades.getProperty("DATABASE_PUERTO_GAME"));
				DATABASE_USUARIO_GAME	=	propiedades.getProperty("DATABASE_USUARIO_GAME");
				DATABASE_PASSWORD_GAME	=	propiedades.getProperty("DATABASE_PASSWORD_GAME");
				DATABASE_NOMBRE_GAME	=	propiedades.getProperty("DATABASE_NOMBRE_GAME");
				
				ACTIVAR_FILA_DE_ESPERA = Boolean.parseBoolean((propiedades.getProperty("ACTIVAR_FILA_DE_ESPERA")));
				MAXIMOS_FILA_ESPERA = Integer.valueOf(propiedades.getProperty("MAXIMOS_FILA_ESPERA"));
				PLAZAS_SERVIDOR = Short.valueOf(propiedades.getProperty("PLAZAS_SERVIDOR"));
				TIPO_COMUNIDAD = Byte.valueOf(propiedades.getProperty("TIPO_COMUNIDAD"));
				
				propiedades.clear();
				propiedades = null;
				return true;
			}
		}
		catch (final IOException e) 
		{
			Consola.println("Error en la configuracion: " + e.getMessage());
		}
		return false;
	}
	
	public static void crear_Archivo_Configuracion()
	{
		try 
		{
			propiedades = new Properties();
			
			propiedades.setProperty("PUERTO_GAME", Integer.toString(PUERTO_GAME));
			propiedades.setProperty("PUERTO_COMUNICADOR", Integer.toString(PUERTO_COMUNICADOR));
			
			propiedades.setProperty("DATABASE_IP_LOGIN", DATABASE_IP_LOGIN);
			propiedades.setProperty("DATABASE_PUERTO_LOGIN", Integer.toString(DATABASE_PUERTO_LOGIN));
			propiedades.setProperty("DATABASE_USUARIO_LOGIN", DATABASE_USUARIO_LOGIN);
			propiedades.setProperty("DATABASE_PASSWORD_LOGIN", DATABASE_PASSWORD_LOGIN);
			propiedades.setProperty("DATABASE_NOMBRE_LOGIN", DATABASE_NOMBRE_LOGIN);
			
			propiedades.setProperty("SERVIDOR_ID", Short.toString(SERVIDOR_ID));
			
			propiedades.setProperty("DATABASE_IP_GAME", DATABASE_IP_GAME);
			propiedades.setProperty("DATABASE_PUERTO_GAME", Integer.toString(DATABASE_PUERTO_GAME));
			propiedades.setProperty("DATABASE_USUARIO_GAME", DATABASE_USUARIO_GAME);
			propiedades.setProperty("DATABASE_PASSWORD_GAME", DATABASE_PASSWORD_GAME);
			propiedades.setProperty("DATABASE_NOMBRE_GAME", DATABASE_NOMBRE_GAME);
			
			propiedades.setProperty("ACTIVAR_FILA_DE_ESPERA", Boolean.toString(ACTIVAR_FILA_DE_ESPERA));
			propiedades.setProperty("MAXIMOS_FILA_ESPERA", Integer.toString(MAXIMOS_FILA_ESPERA));
			propiedades.setProperty("PLAZAS_SERVIDOR", Short.toString(PLAZAS_SERVIDOR));
			propiedades.setProperty("TIPO_COMUNIDAD", Byte.toString(TIPO_COMUNIDAD));
			
			propiedades.store(new FileOutputStream(archivo_configuracion), "Archivo de configuraci�n");
			propiedades.clear();
			propiedades = null;
		}
		catch (final IOException e) 
		{
			Consola.println("Error en la configuracion: " + e.getMessage());
		}
	}
	
	public static File get_Archivo_configuracion()
	{
		return archivo_configuracion;
	}

	public static void cargar_Paquetes()
	{
        Reflections reflections = new Reflections("juego.paquetes");
        reflections.getTypesAnnotatedWith(Paquete.class).forEach(x ->
        {
        	try 
        	{
				paquetes_emulador.put(x.getAnnotation(Paquete.class).value(), GestorPaquetes.class.cast(x.newInstance()));
			} 
        	catch (InstantiationException | IllegalAccessException e) {}
        });
    }
	
	public static Map<String, GestorPaquetes> get_Paquetes_Emulador() 
	{
		return paquetes_emulador;
	}
}