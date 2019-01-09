package objetos;

public interface Entidad
{
	public int get_Id();
	public int get_Tipo();
	public byte get_Orientacion();
	public short get_Mapa_Id();
	public short get_Celda_Id();
	
	public enum Tipo_Entidad
    {
        PERSONAJE (0),
        MONSTRUO (-3),
        NPC (-4),
        MERCANTE (-5),
        RECAUDADOR (-6),
        MUTANTE (-8),
        MONTURA (-9),
        PRISMA (-10);
        
        private int tipo_entidad = 0;
        
        private Tipo_Entidad(int _tipo_entidad)
        {
        	tipo_entidad = _tipo_entidad;
        }
        
        public int get_Tipo_Entidad()
        {
        	return tipo_entidad;
        }
    }
}
