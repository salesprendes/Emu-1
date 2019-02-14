package objetos.mapas;

import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.primitives.Shorts;

import juego.enums.TipoDirecciones;
import objetos.entidades.Entidades;
import objetos.entidades.personajes.Personajes;
import objetos.mapas.interactivo.Interactivo;
import objetos.mapas.interactivo.InteractivoModelo;

public final class Celdas 
{
	private final short id;
	private final Mapa mapa;
	private CopyOnWriteArrayList<Entidades> entidades;
	private final byte X, Y, nivel, slope;
	private final byte movimiento;
	private final Interactivo objeto_interactivo;
	private final boolean es_linea_de_vista, esta_activa;
	
	public Celdas(final short _id, final Mapa _mapa, final boolean _esta_activa, final byte tipo_movimiento, final byte _nivel, final byte _slope, final boolean linea_de_vista, final short objeto_interactivo_id)
	{
		id = _id;
		mapa = _mapa;
		esta_activa = _esta_activa;
		movimiento = tipo_movimiento;
		nivel = _nivel;
		slope = _slope;
		es_linea_de_vista = linea_de_vista;
		
		if(objeto_interactivo_id != -1)
			objeto_interactivo = new Interactivo(objeto_interactivo_id, mapa, this);
		else
			objeto_interactivo = null;
		
		final byte ancho = mapa.get_Anchura();
		final int _loc5 = id / (ancho * 2 - 1);
		final int _loc6 = id - _loc5 * (ancho * 2 - 1);
		final int _loc7 = _loc6 % ancho;
		Y = (byte) (_loc5 - _loc7);
		X = (byte) ((id - (ancho - 1) * Y) / ancho);
	}

	public short get_Id()
	{
		return id;
	}

	public Mapas get_Mapa()
	{
		return mapa;
	}

	public byte get_X() 
	{
		return X;
	}

	public byte get_Y()
	{
		return Y;
	}
	
	public byte get_Movimiento()
	{
		return movimiento;
	}

	public boolean get_Es_Linea_De_Vista() 
	{
		return es_linea_de_vista;
	}

	public boolean get_Es_Caminable() 
	{
		return (esta_activa && movimiento > 1) || (objeto_interactivo != null ? objeto_interactivo.get_Es_Caminable() : true);
    }
	
	public byte get_Nivel()
	{
		return nivel;
	}

	public byte get_Slope()
	{
		return slope;
	}

	public Interactivo get_Objeto_interactivo()
	{
		return objeto_interactivo;
	}

	public void get_Agregar_Entidad(Entidades entidad, boolean agregar_mapa) 
	{
        if (entidades == null)
        	entidades = new CopyOnWriteArrayList<Entidades>();
        if(!entidades.contains(entidad))
        	entidades.add(entidad);
        if (agregar_mapa)
         	mapa.get_Agregar_Entidad(entidad);
    }

    public void get_Eliminar_Entidad(Entidades entidad, boolean eliminar_mapa) 
    {
        if (entidades != null) 
        {
            if(entidades.contains(entidad))
            	entidades.remove(entidad);
            if (eliminar_mapa)
        		mapa.get_Eliminar_Entidad(entidad);
            if (entidades.isEmpty()) 
            	entidad = null;
        }
    }

    public CopyOnWriteArrayList<Entidades> get_Entidades()
    {
        if (entidades == null)
            return new CopyOnWriteArrayList<Entidades>();
        return entidades;
    }
    
    /**
     * https://github.com/Emudofus/Dofus/blob/1.29/ank/battlefield/utils/Pathfinding.as#L204
     */
    public TipoDirecciones get_Direccion(Celdas celda_comparacion) 
    {
    	/** Diagonales **/
    	if(X == celda_comparacion.X)
    		return celda_comparacion.Y < Y ? TipoDirecciones.ABAJO_IZQUIERDA : TipoDirecciones.ARRIBA_DERECHA;
    	else if(Y == celda_comparacion.Y)
    		return celda_comparacion.X < X ? TipoDirecciones.ABAJO_DERECHA : TipoDirecciones.ARRIBA_IZQUIERDA;
		/** Linea recta **/
    	else if(X > celda_comparacion.X)
    		return Y > celda_comparacion.Y ? TipoDirecciones.ABAJO : TipoDirecciones.DERECHA;
    	else if(X < celda_comparacion.X)
    		return Y < celda_comparacion.Y ? TipoDirecciones.ARRIBA : TipoDirecciones.IZQUIERDA;
		else
			return null;
    }
    
    public boolean get_Verificar_Habilidad(final short accion_id)
    {
    	for (final InteractivoModelo interactivo : InteractivoModelo.get_Interactivos_Modelos_Cargados()) 
		{
			if (Shorts.contains(interactivo.get_Habilidades(), accion_id))
			{
				if (Shorts.contains(interactivo.get_Gfx(), objeto_interactivo.get_Gfx()))
				{
					if (interactivo.get_Tipo() == 1)
						return objeto_interactivo.get_Estado() == 1;
					else
						return true;
				}
				else
					return false;
			}
		}
    	return false;
    }
    
    public boolean get_Iniciar_Accion(Personajes personaje, final short accion_id, final short celda_id)
    {
    	if (objeto_interactivo == null)
    		return false;

    	switch(accion_id)
    	{
    		case 157://Zaapi
    			if (personaje.get_Alineamiento_Deshonor() >= 3) 
    			{
    				personaje.get_Cuenta().get_Juego_socket().enviar_Paquete("Im183");
    				return false;
    			}
    			personaje.get_Zaapi_Lista_Wc();
    		return true;
    		
    		default:
    			return false;
    	}
    }
    
    public void get_Finalizar_Accion(Personajes personaje, int accion_id)
    {
    	switch (accion_id) 
    	{
    		case 114:
    		case 157:
    		break;
    	}
    }
    
    public int get_Distancia(Celdas destino)
    {
        return Math.abs(X - destino.X) + Math.abs(Y - destino.Y);
    }

    public boolean equals(Celdas celda) 
    {
    	return celda != null? id == celda.id : false;
    }
}
