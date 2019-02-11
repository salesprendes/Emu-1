package objetos.mapas.interactivo;

import java.util.concurrent.TimeUnit;

import main.util.TimerWaiter;
import objetos.mapas.Celdas;
import objetos.mapas.Mapas;

public class Interactivo
{
	private final short gfx;
	private final InteractivoModelo modelo;
	private byte estado;
	private final Mapas mapa;
	private final Celdas celda;
	private boolean es_utilizable;
	
	public Interactivo(final short _gfx, final Mapas _mapa, final Celdas _celda) 
	{
		gfx = _gfx;
		mapa = _mapa;
		celda = _celda;
		estado = 1;
		
		InteractivoModelo _modelo = InteractivoModelo.get_Modelo_Por_Gfx(gfx);
		if(_modelo != null)
			modelo = _modelo;
		else
			modelo = null;
		
		es_utilizable = true;
	}

	public InteractivoModelo get_Modelo()
	{
		return modelo;
	}

	public byte get_Estado()
	{
		return estado;
	}

	public void set_Estado(final byte _estado)
	{
		estado = _estado;
	}

	public Mapas get_Mapa()
	{
		return mapa;
	}

	public Celdas get_Celda()
	{
		return celda;
	}

	public boolean get_Es_utilizable()
	{
		return es_utilizable;
	}

	public void set_Es_utilizable(final boolean _es_utilizable)
	{
		es_utilizable = _es_utilizable;
	}
	
	public void get_Utilizar() 
	{
		es_utilizable = false;
		estado = 2;
		mapa.get_Enviar_Personajes_Mapa(get_Paquete());

		if (modelo.get_Recarga() > 0) 
		{
			TimerWaiter.agregar_Siguiente(() -> 
			{
				if (estado == 1)
					return;

				estado = 5;
				es_utilizable = true;
				mapa.get_Enviar_Personajes_Mapa(get_Paquete());
				es_utilizable = true;
			}, modelo.get_Recarga(), TimeUnit.MILLISECONDS);
		}
	}
	
	public String get_Paquete()
	{
		return "GDF|" + celda.get_Id() + ';' + estado + ";" + (es_utilizable ? 1 : 0);
    }
}
