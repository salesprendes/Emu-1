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
	PJ_SEGUIDOR((byte)25);
	
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
