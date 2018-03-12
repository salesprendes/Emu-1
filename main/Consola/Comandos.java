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
		 		ver_Datos_Memoria();
			break;
			
		 	case "limpiar":
		 		invocar_Garbage();
		 	break;
		 	
		 	case "threads":
		 		ver_Threads_Activos();
		 	break;
		 	
		 	case "debug":
		 		opcion_Debug(comando_args);
		 	break;
			
			default:
				System.out.println("Comando no encontrado");
			break;
		 }
	 }
	 
	 protected static void ver_Datos_Memoria()
	 {
		 int mb = 1024*1024;
		 Runtime runtime = Runtime.getRuntime();
		 System.out.println("Memoria utilizada: " + (runtime.totalMemory() - runtime.freeMemory()) / mb + " mb");
		 System.out.println("Memoria libre: " + runtime.freeMemory() / mb + " mb");
		 System.out.println("Memoria total: " + runtime.totalMemory() / mb + " mb");
		 System.out.println("Memoria máxima: " + runtime.maxMemory() / mb + " mb");
	 }
	 
	 protected static void ver_Threads_Activos()
	 {
		 Thread.getAllStackTraces().keySet().forEach((t) ->
		 {
			 System.out.println(t.getName() + " es Daemon: " + t.isDaemon() + " esta activo: " + t.isAlive());
		 });
	 }
	 
	 protected static void lista_Comandos()
	 {
		 System.out.println("threads: ver los threads activos");
		 System.out.println("memoria: ver el estado de la memoria del heap");
		 System.out.println("limpiar: invoca el garbage para limpiar la memoria");
		 System.out.println("? - ver los comandos"); 
	 }
	 
	 protected static void invocar_Garbage()
	 {
		 Runtime.getRuntime().gc();
	 }
	 
	 protected static void opcion_Debug(String[] args)
	 {
		 if(args.length > 1)
		 {
			switch(args[1].toLowerCase())
			{
				case "on":
					Main.debug = true;
					System.out.println("Modo debug activado");
				break;
				
				case "off":
					Main.debug = false;
					System.out.println("Modo debug desactivado");
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
