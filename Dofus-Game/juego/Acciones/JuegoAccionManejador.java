package juego.Acciones;

import java.util.ArrayList;

import main.consola.Consola;
import objetos.entidades.personajes.Personajes;

public class JuegoAccionManejador
{
	private final ArrayList<JuegoAcciones> acciones_actuales = new ArrayList<JuegoAcciones>();
    private final Personajes personaje;
    private JuegoAccionEstado estado;
    private boolean esta_ocupado;
    
    public JuegoAccionManejador(Personajes _personaje)
    {
    	personaje = _personaje;
    	estado = JuegoAccionEstado.ESPERANDO;
    	esta_ocupado = false;
    }
    
    public JuegoAccionEstado get_Estado()
    {
        if(!esta_ocupado)
            return estado;
        else
            return estado != JuegoAccionEstado.ESPERANDO ? estado : JuegoAccionEstado.ESPERANDO;
    }
    
    public void set_Estado(JuegoAccionEstado _estado) 
    {
    	estado = _estado;
    }
    
    public void set_Ocupado(boolean _valor)
    {
    	esta_ocupado = _valor;
    }
    
    public JuegoAcciones get_Accion_Desde_Id(int accion_id)
    {
        for(JuegoAcciones ga : acciones_actuales)
        {
            if(ga.get_Accion_id() == accion_id)
                return ga;
        }
        return null;
    }
    
    public void get_Crear_Accion(short accion_id)
    {
    	get_Crear_Accion(accion_id, "");
    }
    
    public synchronized void get_Crear_Accion(short accion_id, String args)
    {
    	JuegoAcciones juego_accion;
    	int id = get_Siguiente_Accion_Id();
    	
    	switch (accion_id)
    	{
    		case 1:
    			juego_accion = new Desplazamiento(id, personaje, args);
            break;
            
    		default:
    			 Consola.println("La accion id " + accion_id + " no existe");
    		return;
    	}
    	set_Accion(juego_accion);
    }
    
    public void set_Accion(JuegoAcciones juego_accion)
    {
    	acciones_actuales.add(juego_accion);
        if(acciones_actuales.size() == 1 )
        	get_Iniciar_Accion(juego_accion);
    }
    
    protected void get_Iniciar_Accion(JuegoAcciones juego_accion) 
    {
        if (!juego_accion.get_Esta_Iniciado())
        	acciones_actuales.remove(juego_accion);
    }
    
    public void endAction(int accion_id, final boolean tiene_error)
    {
    	get_Finalizar_Accion(accion_id, tiene_error, "");
    }
    
    public void get_Finalizar_Accion(int accion_id, boolean tiene_error, String args)
    {
    	JuegoAcciones juego_accion = acciones_actuales.get(accion_id);
    	
        if(juego_accion != null) 
        {
            if (tiene_error) 
            {
            	juego_accion.get_Correcto(args);
                if (acciones_actuales.size() > acciones_actuales.indexOf(juego_accion) + 1)
                	get_Iniciar_Accion(acciones_actuales.get(acciones_actuales.indexOf(juego_accion) + 1));
                
                if(acciones_actuales.contains(juego_accion))
                	acciones_actuales.remove(juego_accion);
            } 
            else 
            {
            	juego_accion.get_Fallo(args);
                get_Reset_Acciones();
            }
        }
    }
    
    public void get_Reset_Acciones()
    {
        for (JuegoAcciones actions : acciones_actuales)
        {
            actions.get_Cancelar();
        }
        acciones_actuales.clear();
        set_Estado(JuegoAccionEstado.ESPERANDO);
    }
    
    public int get_Siguiente_Accion_Id()
    {
        int contador = 0;
        for(JuegoAcciones action : acciones_actuales)
        {
            if(action.get_Id() > contador)
            	contador = action.get_Id() + 1;
        }
        return contador;
    }
}
