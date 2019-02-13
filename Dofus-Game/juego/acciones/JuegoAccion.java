package juego.acciones;

import java.util.ArrayDeque;
import java.util.Queue;

import juego.enums.TipoEstadoAcciones;
import main.consola.Consola;
import objetos.entidades.personajes.Personajes;
import objetos.mapas.pathfinding.Descifrador;

public class JuegoAccion
{
	final private Queue<JuegoAcciones> acciones;
	final private Personajes personaje;
    private TipoEstadoAcciones estado;
    
    public JuegoAccion(Personajes _personaje)
    {
    	acciones = new ArrayDeque<JuegoAcciones>();
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
    
    private synchronized void get_Agregar_Accion(JuegoAcciones accion) 
    {
        acciones.add(accion);
        if(acciones.size() == 1)
        {
        	if(estado != TipoEstadoAcciones.ESPERANDO)
    			personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("GA;0");
        	else if(!accion.get_Puede_Hacer_Accion())
        		acciones.remove(accion);
        }
    }
    
    private synchronized void get_Ejecutar_Siguiente_Accion(final String args)
    {
    	JuegoAcciones accion = acciones.peek();
    	boolean puede_hacer_accion = accion.get_Puede_Hacer_Accion();
    	get_Finalizar_Accion(accion.get_Tipo_Accion(), !puede_hacer_accion, args);
    }
    
    public synchronized void get_Finalizar_Accion(final short tipo_accion_id, final boolean tiene_error, final String args) 
    {
    	JuegoAcciones accion = acciones.peek();
    	
        if (accion != null && !tiene_error)
        {
            if (accion.get_Tipo_Accion() == tipo_accion_id)
            	accion.get_Accion_Correcta(args);
            else
            	accion.get_Accion_Fallida(args);
            
            acciones.remove(accion);
        	if (!acciones.isEmpty())
        		get_Ejecutar_Siguiente_Accion(args);
        }
        else
        	get_Cancelar_Acciones(args);
    }
    
    public void get_Cancelar_Acciones(String args)
    {
    	for (JuegoAcciones accion : acciones)
    		accion.get_Accion_Fallida(args);
    	
        acciones.clear();
    }
}
