package objetos.entidades.monstruos;

import java.util.Collection;

import org.joda.time.Interval;

import juego.enums.TipoDirecciones;
import objetos.entidades.Entidades;
import objetos.entidades.Localizacion;
import objetos.mapas.Celdas;
import objetos.mapas.Mapa;
import objetos.mapas.Mapas;

public class GrupoMonstruos implements Entidades
{
	private final int id;
	private final Collection<MonstruoGradoModelo> monstruos;
	private long creacion = System.currentTimeMillis();
	private Localizacion posicion;
	private final long tiempo_aparicion;
	private Mapas mapa;
	private Celdas celda;
	
	public GrupoMonstruos(final int _id, Mapa mapa, final Celdas celda, final Collection<MonstruoGradoModelo> _monstruos) 
	{
		id = _id;
		posicion = new Localizacion(mapa, celda, TipoDirecciones.ABAJO_DERECHA);
		monstruos = _monstruos;
		tiempo_aparicion = 0;
	}
	
	public GrupoMonstruos(final int _id, Mapa _mapa, final Celdas _celda, final Collection<MonstruoGradoModelo> _monstruos, final long _tiempo_aparicion)
	{
		id = _id;
        mapa = _mapa;
        celda = _celda;
        monstruos = _monstruos;
        tiempo_aparicion = _tiempo_aparicion;
    }
	
	public int get_Id()
	{
		return id;
	}

	public TipoEntidades get_Tipo()
	{
		return null;
	}

	public Localizacion get_Localizacion()
	{
		return null;
	}

	public String get_Paquete_Gm()
	{
		if (!monstruos.isEmpty()) 
		{
			final StringBuilder ids = new StringBuilder(2 * monstruos.size());
			final StringBuilder gfx = new StringBuilder(3 * monstruos.size());
			final StringBuilder niveles = new StringBuilder(2 * monstruos.size());
			final StringBuilder colores = new StringBuilder(15 * monstruos.size());
			
			monstruos.forEach(monstruo -> 
			{
				ids.append(monstruo.get_Monstruo_modelo().get_Id()).append(',');;
				gfx.append(monstruo.get_Monstruo_modelo().get_Gfx()).append('^').append((100 + (2 * (monstruo.get_Grado() - 1)))).append(',');
				niveles.append(monstruo.get_Nivel()).append(',');
			});
			
			return new StringBuilder().toString();
		}
		else
			return "";
	}

	public void get_Enviar_Paquete(final String paquete){}
	
	public short get_Estrellas_Porcentaje() 
	{
        return (short) (get_Estrellas() * 20);
    }
	
	public short get_Estrellas() 
	{
        return (short) (new Interval(creacion, System.currentTimeMillis()).toDuration().getStandardMinutes() / 120);
    }
}
