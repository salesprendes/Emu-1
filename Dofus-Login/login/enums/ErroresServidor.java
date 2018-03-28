package login.enums;

public enum ErroresServidor 
{
	SERVIDOR_NO_EXISTENTE('r'),
	SERVIDOR_NO_DISPONIBLE('d'),
	SERVIDORES_LIBRES('f');
	
	final private char tipo_error;
	private ErroresServidor(char _tipo_error) 
	{
		tipo_error = _tipo_error;
	}

	public String toString()
	{
		return "AXE" + tipo_error;
	}
}
