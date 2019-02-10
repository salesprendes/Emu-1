package juego.enums;

public enum PosicionInventario
{
	NO_EQUIPADO((byte)-1),
	AMULETO((byte)0),
	ARMA((byte)1),
	ANILLO1((byte)2),
	CINTURON((byte)3),
	ANILLO2((byte)4),
	BOTAS((byte)5),
	SOMBRERO((byte)6),
	CAPA((byte)7),
	MASCOTA((byte)8),
	DOFUS1((byte)9),
	DOFUS2((byte)10),
	DOFUS3((byte)11),
	DOFUS4((byte)12),
	DOFUS5((byte)13),
	DOFUS6((byte)14),
	ESCUDO((byte)15),
	MONTURA((byte)16),
	COMPAÑERO((byte)17),
	MUTACION((byte)20),
	BOOST((byte)21),
	MALDICION((byte)22),
	BENDICION((byte)23),
	ROLEPLAY((byte)24),
	PJ_SEGUIDOR((byte)25),
	BARRA_OBJETOS_1((byte) 35),
    BARRA_OBJETOS_2((byte) 36),
    BARRA_OBJETOS_3((byte) 37),
    BARRA_OBJETOS_4((byte) 38),
    BARRA_OBJETOS_5((byte) 39),
    BARRA_OBJETOS_6((byte) 40),
    BARRA_OBJETOS_7((byte) 41),
    BARRA_OBJETOS_8((byte) 42),
    BARRA_OBJETOS_9((byte) 43),
    BARRA_OBJETOS_10((byte) 44),
    BARRA_OBJETOS_11((byte) 45),
    BARRA_OBJETOS_12((byte) 46),
    BARRA_OBJETOS_13((byte) 47),
    BARRA_OBJETOS_14((byte) 48),
    BARRA_OBJETOS_15((byte) 49),
    BARRA_OBJETOS_16((byte) 50),
    BARRA_OBJETOS_17((byte) 51),
    BARRA_OBJETOS_18((byte) 52),
    BARRA_OBJETOS_19((byte) 53),
    BARRA_OBJETOS_20((byte) 54),
    BARRA_OBJETOS_21((byte) 55),
    BARRA_OBJETOS_22((byte) 56),
    BARRA_OBJETOS_23((byte) 57);
	
	final private byte posicion;
	
	private PosicionInventario(byte _posicion) 
	{
		posicion = _posicion;
	}
	
	public byte get_Posicion() 
	{
        return posicion;
    }
}
