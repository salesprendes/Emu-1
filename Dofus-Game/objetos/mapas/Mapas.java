package objetos.mapas;

public interface Mapas
{
	short get_Id();
	void get_Enviar_Personajes_Mapa(final String paquete);
	byte get_Anchura();
	Celdas[] get_Celdas();
	default Celdas get_Celda(short id) 
	{
        return get_Celdas()[id];
    }
}
