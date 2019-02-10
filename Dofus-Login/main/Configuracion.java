package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.reflections.Reflections;

import login.paquetes.GestorPaquetes;
import login.paquetes.Paquete;
import main.consola.Consola;

public class Configuracion 
{
	private static Properties propiedades;
	private final static Map<String, GestorPaquetes> paquetes_emulador = new HashMap<String, GestorPaquetes>();
	private final static String nombre_archivo = "conf-login.txt";
	private static File archivo_configuracion = new File(nombre_archivo);
	
	/** Puertos **/
	public static int PUERTO_LOGIN = 443, PUERTO_COMUNICADOR = 489;
	
	/** ACCESO LOGIN **/
	public static int MAXIMOS_LOGINS_FILA_ESPERA = 100;
	public static short POBLACION_RECOMENDADA = 1000, POBLACION_MEDIA_ALTA = 500, POBLACION_ALTA = 300, POBLACION_BAJA = 200, POBLACION_COMPLETA = 20;
	
	/** ACCESO DATABASE **/
	public static String DATABASE_IP_LOGIN = "127.0.0.1";
	public static int DATABASE_PUERTO_LOGIN = 3306;
	public static String DATABASE_USUARIO_LOGIN = "root";
	public static String DATABASE_PASSWORD_LOGIN = "";
	public static String DATABASE_NOMBRE_LOGIN = "dofus_global";
	
	public static boolean cargar_Configuracion()
	{
		try (FileInputStream fileInputStream = new FileInputStream(nombre_archivo))
		{
			propiedades = new Properties();
			propiedades.load(fileInputStream);

			//Puertos
			PUERTO_LOGIN		=	Integer.valueOf(propiedades.getProperty("PUERTO_LOGIN"));
			PUERTO_COMUNICADOR	=	Integer.valueOf(propiedades.getProperty("PUERTO_COMUNICADOR"));
			DATABASE_PUERTO_LOGIN	=	Integer.valueOf(propiedades.getProperty("DATABASE_PUERTO_LOGIN"));

			//Acceso Database
			DATABASE_IP_LOGIN		=	propiedades.getProperty("DATABASE_IP_LOGIN");
			DATABASE_USUARIO_LOGIN	=	propiedades.getProperty("DATABASE_USUARIO_LOGIN");
			DATABASE_PASSWORD_LOGIN	=	propiedades.getProperty("DATABASE_PASSWORD_LOGIN");
			DATABASE_NOMBRE_LOGIN	=	propiedades.getProperty("DATABASE_NOMBRE_LOGIN");

			//Otros
			MAXIMOS_LOGINS_FILA_ESPERA = Integer.valueOf(propiedades.getProperty("MAXIMOS_LOGINS_FILA_ESPERA"));
			POBLACION_RECOMENDADA = Short.valueOf(propiedades.getProperty("POBLACION_RECOMENDADA"));
			POBLACION_MEDIA_ALTA = Short.valueOf(propiedades.getProperty("POBLACION_MEDIA_ALTA"));
			POBLACION_ALTA = Short.valueOf(propiedades.getProperty("POBLACION_ALTA"));
			POBLACION_BAJA = Short.valueOf(propiedades.getProperty("POBLACION_BAJA"));
			POBLACION_COMPLETA = Short.valueOf(propiedades.getProperty("POBLACION_COMPLETA"));

			propiedades.clear();
			propiedades = null;
			return true;
		}
		catch (final IOException e) 
		{
			Consola.println("Error en la configuracion: " + e.getMessage());
			Consola.println("creando archivo de configuración por defecto");
			archivo_configuracion.delete();
			crear_Archivo_Configuracion();
			return false;
		}
	}
	
	public static void crear_Archivo_Configuracion()
	{
		try 
		{
			propiedades = new Properties();
			
			propiedades.setProperty("PUERTO_LOGIN", Integer.toString(PUERTO_LOGIN));
			propiedades.setProperty("PUERTO_COMUNICADOR", Integer.toString(PUERTO_COMUNICADOR));
			
			propiedades.setProperty("DATABASE_IP_LOGIN", DATABASE_IP_LOGIN);
			propiedades.setProperty("DATABASE_PUERTO_LOGIN", Integer.toString(DATABASE_PUERTO_LOGIN));
			propiedades.setProperty("DATABASE_USUARIO_LOGIN", DATABASE_USUARIO_LOGIN);
			propiedades.setProperty("DATABASE_PASSWORD_LOGIN", DATABASE_PASSWORD_LOGIN);
			propiedades.setProperty("DATABASE_NOMBRE_LOGIN", DATABASE_NOMBRE_LOGIN);
			
			propiedades.setProperty("MAXIMOS_LOGINS_FILA_ESPERA", Integer.toString(MAXIMOS_LOGINS_FILA_ESPERA));
			
			propiedades.setProperty("POBLACION_RECOMENDADA", Short.toString(POBLACION_RECOMENDADA));
			propiedades.setProperty("POBLACION_MEDIA_ALTA", Short.toString(POBLACION_MEDIA_ALTA));
			propiedades.setProperty("POBLACION_ALTA", Short.toString(POBLACION_ALTA));
			propiedades.setProperty("POBLACION_BAJA", Short.toString(POBLACION_BAJA));
			propiedades.setProperty("POBLACION_COMPLETA", Short.toString(POBLACION_COMPLETA));
			
			propiedades.store(new FileOutputStream(archivo_configuracion), "Archivo de configuración");
			propiedades.clear();
			propiedades = null;
			archivo_configuracion = null;
		}
		catch (final IOException e) 
		{
			Consola.println("Error en la configuracion por defecto: " + e.getMessage());
		}
	}
	
	public static void cargar_Paquetes()
	{
        Reflections reflections = new Reflections("login.paquetes");
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
