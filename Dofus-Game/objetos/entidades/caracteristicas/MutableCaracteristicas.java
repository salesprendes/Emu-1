package objetos.entidades.caracteristicas;

import juego.enums.TipoCaracteristica;

public interface MutableCaracteristicas extends Caracteristicas
{
	public void set(TipoCaracteristica caracteristica, int valor);
    public void agregar(TipoCaracteristica caracteristica, int valor);
}
