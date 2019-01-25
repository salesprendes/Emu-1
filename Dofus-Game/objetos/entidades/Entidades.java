package objetos.entidades;

import juego.enums.TipoDirecciones;

public interface Entidades
{
	public int get_Id();
	public TipoEntidades get_Tipo();
	public TipoDirecciones get_Orientacion();
	public short get_Mapa_Id();
	public short get_Celda_Id();
	public String get_Paquete_Gm();
	
	public enum TipoEntidades
    {
        PERSONAJE,
        MONSTRUO_PELEA,
        MONSTRUO,
        MONSTRUO_GRUPO,
        NPC,
        MERCANTE,
        RECAUDADOR,
        MUTANTE,
        MUTANTE_JUGADOR,
        CERCADO,
        PRISMA,
		DRAGOPAVO;
		
		public int get_Id() 
		{
            return -ordinal();
        }
		
		public int get_Sprite_Id(final int entidad_id) 
		{
            return -(100 * entidad_id + ordinal());
        }
    }
}
