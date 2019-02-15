package objetos.pelea.turnos;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

import objetos.pelea.Pelea;
import objetos.pelea.Peleador;

import static main.util.CollectionQuery.from;

public class TurnoLista
{
	private final List<Turno> turnos = Collections.synchronizedList(new LinkedList<Turno>());
	private Turno turno_actual;
	private byte index;
	
	public TurnoLista(final Pelea pelea) 
	{
		List<Turno> primer_equipo = Lists.reverse(from(pelea.get_Primer_equipo()).orderBy(Peleador.get_Comparador_Iniciativa()).transform(luchador -> new Turno(pelea, luchador)).computeList(new LinkedList<Turno>()));
		List<Turno> segundo_equipo = Lists.reverse(from(pelea.get_Segundo_equipo()).orderBy(Peleador.get_Comparador_Iniciativa()).transform(luchador -> new Turno(pelea, luchador)).computeList(new LinkedList<Turno>()));
	
		int tamano = primer_equipo.size() > segundo_equipo.size() ? primer_equipo.size() : segundo_equipo.size();
		
		if (primer_equipo.get(0).get_Luchador().get_Iniciativa() > segundo_equipo.get(0).get_Luchador().get_Iniciativa())
			inicializar(primer_equipo, segundo_equipo, tamano);
        else
        	inicializar(segundo_equipo, primer_equipo, tamano);
		
		turno_actual = turnos.get(0);
	}
	
	private void inicializar(final List<Turno> primero, final List<Turno> segundo, final int tamano) 
	{
        IntStream.range(0, tamano).forEach(i -> 
        {
            if (primero.size() > i)
            	turnos.add(primero.get(i));
            if (segundo.size() > i)
            	turnos.add(segundo.get(i));
        });
    }
	
	public Turno get_Siguiente_Turno() 
	{
        return turno_actual = turnos.get(get_Siguiente_Index());
    }
	
	private int get_Siguiente_Index() 
	{
        index++;
        return (index > (turnos.size() - 1)) ? (index = 0) : index;
    }
	
	public void acabar_Turno() 
	{
		turnos.forEach(turn -> 
		{
            if (turn.get_Timer() != null)
                turn.get_Timer().cancel();
            if (turn.get_Timer_task() != null)
                turn.get_Timer_task().cancel();
        });
    }
}
