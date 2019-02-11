package main.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerWaiter
{
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    
    public static void get_Actualizar()
    {
        scheduler.shutdownNow();
        scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    }
    
    public static void agregar_Siguiente(Runnable run, long tiempo, TimeUnit unidad) 
    {
        scheduler.schedule(run, tiempo, unidad);
    }
    
    public static void addNext(Runnable run, long tiempo) 
    {
    	agregar_Siguiente(run, tiempo, TimeUnit.MILLISECONDS);
    }
}
