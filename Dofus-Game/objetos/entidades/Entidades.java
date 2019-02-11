package objetos.entidades;

public interface Entidades
{
	public int get_Id();
	public TipoEntidades get_Tipo();
	public Localizacion get_Localizacion();
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
    }
}
