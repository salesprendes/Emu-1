package objetos.mapas.pathfinding;

import juego.enums.TipoDirecciones;
import main.consola.Consola;
import main.util.Crypt;
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
			Consola.println("celda en el path invalido");
			return null;
		}
		
		return mapa.get_Celda(siguiente_celda_id);
	}
	
	public PathFinding get_Descodificado(String path_codificado, Celdas celda_inicial)
	{
        if (path_codificado.length() % 3 != 0) 
        {
        	Consola.println("path invalido, longitud menor a 3");
        	return null;
        }

        PathFinding camino = new PathFinding(this);
        short siguiente_celda = (short) ((Crypt.get_ordinal(path_codificado.charAt(1)) & 15) << 6 | Crypt.get_ordinal(path_codificado.charAt(2)));
        camino.add(new Camino(celda_inicial, celda_inicial.get_Direccion(mapa.get_Celda(siguiente_celda)).get_Ortogonal_2_Direcciones()));

        for (int i = 0; i < path_codificado.length(); i += 3)
        {
        	TipoDirecciones direccion = TipoDirecciones.get_Direccion_Desde_Char(path_codificado.charAt(i));
        	siguiente_celda = (short) ((Crypt.get_ordinal(path_codificado.charAt(i + 1)) & 15) << 6 | Crypt.get_ordinal(path_codificado.charAt(i + 2)));
        
        	if (siguiente_celda < 0 || siguiente_celda >= mapa.get_Celdas().length) 
        	{
        		Consola.println("celda no existente en el mapa");
    			return null;
            }
        	
        	get_Crear_Camino(camino, camino.celda_objetivo(), mapa.get_Celda(siguiente_celda), direccion);
        }
        return camino;
    }
	
	public String get_Codificado(PathFinding path) 
	{
        StringBuilder path_codificado = new StringBuilder(path.size() * 3);

        for (int i = 0; i < path.size(); ++i) 
        {
            Camino paso = path.get(i);
            path_codificado.append(paso.get_Direccion().get_Direccion_Char());

            while (i + 1 < path.size() && path.get(i + 1).get_Direccion() == paso.get_Direccion()) 
            {
                ++i;
            }
            
            path_codificado.append(Crypt.get_codificar_String(path.get(i).get_Celda().get_Id(), 2));
        }
        return path_codificado.toString();
    }
	
	private void get_Crear_Camino(PathFinding path, Celdas celda_inicio, Celdas celda_destino, TipoDirecciones direccion)
	{
        int limite_pasos =  2 * mapa.get_Anchura() + 1;

        while (!celda_inicio.equals(celda_destino))
        {
        	celda_inicio = get_Siguiente_Celda_Desde_Direccion(celda_inicio, direccion);
            path.add(new Camino(celda_inicio, direccion));

            if (--limite_pasos < 0) 
            {
            	Consola.println("path invalido: mala direccion");
    			return;
            }
        }
    }
}
