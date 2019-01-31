package objetos.entidades.caracteristicas;

import juego.enums.TipoCaracteristica;

public interface Caracteristicas
{
	public int get_Caracteristica(TipoCaracteristica caracteristica);
	public void set_Caracteristica(TipoCaracteristica caracteristica, int valor);
}
