package login.Enum;

public enum ErroresLogin 
{
	CUENTA_CONECTADA('a'),
	CUENTA_BANEADA('b'),
	CUENTA_CONECTADA_SERVIDOR_JUEGO('c'),
	CUENTA_YA_CONECTADA('d'),
	CUENTA_PASSWORD_INCORRECTA('f'),
	CUENTA_KICKEADA('k'),
	CONEXION_NO_TERMINADA('n'),
	CUENTA_NO_VALIDA('p'),
	VERSION_INCORRECTA('v');
	
	final private char tipo_error;

	private ErroresLogin(char _tipo_error) 
	{
		tipo_error = _tipo_error;
	}

	public String toString()
	{
		return "AlE" + tipo_error;
	}
}
