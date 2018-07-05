package login.paquetes.salida;

final public class ErroresLogin 
{
	final private char tipo_error;
	final static public char CUENTA_CONECTADA = 'a';
	final static public char CUENTA_BANEADA = 'b';
	final static public char CUENTA_CONECTADA_SERVIDOR_JUEGO = 'c';
	final static public char CUENTA_YA_CONECTADA = 'd';
	final static public char CUENTA_PASSWORD_INCORRECTA = 'f';
	final static public char CUENTA_KICKEADA = 'k';
	final static public char CONEXION_NO_TERMINADA = 'n';
	final static public char CUENTA_NO_VALIDA = 'p';
	final static public char CUENTA_APODO_ERROR = 's';
	final static public char CUENTA_SIN_APODO = 'r';
	final static public char VERSION_INCORRECTA = 'v';
	final static public char SERVIDOR_EN_MANTENIMIENTO = 'm';
	
	public ErroresLogin(char _tipo_error)
	{
		tipo_error = _tipo_error;
    }

	public String toString()
	{
        return "AlE" + tipo_error;
    }
}
