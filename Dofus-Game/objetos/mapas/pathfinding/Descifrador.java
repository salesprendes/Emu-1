package objetos.mapas.pathfinding;

import juego.enums.TipoDirecciones;
import main.consola.Consola;
import objetos.mapas.Celdas;
import objetos.mapas.Mapas;

public class Descifrador
{
	final private Mapas mapa;

	public Descifrador(Mapas _mapa) 
	{
		mapa = _mapa;
	}

	public Celdas get_Siguiente_Celda_Desde_Direccion(Celdas celda_inicial, TipoDirecciones direccion)
	{
		short siguiente_celda_id = (short) (celda_inicial.get_Id() + direccion.get_Siguiente_Celda(mapa.get_Anchura()));
		
		if (siguiente_celda_id >= mapa.get_Celdas().length) 
		{
			Consola.println("path invalido");
			return null;
		}
		
		return mapa.get_Celda(siguiente_celda_id);
	}
	
	public PathFinding get_Descodificado(String path_codificado, Celdas celda_inicial)
	{
        if (path_codificado.length() % 3 != 0) 
        {
        	Consola.println("path invalido mala anchura");
        	return null;
        }

        PathFinding camino = new PathFinding(this);

        camino.add(new Camino(celda_inicial, TipoDirecciones.ESTE));

        for (int i = 0; i < path_codificado.length(); i += 3)
        {
        	TipoDirecciones direccion = TipoDirecciones.get_Direccion_Desde_Char(path_codificado.charAt(i));
        }

        return camino;
    }
}
