package main;

import database.ConexionPool;
import juego.JuegoServer;
import juego.JuegoVinculador;
import juego.fila.FilaServer;
import main.consola.Consola;
import objetos.cuentas.Cuentas;
import objetos.entidades.Experiencia;
import objetos.entidades.alineamientos.AlineamientosModelo;
import objetos.entidades.monstruos.MonstruosModelo;
import objetos.entidades.monstruos.MonstruosRaza;
import objetos.entidades.monstruos.MonstruosSuperRaza;
import objetos.entidades.personajes.Dones;
import objetos.entidades.personajes.Especialidades;
import objetos.entidades.personajes.Personajes;
import objetos.entidades.personajes.Razas;
import objetos.items.ItemsModelo;
import objetos.mapas.Areas;
import objetos.mapas.Mapas;
import objetos.mapas.SubAreas;
import objetos.mapas.SuperAreas;

public class Main 
{
	public static boolean modo_debug = false;
	public static boolean esta_vinculado = false;
	public static EstadosEmulador estado_emulador = EstadosEmulador.APAGADO;
	
	/** THREADS **/
	public static FilaServer fila_espera;
	public static JuegoVinculador socket_vinculador;
	public static JuegoServer juego_servidor;
	public static Consola comandos_consola;
	private static ConexionPool database = new ConexionPool();
	
	public static void main(String[] args) 
	{
		Runtime.getRuntime().addShutdownHook(new Thread(() -> cerrar_Emulador()));
		
		Consola.print("Cargando la configuración: ");
		if(Configuracion.cargar_Configuracion())
			Consola.println("correcta");
		else
		{
			Consola.println("incorrecta, creando archivo de configuración por defecto");
			Configuracion.get_Archivo_configuracion().delete();
			Configuracion.crear_Archivo_Configuracion();
		}
		
		/** Conexion a la database **/
		database.cargar_Configuracion();
		Consola.print("Conectando a la base de datos del login: ");
		if(database.comprobar_conexion(database.get_Login_Database()))
			Consola.println("correcta");
		else
			Consola.println("incorrecta");
		
		Consola.print("Conectando a la base de datos del game: ");
		if(database.comprobar_conexion(database.get_Game_Database()))
			Consola.println("correcta");
		else
			Consola.println("incorrecta");
		database.iniciar_Database();
		
		/** Cargando el servidor **/
		estado_emulador = EstadosEmulador.CARGANDO;
		get_Cargar_Juego();
		Configuracion.cargar_Paquetes();
		
		/** Vincular el game **/
		estado_emulador = EstadosEmulador.VINCULANDO;
		Vincular_Login();
		
		/** Crear Servers **/
		if(Configuracion.ACTIVAR_FILA_DE_ESPERA)
			fila_espera = new FilaServer();
		juego_servidor = new JuegoServer();
		comandos_consola = new Consola();
		estado_emulador = EstadosEmulador.ENCENDIDO;
	}
	
	public static void Vincular_Login()
	{
		if(!esta_vinculado)
		{
			while(!esta_vinculado && estado_emulador == EstadosEmulador.VINCULANDO)
			{
				socket_vinculador = new JuegoVinculador();
			}
			if(esta_vinculado)
				Consola.println("LoginServer y GameServer vinculados.");
			else
				Consola.println("Intentando vincular con LoginServer");
		}
	}
	
	public static void get_Cargar_Juego()
	{
		Consola.print("Cargando cuentas: ");
		database.get_Cuentas().get_Cargar_Todas();
		Consola.println(Cuentas.get_Cuentas_Cargadas().size() + " cuentas cargadas");
		
		Consola.print("Cargando las super-areas: ");
		database.get_Mapas().get_Cargar_Todas_Super_Areas();
		Consola.println(SuperAreas.get_SuperAreas_Cargadas().size() + " super-areas cargadas");
		
		Consola.print("Cargando las areas: ");
		database.get_Mapas().get_Cargar_Todas_Areas();
		Consola.println(Areas.get_Areas_Cargadas().size() + " areas cargadas");
		
		Consola.print("Cargando las sub-areas: ");
		database.get_Mapas().get_Cargar_Todas_Sub_Areas();
		Consola.println(SubAreas.get_Sub_Areas_Cargadas().size() + " sub-areas cargadas");
		
		Consola.print("Cargando los mapas: ");
		database.get_Mapas().get_Cargar_Todos_Mapas();
		Consola.println(Mapas.get_Mapas_Cargados().size() + " mapas cargados");
		
		Consola.print("Cargando las experiencias: ");
		database.get_Experiencia().get_Cargar_Todas_Experiencia();
		Consola.println(Experiencia.get_Experiencias_Cargadas().size() + " experiencias cargadas");
		
		Consola.print("Cargando alineamientos: ");
		database.get_Alineamientos().get_Cargar_Todos_Alineamientos();
		Consola.println(AlineamientosModelo.get_Alineamientos_Cargados().size() + " alineamientos cargados");
		
		Consola.print("Cargando especialidades: ");
		database.get_Alineamientos().get_Cargar_Todas_Especialidades();
		Consola.println(Especialidades.get_Especialidades().size() + " especialidades cargadas");
		
		Consola.print("Cargando dones: ");
		database.get_Alineamientos().get_Cargar_Todos_Dones();
		Consola.println(Dones.get_Dones().size() + " dones cargados");
		
		Consola.print("Cargando razas: ");
		database.get_Razas().get_Cargar_Todas_Razas();
		Consola.println(Razas.get_Razas_Cargadas().size() + " razas cargadas");
		
		Consola.print("Cargando items modelo: ");
		database.get_Items().get_Cargar_Todos_Items();
		Consola.println(ItemsModelo.get_Items_Cargados().size() + " items modelo cargados");
		
		Consola.print("Cargando personajes: ");
		database.get_Personajes().get_Cargar_Todos_Personajes();
		database.get_Personajes().get_Cargar_Alineaciones_Personajes();
		database.get_Personajes().get_Cargar_Items_Personajes();
		Consola.println(Personajes.get_Personajes_Cargados().size() + " personajes cargados");
		
		Consola.print("Cargando super razas de los monstruos: ");
		database.get_Monstruos().get_Cargar_Super_Razas();
		Consola.println(MonstruosSuperRaza.get_Super_Razas_Cargadas().size() + " super razas de monstruos cargadas");
		
		Consola.print("Cargando razas de los monstruos: ");
		database.get_Monstruos().get_Cargar_Razas();
		Consola.println(MonstruosRaza.get_Razas_Cargadas().size() + " razas de monstruos cargadas");
		
		Consola.print("Cargando monstruo modelo: ");
		database.get_Monstruos().get_Cargar_Todos_Monstruos();
		Consola.println(MonstruosModelo.get_Monstruos_Modelo_Cargados().size() + " monstruo modelo cargados");
	}
	
	public static void cerrar_Emulador()
	{
		Consola.println("> El servidor se esta cerrando");
		if (estado_emulador == EstadosEmulador.ENCENDIDO)
		{
			if(fila_espera != null)
				fila_espera.detener_Fila();
			if(socket_vinculador != null)
				socket_vinculador.cerrar_Conexion();
			if(comandos_consola != null)
				comandos_consola.interrupt();
			if(juego_servidor != null)
				juego_servidor.detener_Server_Socket();
			estado_emulador = EstadosEmulador.APAGADO;
		}
		Consola.println("> El emulador esta cerrado.");
		System.exit(1);
	}
	
	public static ConexionPool get_Database()
	{
		return database;
	}
}
