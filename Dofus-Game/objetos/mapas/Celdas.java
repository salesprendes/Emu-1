package objetos.mapas;

import java.util.concurrent.CopyOnWriteArrayList;

import juego.enums.TipoDirecciones;
import objetos.entidades.Entidades;

public class Celdas 
{
	private final short id, layer_objeto_2_num, layer_objeto_1_num;
	private final Mapas mapa;
	private CopyOnWriteArrayList<Entidades> entidades;
	private final byte X, Y, ground_slope, layer_ground_num, layer_ground_rot, ground_nivel, tipo_movimiento, layer_objeto_1_rot;
	private final boolean layer_objeto_1_flip, layer_objeto_2_flip, layer_objeto_2_interactivo, esta_activa, es_linea_de_vista, layer_ground_flip;
	
	public Celdas(final short _id, final Mapas _mapa, final boolean _esta_activa, final boolean _es_linea_de_vista, final byte _layer_ground_num, final byte _ground_slope, final short _layer_objeto_1_num, final short _layer_objeto_2_num, final byte _layer_ground_rot, final byte _ground_nivel, final byte _tipo_movimiento, final boolean _layer_ground_flip, final byte _layer_objeto_1_rot, final boolean _layer_objeto_1_flip, final boolean _layer_objeto_2_flip, final boolean _layer_objeto_2_interactivo)
	{
		id = _id;
		mapa = _mapa;
		es_linea_de_vista = _es_linea_de_vista;
		esta_activa = _esta_activa;
		layer_ground_num = _layer_ground_num;
		ground_slope = _ground_slope;
		layer_objeto_1_num = _layer_objeto_1_num;
		layer_objeto_2_num = _layer_objeto_2_num;
		layer_ground_rot = _layer_ground_rot;
		ground_nivel = _ground_nivel;
		tipo_movimiento = _tipo_movimiento;
		layer_ground_flip = _layer_ground_flip;
		layer_objeto_1_rot = _layer_objeto_1_rot;
		layer_objeto_1_flip = _layer_objeto_1_flip;
		layer_objeto_2_flip = _layer_objeto_2_flip;
		layer_objeto_2_interactivo = _layer_objeto_2_interactivo;
		
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

	public byte get_Ground_Slope() 
	{
		return ground_slope;
	}

	public byte get_Layer_Ground_Num() 
	{
		return layer_ground_num;
	}
	
	public short get_Layer_Objeto_1_Num() 
	{
		return layer_objeto_1_num;
	}

	public short get_layer_Objeto_2_Num() 
	{
		return layer_objeto_2_num;
	}
	
	public byte get_Layer_Ground_Rot() 
	{
		return layer_ground_rot;
	}

	public byte get_Ground_nivel() 
	{
		return ground_nivel;
	}

	/**
     * - 0 significa que no se puede caminar
     * - 1 significa caminable, pero no en un camino
     * - 2 a 5 significa diferentes niveles de celdas caminables. Más grande es el movimiento, más bajo es el peso en el path.
     */
	public byte get_Tipo_Movimiento() 
	{
		return tipo_movimiento;
	}

	public boolean get_Es_Layer_Ground_Flip() 
	{
		return layer_ground_flip;
	}

	public boolean get_Esta_Activa() 
	{
		return esta_activa;
	}

	public boolean get_Es_Linea_De_Vista() 
	{
		return es_linea_de_vista;
	}

	public byte get_Layer_objeto_1_Rot() 
	{
		return layer_objeto_1_rot;
	}

	public boolean get_Es_Layer_objeto_1_Flip() 
	{
		return layer_objeto_1_flip;
	}

	public boolean get_Es_Layer_objeto_2_Flip() 
	{
		return layer_objeto_2_flip;
	}

	public boolean get_Es_Layer_objeto_2_Interactivo() 
	{
		return layer_objeto_2_interactivo;
	}
	
	public boolean get_Es_Caminable() 
	{
        return esta_activa && tipo_movimiento > 1;
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
    
    public int get_Distancia(Celdas destino)
    {
        return Math.abs(X - destino.X) + Math.abs(Y - destino.Y);
    }

    public boolean equals(Celdas celda) 
    {
    	return celda != null? id == celda.id : false;
    }
}
