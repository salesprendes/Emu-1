package main.consola;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

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
		 	
		 	case "cpu":
		 		Consola.println(ver_Uso_Cpu() * 100 + "%");
		 	break;
		 	
		 	case "limpiar":
		 	case "clear":
		 		limpiar_Consola();
		 	break;
		 	
		 	case "debug":
		 		opcion_Debug(comando_args);
		 	break;
			
			default:
				Consola.println("Comando no encontrado");
			break;
		 }
	 }
	 
	 private static void ver_Datos_Memoria(String[] args)
	 {
		 if(args.length > 1 && args[1].toLowerCase().equals("limpiar"))
		 {
			 Runtime.getRuntime().gc();
		 }
		 
		 int mb = 1024*1024;
		 Runtime runtime = Runtime.getRuntime();
		 Consola.println("Memoria utilizada: " + (runtime.totalMemory() - runtime.freeMemory()) / mb + " mb");
		 Consola.println("Memoria libre: " + runtime.freeMemory() / mb + " mb");
		 Consola.println("Memoria total: " + runtime.totalMemory() / mb + " mb");
		 Consola.println("Memoria máxima: " + runtime.maxMemory() / mb + " mb");
	 }
	 
	 private static void ver_Threads_Activos()
	 {
		 Thread.getAllStackTraces().keySet().forEach(t ->
		 {
			 Consola.println(t.getName() + " es Daemon: " + t.isDaemon() + " esta activo: " + t.isAlive() + " estado: " + t.getState());
		 });
	 }
	 
	 private static void lista_Comandos()
	 {
		 Consola.println("threads: ver los threads activos");
		 Consola.println("memoria: permite ver el estado de la memoria del heap Parametro: limpia (invoca el garbage)");
		 Consola.println("limpiar/clear: limpia la consola");
		 Consola.println("debug: (on, off) permite activar/desactivar para ver los mensajes debug");
		 Consola.println("?: permite ver los comandos");
	 }
	 
	 private static Float ver_Uso_Cpu()
	 {
		 final ThreadMXBean TMB = ManagementFactory.getThreadMXBean();
		 long time = System.nanoTime(), lastNanoTime = 0, lastCpuTime = 0, cpuTime = 0;
		 final float timeDiff = time - lastNanoTime;

		 for(long id : TMB.getAllThreadIds())
		 {
			 cpuTime += TMB.getThreadCpuTime(id);
		 }

		 float cpuDiff = cpuTime - lastCpuTime;
		 if(cpuDiff < 0)
			 cpuDiff = 0;

		 lastNanoTime = time;
		 lastCpuTime = cpuTime;
		 return cpuDiff / timeDiff / Runtime.getRuntime().availableProcessors();
	 }
	 
	 private static void limpiar_Consola()
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
	 
	 private static void opcion_Debug(String[] args)
	 {
		 if(args.length > 1)
		 {
			switch(args[1].toLowerCase())
			{
				case "on":
					Main.modo_debug = true;
					Consola.println("Modo debug activado");
				break;
				
				case "off":
					Main.modo_debug = false;
					Consola.println("Modo debug desactivado");
				break;
				
				default:
					Consola.println("opción no valida");
				break;
			}
		 }
		 else
		 {
			 Consola.println("Numero de argumentos no valido");
		 }
	 }
}
