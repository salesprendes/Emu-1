package main.Consola;

public class Comandos 
{
	public static void ejecutar(String comando)
	{
		 String[] comando_args = comando.split(" ");
		 
		 switch(comando_args[0].toLowerCase().trim())
		 {

		 	case "?":
		 		
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
	 
	 protected static void invocar_Garbage()
	 {
		 System.gc();
	 }
}
