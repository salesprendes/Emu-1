package juego.acciones;

import java.util.ArrayList;

import juego.enums.TipoEstadoAcciones;
import main.consola.Consola;
import objetos.entidades.personajes.Personajes;
import objetos.mapas.pathfinding.Descifrador;

public class JuegoAccion
{
	private final ArrayList<JuegoAcciones> acciones = new ArrayList<JuegoAcciones>();
    private final Personajes personaje;
    private TipoEstadoAcciones estado;
    private boolean esta_ocupado;
	private byte contador;
    
    public JuegoAccion(Personajes _personaje)
    {
    	personaje = _personaje;
    	estado = TipoEstadoAcciones.ESPERANDO;
    	esta_ocupado = false;
    }
    
    public TipoEstadoAcciones get_Estado()
    {
        if(!esta_ocupado)
            return estado;
        else
            return estado != TipoEstadoAcciones.ESPERANDO ? estado : TipoEstadoAcciones.ESPERANDO;
    }
    
    public void set_Estado(TipoEstadoAcciones _estado) 
    {
    	estado = _estado;
    }
    
    public void set_Ocupado(boolean _valor)
    {
    	esta_ocupado = _valor;
    }
    
    public JuegoAcciones get_Accion_Desde_Id(int accion_id)
    {
        for(JuegoAcciones ga : acciones)
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
    
    public synchronized void get_Crear_Accion(final short accion_id, final String args)
    {
    	JuegoAcciones juego_accion;
    	short id = get_Siguiente_Accion_Id();
    	System.out.println(id);
    	
    	switch (accion_id)
    	{
    		case 1:
    			juego_accion = new Desplazamiento(id, personaje, new Descifrador(personaje.get_Localizacion().get_Mapa()).get_Descodificado(args, personaje.get_Localizacion().get_Celda()));
            break;
            
    		case 500:
    			juego_accion = new MapaAccion(id, personaje, args);
    		break;
            
    		default:
    			 Consola.println("La accion id " + accion_id + " no existe");
    		return;
    	}
    	set_Accion(juego_accion);
    }
    
    public void set_Accion(JuegoAcciones accion)
    {
    	acciones.add(accion);
        if(acciones.size() == 1)
        	get_Iniciar_Accion(accion);
    }
    
    protected void get_Iniciar_Accion(JuegoAcciones juego_accion) 
    {
        if (!juego_accion.get_Esta_Iniciado())
        	acciones.remove(juego_accion);
    }
    
    public void get_Finalizar_Accion(int accion_id, final boolean tiene_error)
    {
    	get_Finalizar_Accion(accion_id, tiene_error, "");
    }
    
    public synchronized void get_Finalizar_Accion(int accion_id, boolean tiene_error, String args)
    {
    	JuegoAcciones juego_accion = acciones.get(accion_id);
    	
        if(juego_accion != null)
        {
            if (!tiene_error)
            {
            	juego_accion.get_Correcto(args);
            	estado = TipoEstadoAcciones.ESPERANDO;
            	
                if (acciones.size() > acciones.indexOf(juego_accion) + 1)
                	get_Iniciar_Accion(acciones.get(acciones.indexOf(juego_accion) + 1));
                
                if(acciones.contains(juego_accion))
                	acciones.remove(juego_accion);
            } 
            else
            {
            	get_Cancelar_Acciones(args);
            }
        }
    }
    
    public void get_Cancelar_Acciones(String args)
    {
        for (JuegoAcciones acciones : acciones)
        	acciones.get_Cancelar(args);
        
        acciones.clear();
        estado = TipoEstadoAcciones.ESPERANDO;
    }
    
    public byte get_Siguiente_Accion_Id()
    {
        return acciones.size() > 0 ? contador++ : contador;
    }
}
