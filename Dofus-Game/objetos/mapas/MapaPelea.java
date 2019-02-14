package objetos.mapas;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import objetos.entidades.Entidades;
import objetos.pelea.Pelea;

public class MapaPelea implements Mapas
{
	private final Mapa mapa_modelo;
	private final short mapa_id;
	private final Celdas[] celdas;
	private final List<Entidades> luchadores;
	private final Pelea pelea;
	
	public MapaPelea(final Mapa _mapa_modelo, final Celdas[] _celdas, final Pelea _pelea) 
	{
		mapa_modelo = _mapa_modelo;
        mapa_id = mapa_modelo.get_Id();
        celdas = _celdas;
        pelea = _pelea;
        luchadores = new CopyOnWriteArrayList<Entidades>();
    }
	
	public void set_Agregar_Luchador(Entidades luchador) 
	{
		luchadores.add(luchador);
    }
	
	public short get_Id()
	{
		return mapa_id;
	}
	
	public void get_Enviar_Personajes_Mapa(final String paquete)
	{
		luchadores.forEach(luchador -> luchador.get_Enviar_Paquete(paquete));
	}
	
	public byte get_Anchura()
	{
		return mapa_modelo.get_Anchura();
	}

	public Celdas[] get_Celdas()
	{
		return celdas;
	}
}
