package objetos.pelea.turnos;

import java.util.Timer;
import java.util.TimerTask;

import objetos.pelea.Pelea;
import objetos.pelea.Peleador;

public class Turno
{
	private final Pelea pelea;
    private final Peleador luchador;

    private Timer timer;
    private TimerTask timer_task;
    
    public Turno(final Pelea _pelea, final Peleador _luchador) 
    {
        pelea = _pelea;
        luchador = _luchador;
        luchador.set_Turno(this);
    }
    
    private void schedule_turno() 
    {
        (timer = new Timer()).schedule(timer_task = new TimerTask() 
        {
            @Override
            public void run() 
            {
            	get_Finalizar_Turno(true);
            }
        }, 30000 + 750);
    }
    
    public void get_Finalizar_Turno(boolean forzar) 
    {
    	
    }
    
    @Override
    public int hashCode() 
    {
        return luchador.get_Id();
    }
}
