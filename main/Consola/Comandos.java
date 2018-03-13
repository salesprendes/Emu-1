package main.Consola;

import main.Main;

public class Comandos 
{
	public static void ejecutar(String comando)
	{
		 String[] comando_args = comando.split(" ");
		 
		 switch(comando_args[0].toLowerCase().trim())
		 {

		 	case "?":
		 		lista_Comandos();
			break;
			 
		 	case "memoria":
		 		ver_Datos_Memoria(comando_args);
			break;
		 	
		 	case "threads":
		 		ver_Threads_Activos();
		 	break;
		 	
		 	case "limpiar":
		 	case "clear":
		 		limpiar_Consola();
		 	break;
		 	
		 	case "debug":
		 		opcion_Debug(comando_args);
		 	break;
			
			default:
				System.out.println("Comando no encontrado");
			break;
		 }
	 }
	 
	 protected static void ver_Datos_Memoria(String[] args)
	 {
		 int mb = 1024*1024;
		 Runtime runtime = Runtime.getRuntime();
		 System.out.println("Memoria utilizada: " + (runtime.totalMemory() - runtime.freeMemory()) / mb + " mb");
		 System.out.println("Memoria libre: " + runtime.freeMemory() / mb + " mb");
		 System.out.println("Memoria total: " + runtime.totalMemory() / mb + " mb");
		 System.out.println("Memoria máxima: " + runtime.maxMemory() / mb + " mb");
		 
		 if(args.length > 1 && args[1].toLowerCase().equals("limpiar"))
		 {
			 Runtime.getRuntime().gc();
		 }
	 }
	 
	 protected static void ver_Threads_Activos()
	 {
		 Thread.getAllStackTraces().keySet().forEach(t ->
		 {
			 System.out.println(t.getName() + " es Daemon: " + t.isDaemon() + " esta activo: " + t.isAlive());
		 });
	 }
	 
	 protected static void lista_Comandos()
	 {
		 System.out.println("threads: ver los threads activos");
		 System.out.println("memoria: permite ver el estado de la memoria del heap Parametro: limpia (invoca el garbage)");
		 System.out.println("limpiar/clear: limpia la consola");
		 System.out.println("debug: (on, off) permite activar/desactivar para ver los mensajes debug");
		 System.out.println("? - ver los comandos"); 
	 }
	 
	 protected static void limpiar_Consola()
	 {
		 try
		 {
			 if (System.getProperty("os.name").startsWith("Windows"))
			 {
				 new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			 }
			 else
			 {
				 Runtime.getRuntime().exec("clear");
			 }
		 }
		 catch (final Exception e){}
	 }
	 
	 protected static void opcion_Debug(String[] args)
	 {
		 if(args.length > 1)
		 {
			switch(args[1].toLowerCase())
			{
				case "on":
					Main.modo_debug = true;
					System.out.println("Modo debug activado");
				break;
				
				case "off":
					Main.modo_debug = false;
					System.out.println("Modo debug desactivado");
				break;
				
				default:
					System.out.println("opción no valida");
				break;
			}
		 }
		 else
		 {
			 System.out.println("Numero de argumentos no valido");
			 return;
		 }
	 }
}
