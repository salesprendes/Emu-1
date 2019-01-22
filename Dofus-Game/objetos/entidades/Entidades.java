package objetos.entidades;

import juego.enums.TipoDirecciones;

public interface Entidades
{
	public int get_Id();
	public byte get_Tipo();
	public TipoDirecciones get_Orientacion();
	public short get_Mapa_Id();
	public short get_Celda_Id();
	
	public enum TipoEntidades
    {
        PERSONAJE ((byte)0),
        MONSTRUO ((byte)-3),
        NPC ((byte)-4),
        MERCANTE ((byte)-5),
        RECAUDADOR ((byte)-6),
        MUTANTE ((byte)-8),
        CERCADO ((byte)-9),
        PRISMA ((byte)-10),
		DRAGOPAVO ((byte)-11);
        
        private byte tipo_entidad = 0;
        
        private TipoEntidades(byte _tipo_entidad)
        {
        	tipo_entidad = _tipo_entidad;
        }
        
        public byte get_Tipo_Entidad()
        {
        	return tipo_entidad;
        }
    }
}
