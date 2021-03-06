package objetos.mapas.pathfinding;

import java.util.Arrays;

import juego.enums.TipoDirecciones;
import main.Main;
import main.consola.Consola;
import main.util.Crypt;
import objetos.mapas.Celdas;
import objetos.mapas.Mapa;

public class Descifrador
{
	public static char[] direcciones_char = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
	final private Mapa mapa;

	public Descifrador(Mapa _mapa) 
	{
		mapa = _mapa;
	}

	public Celdas get_Siguiente_Celda_Desde_Direccion(Celdas celda_inicial, TipoDirecciones direccion)
	{
		short siguiente_celda_id = (short) (celda_inicial.get_Id() + direccion.get_Siguiente_Celda(mapa.get_Anchura()));

		if (siguiente_celda_id >= mapa.get_Celdas().length || siguiente_celda_id <= 0) 
		{
			Consola.println("celda en el path invalida");
			return null;
		}

		return mapa.get_Celda(siguiente_celda_id);
	}

	public PathFinding get_Descodificado(String path_codificado, Celdas celda_inicial)
	{
		if (path_codificado.length() % 3 != 0)
		{
			if(Main.modo_debug)
				Consola.println("path invalido longitud no es correcta");
			return null;
		}

		PathFinding camino = null;
		TipoDirecciones direccion = null;
		short siguiente_celda = (short) ((Crypt.get_ordinal(path_codificado.charAt(1)) & 15) << 6 | Crypt.get_ordinal(path_codificado.charAt(2)));

		if(siguiente_celda < mapa.get_Celdas().length && siguiente_celda >= 0)
			direccion = celda_inicial.get_Direccion(mapa.get_Celda(siguiente_celda));

		if(direccion != null)
		{
			camino = new PathFinding(this);
			camino.add(new Camino(celda_inicial, direccion));
		}

		for (int i = 0; i < path_codificado.length(); i += 3)
		{
			//el array esta ordenado asi que es mejor usar el binario search para mejor rendimiento
			if(Arrays.binarySearch(direcciones_char, path_codificado.charAt(i)) != -1)//si envian direccion no existente evita wpe
				direccion = TipoDirecciones.get_Direccion_Desde_Char(path_codificado.charAt(i));

			if(direccion == null)
			{
				if(Main.modo_debug)
					Consola.println("direccion nula");
				return null;
			}

			siguiente_celda = (short) ((Crypt.get_ordinal(path_codificado.charAt(i + 1)) & 15) << 6 | Crypt.get_ordinal(path_codificado.charAt(i + 2)));
			if (siguiente_celda <= 0 || mapa.get_Celdas().length <= siguiente_celda)
			{
				if(Main.modo_debug)
					Consola.println("la celda no existe en el mapa");
				return null;
			}

			if(!get_Puede_Crear_Camino(camino, camino.celda_objetivo(), mapa.get_Celda(siguiente_celda), direccion))
				return null;
		}
		return camino;
	}

	public String get_Codificado(final PathFinding path, final boolean esta_con_montura) 
	{
		boolean esta_caminando = path.size() < 6;
    	int tiempo = 0;
		StringBuilder path_codificado = new StringBuilder(path.size() * 3);//1(direccion) + 2 (celda)
		
		Celdas celda_anterior = path.get(0).get_Celda();
		path_codificado.append(path.get(0).get_Direccion().get_Direccion_Char()).append(Crypt.get_codificar_String(celda_anterior.get_Id(), 2));
		
		for (int i = 1; i < path.size(); ++i)
		{
			Camino paso = path.get(i);
			path_codificado.append(paso.get_Direccion().get_Direccion_Char());
			
			Celdas celda_siguiente = paso.get_Celda();
			TipoDirecciones direccion = celda_anterior.get_Direccion(celda_siguiente);
			tiempo += 25 / (esta_con_montura ? direccion.get_Velocidad_Montura() : esta_caminando ? direccion.get_Velocidad_Caminando() : direccion.get_Velocidad_Corriendo());
			
			if (celda_anterior.get_Nivel() < celda_siguiente.get_Nivel())
				tiempo += 100;
			else if (celda_siguiente.get_Nivel() > celda_anterior.get_Nivel())
				tiempo -= 100;
			else if (celda_anterior.get_Slope() != celda_siguiente.get_Slope())
			{
				if (celda_anterior.get_Slope() == 1)
					tiempo += 100;
				else if (celda_siguiente.get_Slope() == 1)
					tiempo -= 100;
			}
			
			while (i + 1 < path.size() && path.get(i + 1).get_Direccion() == paso.get_Direccion()) 
			{
				++i;
			}
			
			celda_anterior = celda_siguiente;
			path_codificado.append(Crypt.get_codificar_String(path.get(i).get_Celda().get_Id(), 2));
		}
		
		path.set_Tiempo_Recorrido_Sin_Pelea(tiempo);
		return path_codificado.toString();
	}

	private boolean get_Puede_Crear_Camino(PathFinding path, Celdas siguiente_celda, Celdas celda_destino, TipoDirecciones direccion)
	{
		int limite_pasos =  ((2 * mapa.get_Anchura()) + 1);//para que no salga del limite del mapa

		while (siguiente_celda != null && !siguiente_celda.equals(celda_destino))
		{
			siguiente_celda = get_Siguiente_Celda_Desde_Direccion(siguiente_celda, direccion);
			
			if (--limite_pasos < 0 || siguiente_celda == null) 
			{
				if(Main.modo_debug)
					Consola.println("path invalido");
				return false;
			}

			path.add(new Camino(siguiente_celda, direccion));
		}
		return true;
	}
}
