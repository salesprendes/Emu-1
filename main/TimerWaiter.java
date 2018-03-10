package main;

import java.util.Timer;
import java.util.TimerTask;

public class TimerWaiter 
{
	private final Timer timer = new Timer();

	public TimerTask tiempo(final Runnable r, final long delay, final long tiempo)
	{
		final TimerTask task = new TimerTask() 
		{ 
			public void run() 
			{ 
				r.run(); 
			}
		};
		timer.scheduleAtFixedRate(task, delay, tiempo);
		return task;
	}
}
