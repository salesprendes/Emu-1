package juego.acciones;

import java.util.ArrayDeque;
import java.util.Queue;

import juego.enums.TipoEstadoAcciones;
import main.consola.Consola;
import objetos.entidades.personajes.Personajes;
import objetos.mapas.pathfinding.Descifrador;

public class JuegoAccion
{
	final private Queue<JuegoAcciones> acciones = new ArrayDeque<JuegoAcciones>();
	private final Personajes personaje;
    private TipoEstadoAcciones estado;
    
    public JuegoAccion(Personajes _personaje)
    {
    	personaje = _personaje;
    	estado = TipoEstadoAcciones.ESPERANDO;
    }
    
    public synchronized void get_Crear_Accion(final short accion_id, final String args)
    {
    	switch (accion_id)
    	{
    		case 1:
    			get_Agregar_Accion(new Desplazamiento(personaje, new Descifrador(personaje.get_Localizacion().get_Mapa()).get_Descodificado(args, personaje.get_Localizacion().get_Celda())));
            break;
            
    		case 500:
    			get_Agregar_Accion(new MapaAccion(personaje, args));
    		break;
            
    		default:
    			 Consola.println("La accion id " + accion_id + " no existe");
    		return;
    	}
    }
    
    public TipoEstadoAcciones get_Estado()
    {
        return estado;
    }
    
    public void set_Estado(TipoEstadoAcciones _estado) 
    {
    	estado = _estado;
    }
    
	public Queue<JuegoAcciones> getAcciones()
	{
		return acciones;
	}
    
    private synchronized void get_Agregar_Accion(JuegoAcciones accion) 
    {
        acciones.add(accion);
        if(acciones.size() == 1)
        {
        	if(!accion.get_Puede_Hacer_Accion())
            	acciones.remove(accion);
        }
    }
    
    private synchronized void get_Ejecutar_Siguiente_Accion()
    {
    	while (!acciones.isEmpty()) 
    	{
    		JuegoAcciones accion = acciones.remove();
    		if(accion.get_Puede_Hacer_Accion())
    			get_Finalizar_Accion(accion, false, "");
    	}
    }
    
    public synchronized void get_Finalizar_Accion(JuegoAcciones accion, boolean tiene_error, String args) 
    {
        if (accion != null)
        {
            if (!tiene_error)
            {
            	accion.get_Accion_Correcta(args);
            	acciones.remove(accion);
            	
            	if (acciones.size() > 0)
            		get_Ejecutar_Siguiente_Accion();
            }
            else
            	get_Cancelar_Acciones(args);
        }
        estado = TipoEstadoAcciones.ESPERANDO;
    }
    
    public void get_Cancelar_Acciones(String args)
    {
    	for (JuegoAcciones acciones : acciones)
        	acciones.get_Accion_Fallida(args);
        
        acciones.clear();
    }
}
