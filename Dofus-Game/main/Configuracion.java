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

	/** ACCESO DATABASE **/
	public static String DATABASE_IP = "127.0.0.1";
	public static int DATABASE_PUERTO = 3306;
	public static String DATABASE_USUARIO = "root";
	public static String DATABASE_PASSWORD = "";
	public static String DATABASE_NOMBRE_LOGIN = "dofus_global";
	public static String DATABASE_NOMBRE_GAME = "dofus_servidor";
	

	public static boolean cargar_Configuracion()
	{
		try (FileInputStream fileInputStream = new FileInputStream(nombre_archivo))
		{
			propiedades = new Properties();
			propiedades.load(fileInputStream);

			PUERTO_GAME		=	Integer.valueOf(propiedades.getProperty("PUERTO_GAME"));
			PUERTO_COMUNICADOR	=	Integer.valueOf(propiedades.getProperty("PUERTO_COMUNICADOR"));

			DATABASE_IP		=	propiedades.getProperty("DATABASE_IP");
			DATABASE_PUERTO	=	Integer.valueOf(propiedades.getProperty("DATABASE_PUERTO"));
			DATABASE_USUARIO	=	propiedades.getProperty("DATABASE_USUARIO");
			DATABASE_PASSWORD	=	propiedades.getProperty("DATABASE_PASSWORD");
			DATABASE_NOMBRE_LOGIN	=	propiedades.getProperty("DATABASE_NOMBRE_LOGIN");
			DATABASE_NOMBRE_GAME	=	propiedades.getProperty("DATABASE_NOMBRE_GAME");

			SERVIDOR_ID	=	Short.valueOf(propiedades.getProperty("SERVIDOR_ID"));
			
			ACTIVAR_FILA_DE_ESPERA = Boolean.parseBoolean((propiedades.getProperty("ACTIVAR_FILA_DE_ESPERA")));
			MAXIMOS_FILA_ESPERA = Integer.valueOf(propiedades.getProperty("MAXIMOS_FILA_ESPERA"));
			PLAZAS_SERVIDOR = Short.valueOf(propiedades.getProperty("PLAZAS_SERVIDOR"));
			TIPO_COMUNIDAD = Byte.valueOf(propiedades.getProperty("TIPO_COMUNIDAD"));

			propiedades.clear();
			propiedades = null;
			return true;
		}
		catch (final Exception e) 
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

			propiedades.setProperty("PUERTO_GAME", Integer.toString(PUERTO_GAME));
			propiedades.setProperty("PUERTO_COMUNICADOR", Integer.toString(PUERTO_COMUNICADOR));

			propiedades.setProperty("DATABASE_IP", DATABASE_IP);
			propiedades.setProperty("DATABASE_PUERTO", Integer.toString(DATABASE_PUERTO));
			propiedades.setProperty("DATABASE_USUARIO", DATABASE_USUARIO);
			propiedades.setProperty("DATABASE_PASSWORD", DATABASE_PASSWORD);
			propiedades.setProperty("DATABASE_NOMBRE_LOGIN", DATABASE_NOMBRE_LOGIN);
			propiedades.setProperty("DATABASE_NOMBRE_GAME", DATABASE_NOMBRE_GAME);

			propiedades.setProperty("SERVIDOR_ID", Short.toString(SERVIDOR_ID));
			
			propiedades.setProperty("ACTIVAR_FILA_DE_ESPERA", Boolean.toString(ACTIVAR_FILA_DE_ESPERA));
			propiedades.setProperty("MAXIMOS_FILA_ESPERA", Integer.toString(MAXIMOS_FILA_ESPERA));
			propiedades.setProperty("PLAZAS_SERVIDOR", Short.toString(PLAZAS_SERVIDOR));
			propiedades.setProperty("TIPO_COMUNIDAD", Byte.toString(TIPO_COMUNIDAD));

			propiedades.store(new FileOutputStream(archivo_configuracion), "Archivo de configuración");
			propiedades.clear();
			propiedades = null;
			archivo_configuracion = null;
		}
		catch (final IOException e) 
		{
			Consola.println("Error en la configuracion: " + e.getMessage());
		}
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
