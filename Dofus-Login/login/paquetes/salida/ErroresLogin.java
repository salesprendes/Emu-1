package login.paquetes.salida;

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
	CUENTA_APODO_ERROR('s'),
	CUENTA_SIN_APODO('r'),
	VERSION_INCORRECTA('v'),
	SERVIDOR_EN_MANTENIMIENTO('m'),
	SERVIDOR_COMPLETO_RECIBE_MUCHAS_CONEXIONES('w');

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
